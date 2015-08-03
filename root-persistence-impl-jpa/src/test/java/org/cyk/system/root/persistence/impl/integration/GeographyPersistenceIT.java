package org.cyk.system.root.persistence.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class GeographyPersistenceIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;

	@Deployment
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	} 
	 
	@Inject private ContactDao contactDao;
	/*
	@Inject private ContactCollectionDao contactCollectionDao;
	@Inject private ElectronicMailDao electronicMailDao;
		*/
	
	private ContactCollection contactCollection1,contactCollection2;
	private PhoneNumberType mobile;
	private LocalityType country;
	private Locality civ;
	
	@Override
	protected void populate() {
		mobile = create(new PhoneNumberType("MOB", "Mobile"));
		country=create(new LocalityType(null, "C", "Country"));
		civ = create(new Locality(null, country, "CIV", "Cote d'Ivoire"));
		
		contactCollection1 = create(new ContactCollection());
		contactCollection2 = create(new ContactCollection());
		mails(null, "z1@mail.com","z2@mail.com","z3@mail.com","z4@mail.com");
		mails(contactCollection1, "a1@mail.com","a2@mail.com");
		mails(contactCollection2, "b1@mail.com","b2@mail.com","b3@mail.com");
		
		phones(null,"1","2","3");
		phones(contactCollection1, "11","22","33","44","55","66","77");
		phones(contactCollection2, "8","9");
	}
	 
	@Override
	protected void queries() {
		assertContactCount("All contacts",21, contactDao.countAll());
		assertContactCount("All collection 1 contacts",9, contactDao.countByCollection(contactCollection1));
		assertContactCount("All collection 2 contacts",5, contactDao.countByCollection(contactCollection2));
		assertContactCount("All email contacts",9, contactDao.countByClass(ElectronicMail.class));
		assertContactCount("All phone contacts",12, contactDao.countByClass(PhoneNumber.class));
		assertContactCount("All collection 1 email contacts",2, contactDao.countByCollectionByClass(contactCollection1,ElectronicMail.class));
		assertContactCount("All collection 1 phone contacts",7, contactDao.countByCollectionByClass(contactCollection1,PhoneNumber.class));
		assertContactCount("All collection 2 email contacts",3, contactDao.countByCollectionByClass(contactCollection2,ElectronicMail.class));
		assertContactCount("All collection 2 phone contacts",2, contactDao.countByCollectionByClass(contactCollection2,PhoneNumber.class));
	}
	
	private void mails(ContactCollection collection,String...values){
		for(String value : values)
			create(new ElectronicMail(collection,value));
	}
	private void phones(ContactCollection collection,String...values){
		for(String value : values){ 
			PhoneNumber phoneNumber = new PhoneNumber();
			phoneNumber.setCollection(collection);
			phoneNumber.setCountry(civ);
			phoneNumber.setNumber(value);
			phoneNumber.setType(mobile);
			phoneNumber.setOrderIndex((byte) 0);
			create(phoneNumber);
		}
	}
	
	private void assertContactCount(String message,Integer expected,Long actual){
		Assert.assertEquals(message,expected.intValue(), actual.intValue());
	}

	@Override protected void create() {}
	@Override protected void delete() {}
	@Override protected void read() {}
	@Override protected void update() {}
	
}
