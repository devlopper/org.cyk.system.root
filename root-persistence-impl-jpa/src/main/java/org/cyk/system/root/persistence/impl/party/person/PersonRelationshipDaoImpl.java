package org.cyk.system.root.persistence.impl.party.person;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipExtremity;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.utility.common.computation.ArithmeticOperator;

public class PersonRelationshipDaoImpl extends AbstractTypedDao<PersonRelationship> implements PersonRelationshipDao,Serializable {

	private static final long serialVersionUID = 6920278182318788380L;

	private String readByPersons,readByRoles,readByPersonsByRoles,readOppositeByPersonsByRoles,readByPersonByRoleByOppositePerson,readByPersonByRoleByOppositeRole;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		String person1 = commonUtils.attributePath(PersonRelationship.FIELD_EXTREMITY_1,PersonRelationshipExtremity.FIELD_PERSON);
		String person2 = commonUtils.attributePath(PersonRelationship.FIELD_EXTREMITY_2,PersonRelationshipExtremity.FIELD_PERSON);
		String role1 = commonUtils.attributePath(PersonRelationship.FIELD_EXTREMITY_1,PersonRelationshipExtremity.FIELD_ROLE);
		String role2 = commonUtils.attributePath(PersonRelationship.FIELD_EXTREMITY_2,PersonRelationshipExtremity.FIELD_ROLE);
		
		registerNamedQuery(readByPersons, _select()
			.whereIdentifierIn(person1,PersonRelationshipExtremity.FIELD_PERSON)
			.or().whereIdentifierIn(person2,PersonRelationshipExtremity.FIELD_PERSON));
		
		registerNamedQuery(readByRoles, _select()
			.whereIdentifierIn(role1,PersonRelationshipExtremity.FIELD_ROLE)
			.or().whereIdentifierIn(role2,PersonRelationshipExtremity.FIELD_ROLE));
		
		registerNamedQuery(readByPersonsByRoles, _select()
			.where()
			.parenthesis(Boolean.TRUE)
				.whereIdentifierIn(person1,PersonRelationshipExtremity.FIELD_PERSON)
				.and().whereIdentifierIn(role1,PersonRelationshipExtremity.FIELD_ROLE)
			.parenthesis(Boolean.FALSE).or()
			.parenthesis(Boolean.TRUE)
				.whereIdentifierIn(person2,PersonRelationshipExtremity.FIELD_PERSON)
				.and().whereIdentifierIn(role2,PersonRelationshipExtremity.FIELD_ROLE)
			.parenthesis(Boolean.FALSE)	
				);
		
		registerNamedQuery(readOppositeByPersonsByRoles, _select()
			.where()
			.parenthesis(Boolean.TRUE)
				.whereIdentifierIn(person1,PersonRelationshipExtremity.FIELD_PERSON)
				.and().whereIdentifierIn(role2,PersonRelationshipExtremity.FIELD_ROLE)
			.parenthesis(Boolean.FALSE).or()
			.parenthesis(Boolean.TRUE)
				.whereIdentifierIn(person2,PersonRelationshipExtremity.FIELD_PERSON)
				.and().whereIdentifierIn(role1,PersonRelationshipExtremity.FIELD_ROLE)
			.parenthesis(Boolean.FALSE)	
				);
		
		registerNamedQuery(readByPersonByRoleByOppositePerson, _select()
				.where(person1,PersonRelationshipExtremity.FIELD_PERSON+"1")
				.and(role1,PersonRelationshipExtremity.FIELD_ROLE,ArithmeticOperator.EQ)
				.and(person2,PersonRelationshipExtremity.FIELD_PERSON+"2",ArithmeticOperator.EQ));
		
