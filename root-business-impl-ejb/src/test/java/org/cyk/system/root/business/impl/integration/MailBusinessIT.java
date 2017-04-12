package org.cyk.system.root.business.impl.integration;

import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.geography.ElectronicMailBusiness;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.api.message.MessageSendingBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.data.Data;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.event.NotificationTemplateDao;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeDao;
import org.cyk.utility.common.generator.RandomDataProvider;
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
    	//inject(MailBusiness.class).setProperties("smtp.gmail.com", 465, "kycdev@gmail.com", "p@ssw0rd*");
    	
    	Person son = inject(PersonBusiness.class).instanciateOneRandomly("P002");
    	son.setName("Komenan");
    	son.setLastnames("Yao Christian");
    	son.getContactCollection().getElectronicMails().clear();
    	son.addContact(inject(ElectronicMailBusiness.class).instanciateOne((ContactCollection)null, "kycdev@gmail.com"));
    	
    	Person father = inject(PersonBusiness.class).addRelationship(son, RootConstant.Code.PersonRelationshipType.FAMILY_FATHER).getPerson1();
    	father.setName("Kouagni");
    	father.setLastnames("N'Dri Jean");
    	father.addContact(inject(ElectronicMailBusiness.class).instanciateOne((ContactCollection)null, "ckydevbackup@gmail.com"));
    	
    	Person mother = inject(PersonBusiness.class).addRelationship(son, RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER).getPerson1();
    	mother.setName("Tchimou");
    	mother.setLastnames("Ahou odette");
    	mother.addContact(inject(ElectronicMailBusiness.class).instanciateOne((ContactCollection)null, "ckydevbackup0@gmail.com"));
    	
    	create(son);
    	
    	java.io.File directory = new java.io.File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\pdf");
    	File file = null;
    	try {
    		file = inject(FileBusiness.class).process(IOUtils.toByteArray(new FileInputStream(new java.io.File(directory, "1.pdf"))), "carte d'identité de komenan yao christian.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	file.setRepresentationType(inject(FileRepresentationTypeDao.class).read(RootConstant.Code.FileRepresentationType.IDENTITY_DOCUMENT));
		create(file);
		create(new FileIdentifiableGlobalIdentifier(file,son));
		
		try {
    		file = inject(FileBusiness.class).process(IOUtils.toByteArray(new FileInputStream(new java.io.File(directory, "2.pdf"))), "image identité de komenan yao christian.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	file.setRepresentationType(inject(FileRepresentationTypeDao.class).read(RootConstant.Code.FileRepresentationType.IDENTITY_IMAGE));
		create(file);
		create(new FileIdentifiableGlobalIdentifier(file,son));
		
		try {
    		file = inject(FileBusiness.class).process(RandomDataProvider.getInstance().getMale().photo().getBytes(), "image identité de papa.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	file.setRepresentationType(inject(FileRepresentationTypeDao.class).read(RootConstant.Code.FileRepresentationType.IDENTITY_IMAGE));
		create(file);
		create(new FileIdentifiableGlobalIdentifier(file,father));
		
		try {
    		file = inject(FileBusiness.class).process(RandomDataProvider.getInstance().getFemale().photo().getBytes(), "image identité de maman.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	file.setRepresentationType(inject(FileRepresentationTypeDao.class).read(RootConstant.Code.FileRepresentationType.IDENTITY_IMAGE));
		create(file);
		create(new FileIdentifiableGlobalIdentifier(file,mother));
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
        
        NotificationTemplate nt = notificationTemplateDao.readByGlobalIdentifierCode(RootConstant.Code.NotificationTemplate.ALARM_USER_INTERFACE);
        nt.getTitleParametersMap().put("title", "Drogba didier");
        nt.getMessageParametersMap().put("body", "The Big Manager");
        
        Notification notification = new Notification();
        notificationBusiness.fill(notification,nt);
        
        //System.setProperty("socksProxyHost", "10.100.100.151");
        //System.setProperty("socksProxyPort", "3128");
        //System.out.println(System.getProperty("http.proxyHost"));
        //System.out.println(System.getProperty("http.proxyPort"));
        MessageSendingBusiness.SendArguments.BLOCKING=Boolean.TRUE;
        //inject(MailBusiness.class).send(notification, "kycdev@gmail.com");
    }

	@Override
	protected void businesses() {
		
	}
	
	//@Test
    public void sendMailToFather(){
		Person son = inject(PersonBusiness.class).find("P002");
    	MessageSendingBusiness.SendArguments.BLOCKING=Boolean.TRUE;
		inject(MailBusiness.class).send(Notification.Builder.buildMail(son, RootConstant.Code.FileRepresentationType.IDENTITY_DOCUMENT
				,Arrays.asList(RootConstant.Code.PersonRelationshipType.FAMILY_FATHER),Boolean.FALSE));
		/*
		inject(MailBusiness.class).send(new Notification.Builder().setRemoteEndPoint(RemoteEndPoint.MAIL_SERVER)
				.addIdentifiables(son)
				.addFileRepresentationTypeCodes(FileRepresentationType.IDENTITY_IMAGE)
				.build());
		
		inject(MailBusiness.class).send(new Notification.Builder().setRemoteEndPoint(RemoteEndPoint.MAIL_SERVER)
				.addIdentifiables(son)
				.addFileRepresentationTypeCodes(FileRepresentationType.IDENTITY_DOCUMENT,FileRepresentationType.IDENTITY_IMAGE)
				.build());
				*/
    }
	
	//@Test
    public void sendMailToMother(){
		Person son = inject(PersonBusiness.class).find("P002");
    	MessageSendingBusiness.SendArguments.BLOCKING=Boolean.TRUE;
    	inject(MailBusiness.class).send(Notification.Builder.buildMail(son, RootConstant.Code.FileRepresentationType.IDENTITY_IMAGE,Arrays.asList(RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER
    			),Boolean.FALSE));
    }
	
	//@Test
    public void sendMultipleMails(){
		Person son = inject(PersonBusiness.class).find("P002");
		Person father = inject(PersonRelationshipDao.class).readByPerson2ByType(son, inject(PersonRelationshipTypeDao.class).read(RootConstant.Code.PersonRelationshipType.FAMILY_FATHER))
				.iterator().next().getPerson1();
		Person mother = inject(PersonRelationshipDao.class).readByPerson2ByType(son, inject(PersonRelationshipTypeDao.class).read(RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER))
				.iterator().next().getPerson1();
    	MessageSendingBusiness.SendArguments.BLOCKING=Boolean.TRUE;
    	inject(MailBusiness.class).send(Notification.Builder.buildMails(Arrays.asList(father,mother,son), RootConstant.Code.FileRepresentationType.IDENTITY_IMAGE
    			,Arrays.asList(RootConstant.Code.PersonRelationshipType.FAMILY_FATHER,RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER
    			),Boolean.TRUE));
    }
	
	@Test
    public void sendMultipleMailsUsingSingleConnection(){
		Collection<Notification> notifications = new ArrayList<>();
		notifications.add(new Notification.Builder().setRemoteEndPoint(RemoteEndPoint.MAIL_SERVER).setTitle("T1").setMessage("M1")
				.addReceiverIdentifiers("kycdev@gmail.com").build());
		notifications.add(new Notification.Builder().setRemoteEndPoint(RemoteEndPoint.MAIL_SERVER).setTitle("T2").setMessage("M2")
				.addReceiverIdentifiers("kycdev@gmail.com").build());
		notifications.add(new Notification.Builder().setRemoteEndPoint(RemoteEndPoint.MAIL_SERVER).setTitle("T3").setMessage("M3")
				.addReceiverIdentifiers("kycdev@gmail.com").build());
    	MessageSendingBusiness.SendArguments.BLOCKING=Boolean.TRUE;
    	inject(MailBusiness.class).send(notifications);
    }

}
