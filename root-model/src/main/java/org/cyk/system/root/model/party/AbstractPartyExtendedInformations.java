package org.cyk.system.root.model.party;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.RepeatedEvent;
import org.cyk.system.root.model.geography.Location;

@Getter @Setter @MappedSuperclass 
public abstract class AbstractPartyExtendedInformations<PARTY extends Party> extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	@OneToOne @JoinColumn(nullable=false) @NotNull protected PARTY party;
	
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true) protected Location birthLocation;
	@OneToOne(cascade=CascadeType.ALL) protected RepeatedEvent birthDateAnniversary;
	
}
