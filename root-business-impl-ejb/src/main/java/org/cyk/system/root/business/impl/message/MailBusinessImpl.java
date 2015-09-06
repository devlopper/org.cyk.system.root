package org.cyk.system.root.business.impl.message;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessServiceImpl;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.message.SmtpSocketFactory;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.security.Credentials;

public class MailBusinessImpl extends AbstractBusinessServiceImpl implements MailBusiness , Serializable {
    
	private static final long serialVersionUID = 4468167686499924200L;
	
	public static final String PROPERTY_FORMAT = "mail.smtp.%s";
	
	public static SmtpProperties SMTP_PROPERTIES;
	/*
	public static final String PROPERTY_USER = String.format(PROPERTY_FORMAT, "user");
	public static final String PROPERTY_PASSWORD = String.format(PROPERTY_FORMAT, "password");
	
	private static final Properties PROPERTIES = new Properties();
	static{
		
		PROPERTIES.put(String.format(PROPERTY_FORMAT, "host"), "smtp.gmail.com");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "from"), "kycdev@gmail.com");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "user"), "kycdev@gmail.com");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "password"), "p@ssw0rd*");
    	
    	//setProperties("smtp.gmail.com", "kycdev@gmail.com", "kycdev@gmail.com", "p@ssw0rd*");
    	
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "socketFactory.port"), "465");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "port"), "465");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "socketFactory.fallback"), "false");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "auth"), "true");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "socketFactory.class"), "javax.net.ssl.SSLSocketFactory");
	}
	*/
	//@Resource(name="java:app/mail/cyk_root" /*lookup = "mail/cyk_root"*/ )
    private Session session;

    @Inject private ApplicationBusiness applicationBusiness;
    
    private void send(final Notification notification,final InternetAddress[] addresses,final SendOptions options) {
    	Properties properties = convert(getSmtpProperties());
    	session = Session.getInstance(properties,new Authenticator() {
    		@Override
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication(getSmtpProperties().getCredentials().getUsername(), getSmtpProperties().getCredentials().getPassword());
    		}
		});
    	
    	Thread thread = new Thread(){
            public void run() {
                MimeMessage message = new MimeMessage(session);
                try {
                    message.setFrom(new InternetAddress(SMTP_PROPERTIES.getFrom()));
                    message.setRecipients(Message.RecipientType.TO, addresses);
                    message.setSubject(notification.getTitle());
                    message.setSentDate(new Date());
                    message.setContent(notification.getMessage(), "text/html; charset=utf-8");
                    logDebug("Sending mail to {}", StringUtils.join(addresses));
                    logTrace("From {} , Recipients {} , Subject {} , Date {} , Blocking {}",message.getFrom(),message.getRecipients(Message.RecipientType.TO),
                    		message.getSubject(),message.getSentDate(),options.getBlocking());
                    logTrace("Content = {}", message.getContent());
                    Transport.send(message);
                    logDebug("Mail sent to {}", StringUtils.join(addresses));
                } catch (Exception e) {
                	e.printStackTrace();
                    logThrowable(e);
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
