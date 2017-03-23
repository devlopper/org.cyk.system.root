package org.cyk.system.root.persistence.api.party.person;

import java.util.Collection;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.persistence.api.TypedDao;

public interface PersonRelationshipDao extends TypedDao<PersonRelationship> { 

	Collection<PersonRelationship> readByPerson(Person person);
	Collection<PersonRelationship> readByType(PersonRelationshipType type);
	Collection<PersonRelationship> readByPersonByType(Person person,PersonRelationshipType type);
	Collection<PersonRelationship> readByPerson1ByType(Person person,PersonRelationshipType type);
	Collection<PersonRelationship> readByPerson2ByType(Person person,PersonRelationshipType type);
	Collection<PersonRelationship> readByPerson2ByTypes(Collection<Person> persons,Collection<PersonRelationshipType> types);
	
	PersonRelationship readByPerson1ByTypeByPerson2(Person person1,PersonRelationshipType type,Person person2);
}
