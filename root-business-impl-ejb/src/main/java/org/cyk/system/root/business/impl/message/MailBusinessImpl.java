package org.cyk.system.root.business.impl.message;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessServiceImpl;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.party.Party;

public class MailBusinessImpl extends AbstractBusinessServiceImpl implements MailBusiness , Serializable {
    
	private static final long serialVersionUID = 4468167686499924200L;
	
	@Resource(lookup = "mail/cyk_root")
    private Session session;

    private void send(final Notification notification,final InternetAddress[] addresses,SendOptions options) {
    	Thread thread = new Thread(){
            public void run() {
                MimeMessage message = new MimeMessage(session);
                try {
                    message.setFrom(new InternetAddress(session.getProperty("mail.from")));
                    message.setRecipients(Message.RecipientType.TO, addresses);
                    message.setSubject(notification.getTitle());
                    message.setSentDate(new Date());
                    message.setContent(notification.getMessage(), "text/html; charset=utf-8");
                    Transport.send(message);
                } catch (Exception e) {
                    __logger__().error(e.toString(),e);
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

}
