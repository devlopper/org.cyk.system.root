package org.cyk.system.root.service.impl.unit;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class MailUT extends AbstractUnitTest {

    private static final long serialVersionUID = -6691092648665798471L;

    @Override
    protected void _execute_() {
        super._execute_();
    }

    @Test
    public void send() {
    	Properties properties = new Properties();
    	String prefix = "mail.smtp";
      	properties.put(prefix+".host", "smtp.gmail.com");
  		properties.put(prefix+".from", "kycdev@gmail.com");
  		properties.put(prefix+".user", "kycdev@gmail.com");
  		
      	properties.put(prefix+".socketFactory.port", "465");
    	properties.put(prefix+".port", "465");
    	properties.put(prefix+".socketFactory.fallback", "false");
    	properties.put(prefix+".auth", "true");
    	properties.put(prefix+".password", "p@ssw0rd*");
    	properties.put(prefix+".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    	
    	Session session = Session.getInstance(properties,new Authenticator() {
    		@Override
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication("kycdev@gmail.com", "p@ssw0rd*");
    		}
		});
    	
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(session.getProperty(prefix+".from")));
        	message.setFrom(new InternetAddress("kycdev@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("kycdev@gmail.com"));
            message.setSubject("TestTitle");
            message.setSentDate(new Date());
            message.setContent("TestMessage", "text/html; charset=utf-8");
            Transport.send(message);
        } catch (Exception e) {
        	e.printStackTrace();
        }
           
        
    }

}
