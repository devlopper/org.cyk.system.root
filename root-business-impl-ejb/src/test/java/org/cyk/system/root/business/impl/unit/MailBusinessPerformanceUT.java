package org.cyk.system.root.business.impl.unit;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.message.MessageSendingBusiness.SendArguments;
import org.cyk.system.root.business.api.message.MessageSendingBusiness.SendListener;
import org.cyk.system.root.business.impl.file.FileBusinessImpl;
import org.cyk.system.root.business.impl.message.MailBusinessImpl;
import org.cyk.system.root.business.impl.message.SmtpPropertiesBusinessImpl;
import org.cyk.system.root.model.event.Notification;
import org.cyk.utility.common.ThreadPoolExecutor;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class MailBusinessPerformanceUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private MailBusinessImpl mailBusiness;
	@InjectMocks private FileBusinessImpl fileBusiness;
	@InjectMocks private SmtpPropertiesBusinessImpl smtpPropertiesBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(mailBusiness);
		collection.add(fileBusiness);
		collection.add(smtpPropertiesBusiness);
	}
	
	@Test
	public void sendContentWithAttachement() {
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\pdf");
		Collection<Notification> notifications = new ArrayList<>();
		
		for(int i = 0 ; i < 100 ; i++){
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
			
			@Override
			public void sent(Notification notification) {
				super.sent(notification);
				pause(1000l * 10);
			}

		};
		SendArguments sendArguments = new SendArguments();
		sendArguments.setProperties(smtpPropertiesBusiness.convertToProperties("smtp.gmail.com", 465, "kycdev@gmail.com", "p@ssw0rd*"));
		sendArguments.setNumberOfRetry(30l);
		sendArguments.setNumberOfMillisecondBeforeRetry(1000l * 10);
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
	
}
