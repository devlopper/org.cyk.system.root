package org.cyk.system.root.business.impl.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.cyk.system.root.business.api.message.MessageSendingBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessServiceImpl;
import org.cyk.system.root.model.event.Notification;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractMessageSendingBusiness<ADDRESS> extends AbstractBusinessServiceImpl implements MessageSendingBusiness<ADDRESS>,Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/
	
	@Getter @Setter
	public static abstract class AbstractSender<ADDRESS> extends AbstractBean implements Runnable, Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Notification notification;
		protected Collection<SendListener> listeners;
		
		public AbstractSender(Notification notification,SendListener listener) {
			this.notification = notification;
			addListener(listener);
		}
		
        public void run() {
        	LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Send","message");
        	logMessageBuilder.addParameters("title",notification.getTitle(),"message",notification.getMessage(),"#attachements"
        			,notification.getFiles()==null?0:notification.getFiles().size(),"from",notification.getSenderIdentifier(),"to",notification.getReceiverIdentifiers());
            try {
                __run__(logMessageBuilder);
                logMessageBuilder.addParameters("status","succeed");
                listenerUtils.execute(listeners, new ListenerUtils.VoidMethod<SendListener>() {
					@Override
					public void execute(SendListener listener) {
						listener.sent(notification);
					}
				});
            } catch (final Exception e) {
            	logMessageBuilder.addParameters("status","failed");
            	listenerUtils.execute(listeners, new ListenerUtils.VoidMethod<SendListener>() {
					@Override
					public void execute(SendListener listener) {
						listener.handleThrowable(notification,e);
					}
				});
                throw new RuntimeException(e);
            } finally {
            	logTrace(logMessageBuilder);
            }
        }
        
        protected abstract void __run__(LogMessage.Builder logMessageBuilder) throws Exception;
		
        protected abstract ADDRESS getAddress(String identifier) throws Exception;
        
        public Collection<ADDRESS> getReceiverAddresses(Notification notification) {
			Collection<ADDRESS> addresses = new LinkedList<>();
	        for(String adrress : notification.getReceiverIdentifiers())
				try {
					addresses.add(getAddress(adrress));
				} catch (Exception e) {
					e.printStackTrace();
				}
	        return addresses;
		}
        
        public void addListener(SendListener listener){
        	if(listener==null)
        		return;
        	if(listeners==null)
        		listeners = new ArrayList<>();
        	listeners.add(listener);
        }
        
        /**/
        
	}
}
