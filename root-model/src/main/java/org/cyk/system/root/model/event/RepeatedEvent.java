package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.time.FlexibleDate;
import org.cyk.system.root.model.time.FlexibleTime;

/**
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @Entity @NoArgsConstructor
public class RepeatedEvent extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;
	
	@ManyToOne private Event event;
    
	@Embedded private FlexibleDate date = new FlexibleDate();
	
	@Embedded private FlexibleTime time = new FlexibleTime();
	
    @Override
    public String toString() {
    	return event.toString();
    }

}
