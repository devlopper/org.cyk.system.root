package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.ContactManager;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.annotation.UIField.OneRelationshipInputType;

@Getter @Setter @MappedSuperclass @AllArgsConstructor @NoArgsConstructor
public abstract class AbstractEvent extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;

	/**
	 * Type
	 */
	@UIField
	@ManyToOne protected EventType type;
	/**
	 * Title. Rich text
	 */
	@UIField(textArea=true)
	protected String title;
	/**
	 * Description. Rich text
	 */
	@UIField(textArea=true)
	@Column(length=1024 * 1) protected String description;
	
	/**
     * The period
     */
    @UIField(oneRelationshipInputType=OneRelationshipInputType.FIELDS)
    @Embedded protected Period period = new Period();
	
	/**
	 * Contacts
	 */
	@OneToOne(cascade=CascadeType.ALL) protected ContactManager contactManager = new ContactManager();
	
	/**
	 * Alarm
	 */
	@OneToOne(cascade=CascadeType.ALL) protected Alarm alarm;
	
}
