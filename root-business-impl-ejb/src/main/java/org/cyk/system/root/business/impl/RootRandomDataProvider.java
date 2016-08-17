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
import org.cyk.system.root.business.api.geography.CountryBusiness;
import org.cyk.system.root.business.api.geography.LocationTypeBusiness;
import org.cyk.system.root.business.api.geography.PhoneNumberTypeBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobInformations;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.persistence.api.party.person.SexDao;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.generator.RandomDataProvider.RandomFile;
import org.cyk.utility.common.generator.RandomDataProvider.RandomPerson;

import lombok.Getter;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER,order=RootBusinessLayer.DEPLOYMENT_ORDER+1)
public class RootRandomDataProvider extends AbstractRandomDataProvider implements Serializable {

	private static final long serialVersionUID = -6026758194823798041L;

	private static RootRandomDataProvider INSTANCE;
	
	@Inject private GenericBusiness genericBusiness;
	@Inject private PersonBusiness personBusiness;
	
	@Getter private Collection<RootRandomDataProviderListener> randomDataProviderListeners = new ArrayList<>();
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> IDENTIFIABLE oneFromDatabase(Class<IDENTIFIABLE> identifiableClass){
		return BusinessInterfaceLocator.getInstance().injectTyped(identifiableClass).findOneRandomly();
	}
	
	public Person person(Boolean male,Country country,PhoneNumberType type,Boolean genSignature){
		RandomPerson randomPerson = Boolean.TRUE.equals(male)?randomDataProvider.getMale():randomDataProvider.getFemale();
		Person person = new Person();
		person.getGlobalIdentifierCreateIfNull();
		person.setExtendedInformations(new PersonExtendedInformations(person));
		person.setJobInformations(new JobInformations(person));
		
		person.setName(randomPerson.firstName());
		person.setLastnames(randomPerson.lastName());
		person.setSex(inject(SexDao.class).read(Boolean.TRUE.equals(male)?Sex.MALE:Sex.FEMALE));
		person.setSurname(randomPerson.surName());
		person.setBirthDate(randomDataProvider.randomDate(DateUtils.addYears(new Date(), -50), DateUtils.addYears(new Date(), -20)) );
		person.setContactCollection(contactCollection(country,type));
		File photo = new File();
		RandomFile randomFile = randomPerson.photo();
		photo.setBytes(randomFile.getBytes());
		photo.setExtension(randomFile.getExtension());
		person.setImage(photo);
		
		person.getExtendedInformations().setBirthLocation(location(null));
		person.getExtendedInformations().setTitle(oneFromDatabase(PersonTitle.class));
		person.getJobInformations().setTitle(oneFromDatabase(JobTitle.class));
		person.getJobInformations().setFunction(oneFromDatabase(JobFunction.class));
		
		if(Boolean.TRUE.equals(genSignature)){
			if(person.getExtendedInformations()==null){
				person.setExtendedInformations(new PersonExtendedInformations());
				person.getExtendedInformations().setParty(person);
			}
			File signature = new File();
			randomFile = randomDataProvider.signatureSpecimen();
			signature.setBytes(randomFile.getBytes());
			signature.setExtension(randomFile.getExtension());
			person.getExtendedInformations().setSignatureSpecimen(signature);
			
		}
		return person;
	}
	
	public Person person(){
		return person(randomDataProvider.randomBoolean(), inject(CountryBusiness.class).find(Country.COTE_DIVOIRE), 
				inject(PhoneNumberTypeBusiness.class).find(PhoneNumberType.LAND),Boolean.TRUE);
	}
	
	public void createPerson(Integer count){
		Collection<Person> collection = new ArrayList<>();
		for(int i=0;i<count;i++)
			collection.add(person());
		personBusiness.create(collection);
	}
	
	public ContactCollection contactCollection(Country country,PhoneNumberType type){
		ContactCollection contactCollection = new ContactCollection();
		contactCollection.setPhoneNumbers(new ArrayList<PhoneNumber>());
		phoneNumber(contactCollection, country,type);
		contactCollection.setElectronicMails(new ArrayList<ElectronicMail>());
		contactCollection.setLocations(new ArrayList<Location>());
		email(contactCollection);
		location(contactCollection);
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
		return phoneNumber(contactCollection, inject(CountryBusiness.class).find(Country.COTE_DIVOIRE), inject(PhoneNumberTypeBusiness.class).find(PhoneNumberType.LAND));
	}
	
	public ElectronicMail email(ContactCollection contactCollection){
		ElectronicMail electronicMail = new ElectronicMail();
		electronicMail.setCollection(contactCollection);
		electronicMail.setAddress(randomDataProvider.randomWord(RandomDataProvider.WORD_EMAIL, 5, 10));
		if(contactCollection!=null)
			contactCollection.getElectronicMails().add(electronicMail);
		return electronicMail; 
	}
	
	public Location location(ContactCollection contactCollection){
		Location location = new Location();
		location.setCollection(contactCollection);
		location.setOtherDetails(randomDataProvider.randomWord(RandomDataProvider.WORD_LOCATION, 5, 10));
		location.setLocality(inject(CountryBusiness.class).find(Country.COTE_DIVOIRE).getLocality());
		location.setType(inject(LocationTypeBusiness.class).find(LocationType.HOME));
		if(contactCollection!=null)
			contactCollection.getLocations().add(location);
		return location; 
	}
	
	public <ACTOR extends AbstractActor> ACTOR actor(Class<ACTOR> actorClass){
		ACTOR actor = newInstance(actorClass);
		actor.setPerson(person());
		set(actor);
		return actor;
	}
	
	public <T extends AbstractActor> void createActor(Class<T> actorClass,Integer count){
		for(int i=0;i<count;i++)
			genericBusiness.create(actor(actorClass));
    }
	
	/**/
	
	private void set(Object object){
		for(RootRandomDataProviderListener listener : randomDataProviderListeners)
			listener.set(object);
	}
	
	/**/
    
	public static RootRandomDataProvider getInstance() {
		return INSTANCE;
	}
	
	/**/
	
	public static interface RootRandomDataProviderListener{
		<T> T instanciate(Class<T> aClass);
		void set(Object object);
	}
	
	public static class RootRandomDataProviderAdapter extends BeanAdapter implements RootRandomDataProviderListener,Serializable{
		private static final long serialVersionUID = 581887995233346336L;

		@Override
		public <T> T instanciate(Class<T> aClass) {
			return null;
		}

		@Override
		public void set(Object object) {}
		
	}
	
}
