package org.cyk.system.root.model.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
	
	
	/**
	 * Registered user accounts to which notification has been delivered
	 */
	private Collection<UserAccount> userAccounts = new ArrayList<>();
	
	private Boolean all = Boolean.FALSE;
	
	public void addAttachement(Attachement attachement){
		if(attachements==null)
			attachements = new ArrayList<>();
		attachements.add(attachement); 
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
