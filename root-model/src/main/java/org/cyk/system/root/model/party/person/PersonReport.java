package org.cyk.system.root.model.party.person;

import java.io.InputStream;
import java.io.Serializable;

import org.cyk.system.root.model.party.PartyReport;
import org.cyk.utility.common.generator.RandomDataProvider.RandomPerson;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonReport extends PartyReport implements Serializable {

	private static final long serialVersionUID = 4273143271881011482L;

	protected String lastnames,surname,sex,maritalStatus,nationality,names,title,jobFonction,jobTitle;
	protected InputStream signatureSpecimen;
	
	protected Boolean generateSignatureSpecimen=Boolean.FALSE;
	
	@Override
	public void generate() { 
		super.generate();
		Boolean male = provider.randomBoolean();
		RandomPerson person = male ? provider.getMale() : provider.getFemale();
		if(Boolean.TRUE.equals(globalIdentifier.getGenerateImage()))
			globalIdentifier.setImage(inputStream(person.photo().getBytes()));
		globalIdentifier.setName(person.firstName());
		lastnames = person.lastName();
		title = male ? "Mr":provider.randomBoolean()?"Mlle":"Mme";
		names = globalIdentifier.getName()+" "+lastnames;
		surname = provider.randomWord(3, 6);
		sex = male ? "M":"F";
		maritalStatus = provider.randomBoolean() ? "Marie" : "Célibataire";
		nationality = provider.randomWord(10, 20);
		jobFonction = provider.randomWord(10, 20);
		jobTitle = provider.randomWord(10, 20);
		if(Boolean.TRUE.equals(generateSignatureSpecimen))
			signatureSpecimen = inputStream(provider.signatureSpecimen().getBytes());
	}
	
}
