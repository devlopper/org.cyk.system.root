package org.cyk.system.root.business.api.party.person;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;

public interface PersonRelationshipBusiness extends TypedBusiness<PersonRelationship> {

	Collection<PersonRelationship> findByPerson(Person person);
	Collection<PersonRelationship> findByType(PersonRelationshipType type);
 
}
