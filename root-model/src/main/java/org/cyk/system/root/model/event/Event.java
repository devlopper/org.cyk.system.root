package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.geography.ContactManager;

/**
 * Something that happens
 * @author Christian Yao Komenan
 *
 */
@Entity
@Getter @Setter @NoArgsConstructor
public class Event extends AbstractEvent implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;

    public Event(EventType type, String title, String description, Period period) {
        super(type, title, description,period, new ContactManager(), null);
    }
	
	
}
