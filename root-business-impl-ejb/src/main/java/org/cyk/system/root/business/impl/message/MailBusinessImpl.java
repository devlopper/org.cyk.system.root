package org.cyk.system.root.business.impl.message;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessException;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessServiceImpl;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.party.Party;

public class MailBusinessImpl extends AbstractBusinessServiceImpl implements MailBusiness , Serializable {
    
	private static final long serialVersionUID = 4468167686499924200L;
	
	public static final String PROPERTY_FORMAT = "mail.smtp.%s";
	
	public static final String PROPERTY_USER = String.format(PROPERTY_FORMAT, "user");
	public static final String PROPERTY_PASSWORD = String.format(PROPERTY_FORMAT, "password");
	
	private static final Properties PROPERTIES = new Properties();
	static{
		/*
		PROPERTIES.put(String.format(PROPERTY_FORMAT, "host"), "smtp.gmail.com");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "from"), "kycdev@gmail.com");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "user"), "kycdev@gmail.com");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "password"), "p@ssw0rd*");
    	*/
    	//setProperties("smtp.gmail.com", "kycdev@gmail.com", "kycdev@gmail.com", "p@ssw0rd*");
    	
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "socketFactory.port"), "465");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "port"), "465");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "socketFactory.fallback"), "false");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "auth"), "true");
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "socketFactory.class"), "javax.net.ssl.SSLSocketFactory");
	}
	
	//@Resource(name="java:app/mail/cyk_root" /*lookup = "mail/cyk_root"*/ )
    private Session session;

    private void send(final Notification notification,final InternetAddress[] addresses,final SendOptions options) {
    	
    	session = Session.getInstance(PROPERTIES,new Authenticator() {
    		@Override
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication(PROPERTIES.getProperty(PROPERTY_USER), PROPERTIES.getProperty(PROPERTY_PASSWORD));
    		}
		});
    	
    	Thread thread = new Thread(){
            public void run() {
                MimeMessage message = new MimeMessage(session);
                try {
                    //message.setFrom(new InternetAddress(session.getProperty("mail.from")));
                	message.setFrom(new InternetAddress("kycdev@gmail.com"));
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
	public Properties getConnectionProperties() {
		return PROPERTIES;
	}

	@Override
	public void setConnectionProperties(Properties properties) {
		throw new BusinessException("Not Yet implemented");
	}

	@Override
	public void setConnectionProperties(String host, String from,String username, String password) {
		PROPERTIES.put(String.format(PROPERTY_FORMAT, "host"), host);
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "from"), from);
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "user"), username);
    	PROPERTIES.put(String.format(PROPERTY_FORMAT, "password"), password);
	}

}
