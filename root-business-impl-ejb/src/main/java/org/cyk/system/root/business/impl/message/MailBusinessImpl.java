package org.cyk.system.root.business.impl.message;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.inject.Inject;
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
import org.cyk.system.root.business.api.BusinessExceptionNoRollBack;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessServiceImpl;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.message.SmtpSocketFactory;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.utility.common.LogMessage;

public class MailBusinessImpl extends AbstractBusinessServiceImpl implements MailBusiness , Serializable {
    
	private static final long serialVersionUID = 4468167686499924200L;
	
	public static final String PROPERTY_FORMAT = "mail.smtp.%s";
	
	public static SmtpProperties SMTP_PROPERTIES;
	
    private Session session;

    @Inject private ApplicationBusiness applicationBusiness;
    
    private void send(final Notification notification,final InternetAddress[] addresses,final SendOptions options) {
    	exceptionUtils().exception(StringUtils.isBlank(notification.getTitle()), "notification.title.required");
    	exceptionUtils().exception(StringUtils.isBlank(notification.getMessage()) && (notification.getFiles()==null || notification.getFiles().isEmpty())
    			, "notification.messageorattachement.required");
    	Properties properties = convert(getSmtpProperties());
    	session = Session.getInstance(properties,new Authenticator() {
    		@Override
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication(getSmtpProperties().getCredentials().getUsername(), getSmtpProperties().getCredentials().getPassword());
    		}
		});
    	
    	Thread thread = new Thread(){
            public void run() {
            	LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Send","mail");
            	logMessageBuilder.addParameters("title",notification.getTitle(),"message",notification.getMessage(),"#attachements",notification.getFiles()==null?0:notification.getFiles().size()
            			,"from",SMTP_PROPERTIES.getFrom(),"to",notification.getReceiverIdentifiers(),"blocking",options.getBlocking());
                MimeMessage message = new MimeMessage(session);
                try {
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
                        		exceptionUtils().exception(StringUtils.isBlank(file.getMime()), "mail.file.mime.required");
                        		MimeBodyPart bodyPart = new MimeBodyPart();
                                bodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(file.getBytes()==null ? inject(FileBusiness.class).findBytes(file) 
                                		: file.getBytes(), file.getMime())));
                                bodyPart.setFileName(StringUtils.defaultString(file.getName(),RandomStringUtils.randomAlphabetic(5)));
                                multipart.addBodyPart(bodyPart);
                        	}
                        
                        if(StringUtils.isNotBlank(notification.getMessage())){
	                        BodyPart htmlBodyPart = new MimeBodyPart();
	                        htmlBodyPart.setContent(notification.getMessage() , notification.getMime());
	                        multipart.addBodyPart(htmlBodyPart);
                        }
                        
