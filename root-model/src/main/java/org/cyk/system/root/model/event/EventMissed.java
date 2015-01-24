package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EventMissed extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;
	
	/*
	 * Which participant missed what for what reason and how long ?
	 */
	
	@ManyToOne private EventParticipation participation;
		
	@ManyToOne private EventMissedReason reason; 
    
	/**
	 * Duration in millisecond
	 */
	private Long duration;
	
}
