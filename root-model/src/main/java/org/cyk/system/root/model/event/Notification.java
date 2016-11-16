package org.cyk.system.root.model.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.BeanAdapter;

/**
 * Notification
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter
public class Notification implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;

	private Date date;
	/**
	 * Title. Rich text
	 */
	private String title;
	/**
	 * Message. Rich text
	 */
	private String message;
	
	private String mime="text/html";
	
	private Collection<File> files;
	
	/**
	 * Remote end point
	 */
	private RemoteEndPoint remoteEndPoint;
	
	private Set<String> receiverIdentifiers = new LinkedHashSet<>();
	
	/**
	 * Registered user accounts to which notification has been delivered
	 */
	private Collection<UserAccount> userAccounts = new ArrayList<>();
	
	private Boolean all = Boolean.FALSE;
	
	public Notification addFile(String name, byte[] bytes,String mime){
		if(files==null)
			files = new ArrayList<>();
		File file = new File();
		file.setName(name);
		file.setBytes(bytes);
		file.setMime(mime);
		files.add(file); 
		return this;
	}
	
	public Notification addReceiverIdentifiers(String identifier,String...identifiers){
		receiverIdentifiers.add(identifier);
		if(identifiers!=null)
			receiverIdentifiers.addAll(Arrays.asList(identifiers));
		return this;
	}
	
	@Override
	public String toString() {
	    return title+"\r\n"+message;
	}
	
	/**/
	
	@Getter @Setter
	public static class Builder extends AbstractBuilder<Notification> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Collection<AbstractIdentifiable> identifiables;
		private RemoteEndPoint remoteEndPoint;
		
		public Builder() {
			super(Notification.class);
		}

		@Override
		public Notification build() {
			Notification notification = new Notification();
			notification.setTitle(listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
				@Override
				public String execute(Listener listener) {
					return listener.getTitle(identifiables, remoteEndPoint);
				}
			}));
			notification.setMessage(listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
				@Override
				public String execute(Listener listener) {
					return listener.getMessage(identifiables, remoteEndPoint);
				}
			}));
			return notification;
		}
		
		public Builder addIdentifiable(AbstractIdentifiable identifiable){
			if(identifiables==null)
				identifiables = new ArrayList<>();
			identifiables.add(identifiable);
			return this;
		}
		public Builder setRemoteEndPoint(RemoteEndPoint remoteEndPoint){
			this.remoteEndPoint = remoteEndPoint;
			return this;
		}
		
		/**/
		
		public static interface Listener {
			
			public Collection<Listener> COLLECTION = new ArrayList<>();
			
			String getTitle(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint);
			String getMessage(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint);
			Set<String> getReceiverIdentifiers(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint);
			
			public static class Adapter extends BeanAdapter implements Listener,Serializable {
				private static final long serialVersionUID = 1L;

				@Override
				public String getTitle(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint) {
					return null;
				}

				@Override
				public String getMessage(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint) {
					return null;
				}
				
				@Override
				public Set<String> getReceiverIdentifiers(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint) {
					return null;
				}
				
				/**/
				
				public static class Default extends Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					
					
				}
			}
		}
		
	}
	
	/**/
	
	public static enum RemoteEndPoint{
		USER_INTERFACE,
		MAIL_SERVER,
		PHONE,
		
		;
		
		public NotificationTemplate alarmTemplate;
		
		private RemoteEndPoint(){}
		
	}
	
}
