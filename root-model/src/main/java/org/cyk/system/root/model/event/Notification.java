package org.cyk.system.root.model.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.BeanAdapter;

import lombok.Getter;
import lombok.Setter;

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
		private Set<String> fileRepresentationTypeCodes;
		private Collection<File> files;
		/* ContactCollections */
		private Set<String> personRelationshipTypeCodes;
		private Set<String> partyCodes;
		private Boolean areIdentifiablesReceivers = Boolean.TRUE;
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
					return listener.getReceiverIdentifiers(identifiables, remoteEndPoint,areIdentifiablesReceivers,partyCodes,personRelationshipTypeCodes);
				}
			}));
			
			notification.addFiles(files);
			notification.addFiles(listenerUtils.getCollection(Listener.COLLECTION, new ListenerUtils.CollectionMethod<Listener, File>() {
				@Override
				public Collection<File> execute(Listener listener) {
					return listener.getFiles(identifiables, remoteEndPoint,fileRepresentationTypeCodes);
				}
			}));
			
			if(StringUtils.isBlank(notification.getTitle())){
				if(notification.getFiles()!=null){
					Set<String> titles = new LinkedHashSet<>();
					for(File file : notification.getFiles())
						titles.add(file.getName());
					notification.setTitle(StringUtils.join(titles,Constant.CHARACTER_COMA.toString()));
				}
			}
			notification.setRemoteEndPoint(remoteEndPoint);
			notification.setDate(date);
			return notification;
		}
		
		public Builder addIdentifiables(Collection<AbstractIdentifiable> identifiables){
			if(this.identifiables==null)
				this.identifiables = new ArrayList<>();
			this.identifiables.addAll(identifiables);
			return this;
		}
		
		public Builder addIdentifiables(AbstractIdentifiable...identifiables){
			return addIdentifiables(Arrays.asList(identifiables));
		}
		
		public Builder addFileRepresentationTypeCodes(Collection<String> fileRepresentationTypeCodes){
			if(this.fileRepresentationTypeCodes==null)
				this.fileRepresentationTypeCodes = new LinkedHashSet<>();
			this.fileRepresentationTypeCodes.addAll(fileRepresentationTypeCodes);
			return this;
		}
		
		public Builder addFileRepresentationTypeCodes(String...fileRepresentationTypeCodes){
			return addFileRepresentationTypeCodes(Arrays.asList(fileRepresentationTypeCodes));
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
		
		public Builder setAreIdentifiablesReceivers(Boolean areIdentifiablesReceivers){
			this.areIdentifiablesReceivers = areIdentifiablesReceivers;
			return this;
		}
		
		public Builder addPersonRelationshipTypeCodes(Collection<String> personRelationshipTypeCodes){
			if(personRelationshipTypeCodes!=null){
				if(this.personRelationshipTypeCodes==null)
					this.personRelationshipTypeCodes = new LinkedHashSet<>();
				this.personRelationshipTypeCodes.addAll(personRelationshipTypeCodes);
			}
			return this;
		}
		
		public Builder addPersonRelationshipTypeCodes(String...personRelationshipTypeCodes){
			return addPersonRelationshipTypeCodes(Arrays.asList(personRelationshipTypeCodes));
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
		
		public static Notification build(RemoteEndPoint remoteEndPoint,Collection<AbstractIdentifiable> identifiables,Collection<String> fileRepresentationTypeCodes,Collection<String> personRelationshipTypeCodes,Boolean areIdentifiablesReceivers){
			return new Notification.Builder().setRemoteEndPoint(remoteEndPoint)
					.addIdentifiables(identifiables)
					.addFileRepresentationTypeCodes(fileRepresentationTypeCodes)
					.setAreIdentifiablesReceivers(areIdentifiablesReceivers)
					.addPersonRelationshipTypeCodes(personRelationshipTypeCodes)
					.build();
		}
		
		public static Notification build(RemoteEndPoint remoteEndPoint,AbstractIdentifiable identifiable,String fileRepresentationTypeCode,Collection<String> personRelationshipTypeCodes,Boolean isIdentifiableReceiver){
			return build(remoteEndPoint,Arrays.asList(identifiable), Arrays.asList(fileRepresentationTypeCode),personRelationshipTypeCodes, isIdentifiableReceiver);
		}
		
		public static Notification build(RemoteEndPoint remoteEndPoint,AbstractIdentifiable identifiable,String fileRepresentationTypeCode,Collection<String> personRelationshipTypeCodes){
			return build(remoteEndPoint,identifiable, fileRepresentationTypeCode,personRelationshipTypeCodes, Boolean.TRUE);
		}
		
		public static Notification buildMail(AbstractIdentifiable identifiable,String fileRepresentationTypeCode,Collection<String> personRelationshipTypeCodes,Boolean isIdentifiableReceiver){
			return build(RemoteEndPoint.MAIL_SERVER,identifiable, fileRepresentationTypeCode,personRelationshipTypeCodes,isIdentifiableReceiver);
		}
		
		public static Notification buildMail(AbstractIdentifiable identifiable,String fileRepresentationTypeCode,Collection<String> personRelationshipTypeCodes){
			return buildMail(identifiable, fileRepresentationTypeCode,personRelationshipTypeCodes,Boolean.TRUE);
		}
		
		public static Notification buildMail(AbstractIdentifiable identifiable,String fileRepresentationTypeCode){
			return build(RemoteEndPoint.MAIL_SERVER,identifiable, fileRepresentationTypeCode,null);
		}
		
		public static Collection<Notification> buildMails(Collection<? extends AbstractIdentifiable> identifiables,String fileRepresentationTypeCode,Collection<String> personRelationshipTypeCodes,Boolean isIdentifiableReceiver){
			Collection<Notification> notifications = new ArrayList<>();
			for(AbstractIdentifiable identifiable : identifiables)
				notifications.add(buildMail(identifiable, fileRepresentationTypeCode, personRelationshipTypeCodes, isIdentifiableReceiver));
			return notifications;
		}
		
		/**/
		
		public static interface Listener {
			
			public Collection<Listener> COLLECTION = new ArrayList<>();
			
			String getTitle(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint);
			String getMessage(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint);
			Set<String> getReceiverIdentifiers(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint,Boolean areIdentifiablesReceivers,Collection<String> partyCodes,Collection<String> personRelationshipTypeCodes);
			Set<File> getFiles(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint,Set<String> fileRepresentationTypeCodes);
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
				public Set<String> getReceiverIdentifiers(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint,Boolean areIdentifiablesReceivers,Collection<String> partyCodes,Collection<String> personRelationshipCodes) {
					return null;
				}
				
				@Override
				public Set<File> getFiles(Collection<AbstractIdentifiable> identifiables,RemoteEndPoint remoteEndPoint,Set<String> fileRepresentationTypeCodes) {
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
