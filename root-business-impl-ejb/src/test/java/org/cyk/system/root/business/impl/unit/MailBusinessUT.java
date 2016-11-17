package org.cyk.system.root.business.impl.unit;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.message.MessageSendingBusiness;
import org.cyk.system.root.business.impl.file.FileBusinessImpl;
import org.cyk.system.root.business.impl.message.MailBusinessImpl;
import org.cyk.system.root.model.event.Notification;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class MailBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private MailBusinessImpl mailBusiness;
	@InjectMocks private FileBusinessImpl fileBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(mailBusiness);
		collection.add(fileBusiness);
		mailBusiness.setProperties("smtp.gmail.com", 465, "kycdev@gmail.com", "p@ssw0rd*");
	}
	
	@Test
	public void sendSameMessageToOneBlocking() {
		MessageSendingBusiness.SendOptions.BLOCKING=Boolean.TRUE;
		Notification notification = new Notification();
		notification.setTitle("A message to one");
		notification.setMessage("TestMessage");
		mailBusiness.send(notification, new String[]{"kycdev@gmail.com"});
	}
	
	@Test
	public void sendSameMessageToManyBlockingWithUnknownId() {
		MessageSendingBusiness.SendOptions.BLOCKING=Boolean.TRUE;
		Notification notification = new Notification();
		notification.setTitle("A message to many with some undelivered");
		notification.setMessage("TestMessage");
		mailBusiness.send(notification, new String[]{"kycdev@gmail.com","kycdev1@gmail.com","kycdev2@gmail.com"});
	}
	
	@Test
	public void sendDifferentMessageToManyBlocking() {
		MessageSendingBusiness.SendOptions.BLOCKING=Boolean.TRUE;
		Collection<Notification> notifications = new ArrayList<>();
		
		Notification notification = new Notification();
		notification.setTitle("A message title to receiver 1");
		notification.setMessage("Message to reveiver 1");
		notification.addReceiverIdentifiers("kycdev@gmail.com");
		notifications.add(notification);
		
		notification = new Notification();
		notification.setTitle("A message title to receiver 2");
		notification.setMessage("Message to reveiver 2");
		notification.addReceiverIdentifiers("kycdev@gmail.com");
		notifications.add(notification);
		
		notification = new Notification();
		notification.setTitle("A message title to receiver 3");
		notification.setMessage("Message to reveiver 3");
		notification.addReceiverIdentifiers("kycdev@gmail.com");
		notifications.add(notification);
		
		mailBusiness.send(notifications);
	}
	
	@Test
	public void sendSameMessageToOneBlockingWithAttachements() {
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\pdf");
		MessageSendingBusiness.SendOptions.BLOCKING=Boolean.TRUE;
		Notification notification = new Notification();
		notification.setTitle("A message with attachements");
		notification.setMessage("TestMessage");
		try {
			notification.addFile("bulletin de bryan",IOUtils.toByteArray(new FileInputStream(new File(directory, "1.pdf"))), "application/pdf");
			notification.addFile("bulletin de manou",IOUtils.toByteArray(new FileInputStream(new File(directory, "2.pdf"))), "application/pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mailBusiness.send(notification, new String[]{"kycdev@gmail.com"});
	}
	
	@Test
	public void sendDifferentMessageToManyBlockingWithAttachements() {
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\pdf");
		MessageSendingBusiness.SendOptions.BLOCKING=Boolean.TRUE;
		Collection<Notification> notifications = new ArrayList<>();
		
		Notification notification = new Notification();
		notification.setTitle("Hi Parent of bryan");
		notification.setMessage("Those are your files");
		notification.addReceiverIdentifiers("kycdev@gmail.com");
		try {
			notification.addFile("bulletin de bryan",IOUtils.toByteArray(new FileInputStream(new File(directory, "1.pdf"))), "application/pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		notifications.add(notification);
		
		notification = new Notification();
		notification.setTitle("Hi parent of manou");
		notification.setMessage("Great to deliver to you yours files");
		notification.addReceiverIdentifiers("kycdev@gmail.com");
		try {
			notification.addFile("bulletin de manou",IOUtils.toByteArray(new FileInputStream(new File(directory, "2.pdf"))), "application/pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		notifications.add(notification);
		
		mailBusiness.send(notifications);
	}
	
	/*@Test
    public void sendMailToParents(){
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\pdf");
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
    	
    	org.cyk.system.root.model.file.File file = null;
    	try {
    		file = fileBusiness.process(IOUtils.toByteArray(new FileInputStream(new File(directory, "1.pdf"))), "bulletin de bryan.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	MessageSendingBusiness.SendOptions.BLOCKING=Boolean.TRUE;
		Notification notification = new Notification.Builder().setRemoteEndPoint(RemoteEndPoint.MAIL_SERVER).addIdentifiables(son).addFile(file)
				.build();
		inject(MailBusiness.class).send(notification);
    }*/

}
