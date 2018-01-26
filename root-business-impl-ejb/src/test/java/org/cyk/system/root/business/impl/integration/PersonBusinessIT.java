package org.cyk.system.root.business.impl.integration;

import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_FATHER;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_SON;

import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.business.api.party.PartyIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.business.impl.RootDataProducerHelper;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.persistence.api.party.PartyBusinessRoleDao;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.junit.Test;

public class PersonBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void listenPopulateStart() {
    	super.listenPopulateStart();
    	RootDataProducerHelper.addExcelSheetPersonClasses();
    }
    
    @Test
    public void crudPersonRandomly(){
    	TestCase testCase = instanciateTestCase();
    	Person person = inject(PersonBusiness.class).instanciateOneRandomly("p001");
    	testCase.create(person);
    	testCase.assertCountAll(File.class, 2);
    	testCase.deleteByCode(Person.class, "p001");
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
    	testCase.create(inject(MovementCollectionBusiness.class).instanciateOne().setCode("mc001"));
    	testCase.create(inject(MovementCollectionBusiness.class).instanciateOne().setCode("mc002"));
    	
    	testCase.create(inject(PersonBusiness.class).instanciateOneRandomly("p001"));
    	testCase.create(inject(PersonBusiness.class).instanciateOneRandomly("p002"));
    	
    	PartyIdentifiableGlobalIdentifier partyIdentifiableGlobalIdentifier = new PartyIdentifiableGlobalIdentifier();
    	partyIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifier(testCase.read(MovementCollection.class, "mc001").getGlobalIdentifier())
    		.setParty(testCase.read(Person.class, "p002")).setRole(inject(PartyBusinessRoleDao.class).read(RootConstant.Code.PartyBusinessRole.IN_CHARGE));
    	testCase.create(partyIdentifiableGlobalIdentifier);
    	
    	PartyIdentifiableGlobalIdentifier.Filter filter = new PartyIdentifiableGlobalIdentifier.Filter();
    	assertEquals(1, inject(PartyIdentifiableGlobalIdentifierBusiness.class).findByFilter(filter, new DataReadConfiguration()).size());
    	/*
    	filter = new PartyIdentifiableGlobalIdentifier.Filter();
    	filter.addMaster(testCase.read(Person.class, "p001"));
    	assertEquals(0, inject(PartyIdentifiableGlobalIdentifierBusiness.class).findByFilter(filter, new DataReadConfiguration()).size());
    	
    	filter = new PartyIdentifiableGlobalIdentifier.Filter();
    	filter.addMaster(testCase.read(Person.class, "p002"));
    	assertEquals(1, inject(PartyIdentifiableGlobalIdentifierBusiness.class).findByFilter(filter, new DataReadConfiguration()).size());
    	*/
    	
    	filter = new PartyIdentifiableGlobalIdentifier.Filter();
    	filter.addMaster(testCase.read(MovementCollection.class, "mc001"));
    	assertEquals(1, inject(PartyIdentifiableGlobalIdentifierBusiness.class).findByFilter(filter, new DataReadConfiguration()).size());
    	
    	filter = new PartyIdentifiableGlobalIdentifier.Filter();
    	filter.addMaster(testCase.read(MovementCollection.class, "mc002"));
    	assertEquals(0, inject(PartyIdentifiableGlobalIdentifierBusiness.class).findByFilter(filter, new DataReadConfiguration()).size());
    	
    	testCase.clean();
    }

}
