package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.geography.ContactManager;
import org.cyk.utility.common.annotation.UIField;

/**
 * To Plan a thing to occur at or during a particular time or period
 * @author Christian Yao Komenan
 *
 */
@Entity
@Getter @Setter @NoArgsConstructor
public class Schedule extends AbstractEvent implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * The day of the week
	 */
	@UIField
	protected Byte day;
	/**
	 * The start hour
	 */
	@UIField
	protected Byte startHour;
	/**
	 * The start minute
	 */
	@UIField
	protected Byte startMinute;
	/**
	 * The end hour
	 */
	@UIField
	protected Byte endHour;
	/**
	 * The end minute
	 */
	@UIField
	protected Byte endMinute;
	
	
    public Schedule(EventType type, String title, String description, Byte day, Byte startHour, Byte startMinute,Byte endHour, Byte endMinute, Period period) {
        super(type, title, description,period, new ContactManager(), null);
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }
	
	

}