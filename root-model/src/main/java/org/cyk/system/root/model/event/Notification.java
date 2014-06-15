package org.cyk.system.root.model.event;

import java.io.Serializable;

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

	/**
	 * Title. Rich text
	 */
	private String title;
	/**
	 * Message. Rich text
	 */
	private String message;
	
	@Override
	public String toString() {
	    return title+"\r\n"+message;
	}
}
