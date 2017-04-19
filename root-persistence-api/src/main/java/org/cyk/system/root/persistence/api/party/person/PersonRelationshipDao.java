package org.cyk.system.root.persistence.api.party.person;

import java.util.Collection;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.persistence.api.TypedDao;

public interface PersonRelationshipDao extends TypedDao<PersonRelationship> { 

	/**
	 * Read where a person participates to a relationship
	 * @param persons
	 * @return
	 */
	Collection<PersonRelationship> readByPersons(Collection<Person> persons);
	
	/**
	 * {@link PersonRelationshipDao#readByPersons(Collection)}
	 * @param person
	 * @return
	 */
	Collection<PersonRelationship> readByPerson(Person person);
	
	/**
	 * Read where a role participates to a relationship
	 * @param roles
	 * @return
	 */
	Collection<PersonRelationship> readByRoles(Collection<PersonRelationshipTypeRole> roles);
	
	/**
	 * {@link PersonRelationshipDao#readByRoles(Collection)}
	 * @param role
	 * @return
	 */
	Collection<PersonRelationship> readByRole(PersonRelationshipTypeRole role);
	
	/**
	 * Read where a couple of person on a side and a role on the same side participates to a relationship
	 * @param persons
	 * @param roles
	 * @return
	 */
	Collection<PersonRelationship> readByPersonsByRoles(Collection<Person> persons,Collection<PersonRelationshipTypeRole> roles);
	
	/**
	 * {@link PersonRelationshipDao#readByPersonsByRoles(Collection,Collection)}
	 * @param person
	 * @param role
	 * @return
	 */
	Collection<PersonRelationship> readByPersonByRole(Person person,PersonRelationshipTypeRole role);
	
	/**
	 * {@link PersonRelationshipDao#readByPersonsByRoles(Collection,Collection)}
	 * @param person
	 * @param roles
	 * @return
	 */
	Collection<PersonRelationship> readByPersonByRoles(Person person,Collection<PersonRelationshipTypeRole> roles);
	
	/**
	 * {@link PersonRelationshipDao#readByPersonsByRoles(Collection,Collection)}
	 * @param persons
	 * @param role
	 * @return
	 */
	Collection<PersonRelationship> readByPersonsByRole(Collection<Person> persons,PersonRelationshipTypeRole role);
	
	/**
	 * Read where a couple of person on a side and a role on the opposite side participates to a relationship
	 * @param persons
	 * @param roles
	 * @return
	 */
	Collection<PersonRelationship> readOppositeByPersonsByRoles(Collection<Person> persons,Collection<PersonRelationshipTypeRole> roles);
	
	/**
	 * {@link PersonRelationshipDao#readOppositeByPersonsByRoles(Collection,Collection)}
	 * @param person
	 * @param role
	 * @return
	 */
	Collection<PersonRelationship> readOppositeByPersonByRole(Person person,PersonRelationshipTypeRole role);
	
	/**
	 * {@link PersonRelationshipDao#readOppositeByPersonsByRoles(Collection,Collection)}
	 * @param persons
	 * @param role
	 * @return
	 */
	Collection<PersonRelationship> readOppositeByPersonsByRole(Collection<Person> persons,PersonRelationshipTypeRole role);
	
	/**
	 * {@link PersonRelationshipDao#readOppositeByPersonsByRoles(Collection,Collection)}
	 * @param person
	 * @param roles
	 * @return
	 */
	Collection<PersonRelationship> readOppositeByPersonByRoles(Person person,Collection<PersonRelationshipTypeRole> roles);
	
	/**
	 * Read where the couple of person1 and role1 on a side and person2 on the other side participates to a relationship
	 * @param person1
	 * @param role1
	 * @param person2
	 * @return
	 */
	PersonRelationship readByPerson1ByRole1ByPerson2(Person person1,PersonRelationshipTypeRole role1,Person person2);
	
}
