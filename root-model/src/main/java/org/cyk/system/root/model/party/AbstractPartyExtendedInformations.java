package org.cyk.system.root.model.party;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @MappedSuperclass @NoArgsConstructor
public abstract class AbstractPartyExtendedInformations<PARTY extends Party> extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	@OneToOne @JoinColumn(nullable=false) @NotNull protected PARTY party;

	public AbstractPartyExtendedInformations(PARTY party) {
		super();
		this.party = party;
	}
	
	
	
}
