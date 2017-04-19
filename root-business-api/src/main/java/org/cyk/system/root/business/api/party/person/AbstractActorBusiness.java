package org.cyk.system.root.business.api.party.person;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;

public interface AbstractActorBusiness<ACTOR extends AbstractActor,SEARCH_CRITERIA extends AbstractActor.AbstractSearchCriteria<ACTOR>> extends TypedBusiness<ACTOR> {

	ACTOR findByPerson(Person person);
	
	ACTOR instanciateOne(AbstractActor actor);
	
	ACTOR instanciateOne(String code,String[] names);
	
	Collection<ACTOR> instanciateMany(String[] codes);
	
	Collection<ACTOR> findByCriteria(SEARCH_CRITERIA criteria);
	
	Long countByCriteria(SEARCH_CRITERIA criteria);
	
}
