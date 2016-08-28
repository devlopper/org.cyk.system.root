package org.cyk.system.root.persistence.impl.integration;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.junit.Assert;

public class GeographyPersistenceIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;
 
	private ContactCollection contactCollection1,contactCollection2;
	private PhoneNumberType mobile;
	private LocalityType country;
	private Country civ;
	
	@Override
	protected void populate() {
		mobile = create(new PhoneNumberType("MOB", "Mobile"));
		country=create(new LocalityType(null, "C", "Country"));
		Locality lciv = create(new Locality(null, country, "CIV", "Cote d'Ivoire"));
		civ = create(new Country(lciv, 225));
		
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
		assertContactCount("All contacts",21, inject(ContactDao.class).countAll());
		assertContactCount("All collection 1 contacts",9, inject(ContactDao.class).countByCollection(contactCollection1));
		assertContactCount("All collection 2 contacts",5, inject(ContactDao.class).countByCollection(contactCollection2));
		
		assertContactCount("All email contacts",9, inject(ContactDao.class).countByClass(ElectronicMail.class));
		contains(ElectronicMail.class, inject(ContactDao.class).readByClass(ElectronicMail.class), new Object[]{"address"}, new Object[][]{
			 {"z1@mail.com"},{"z2@mail.com"},{"z3@mail.com"},{"z4@mail.com"},{"a1@mail.com"},{"a2@mail.com"},{"b1@mail.com"},{"b2@mail.com"},{"b3@mail.com"}
		});
		
		assertContactCount("All phone contacts",12, inject(ContactDao.class).countByClass(PhoneNumber.class));
		assertContactCount("All collection 1 email contacts",2, inject(ContactDao.class).countByCollectionByClass(contactCollection1,ElectronicMail.class));
		assertContactCount("All collection 1 phone contacts",7, inject(ContactDao.class).countByCollectionByClass(contactCollection1,PhoneNumber.class));
		assertContactCount("All collection 2 email contacts",3, inject(ContactDao.class).countByCollectionByClass(contactCollection2,ElectronicMail.class));
		assertContactCount("All collection 2 phone contacts",2, inject(ContactDao.class).countByCollectionByClass(contactCollection2,PhoneNumber.class));

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
			phoneNumber.setOrderNumber((byte) 0);
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
