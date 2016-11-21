package org.cyk.system.root.business.api.message;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.ThreadPoolExecutor;
import org.cyk.utility.common.cdi.BeanAdapter;

import lombok.Getter;
import lombok.Setter;

public interface MessageSendingBusiness<ADDRESS> extends BusinessService {
	
	void send(Notification notification,String[] theReceiverIds,SendArguments options);
	void send(Notification notification,String[] theReceiverIds);
	
	void send(Notification notification,String aReceiverId,SendArguments options);
	void send(Notification notification,String aReceiverId);
	
	void send(Notification notification,Collection<String> theReceiverIds,SendArguments options);
	void send(Notification notification,Collection<String> theReceiverIds);
	
	void send(Notification notification,Party[] theReceiverIds,SendArguments options);
	void send(Notification notification,Party[] theReceiverIds);
	
	void send(Notification notification,Party aReceiverId,SendArguments options);
	void send(Notification notification,Party aReceiverId);
	
	void sendParty(Notification notification,Collection<Party> theReceiverIds,SendArguments options);
	void sendParty(Notification notification,Collection<Party> theReceiverIds);

	void send(Notification notification,SendArguments options);
	void send(Notification notification);
	
	void send(Collection<Notification> notifications,SendListener listener,SendArguments arguments);
	void send(Collection<Notification> notifications,SendListener listener);
	void send(Collection<Notification> notifications);
	
	/**/
	
	public static interface SendListener {
    	
    	void handleThrowable(Notification notification,Throwable throwable);
    	void sent(Notification notification);
    	
    	public static class Adapter extends BeanAdapter implements SendListener,Serializable {
			private static final long serialVersionUID = 1L;
    		
			@Override
			public void handleThrowable(Notification notification,Throwable throwable) {}
			
			@Override
			public void sent(Notification notification) {}
			/**/
			
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}
    	}
    }
	
	@Getter @Setter
	public static class SendArguments{
		
		public static Boolean BLOCKING = Boolean.FALSE;
		
		private Boolean blocking=BLOCKING,debug=Boolean.FALSE;
		private Integer corePoolSize=1,maximumPoolSize=1;
		private Long numberOfRetry = 5l,keepAliveTime=1l,timeout=1l;
		private TimeUnit keepAliveTimeUnit = TimeUnit.SECONDS,timeoutUnit=TimeUnit.MINUTES;
		private Long numberOfMillisecondBeforeRetry = 1000l * 6;
		private ThreadPoolExecutor.Listener threadPoolExecutorListener;
	}
	
	/**/
	
	public static interface Listener<ADDRESS> {
    	
		Collection<ADDRESS> getReceiverAddresses(Notification notification);
		
    	public static class Adapter<ADDRESS> extends BeanAdapter implements Listener<ADDRESS>,Serializable {
			private static final long serialVersionUID = 1L;
    		
			@Override
			public Collection<ADDRESS> getReceiverAddresses(Notification notification) {
				return null;
			}
			
			/**/
			
			public static class Default<ADDRESS> extends Adapter<ADDRESS> implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}

			
    	}
    	
    }
}
