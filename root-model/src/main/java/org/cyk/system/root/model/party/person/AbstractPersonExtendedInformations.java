package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.party.AbstractPartyExtendedInformations;

@Getter @Setter @MappedSuperclass  @NoArgsConstructor
public abstract class AbstractPersonExtendedInformations extends AbstractPartyExtendedInformations<Person> implements Serializable {

	private static final long serialVersionUID = 4416245866978363658L;

	public AbstractPersonExtendedInformations(Person party) {
		super(party);
	}
	
	public Person getPerson(){
		return getParty();
	}

	
}