		registerNamedQuery(readByPersonByRoleByOppositeRole, _select()
			.where(person1,PersonRelationshipExtremity.FIELD_PERSON)
			.and(role1,PersonRelationshipExtremity.FIELD_ROLE+"1",ArithmeticOperator.EQ)
			.and(role2,PersonRelationshipExtremity.FIELD_ROLE+"2",ArithmeticOperator.EQ));
		
	}
	
	@Override
	public Collection<PersonRelationship> readByPersons(Collection<Person> persons) {
		return namedQuery(readByPersons).parameterIdentifiers(PersonRelationshipExtremity.FIELD_PERSON, persons).resultMany();
	}
	
	@Override
	public Collection<PersonRelationship> readByPerson(Person person) {
		return readByPersons(Arrays.asList(person));
	}

	@Override
	public Collection<PersonRelationship> readByRoles(Collection<PersonRelationshipTypeRole> roles) {
		return namedQuery(readByRoles).parameterIdentifiers(PersonRelationshipExtremity.FIELD_ROLE, roles).resultMany();
	}
	
	@Override
	public Collection<PersonRelationship> readByRole(PersonRelationshipTypeRole role) {
		return readByRoles(Arrays.asList(role));
	}
	
	@Override
	public Collection<PersonRelationship> readByPersonsByRoles(Collection<Person> persons,Collection<PersonRelationshipTypeRole> roles) {
		return namedQuery(readByPersonsByRoles).parameterIdentifiers(PersonRelationshipExtremity.FIELD_PERSON, persons)
				.parameterIdentifiers(PersonRelationshipExtremity.FIELD_ROLE, roles).resultMany();
	}
	
	@Override
	public Collection<PersonRelationship> readByPersonsByRole(Collection<Person> persons,PersonRelationshipTypeRole role) {
		return readByPersonsByRoles(persons, Arrays.asList(role));
	}
	
	@Override
	public Collection<PersonRelationship> readByPersonByRoles(Person person,Collection<PersonRelationshipTypeRole> roles) {
		return readByPersonsByRoles(Arrays.asList(person), roles);
	}
	
	@Override
	public Collection<PersonRelationship> readByPersonByRole(Person person,PersonRelationshipTypeRole role) {
		return readByPersonsByRoles(Arrays.asList(person), Arrays.asList(role));
	}
	
	@Override
	public Collection<PersonRelationship> readOppositeByPersonsByRoles(Collection<Person> persons,Collection<PersonRelationshipTypeRole> roles) {
		return namedQuery(readOppositeByPersonsByRoles).parameterIdentifiers(PersonRelationshipExtremity.FIELD_PERSON, persons)
				.parameterIdentifiers(PersonRelationshipExtremity.FIELD_ROLE, roles).resultMany();
	}
	
	@Override
	public Collection<PersonRelationship> readOppositeByPersonsByRole(Collection<Person> persons,PersonRelationshipTypeRole role) {
		return readOppositeByPersonsByRoles(persons, Arrays.asList(role));
	}
	
	@Override
	public Collection<PersonRelationship> readOppositeByPersonByRoles(Person person,Collection<PersonRelationshipTypeRole> roles) {
		return readOppositeByPersonsByRoles(Arrays.asList(person), roles);
	}
	
	@Override
	public Collection<PersonRelationship> readOppositeByPersonByRole(Person person,PersonRelationshipTypeRole role) {
		return readOppositeByPersonsByRoles(Arrays.asList(person), Arrays.asList(role));
	}
	
	@Override
	public PersonRelationship readByPersonByRoleByOppositePerson(Person person1,PersonRelationshipTypeRole role,Person person2) {
		return namedQuery(readByPersonByRoleByOppositePerson).parameter(PersonRelationshipExtremity.FIELD_PERSON+"1", person1)
				.parameter(PersonRelationshipExtremity.FIELD_ROLE, role).parameter(PersonRelationshipExtremity.FIELD_PERSON+"2", person2)
				.ignoreThrowable(NoResultException.class).resultOne();
	}

	@Override
	public Collection<PersonRelationship> readByPersonByRoleByOppositeRole(Person person1, PersonRelationshipTypeRole role1,PersonRelationshipTypeRole role2) {
		return namedQuery(readByPersonByRoleByOppositeRole).parameter(PersonRelationshipExtremity.FIELD_PERSON, person1)
				.parameter(PersonRelationshipExtremity.FIELD_ROLE+"1", role1).parameter(PersonRelationshipExtremity.FIELD_ROLE+"2", role2)
				.resultMany();
	}

}
