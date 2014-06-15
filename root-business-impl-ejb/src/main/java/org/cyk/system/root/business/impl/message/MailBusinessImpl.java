package org.cyk.system.root.business.impl.message;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.extern.java.Log;

import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.cdi.AbstractBean;

@Log
public class MailBusinessImpl extends AbstractBean implements MailBusiness , Serializable {
    
    @Resource(lookup = "mail/cyk_root")
    private Session session;

    private void send(final Notification notification,final InternetAddress[] addresses) {
        new Thread(){
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
                    log.log(Level.SEVERE,e.toString(),e);
                }
            };
        }.start();
        
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
    public void send(Notification notification, String[] theReceiverIds) {
        send(notification, addresses(theReceiverIds));
    }

    @Override
    public void send(Notification notification, String aReceiverId) {
        send(notification, new String[]{aReceiverId});
    }

    @Override
    public void send(Notification notification, Collection<String> theReceiverIds) {
        send(notification, theReceiverIds.toArray(new String[]{}));
    }

    @Override
    public void send(Notification notification, Party[] theReceiverIds) {
        
    }

    @Override
    public void send(Notification notification, Party aReceiverId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendParty(Notification notification, Collection<Party> theReceiverIds) {
        // TODO Auto-generated method stub
        
    }

}
