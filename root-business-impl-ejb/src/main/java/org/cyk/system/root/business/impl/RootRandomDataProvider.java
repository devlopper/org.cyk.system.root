package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.generator.RandomDataProvider;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER,order=RootBusinessLayer.DEPLOYMENT_ORDER+1)
public class RootRandomDataProvider extends AbstractRandomDataProvider implements Serializable {

	private static final long serialVersionUID = -6026758194823798041L;

	private static RootRandomDataProvider INSTANCE;
	
	@Inject private GenericBusiness genericBusiness;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	@SuppressWarnings("unchecked")
	public <IDENTIFIABLE extends AbstractIdentifiable> IDENTIFIABLE oneFromDatabase(Class<IDENTIFIABLE> identifiableClass){
		return (IDENTIFIABLE) BusinessLocator.getInstance().locate(identifiableClass).findOneRandomly();
	}
	
	public Person person(Boolean male,Country country,PhoneNumberType type){
		Person person = new Person();
		person.setName(Boolean.TRUE.equals(male)?randomDataProvider.getMale().firstName():randomDataProvider.getFemale().firstName());
		person.setLastName(Boolean.TRUE.equals(male)?randomDataProvider.getMale().lastName():randomDataProvider.getFemale().lastName());
		person.setBirthDate(randomDataProvider.randomDate(DateUtils.addYears(new Date(), -50), DateUtils.addYears(new Date(), -20)) );
		person.setContactCollection(contactCollection(country,type));
		File photo = new File();
		photo.setBytes(Boolean.TRUE.equals(male)?randomDataProvider.getMale().photo():randomDataProvider.getFemale().photo());
		photo.setExtension("png");
		person.setImage(photo);
		return person;
	}
	
	public Person person(){
		return person(randomDataProvider.randomBoolean(), RootBusinessLayer.getInstance().getCountryCoteDivoire(), 
				RootBusinessLayer.getInstance().getLandPhoneNumberType());
	}
	
	public void createPerson(Integer count){
		Collection<AbstractIdentifiable> collection = new ArrayList<>();
		for(int i=0;i<count;i++)
			collection.add(person());
		genericBusiness.create(collection);
	}
	
	public ContactCollection contactCollection(Country country,PhoneNumberType type){
		ContactCollection contactCollection = new ContactCollection();
		contactCollection.setPhoneNumbers(new ArrayList<PhoneNumber>());
		phoneNumber(contactCollection, country,type);
		contactCollection.setElectronicMails(new ArrayList<ElectronicMail>());
		email(contactCollection);
		return contactCollection;
	}
	
	public PhoneNumber phoneNumber(ContactCollection contactCollection,Country country,PhoneNumberType type){
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setCollection(contactCollection);
		phoneNumber.setCountry(country);
		phoneNumber.setType(type);
		phoneNumber.setNumber(RandomStringUtils.randomNumeric(8));
		if(contactCollection!=null)
			contactCollection.getPhoneNumbers().add(phoneNumber);
		return phoneNumber;
	}
	
	public PhoneNumber phoneNumber(ContactCollection contactCollection){
		return phoneNumber(contactCollection, RootBusinessLayer.getInstance().getCountryCoteDivoire(), RootBusinessLayer.getInstance().getLandPhoneNumberType());
	}
	
	public ElectronicMail email(ContactCollection contactCollection){
		ElectronicMail electronicMail = new ElectronicMail();
		electronicMail.setCollection(contactCollection);
		electronicMail.setAddress(RandomDataProvider.getInstance().randomWord(RandomDataProvider.WORD_EMAIL, 5, 10));
		if(contactCollection!=null)
			contactCollection.getElectronicMails().add(electronicMail);
		return electronicMail; 
	}
	
	public <ACTOR extends AbstractActor> ACTOR actor(Class<ACTOR> actorClass){
		ACTOR actor = newInstance(actorClass);
		actor.setPerson(person());
		return actor;
	}
	
	public <T extends AbstractActor> void createActor(Class<T> actorClass,Integer count){
		for(int i=0;i<count;i++)
			genericBusiness.create(actor(actorClass));
    }
    
	public static RootRandomDataProvider getInstance() {
		return INSTANCE;
	}
	
}
