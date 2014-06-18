package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.geography.ContactManager;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.annotation.UIField.OneRelationshipInputType;

/**
 * Something that happens
 * @author Christian Yao Komenan
 *
 */
@Entity
@Getter @Setter @NoArgsConstructor
public class Event extends AbstractEvent implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;

	/**
     * The period
     */
    @UIField(oneRelationshipInputType=OneRelationshipInputType.FIELDS)
    @Embedded protected Period period = new Period();

    public Event(EventType type, String title, String description, Period period) {
        super(type, title, description, new ContactManager(), null);
        this.period = period;
    }
	
	
}
