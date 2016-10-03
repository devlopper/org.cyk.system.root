package org.cyk.system.root.persistence.api.party.person;

import java.util.Collection;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.persistence.api.TypedDao;

public interface PersonRelationshipDao extends TypedDao<PersonRelationship> { 

	Collection<PersonRelationship> readByPerson(Person person);
	Collection<PersonRelationship> readByType(PersonRelationshipType type);
	
}
