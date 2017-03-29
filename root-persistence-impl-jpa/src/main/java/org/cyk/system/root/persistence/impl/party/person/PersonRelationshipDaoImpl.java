package org.cyk.system.root.persistence.impl.party.person;

import java.util.Collection;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class PersonRelationshipDaoImpl extends AbstractTypedDao<PersonRelationship> implements PersonRelationshipDao {

	private static final long serialVersionUID = 6920278182318788380L;

	private String readByPerson,readByType,readByPersonByType,readByPerson1ByType,readByPerson2ByType,readByPerson2ByTypes,readByPerson1ByTypeByPerson2;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByPerson, _select().where(PersonRelationship.FIELD_PERSON1).or(PersonRelationship.FIELD_PERSON2));
		registerNamedQuery(readByType, _select().where(PersonRelationship.FIELD_TYPE));
		registerNamedQuery(readByPersonByType, _select().where().parenthesis(Boolean.TRUE).where(PersonRelationship.FIELD_PERSON1).or(PersonRelationship.FIELD_PERSON2)
				.parenthesis(Boolean.FALSE).and(PersonRelationship.FIELD_TYPE));
		registerNamedQuery(readByPerson1ByType, _select().where(PersonRelationship.FIELD_PERSON1).and(PersonRelationship.FIELD_TYPE));
		registerNamedQuery(readByPerson2ByType, _select().where(PersonRelationship.FIELD_PERSON2).and(PersonRelationship.FIELD_TYPE));
		registerNamedQuery(readByPerson2ByTypes, _select().whereIdentifierIn(PersonRelationship.FIELD_PERSON2,PersonRelationship.FIELD_PERSON2)
				.and().whereIdentifierIn(PersonRelationship.FIELD_TYPE,PersonRelationship.FIELD_TYPE));
		registerNamedQuery(readByPerson1ByTypeByPerson2, _select().where(PersonRelationship.FIELD_PERSON1).and(PersonRelationship.FIELD_TYPE)
				.and(PersonRelationship.FIELD_PERSON2));
	}
	
	@Override
	public Collection<PersonRelationship> readByPerson(Person person) {
		return namedQuery(readByPerson).parameter(PersonRelationship.FIELD_PERSON1, person).parameter(PersonRelationship.FIELD_PERSON2, person).resultMany();
	}

	@Override
	public Collection<PersonRelationship> readByType(PersonRelationshipType type) {
		return namedQuery(readByType).parameter(PersonRelationship.FIELD_TYPE, type).resultMany();
	}

	@Override
	public Collection<PersonRelationship> readByPersonByType(Person person, PersonRelationshipType type) {
		return namedQuery(readByType).parameter(PersonRelationship.FIELD_PERSON1, person).parameter(PersonRelationship.FIELD_PERSON2, person)
				.parameter(PersonRelationship.FIELD_TYPE, type).resultMany();
	}

	@Override
	public Collection<PersonRelationship> readByPerson1ByType(Person person, PersonRelationshipType type) {
		return namedQuery(readByPerson1ByType).parameter(PersonRelationship.FIELD_PERSON1, person).parameter(PersonRelationship.FIELD_TYPE, type).resultMany();
	}

	@Override
	public Collection<PersonRelationship> readByPerson2ByType(Person person, PersonRelationshipType type) {
		return namedQuery(readByPerson2ByType).parameter(PersonRelationship.FIELD_PERSON2, person).parameter(PersonRelationship.FIELD_TYPE, type).resultMany();
	}
	
	@Override
	public Collection<PersonRelationship> readByPerson2ByTypes(Collection<Person> persons, Collection<PersonRelationshipType> types) {
		return namedQuery(readByPerson2ByTypes).parameterIdentifiers(PersonRelationship.FIELD_PERSON2, persons)
				.parameterIdentifiers(PersonRelationship.FIELD_TYPE, types).resultMany();
	}
	
	@Override
	public PersonRelationship readByPerson1ByTypeByPerson2(Person person1,PersonRelationshipType type,Person person2) {
		return namedQuery(readByPerson1ByTypeByPerson2).parameter(PersonRelationship.FIELD_PERSON1, person1)
				.parameter(PersonRelationship.FIELD_TYPE, type).parameter(PersonRelationship.FIELD_PERSON2, person2)
				.ignoreThrowable(NoResultException.class).resultOne();
	}

}
