package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.PartyReport;
import org.cyk.utility.common.generator.RandomDataProvider.RandomPerson;

@Getter @Setter
public class PersonReport extends PartyReport implements Serializable {

	private static final long serialVersionUID = 4273143271881011482L;

	protected String lastName,birthDate,birthLocation,sex,maritalStatus,nationality;

	@Override
	public void generate() { 
		super.generate();
		RandomPerson person = provider.getMale();
		image = inputStream(person.photo());
		name = person.firstName();
		lastName = person.lastName();
	}
	
}
