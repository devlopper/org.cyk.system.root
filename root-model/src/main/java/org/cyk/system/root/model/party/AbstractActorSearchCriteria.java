package org.cyk.system.root.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;

@Getter @Setter
public abstract class AbstractActorSearchCriteria<ACTOR extends AbstractActor> extends AbstractFieldValueSearchCriteriaSet implements Serializable {

	private static final long serialVersionUID = 6796076474234170332L;

	protected PersonSearchCriteria person;
	
	public AbstractActorSearchCriteria(){
		this(null);
	}
	
	public AbstractActorSearchCriteria(String name) {
		person = new PersonSearchCriteria(name);
	}
	
}
