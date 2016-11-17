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
import org.cyk.system.root.model.geography.ContactCollection;
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
		File file = new File();
		file.setName(name);
		file.setBytes(bytes);
		file.setMime(mime);
		return addFile(file); 
	}
	public Notification addFile(File file){
		addFiles(Arrays.asList(file)); 
		return this;
	}
	public Notification addFiles(Collection<File> files){
		if(files!=null){
			if(this.files==null)
				this.files = new ArrayList<>();
			this.files.addAll(files); 
		}
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
		private Date date;
		/* Files */
		private Set<String> reportTemplateCodes;
		private Collection<File> files;
		/* ContactCollections */
		private Set<String> personRelationshipCodes;
		private Set<String> partyCodes;
		private Set<ContactCollection> contactCollections;
		
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
			notification.setReceiverIdentifiers(listenerUtils.getSet(Listener.COLLECTION, new ListenerUtils.CollectionMethod.Set<Listener,String>() {
				@Override
				public Set<String> execute(Listener listener) {
					return listener.getReceiverIdentifiers(identifiables, remoteEndPoint,partyCodes,personRelationshipCodes);
				}
			}));
			
			notification.addFiles(files);
			notification.addFiles(listenerUtils.getCollection(Listener.COLLECTION, new ListenerUtils.CollectionMethod<Listener, File>() {
				@Override
				public Collection<File> execute(Listener listener) {
					return listener.getFiles(identifiables, remoteEndPoint);
				}
			}));
			
			notification.setRemoteEndPoint(remoteEndPoint);
			notification.setDate(date);
			return notification;
		}
		
		public Builder addIdentifiables(AbstractIdentifiable...identifiables){
			if(this.identifiables==null)
				this.identifiables = new ArrayList<>();
			this.identifiables.addAll(Arrays.asList(identifiables));
			return this;
		}
		
		public Builder addReportTemplateCodes(String...reportTemplateCodes){
			if(this.reportTemplateCodes==null)
				this.reportTemplateCodes = new LinkedHashSet<>();
			this.reportTemplateCodes.addAll(Arrays.asList(reportTemplateCodes));
			return this;
		}
		
		public Builder addFile(File file){
			if(files==null)
				files = new ArrayList<>();
			files.add(file); 
			return this;
		}
		
		public Builder setRemoteEndPoint(RemoteEndPoint remoteEndPoint){
			this.remoteEndPoint = remoteEndPoint;
			return this;
		}
		
		public Builder addPersonRelationshipCodes(String...codes){
			if(this.personRelationshipCodes==null)
				this.personRelationshipCodes = new LinkedHashSet<>();
			this.personRelationshipCodes.addAll(Arrays.asList(codes));
			return this;
		}
		
		public Builder addPartyCodes(String...codes){
			if(this.partyCodes==null)
				this.partyCodes = new LinkedHashSet<>();
			this.partyCodes.addAll(Arrays.asList(codes));
			return this;
		}
		
		public Builder addContactCollections(ContactCollection...contactCollections){
			if(this.contactCollections==null)
				this.contactCollections = new LinkedHashSet<>();
			this.contactCollections.addAll(Arrays.asList(contactCollections));
			return this;
		}
		
		/**/
		
		public static interface Listener {
			
			public Collection<Listener> COLLECTION = new ArrayList<>();
			
			String getTitle(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint);
			String getMessage(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint);
			Set<String> getReceiverIdentifiers(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint,Collection<String> partyCodes,Collection<String> personRelationshipCodes);
			Set<File> getFiles(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint);
			Set<ContactCollection> getContactCollections(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint);
			Set<String> getReportTemplateCodes(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint);
			
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
				public Set<String> getReceiverIdentifiers(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint,Collection<String> partyCodes,Collection<String> personRelationshipCodes) {
					return null;
				}
				
				@Override
				public Set<File> getFiles(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint) {
					return null;
				}
				
				@Override
				public Set<ContactCollection> getContactCollections(Collection<AbstractIdentifiable> identifiables, RemoteEndPoint remoteEndPoint) {
					return null;
				}
				
				@Override
				public Set<String> getReportTemplateCodes(Collection<AbstractIdentifiable> identifiables, RemoteEndPoint remoteEndPoint) {
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
	
	/**/
	
}
