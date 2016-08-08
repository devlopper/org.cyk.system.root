package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Entity
@Getter @Setter @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class EventParty extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;
	
	@ManyToOne private Event event;
	
	/*
	 * Who participates to what ?
	 */
	/**
	 * Participant
	 */
	@ManyToOne private Party party;
	
	private Boolean alertParty = Boolean.TRUE;
	
	@Transient private EventMissed missed;

	public EventParty(Party party) {
		super();
		this.party = party;
	}
	
	public EventParty(Party party, Event event) {
		super();
		this.party = party;
		this.event = event;
	}
    
	@Override
	public String toString() {
		return party+" - "+event;
	}



	
}
