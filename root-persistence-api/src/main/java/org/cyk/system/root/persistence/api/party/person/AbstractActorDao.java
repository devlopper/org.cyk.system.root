package org.cyk.system.root.persistence.api.party.person;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractActorDao<ACTOR extends AbstractActor> extends TypedDao<ACTOR> {

	ACTOR readByPerson(Person person);
	
	ACTOR readByRegistrationCode(String registrationCode);
	
}
