package org.cyk.system.root.business.api.party.person;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;

public interface PersonRelationshipBusiness extends TypedBusiness<PersonRelationship> {

	Collection<PersonRelationship> findByPerson(Person person);
	Collection<PersonRelationship> findByPersons(Collection<Person> persons);
	
	Collection<PersonRelationship> findByRole(PersonRelationshipTypeRole role);
	Collection<PersonRelationship> findByRoles(Collection<PersonRelationshipTypeRole> roles);
	
	Collection<PersonRelationship> findByPersonByRole(Person person,PersonRelationshipTypeRole role);
	Collection<PersonRelationship> findByPersonsByRoles(Collection<Person> persons,Collection<PersonRelationshipTypeRole> roles);
	Collection<PersonRelationship> findByPersonsByRole(Collection<Person> persons,PersonRelationshipTypeRole role);
	Collection<PersonRelationship> findByPersonByRoles(Person person,Collection<PersonRelationshipTypeRole> roles);
	
	Collection<PersonRelationship> findOppositeByPersonByRole(Person person,PersonRelationshipTypeRole role);
	Collection<PersonRelationship> findOppositeByPersonsByRoles(Collection<Person> persons,Collection<PersonRelationshipTypeRole> roles);
	Collection<PersonRelationship> findOppositeByPersonsByRole(Collection<Person> persons,PersonRelationshipTypeRole role);
	Collection<PersonRelationship> findOppositeByPersonByRoles(Person person,Collection<PersonRelationshipTypeRole> roles);
	
	/*
	Collection<PersonRelationship> findByPerson(Person person);
	Collection<PersonRelationship> findByType(PersonRelationshipType type);
	Collection<PersonRelationship> findByType(Collection<PersonRelationship> personRelationships,PersonRelationshipType type);
	PersonRelationship findOneByType(Collection<PersonRelationship> personRelationships,PersonRelationshipType type);
	Collection<PersonRelationship> findByPersonByType(Person person,PersonRelationshipType type);

	Collection<PersonRelationship> findByPerson1ByType(Person person,PersonRelationshipType type);
	
	Collection<PersonRelationship> findByPerson2ByTypes(Collection<Person> persons,Collection<PersonRelationshipType> types);
	Collection<PersonRelationship> findByPerson2ByType(Person person,PersonRelationshipType type);
	*/
	PersonRelationship instanciateOne(String person1Code,String person1RoleCode,String person2Code,String person2RoleCode);
 
	Collection<Person> getRelatedPersons(Collection<PersonRelationship> personRelationships,Person person);
}
