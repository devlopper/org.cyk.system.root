package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.Setter;

//@Entity 
@Getter @Setter
public class Schedule extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;

	@ManyToOne private ScheduleCollection collection;

	private String title;
	
	private Byte weekDayIndex;
	
	@Embedded private FlexibleTime startingTime = new FlexibleTime();
	
	/**
	 * Duration in millisecond
	 */
	private Integer duration;

}
