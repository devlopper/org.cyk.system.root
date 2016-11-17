package org.cyk.system.root.business.impl.integration;

import java.io.FileInputStream;
import java.net.URISyntaxException;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.geography.ElectronicMailBusiness;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.api.message.MessageSendingBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.data.Data;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.persistence.api.event.NotificationTemplateDao;
import org.junit.Test;

public class MailBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Inject private FileBusiness fileBusiness;
    @Inject private NotificationBusiness notificationBusiness;
    @Inject private NotificationTemplateDao notificationTemplateDao;
    
    private NotificationTemplate notificationTemplate1,notificationTemplate2;
    
    @Override
    protected void populate() {
    	super.populate();
    	inject(MailBusiness.class).setProperties("smtp.gmail.com", 465, "kycdev@gmail.com", "p@ssw0rd*");
    }
    
    @Override
    protected void _execute_() {
        super._execute_();
        installApplication();
        notificationTemplate1 = new NotificationTemplate();
        notificationTemplate1.setCode("NTC1");
        notificationTemplate1.setName("NTN1");
        notificationTemplate1.setTitle(fileBusiness.process("Hello ${name}".getBytes(), "template1.txt"));
        notificationTemplate1.setMessage(fileBusiness.process("The name is : ${name}".getBytes(), "template1.txt"));
        genericBusiness.create(notificationTemplate1);
        
        notificationTemplate2 = new NotificationTemplate();
        notificationTemplate2.setCode("NTC2");
        notificationTemplate2.setName("NTN2");
        notificationTemplate2.setTitle(fileBusiness.process("Second Temp , Hello ${name}".getBytes(), "template1.txt"));
        notificationTemplate2.setMessage(new File());
        try {
            notificationTemplate2.getMessage().setUri(Data.class.getResource("template.html").toURI()); 
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        genericBusiness.create(notificationTemplate2);
        
        NotificationTemplate nt = notificationTemplateDao.readByGlobalIdentifierCode(NotificationTemplate.ALARM_USER_INTERFACE);
        nt.getTitleParametersMap().put("title", "Drogba didier");
        nt.getMessageParametersMap().put("body", "The Big Manager");
        
        Notification notification = new Notification();
        notificationBusiness.fill(notification,nt);
        
        //System.setProperty("socksProxyHost", "10.100.100.151");
        //System.setProperty("socksProxyPort", "3128");
        //System.out.println(System.getProperty("http.proxyHost"));
        //System.out.println(System.getProperty("http.proxyPort"));
        MessageSendingBusiness.SendOptions.BLOCKING=Boolean.TRUE;
        //inject(MailBusiness.class).send(notification, "kycdev@gmail.com");
    }

	@Override
	protected void businesses() {
		
	}
	
	@Test
    public void sendMailToParents(){
		java.io.File directory = new java.io.File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\pdf");
    	Person son = inject(PersonBusiness.class).instanciateOneRandomly("P002");
    	son.setName("Komenan");
    	son.setLastnames("Yao Christian");
    	son.addContact(inject(ElectronicMailBusiness.class).instanciateOne((ContactCollection)null, "kycdev@gmail.com"));
    	
    	Person father = inject(PersonBusiness.class).addRelationship(son, PersonRelationshipType.FAMILY_FATHER).getPerson1();
    	father.setName("Kouagni");
    	father.setLastnames("N'Dri Jean");
    	father.addContact(inject(ElectronicMailBusiness.class).instanciateOne((ContactCollection)null, "ckydevbackup@gmail.com"));
    	
    	Person mother = inject(PersonBusiness.class).addRelationship(son, PersonRelationshipType.FAMILY_MOTHER).getPerson1();
    	mother.setName("Tchimou");
    	mother.setLastnames("Ahou odette");
    	mother.addContact(inject(ElectronicMailBusiness.class).instanciateOne((ContactCollection)null, "ckydevbackup0@gmail.com"));
    	
    	create(son);
    	
    	File file = null;
    	try {
    		file = inject(FileBusiness.class).process(IOUtils.toByteArray(new FileInputStream(new java.io.File(directory, "1.pdf"))), "bulletin de bryan.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	MessageSendingBusiness.SendOptions.BLOCKING=Boolean.TRUE;
		Notification notification = new Notification.Builder().setRemoteEndPoint(RemoteEndPoint.MAIL_SERVER).addIdentifiables(son).addFile(file)
				.build();
		inject(MailBusiness.class).send(notification);
    }

}
