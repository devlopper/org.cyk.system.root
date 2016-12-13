package org.cyk.system.root.business.impl.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.message.SmtpSocketFactory;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.ThreadPoolExecutor;

import com.sun.mail.util.MailConnectException;

import lombok.Getter;
import lombok.Setter;

public class MailBusinessImpl extends AbstractMessageSendingBusiness<InternetAddress> implements MailBusiness , Serializable {
    
	private static final long serialVersionUID = 4468167686499924200L;
	
	public static final String SMTP = "smtp";
	public static final String PROPERTY_FORMAT = "mail."+SMTP+"%s.%s";
	
	public static SmtpProperties SMTP_PROPERTIES;
	
    private Session getSession(Boolean debug) {
    	Session session = null;
    	Properties properties = convert(getSmtpProperties());
    	System.out.println("Mail session created : "+properties);
    	session = Session.getInstance(properties,new Authenticator() {
    		@Override
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication(getSmtpProperties().getCredentials().getUsername(), getSmtpProperties().getCredentials().getPassword());
    		}
		});
    	session.setDebug(debug);
    	return session;
		
    }
    
    private void send(final Session session,final Notification notification,final InternetAddress[] addresses) {
    	/*exceptionUtils().exception(StringUtils.isBlank(notification.getTitle()), "notification.title.required");
    	exceptionUtils().exception(StringUtils.isBlank(notification.getMessage()) && (notification.getFiles()==null || notification.getFiles().isEmpty())
    			, "notification.messageorattachement.required");
    	*/
    	
    	new Sender(session, notification).run();
        
    }
    
    private InternetAddress[] getAddresses(String...theAddresses){
        Collection<InternetAddress> receiverAddresses = new LinkedList<>();
        for(String adrress : theAddresses)
            try {
                receiverAddresses.add(new InternetAddress(adrress));
            } catch (AddressException e) {
                e.printStackTrace();
            }
        return receiverAddresses.toArray(new InternetAddress[]{});
    }
        
    @Override
    public void send(Notification notification, String[] theReceiverIds,SendArguments arguments) {
        Session session = getSession(arguments.getDebug());
        send(session,notification, getAddresses(theReceiverIds));
    }
    
    @Override
    public void send(Notification notification, String[] theReceiverIds) {
    	send(notification, theReceiverIds,new SendArguments());
    }

    @Override
    public void send(Notification notification, String aReceiverId,SendArguments options) {
        send(notification, new String[]{aReceiverId},options);
    }
    
    @Override
    public void send(Notification notification, String aReceiverId) {
        send(notification, new String[]{aReceiverId},new SendArguments());
    }

    @Override
    public void send(Notification notification, Collection<String> theReceiverIds,SendArguments options) {
        send(notification, theReceiverIds.toArray(new String[]{}),options);
    }
    
    @Override
    public void send(Notification notification, Collection<String> theReceiverIds) {
        send(notification, theReceiverIds.toArray(new String[]{}),new SendArguments());
    }
    
    @Override
    public void send(Notification notification,SendArguments options) {
        send(notification, notification.getReceiverIdentifiers().toArray(new String[]{}),options);
    }
    
    @Override
    public void send(Notification notification) {
        send(notification,new SendArguments());
    }
    
    @Override
    public void send(Collection<Notification> notifications,SendListener listener,SendArguments arguments) {
    	Collection<Class<?>> classes = new ArrayList<>();
		//classes.add(MessagingException.class);
		classes.add(MailConnectException.class);
    	ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(arguments.getCorePoolSize(), arguments.getMaximumPoolSize(), arguments.getKeepAliveTime()
    			, arguments.getKeepAliveTimeUnit(), notifications.size(),arguments.getTimeout(), arguments.getTimeoutUnit(),classes);
    	threadPoolExecutor.addListener(arguments.getThreadPoolExecutorListener());
    	Session session = getSession(arguments.getDebug());
        for(Notification notification : notifications)
        	threadPoolExecutor.execute(new Sender(session, notification,listener));
    		
        ThreadPoolExecutor.execute(threadPoolExecutor,arguments.getNumberOfRetry(),arguments.getNumberOfMillisecondBeforeRetry());
    }
    
    @Override
    public void send(Collection<Notification> notifications,SendListener listener) {
        send(notifications,listener,new SendArguments());
    }
    
    @Override
    public void send(Collection<Notification> notifications) {
        send(notifications,null,new SendArguments());
    }

    @Override
    public void send(Notification notification, Party[] theReceiverIds,SendArguments options) {
        
    }
    @Override
    public void send(Notification notification, Party[] theReceiverIds) {
        
    }

    @Override
    public void send(Notification notification, Party aReceiverId,SendArguments options) {
           
    }
    @Override
    public void send(Notification notification, Party aReceiverId) {
           
    }

    @Override
    public void sendParty(Notification notification, Collection<Party> theReceiverIds,SendArguments options) {
        
    }
    @Override
    public void sendParty(Notification notification, Collection<Party> theReceiverIds) {
        
    }
    
    /**/
    
    @Override
	public Properties convert(SmtpProperties smtpProperties) {
		Properties properties = new Properties();
		addProperty(properties, "host", smtpProperties.getHost());
		addProperty(properties, "from", smtpProperties.getFrom());
		addProperty(properties, "user", smtpProperties.getCredentials().getUsername());
		addProperty(properties, "password", smtpProperties.getCredentials().getPassword());
		
		addProperty(properties, "socketFactory.port", smtpProperties.getSocketFactory().getPort());
		addProperty(properties, "port", smtpProperties.getPort());
		addProperty(properties, "socketFactory.fallback", smtpProperties.getSocketFactory().getFallback());
		addProperty(properties, "auth", smtpProperties.getAuthenticated());
		addProperty(properties, "socketFactory.class", smtpProperties.getSocketFactory().getClazz());
		
		addProperty(properties, "starttls.enable", smtpProperties.getSecured());
		addProperty(properties, "ssl.enable", smtpProperties.getSecured());
		return properties;
	}
    
    private void addProperty(Properties properties,String name,Object value){
    	properties.put(String.format(PROPERTY_FORMAT, Constant.EMPTY_STRING,name), value);
    	properties.put(String.format(PROPERTY_FORMAT, "s",name), value);
    }
    
	@Override
	public SmtpProperties getSmtpProperties() {
		//SMTP_PROPERTIES = RootBusinessLayer.getInstance().getDefaultSmtpProperties();
		if(SMTP_PROPERTIES==null){
			SMTP_PROPERTIES = new SmtpProperties();
			SMTP_PROPERTIES.setHost(null);
			SMTP_PROPERTIES.setFrom(null);
			
			SMTP_PROPERTIES.setCredentials(new Credentials());
			SMTP_PROPERTIES.getCredentials().setUsername(null);
			SMTP_PROPERTIES.getCredentials().setPassword(null);
			SMTP_PROPERTIES.setPort(null);
			
			SMTP_PROPERTIES.setSocketFactory(new SmtpSocketFactory());
			SMTP_PROPERTIES.getSocketFactory().setClazz("javax.net.ssl.SSLSocketFactory");
			SMTP_PROPERTIES.getSocketFactory().setFallback(Boolean.FALSE);
			SMTP_PROPERTIES.getSocketFactory().setPort(null);
			SMTP_PROPERTIES.setAuthenticated(Boolean.TRUE);
			SMTP_PROPERTIES.setSecured(Boolean.TRUE);
		}
		return SMTP_PROPERTIES;
	}
	
	@Override
	public void setProperties(String localhost,Integer port,String username,String password,Boolean secured) {
		SmtpProperties smtpProperties = getSmtpProperties();
		smtpProperties.setHost(localhost);
		smtpProperties.setFrom(username);
		smtpProperties.getCredentials().setUsername(username);
		smtpProperties.getCredentials().setPassword(password);
		smtpProperties.setPort(port);
		smtpProperties.getSocketFactory().setPort(port);
		smtpProperties.setSecured(secured);
	}
	
	@Override
	public void setProperties(String localhost, Integer port, String username, String password) {
		setProperties(localhost, port, username, password,Boolean.TRUE);	
	}
    
	/**/
	
	@Getter @Setter
	public static class Sender extends AbstractSender<InternetAddress> implements Runnable, Serializable {
		private static final long serialVersionUID = 1L;
		
		private Session session;
		
		public Sender(Session session, Notification notification,SendListener listener) {
			super(notification,listener);
			this.session = session;
		}
		
		public Sender(Session session, Notification notification) {
			this(session, notification, null);
		}
				
		@Override
		protected void __run__(LogMessage.Builder logMessageBuilder) throws Exception{
			InternetAddress[] addresses = getReceiverAddresses(notification).toArray(new InternetAddress[]{});
			MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(SMTP_PROPERTIES.getFrom()));
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(notification.getTitle());
            message.setSentDate(notification.getDate() == null ? new Date() : notification.getDate());
            String type = notification.getMime()+"; charset=utf-8";
            if(notification.getFiles()==null){
            	message.setContent(notification.getMessage(), type);
            }else{
            	Multipart multipart = new MimeMultipart();
                if(notification.getFiles()!=null)
                	for(File file : notification.getFiles()){
                		if(StringUtils.isBlank(file.getMime())){
                			logMessageBuilder.addParameters("mime file empty , id",file.getIdentifier());
                		}else{
                			MimeBodyPart bodyPart = new MimeBodyPart();
                            bodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(file.getBytes()==null ? inject(FileBusiness.class).findBytes(file) 
                            		: file.getBytes(), file.getMime())));
                            bodyPart.setFileName(StringUtils.defaultString(file.getName(),RandomStringUtils.randomAlphabetic(5)));
                            multipart.addBodyPart(bodyPart);
                		}
                	}
                if(StringUtils.isNotBlank(notification.getMessage())){
                    BodyPart htmlBodyPart = new MimeBodyPart();
                    htmlBodyPart.setContent(notification.getMessage() , notification.getMime());
                    multipart.addBodyPart(htmlBodyPart);
                }
                
                message.setContent(multipart);
            }
            Transport.send(message);
       
            
        }		
		
		@Override
		public InternetAddress getAddress(String identifier) throws Exception{
			return new InternetAddress(identifier);
		}
		
        /**/
        
	}
}
