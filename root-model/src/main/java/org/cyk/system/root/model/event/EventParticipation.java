package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;

@Entity
@Getter @Setter @NoArgsConstructor
public class EventParticipation extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;
	
	/*
	 * Who participates to what ?
	 */
	/**
	 * Participant
	 */
	@ManyToOne private Party party;
	
	@ManyToOne private Event event;

	public EventParticipation(Party party, Event event) {
		super();
		this.party = party;
		this.event = event;
	}
    
	@Override
	public String toString() {
		return party+" - "+event;
	}
}
