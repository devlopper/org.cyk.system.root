package org.cyk.system.root.business.impl.integration;

import static org.cyk.system.root.model.RootConstant.Code.BusinessRole.IN_CHARGE;
import static org.cyk.system.root.model.RootConstant.Code.BusinessRole.PROVIDER;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_FATHER;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_SON;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.persistence.api.party.PartyIdentifiableGlobalIdentifierDao;
import org.cyk.utility.common.helper.ClassHelper;
import org.junit.Test;

public class PersonBusinessIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;
    
    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    @Test
    public void crudPerson(){
    	TestCase testCase = instanciateTestCase();
    	String personCode = testCase.getRandomAlphabetic();
    	testCase.assertCountAll(File.class, 0);
    	testCase.create(testCase.instanciateOne(Person.class,personCode));
    	testCase.assertCountAll(File.class, 0);
    	testCase.deleteByCode(Person.class, personCode);
    	testCase.clean();
    }
    
    @Test
    public void crudPersonWithContactCollection(){
    	TestCase testCase = instanciateTestCase();
    	String personCode = testCase.getRandomAlphabetic();
    	testCase.assertCountAll(File.class, 0);
    	testCase.create(testCase.instanciateOne(Person.class,personCode).setContactCollection(
    			testCase.instanciateOne(ContactCollection.class))
    			);
    	testCase.assertCountAll(File.class, 0);
    	testCase.deleteByCode(Person.class, personCode);
    	testCase.clean();
    }
    
    @Test
    public void crudPersonWithRandomContactCollection(){
    	TestCase testCase = instanciateTestCase();
    	String personCode = testCase.getRandomAlphabetic();
    	testCase.assertCountAll(File.class, 0);
    	testCase.create(testCase.instanciateOne(Person.class,personCode).setContactCollection(
    			inject(ContactCollectionBusiness.class).instanciateOneRandomly())
    			);
    	testCase.assertCountAll(File.class, 0);
    	testCase.deleteByCode(Person.class, personCode);
    	testCase.clean();
    }
    
    @Test
    public void crudPersonRandomly(){
    	TestCase testCase = instanciateTestCase();
    	String personCode = testCase.getRandomHelper().getAlphabetic(5);
    	Person person = inject(PersonBusiness.class).instanciateOneRandomly(personCode);
    	testCase.create(person);
    	testCase.assertCountAll(File.class, 2);
    	testCase.deleteByCode(Person.class, personCode);
    	testCase.clean();
    }
    
    //@Test
    public void crudPersonRelationshipRandomly(){
    	TestCase testCase = instanciateTestCase();
    	String f1="f1",s1="s1",f2="f2",s2="s2";
    	testCase.createManyPersonRandomly(f1,s1,f2,s2);
 
    	PersonRelationship personRelationship1 = inject(PersonRelationshipBusiness.class).instanciateOne(f1, FAMILY_PARENT_FATHER, s1, FAMILY_PARENT_SON);
    	testCase.create(personRelationship1);
    	
    	PersonRelationship personRelationship2 = inject(PersonRelationshipBusiness.class).instanciateOne(f2, FAMILY_PARENT_FATHER, s2, FAMILY_PARENT_SON);
    	testCase.create(personRelationship2);
    	
    	testCase.assertPersonRelationship(f1, FAMILY_PARENT_FATHER, FAMILY_PARENT_SON, new String[]{s1});
    	testCase.assertPersonRelationship(f2, FAMILY_PARENT_FATHER, FAMILY_PARENT_SON, new String[]{s2});
    	
    	testCase.delete(personRelationship1);
    	testCase.delete(personRelationship2);
    	
    	testCase.clean();
    }
    
    @Test
    public void crudPersonRelationship(){
    	TestCase testCase = instanciateTestCase();
    	testCase.createOnePerson("JNK","Komenan","N'Dri Jean","dad@gmail.com");
    	testCase.createOnePerson("OAK","Komenan","Ahou odette","mom@gmail.com");
    	testCase.createOnePerson("CYK","Komenan","Yao Christian","stud@gmail.com");	
    	testCase.createFamilyPersonRelationship("JNK", "OAK", new String[]{"CYK"}, null);
    	testCase.clean();
    }
        
    @Test
    public void createPersonWithExistingCode(){
    	TestCase testCase = instanciateTestCase();
    	testCase.create(inject(PersonBusiness.class).instanciateOne().setCode("ABC"));
    	testCase.create(inject(PersonBusiness.class).instanciateOne().setCode("ABC"),"Un enregistrement de type personne avec pour code <<ABC>> existe déja.");
		testCase.clean();
    }
    
    @Test
    public void crudPersonAndBusinessRole(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode01 = testCase.getRandomAlphabetic();
    	String movementCollectionCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode01));
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode02));
    	
    	String personCode01 = testCase.getRandomAlphabetic();
    	String personCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Person.class,personCode01));
    	testCase.create(testCase.instanciateOne(Person.class,personCode02));
    	
    	PartyIdentifiableGlobalIdentifier partyIdentifiableGlobalIdentifier = new PartyIdentifiableGlobalIdentifier();
    	partyIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifierFromCode(MovementCollection.class, movementCollectionCode01)
    		.setPartyFromCode(personCode02).setBusinessRoleFromCode(IN_CHARGE);
    	testCase.create(partyIdentifiableGlobalIdentifier);
    	
    	assertEquals(1l, inject(PartyIdentifiableGlobalIdentifierDao.class).countByFilter(new PartyIdentifiableGlobalIdentifier.Filter()));
    	
    	assertEquals(1l, inject(PartyIdentifiableGlobalIdentifierDao.class).countByFilter(new PartyIdentifiableGlobalIdentifier.Filter()
    			.addMasterIdentifiableGlobalIdentifier(MovementCollection.class, movementCollectionCode01)));
    	
    	assertEquals(0l, inject(PartyIdentifiableGlobalIdentifierDao.class).countByFilter(new PartyIdentifiableGlobalIdentifier.Filter()
    			.addMasterIdentifiableGlobalIdentifier(MovementCollection.class, movementCollectionCode02)));
    	
    	assertEquals(0l, inject(PartyIdentifiableGlobalIdentifierDao.class).countByFilter(new PartyIdentifiableGlobalIdentifier.Filter()
    			.addMaster(Person.class, personCode01)));
    	
    	assertEquals(1l, inject(PartyIdentifiableGlobalIdentifierDao.class).countByFilter(new PartyIdentifiableGlobalIdentifier.Filter()
    			.addMaster(Person.class, personCode02)
    			));
    	
    	assertEquals(0l, inject(PartyIdentifiableGlobalIdentifierDao.class).countByFilter(new PartyIdentifiableGlobalIdentifier.Filter()
    			.addMaster(BusinessRole.class, PROVIDER)));
    	
    	assertEquals(1l, inject(PartyIdentifiableGlobalIdentifierDao.class).countByFilter(new PartyIdentifiableGlobalIdentifier.Filter()
    			.addMaster(BusinessRole.class, IN_CHARGE)
    			));
    	
    	testCase.clean();
    }
    
    /**/
    
    @SuppressWarnings("unchecked")
	public static class Data extends DataSet.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@SuppressWarnings({ "rawtypes" })
		@Override
		public Collection getClasses() {
			return Arrays.asList(Party.class);
		}
		
    }

}
