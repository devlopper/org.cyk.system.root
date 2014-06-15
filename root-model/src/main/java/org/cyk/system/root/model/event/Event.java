package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.ContactManager;

/**
 * Something that happens
 * @author Christian Yao Komenan
 *
 */
@Entity
@Getter @Setter
public class Event extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;

	/**
	 * Type
	 */
	@ManyToOne private EventType type;
	/**
	 * Title. Rich text
	 */
	private String title;
	/**
	 * Description. Rich text
	 */
	@Column(length=1024 * 1) private String description;
	/**
	 * Contacts
	 */
	@OneToOne(cascade=CascadeType.ALL) private ContactManager contactManager = new ContactManager();
	/**
	 * Schedule
	 */
	@OneToOne(cascade=CascadeType.ALL) private Schedule schedule;
	/**
	 * Alarm
	 */
	@OneToOne(cascade=CascadeType.ALL) private Alarm alarm;
	
	/*
	@Override
	public String toString() {
		if(type==null)
			return title;
		return type.toString()+(StringUtils.isEmpty(title)?"":" - "+title);
	}
	*/
	
}
