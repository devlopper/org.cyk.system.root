package org.cyk.system.root.persistence.api.party.person;

import java.util.Collection;

import org.cyk.system.root.model.party.AbstractActorSearchCriteria;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractActorDao<ACTOR extends AbstractActor,SEARCH_CRITERIA extends AbstractActorSearchCriteria<ACTOR>> extends TypedDao<ACTOR> {

	ACTOR readByPerson(Person person);
	
	ACTOR readByRegistrationCode(String registrationCode);
	
	Collection<ACTOR> readByCriteria(SEARCH_CRITERIA criteria);
	Long countByCriteria(SEARCH_CRITERIA criteria);
	
}
