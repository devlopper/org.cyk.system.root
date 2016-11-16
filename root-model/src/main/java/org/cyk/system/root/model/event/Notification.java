package org.cyk.system.root.model.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.security.UserAccount;

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
	
	private Collection<Attachement> attachements;
	
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
	
	public Notification addAttachement(Attachement attachement){
		if(attachements==null)
			attachements = new ArrayList<>();
		attachements.add(attachement); 
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
	/*
	@Getter @Setter
	public class SendOptions{
		private Boolean blocking=Boolean.FALSE;
	}*/
	
	public static enum RemoteEndPoint{
		USER_INTERFACE,
		MAIL_SERVER,
		PHONE,
		
		;
		
		public NotificationTemplate alarmTemplate;
		
		private RemoteEndPoint(){}
		
	}
	
	@Getter @Setter @AllArgsConstructor
	public static class Attachement implements Serializable {
		private static final long serialVersionUID = -97798334240584174L;
		
		private String name;
		private byte[] bytes;
		private String mime;
		
	}
}
