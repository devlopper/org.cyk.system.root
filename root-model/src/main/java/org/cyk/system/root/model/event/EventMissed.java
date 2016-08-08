package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class EventMissed extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;
	
	/*
	 * Which participant missed what for what reason and how long ?
	 */
	
	@ManyToOne private EventParty eventParty;
		
	@ManyToOne private EventMissedReason reason; 
    
	/**
	 * Duration in millisecond
	 */
	@NotNull @Column(nullable=false) private Long numberOfMillisecond;
	
	public static final String FIELD_EVENT_PARTY = "eventParty";
	public static final String FIELD_REASON = "reason";
	public static final String FIELD_NUMBER_OF_MILLISECOND = "numberOfMillisecond";
}
