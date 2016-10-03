package org.cyk.system.root.persistence.impl.party.person;

import java.util.Collection;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class PersonRelationshipDaoImpl extends AbstractTypedDao<PersonRelationship> implements PersonRelationshipDao {

	private static final long serialVersionUID = 6920278182318788380L;

	private String readByPerson,readByType;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByPerson, _select().where(PersonRelationship.FIELD_PERSON1).or(PersonRelationship.FIELD_PERSON2));
		registerNamedQuery(readByType, _select().where(PersonRelationship.FIELD_TYPE));
	}
	
	@Override
	public Collection<PersonRelationship> readByPerson(Person person) {
		return namedQuery(readByPerson).parameter(PersonRelationship.FIELD_PERSON1, person).parameter(PersonRelationship.FIELD_PERSON2, person).resultMany();
	}

	@Override
	public Collection<PersonRelationship> readByType(PersonRelationshipType type) {
		return namedQuery(readByType).parameter(PersonRelationship.FIELD_TYPE, type).resultMany();
	}

}
