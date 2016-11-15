package org.cyk.system.root.business.impl.unit;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.message.MessageSendingBusiness;
import org.cyk.system.root.business.impl.message.MailBusinessImpl;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.Attachement;
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
	public void sendToOneBlocking() {
		MessageSendingBusiness.SendOptions.BLOCKING=Boolean.TRUE;
		Notification notification = new Notification();
		notification.setDate(new Date());
		notification.setTitle("This is a simple message");
		notification.setMessage("TestMessage");
		mailBusiness.send(notification, new String[]{"kycdev@gmail.com"});
	}
	
	@Test
	public void sendToOneBlockingWithUnknownId() {
		MessageSendingBusiness.SendOptions.BLOCKING=Boolean.TRUE;
		Notification notification = new Notification();
		notification.setDate(new Date());
		notification.setTitle("A message with some undelivered");
		notification.setMessage("TestMessage");
		mailBusiness.send(notification, new String[]{"kycdev@gmail.com","kycdev1@gmail.com","kycdev2@gmail.com"});
	}
	
	@Test
	public void sendToOneBlockingWithAttachements() {
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\pdf");
		MessageSendingBusiness.SendOptions.BLOCKING=Boolean.TRUE;
		Notification notification = new Notification();
		notification.setDate(new Date());
		notification.setTitle("A message with some files");
		notification.setMessage("TestMessage");
		try {
			notification.addAttachement(new Attachement("bulletin de bryan",IOUtils.toByteArray(new FileInputStream(new File(directory, "1.pdf"))), "application/pdf"));
			notification.addAttachement(new Attachement("bulletin de manou",IOUtils.toByteArray(new FileInputStream(new File(directory, "2.pdf"))), "application/pdf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mailBusiness.send(notification, new String[]{"kycdev@gmail.com"});
	}

}
