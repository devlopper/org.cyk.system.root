package org.cyk.system.root.business.impl.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.cyk.system.root.business.api.message.SmtpPropertiesBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.message.SmtpPropertiesDao;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.ThreadPoolExecutor;

import com.sun.mail.util.MailConnectException;

import lombok.Getter;
import lombok.Setter;

public class MailBusinessImpl extends AbstractMessageSendingBusiness<InternetAddress> implements MailBusiness , Serializable {
    
	private static final long serialVersionUID = 4468167686499924200L;
	
    private Session getSession(final Properties properties,Boolean debug) {
    	Session session = null;
    	/*final SmtpProperties smtpProperties = getSmtpProperties();
    	if(properties!=null)
    		properties = convert(smtpProperties);
    	*/
    	System.out.println("Mail session will be created with properties : "+properties);
    	session = Session.getInstance(properties,new Authenticator() {
    		@Override
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication(properties.getProperty(RootConstant.Configuration.SmtpProperties.PROPERTY_USERNAME)
    					, properties.getProperty(RootConstant.Configuration.SmtpProperties.PROPERTY_PASSWORD));
    		}
		});
    	session.setDebug(debug);
    	return session;
    }
    
    private Session getSession(final SmtpProperties smtpProperties,Boolean debug) {
    	return getSession(inject(SmtpPropertiesBusiness.class).convertToProperties(smtpProperties), debug);
    }
    
    private Session getSession(Boolean debug) {
    	return getSession(inject(SmtpPropertiesDao.class).read(RootConstant.Code.SmtpProperties.DEFAULT), debug);
    }
    
    private void send(Properties properties,final Session session,final Notification notification,final InternetAddress[] addresses) {
    	/*exceptionUtils().exception(StringUtils.isBlank(notification.getTitle()), "notification.title.required");
    	exceptionUtils().exception(StringUtils.isBlank(notification.getMessage()) && (notification.getFiles()==null || notification.getFiles().isEmpty())
    			, "notification.messageorattachement.required");
    	*/
    	
    	new Sender(properties,session, notification).run();
        
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
        Session session = getSession(arguments.getProperties(),arguments.getDebug());
        send(arguments.getProperties(),session,notification, getAddresses(theReceiverIds));
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
    			, arguments.getKeepAliveTimeUnit(), arguments.getQueueSize(),arguments.getTimeout(), arguments.getTimeoutUnit(),classes);
    	threadPoolExecutor.addListener(arguments.getThreadPoolExecutorListener());
    	Session session = getSession(arguments.getProperties(),arguments.getDebug());
        for(Notification notification : notifications)
        	threadPoolExecutor.execute(new Sender(arguments.getProperties(),session, notification,listener));
    		
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
    
    @Override
    public void ping(String[] theReceiverIds,SendArguments sendArguments) {
    	Notification notification = new Notification();
    	notification.setTitle("Ping");
    	notification.setMessage("This is a ping");
    	notification.addReceiverIdentifiers(Arrays.asList(theReceiverIds));
		send(notification,sendArguments);
    }
    
    @Override
    public void ping(SendArguments sendArguments) {
    	ping(new String[]{(String) sendArguments.getProperties().get(RootConstant.Configuration.SmtpProperties.PROPERTY_USERNAME)}, sendArguments);
    }
    
    @Override
    public void ping(SmtpProperties smtpProperties) {
    	SendArguments sendArguments = new SendArguments();
    	sendArguments.setProperties(inject(SmtpPropertiesBusiness.class).convertToProperties(smtpProperties));
    	ping(sendArguments);
    }
    
    @Override
    public void pingAll() {
    	for(SmtpProperties smtpProperties : inject(SmtpPropertiesDao.class).readAll()){
    		MailSender sender = new MailSender();
    		sender.setProperties(inject(SmtpPropertiesBusiness.class).convertToProperties(smtpProperties));
    		sender.ping();
    	}
    }
    
    @Override
    public void ping() {
    	SmtpProperties smtpProperties = inject(SmtpPropertiesDao.class).read(RootConstant.Code.SmtpProperties.DEFAULT);
    	ping(smtpProperties);
    }
    
    @Override
    public void send(org.cyk.utility.common.message.Message message,Collection<ElectronicMail> electronicMails,SmtpProperties smtpProperties) {
    	MailSender sender = new MailSender();
    	message.getReceiverIdentifiers().addAll(commonUtils.getPropertyValue(electronicMails, String.class, ElectronicMail.FIELD_ADDRESS));
    	sender.setInput(message)
    		.setProperties(inject(SmtpPropertiesBusiness.class).convertToProperties(smtpProperties))
    		.execute();
    }
    
    @Override
    public void send(org.cyk.utility.common.message.Message message,Collection<ElectronicMail> electronicMails) {
    	send(message,electronicMails, inject(SmtpPropertiesDao.class).readDefaulted());
    }
    
    /**/
    
    
    
	/*@Override
	public SmtpProperties getSmtpProperties() {
		if(SMTP_PROPERTIES==null)
			SMTP_PROPERTIES = inject(SmtpPropertiesDao.class).read(RootConstant.Code.SmtpProperties.DEFAULT); //RootBusinessLayer.getInstance().getDefaultSmtpProperties();
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
			
		}
		
		if(SMTP_PROPERTIES!=null){
			
			SMTP_PROPERTIES.setAuthenticated(Boolean.TRUE);
			SMTP_PROPERTIES.setSecured(Boolean.TRUE);
		}
		
		return SMTP_PROPERTIES;
	}*/
	
	/*@Override
	public void setProperties(String host,Integer port,String username,String password,Boolean secured) {
		SmtpProperties smtpProperties = getSmtpProperties();
		smtpProperties.setHost(host);
		smtpProperties.setFrom(username);
		smtpProperties.getCredentials().setUsername(username);
		smtpProperties.getCredentials().setPassword(password);
		smtpProperties.setPort(port);
		smtpProperties.getSocketFactory().setPort(port);
		smtpProperties.setSecured(secured);
		System.out.println("SMTP properties set. host="+host+" , port="+port+" , username="+username+" , password="+password);
	}
	
	@Override
	public void setProperties(String host, Integer port, String username, String password) {
		setProperties(host, port, username, password,Boolean.TRUE);	
	}*/
    
	/**/
	
	@Getter @Setter
	public static class Sender extends AbstractSender<InternetAddress> implements Runnable, Serializable {
		private static final long serialVersionUID = 1L;
		
		private Session session;
		private Properties properties;
		
		public Sender(Properties properties,Session session, Notification notification,SendListener listener) {
			super(notification,listener);
			this.properties = properties;
			this.session = session;
		}
		
		public Sender(Properties properties,Session session, Notification notification) {
			this(properties,session, notification, null);
		}
				
		@Override
		protected void __run__(LogMessage.Builder logMessageBuilder) throws Exception{
			InternetAddress[] addresses = getReceiverAddresses(notification).toArray(new InternetAddress[]{});
			MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty(RootConstant.Configuration.SmtpProperties.PROPERTY_FROM)));
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
            System.out.println("sending message to "+notification.getReceiverIdentifiers());
            Transport.send(message);
       
            
        }		
		
		@Override
		public InternetAddress getAddress(String identifier) throws Exception{
			return new InternetAddress(identifier);
		}
		
        /**/
        
	}
}
