package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import org.cyk.system.root.model.party.person.Person;

public class PersonDetails extends AbstractPersonDetails<Person> implements Serializable {

	private static final long serialVersionUID = -6365145986204679114L;

	public PersonDetails(Person person) {
		super(person);
	}

	@Override
	protected Person getPerson() {
		return master;
	}

}
