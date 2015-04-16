package org.cyk.system.root.business.api.party.person;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;

public interface AbstractActorBusiness<ACTOR extends AbstractActor> extends TypedBusiness<ACTOR> {

	//void register(ACTOR anActor);
	
	ACTOR findByPerson(Person person);
	
}