                        message.setContent(multipart);
                    }
                     
                    /*logDebug("Sending mail to {}", StringUtils.join(addresses));
                    logTrace("From {} , Recipients {} , Subject {} , Date {} , Blocking {}",message.getFrom(),message.getRecipients(Message.RecipientType.TO),
                    		message.getSubject(),message.getSentDate(),options.getBlocking());
                    logTrace("Content = {}", message.getContent());
                    */
                    Transport.send(message);
                    logMessageBuilder.addParameters("status","succeed");
                } catch (Exception e) {
                	logMessageBuilder.addParameters("status","failed");
                	throw new BusinessExceptionNoRollBack(e.toString());
                	//exceptionUtils().exception(exception);
                	//e.printStackTrace();
                    //logThrowable(e);
                } finally {
                	logTrace(logMessageBuilder);
                }
            };
        };
        if(Boolean.TRUE.equals(options.getBlocking()))
        	thread.run();
        else
        	thread.start();
        
    }
    
    private InternetAddress[] addresses(String...theAddresses){
        Collection<InternetAddress> receiverAddresses = new LinkedList<>();
        for(String adrress : theAddresses)
            try {
                receiverAddresses.add(new InternetAddress(adrress));
            } catch (AddressException e) {
                e.printStackTrace();
            }
        return receiverAddresses.toArray(new InternetAddress[]{});
    }
    
    /*
    private InternetAddress[] addressesParty(Party...parties){
        Collection<String> receiverAddresses = new LinkedList<>();
        for(Party party : parties)
            receiverAddresses.add(party.getContact().getEmail());
        return addresses(receiverAddresses);
    }*/
    
    @Override
    public void send(Notification notification, String[] theReceiverIds,SendOptions options) {
        send(notification, addresses(theReceiverIds),options);
    }
    @Override
    public void send(Notification notification, String[] theReceiverIds) {
        send(notification, addresses(theReceiverIds),new SendOptions());
    }

    @Override
    public void send(Notification notification, String aReceiverId,SendOptions options) {
        send(notification, new String[]{aReceiverId},options);
    }
    @Override
    public void send(Notification notification, String aReceiverId) {
        send(notification, new String[]{aReceiverId},new SendOptions());
    }

    @Override
    public void send(Notification notification, Collection<String> theReceiverIds,SendOptions options) {
        send(notification, theReceiverIds.toArray(new String[]{}),options);
    }
    @Override
    public void send(Notification notification, Collection<String> theReceiverIds) {
        send(notification, theReceiverIds.toArray(new String[]{}),new SendOptions());
    }
    
    @Override
    public void send(Notification notification,SendOptions options) {
        send(notification, notification.getReceiverIdentifiers().toArray(new String[]{}),options);
    }
    @Override
    public void send(Notification notification) {
        send(notification,new SendOptions());
    }
    
    @Override
    public void send(Collection<Notification> notifications,SendOptions options) {
    	for(Notification notification : notifications)
    		send(notification,options);
    }
    @Override
    public void send(Collection<Notification> notifications) {
        send(notifications,new SendOptions());
    }

    @Override
    public void send(Notification notification, Party[] theReceiverIds,SendOptions options) {
        
    }
    @Override
    public void send(Notification notification, Party[] theReceiverIds) {
        
    }

    @Override
    public void send(Notification notification, Party aReceiverId,SendOptions options) {
           
    }
    @Override
    public void send(Notification notification, Party aReceiverId) {
           
    }

    @Override
    public void sendParty(Notification notification, Collection<Party> theReceiverIds,SendOptions options) {
        
    }
    @Override
    public void sendParty(Notification notification, Collection<Party> theReceiverIds) {
        
    }
    
    /**/
    
    @Override
	public Properties convert(SmtpProperties smtpProperties) {
		Properties properties = new Properties();
		
		properties.put(String.format(PROPERTY_FORMAT, "host"), smtpProperties.getHost());
		properties.put(String.format(PROPERTY_FORMAT, "from"), smtpProperties.getFrom());
		properties.put(String.format(PROPERTY_FORMAT, "user"), smtpProperties.getCredentials().getUsername());
		properties.put(String.format(PROPERTY_FORMAT, "password"), smtpProperties.getCredentials().getPassword());
    	
		properties.put(String.format(PROPERTY_FORMAT, "socketFactory.port"), smtpProperties.getSocketFactory().getPort());
		properties.put(String.format(PROPERTY_FORMAT, "port"), smtpProperties.getPort());
		properties.put(String.format(PROPERTY_FORMAT, "socketFactory.fallback"), smtpProperties.getSocketFactory().getFallback());
		properties.put(String.format(PROPERTY_FORMAT, "auth"), smtpProperties.getAuthenticated());
		properties.put(String.format(PROPERTY_FORMAT, "socketFactory.class"), smtpProperties.getSocketFactory().getClazz());
		
		return properties;
	}
    
	@Override
	public SmtpProperties getSmtpProperties() {
		if(SMTP_PROPERTIES==null){
			SMTP_PROPERTIES = applicationBusiness==null?null:applicationBusiness.findCurrentInstance().getSmtpProperties();
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
			}
			
		}
		return SMTP_PROPERTIES;
	}
	
	@Override
	public void setProperties(String localhost,Integer port,String username,String password) {
		SmtpProperties smtpProperties = getSmtpProperties();
		smtpProperties.setHost(localhost);
		smtpProperties.setFrom(username);
		smtpProperties.getCredentials().setUsername(username);
		smtpProperties.getCredentials().setPassword(password);
		smtpProperties.setPort(port);
		smtpProperties.getSocketFactory().setPort(port);
	}
    
}
