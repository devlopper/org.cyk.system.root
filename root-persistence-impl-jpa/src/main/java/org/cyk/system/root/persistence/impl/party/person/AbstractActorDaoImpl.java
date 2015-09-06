package org.cyk.system.root.persistence.impl.party.person;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.person.AbstractActorDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractActorDaoImpl<ACTOR extends AbstractActor> extends AbstractTypedDao<ACTOR> implements AbstractActorDao<ACTOR>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readByPerson,readByRegistrationCode;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByPerson, _select().where("person"));
		registerNamedQuery(readByRegistrationCode, _select().where("registration.code","registrationCode"));
	}
	
	@Override
	public ACTOR readByPerson(Person person) {
		return namedQuery(readByPerson).parameter("person", person).ignoreThrowable(NoResultException.class).resultOne();
	}
	
	@Override
	public ACTOR readByRegistrationCode(String registrationCode) {
		return namedQuery(readByRegistrationCode).parameter("registrationCode", registrationCode).ignoreThrowable(NoResultException.class).resultOne();
	}
}
 