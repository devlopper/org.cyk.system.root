package org.cyk.system.root.business.impl.unit;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.message.MessageSendingBusiness;
import org.cyk.system.root.business.impl.message.MailBusinessImpl;
import org.cyk.system.root.model.event.Notification;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class MailBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private MailBusinessImpl mailBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(mailBusiness);
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

}
