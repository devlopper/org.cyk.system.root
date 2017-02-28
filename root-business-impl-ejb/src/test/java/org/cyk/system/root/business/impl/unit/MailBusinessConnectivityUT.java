package org.cyk.system.root.business.impl.unit;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.business.api.message.MessageSendingBusiness;
import org.cyk.system.root.business.api.message.MessageSendingBusiness.SendArguments;
import org.cyk.system.root.business.api.message.MessageSendingBusiness.SendListener;
import org.cyk.system.root.business.impl.file.FileBusinessImpl;
import org.cyk.system.root.business.impl.message.MailBusinessImpl;
import org.cyk.system.root.model.event.Notification;
import org.cyk.utility.common.ThreadPoolExecutor;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class MailBusinessConnectivityUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private MailBusinessImpl mailBusiness;
	@InjectMocks private FileBusinessImpl fileBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(mailBusiness);
		collection.add(fileBusiness);
		//mailBusiness.setProperties("smtp.gmail.com", 465, "kycdev@gmail.com", "p@ssw0rd*");
		//mailBusiness.setProperties("smtp.iesaci.com", 25, "results@iesaci.com", "school2009");
		mailBusiness.setProperties("smtp.gmail.com", 465, "soldesigdcp@gmail.com", "sigdcp1234");
	}
	
	@Test
	public void sendDifferentMessageToManyBlocking() {
		MessageSendingBusiness.SendArguments.BLOCKING=Boolean.TRUE;
		Collection<Notification> notifications = new ArrayList<>();
		
		for(int i = 0 ; i < 1 ; i++){
			Notification notification = new Notification();
			notification.setTitle("A message title to receiver "+i);
			notification.setMessage("Message to reveiver "+i);
			//notification.addReceiverIdentifiers("results@iesaci.com");
			//notification.addReceiverIdentifiers("kycdev@gmail.com");
			notification.addReceiverIdentifiers("soldesigdcp@gmail.com");
			notifications.add(notification);
		}
		SendListener listener = new SendListener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public void sent(Notification notification) {
				super.sent(notification);
				pause(1000l * 15);
			}

		};
		SendArguments sendArguments = new SendArguments();
		sendArguments.setDebug(Boolean.TRUE);
		sendArguments.setNumberOfRetry(100l);
		sendArguments.setNumberOfMillisecondBeforeRetry(1000l * 10);
		sendArguments.setThreadPoolExecutorListener(new ThreadPoolExecutor.Listener.Adapter.Default() {
			private static final long serialVersionUID = 1L;
			@Override
			public Throwable getThrowable(Throwable throwable) {
				return throwable instanceof RuntimeException ? throwable.getCause() : throwable;
			}
		});
		mailBusiness.send(notifications,listener,sendArguments);
	}
	
}
