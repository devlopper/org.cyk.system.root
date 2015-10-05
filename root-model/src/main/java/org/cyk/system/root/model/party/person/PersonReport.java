package org.cyk.system.root.model.party.person;

import java.io.InputStream;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.PartyReport;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.generator.RandomDataProvider.RandomPerson;

@Getter @Setter
public class PersonReport extends PartyReport implements Serializable {

	private static final long serialVersionUID = 4273143271881011482L;

	protected String lastName,surname,birthDate,birthLocation,sex,maritalStatus,nationality,names,title;
	protected InputStream signatureSpecimen;
	
	protected Boolean generateSignatureSpecimen=Boolean.FALSE;
	
	@Override
	public void generate() { 
		super.generate();
		Boolean male = provider.randomBoolean();
		RandomPerson person = male ? provider.getMale() : provider.getFemale();
		if(Boolean.TRUE.equals(generateImage))
			image = inputStream(person.photo());
		name = person.firstName();
		lastName = person.lastName();
		title = male ? "Mr":provider.randomBoolean()?"Mlle":"Mme";
		names = name+" "+lastName;
		surname = provider.randomWord(3, 6);
		birthDate = "01/01/2014";
		birthLocation = provider.randomWord(RandomDataProvider.WORD_LOCATION, 10, 20);
		sex = male ? "M":"F";
		maritalStatus = provider.randomBoolean() ? "Marie" : "Célibataire";
		nationality = provider.randomWord(10, 20);
		if(Boolean.TRUE.equals(generateSignatureSpecimen))
			signatureSpecimen = inputStream(provider.signatureSpecimen());
	}
	
}
