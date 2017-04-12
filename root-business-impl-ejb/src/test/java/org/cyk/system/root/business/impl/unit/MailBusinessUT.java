package org.cyk.system.root.business.impl.unit;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.message.MessageSendingBusiness;
import org.cyk.system.root.business.api.message.MessageSendingBusiness.SendArguments;
import org.cyk.system.root.business.api.message.MessageSendingBusiness.SendListener;
import org.cyk.system.root.business.impl.file.FileBusinessImpl;
import org.cyk.system.root.business.impl.message.MailBusinessImpl;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.event.Notification;
import org.cyk.utility.common.ThreadPoolExecutor;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class MailBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private MailBusinessImpl mailBusiness;
	@InjectMocks private ExceptionUtils exceptionUtils;
	//@InjectMocks protected LanguageBusiness languageBusiness;
	//@InjectMocks protected NumberBusiness numberBusiness;
    //@InjectMocks protected TimeBusiness timeBusiness;
	@InjectMocks private FileBusinessImpl fileBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(mailBusiness);
		collection.add(fileBusiness);
		collection.add(exceptionUtils);
		//mailBusiness.setProperties("smtp.gmail.com", 465, "kycdev@gmail.com", "p@ssw0rd*");
	}
	
	@Test
	public void sendSameMessageToOneBlocking() {
		MessageSendingBusiness.SendArguments.BLOCKING=Boolean.TRUE;
		Notification notification = new Notification();
		notification.setTitle("A message to one");
		notification.setMessage("TestMessage");
		mailBusiness.send(notification, new String[]{"kycdev@gmail.com"});
	}
	
	@Test
	public void sendSameMessageToManyBlockingWithUnknownId() {
		MessageSendingBusiness.SendArguments.BLOCKING=Boolean.TRUE;
		Notification notification = new Notification();
		notification.setTitle("A message to many with some undelivered");
		notification.setMessage("TestMessage");
		mailBusiness.send(notification, new String[]{"kycdev@gmail.com","kycdev1@gmail.com","kycdev2@gmail.com"});
	}
	
	@Test
	public void sendDifferentMessageToMany() {
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\pdf");
		Collection<Notification> notifications = new ArrayList<>();
		
		for(int i = 0 ; i < 10 ; i++){
			Notification notification = new Notification();
			notification.setTitle("A message title to receiver "+i);
			notification.setMessage("Message to reveiver "+i);
			notification.addReceiverIdentifiers("kycdev@gmail.com");
			try {
				notification.addFile("fichier 1 de personne "+i,IOUtils.toByteArray(new FileInputStream(new File(directory, "1.pdf"))), "application/pdf");
				notification.addFile("fichier 2 de personne "+i,IOUtils.toByteArray(new FileInputStream(new File(directory, "2.pdf"))), "application/pdf");
			} catch (Exception e) {
				e.printStackTrace();
			}
			notifications.add(notification);
		}
		SendListener listener = new SendListener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			
		};
		SendArguments sendArguments = new SendArguments();
		sendArguments.setNumberOfRetry(30l);
		sendArguments.setNumberOfMillisecondBeforeRetry(1000l * 10);
		sendArguments.setCorePoolSize(10);
		sendArguments.setMaximumPoolSize(50);
		sendArguments.setThreadPoolExecutorListener(new ThreadPoolExecutor.Listener.Adapter.Default() {
			private static final long serialVersionUID = 1L;
			@Override
			public Throwable getThrowable(Throwable throwable) {
				return throwable instanceof RuntimeException ? throwable.getCause() : throwable;
			}
		});
		//sendArguments.setDebug(Boolean.TRUE);
		mailBusiness.send(notifications,listener,sendArguments);
	}
	
	@Test
	public void sendSameMessageToOneBlockingWithAttachements() {
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\pdf");
		MessageSendingBusiness.SendArguments.BLOCKING=Boolean.TRUE;
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
		MessageSendingBusiness.SendArguments.BLOCKING=Boolean.TRUE;
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
		
		mailBusiness.send(notifications,null);
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
