package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.Date;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.generator.RandomDataProvider;

public class RootRandomDataProvider extends RandomDataProvider implements Serializable {

	private static final long serialVersionUID = -6026758194823798041L;

	private static final RootRandomDataProvider INSTANCE = new RootRandomDataProvider();
	
	public static RootRandomDataProvider getInstance() {
		return INSTANCE;
	}
	
	/**/
	
	private RootRandomDataProvider() {
		super();
	}
	
	public Person person(Boolean male){
		Person person = new Person();
		person.setName(Boolean.TRUE.equals(male)?getMale().firstName():getFemale().firstName());
		person.setLastName(Boolean.TRUE.equals(male)?getMale().lastName():getFemale().lastName());
		person.setBirthDate( /*randomDate(new Date(DateUtils.MILLIS_PER_DAY*365*30), new Date(DateUtils.MILLIS_PER_DAY*365*10))*/ new Date() );
		File photo = new File();
		photo.setBytes(Boolean.TRUE.equals(male)?getMale().photo():getFemale().photo());
		person.setImage(photo);
		
		return person;
	}
	
	/*
	public ContactCollection contactCollection(){
		ContactCollection contactCollection = new ContactCollection();
		
		return contactCollection;
	}*/
	
	/*private PhoneNumber phoneNumber(ContactCollection contactCollection){
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setCollection(contactCollection);
		phoneNumber.set
		return phoneNumber;
	}*/
	
}
