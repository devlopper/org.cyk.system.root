package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.junit.Test;

public class PersonBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void businesses() {}

    @Test
    public void crudPersonRandomly(){
    	TestCase testCase = instanciateTestCase();
    	//testCase.createManyPersonRandomly(new String[]{"FATHER01F1","MOTHER01F1","SON01F1","SON02F1","DAUGHTER01F1"});//Family 1
    	testCase.createOnePersonRandomly("FATHER01F1");
    	
    	testCase.clean();
    }
    
    @Test
    public void crudPerson(){
    	
    }
    
    @Test
    public void relationship(){
    	TestCase testCase = instanciateTestCase();
    	String f1="f1",s1="s1",f2="f2",s2="s2";
    	testCase.createManyPersonRandomly(f1,s1,f2,s2);
    	
    	PersonRelationship personRelationship1 = inject(PersonRelationshipBusiness.class).instanciateOne(f1, RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_FATHER
    			, s1, RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_SON);
    	testCase.create(personRelationship1);
    	
    	PersonRelationship personRelationship2 = inject(PersonRelationshipBusiness.class).instanciateOne(f2, RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_FATHER
    			, s2, RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_SON);
    	testCase.create(personRelationship2);
    	
    	testCase.assertPersonRelationship(f1, RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_FATHER, RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_SON
    			, new String[]{s1});
    	testCase.assertPersonRelationship(f2, RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_FATHER, RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_SON
    			, new String[]{s2});
    	
    	testCase.delete(personRelationship1);
    	testCase.delete(personRelationship2);
    	
    	testCase.clean();
    }
    
    @Test
    public void crudPersonAndRelationships(){
    	TestCase testCase = instanciateTestCase();
    	testCase.createOnePerson("JNK","Komenan","N'Dri Jean","dad@gmail.com");
    	testCase.createOnePerson("OAK","Komenan","Ahou odette","mom@gmail.com");
    	testCase.createOnePerson("CYK","Komenan","Yao Christian","stud@gmail.com");
    	
    	testCase.createFamilyPersonRelationship("JNK", "OAK", new String[]{"CYK"}, null);
    	
    	testCase.clean();
    	
    }
    
    //@Test
    public void crudPersonFatherAndMother(){
    	/*Long electronicMailCountBeforeCreateSon = inject(ElectronicMailDao.class).countAll();
    	Person son = inject(PersonBusiness.class).instanciateOne(),father,mother;
    	son.setName("Komenan");
    	son.setLastnames("Yao Christian");
    	inject(ElectronicMailBusiness.class).setAddress(son, RootConstant.Code.PersonRelationshipType.FAMILY_FATHER, "f@m.com");
    	inject(ElectronicMailBusiness.class).setAddress(son, RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER, "mom@c.com");
    	create(son);
    	assertEquals(electronicMailCountBeforeCreateSon+2, inject(ElectronicMailDao.class).countAll());
    	father = inject(PersonBusiness.class).findOneByPersonByRelationshipType(son, RootConstant.Code.PersonRelationshipType.FAMILY_FATHER);
    	mother = inject(PersonBusiness.class).findOneByPersonByRelationshipType(son, RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER);
    	
    	String sonCode = son.getCode(),fatherCode = father.getCode(),motherCode = mother.getCode();
    	rootBusinessTestHelper.assertCodeExists(Person.class, sonCode);
    	rootBusinessTestHelper.assertCodeExists(Person.class, fatherCode);
    	rootBusinessTestHelper.assertCodeExists(Person.class, motherCode);
    	
    	Collection<PersonRelationship> fathers = inject(PersonRelationshipDao.class).readByPerson2ByType(son,inject(PersonRelationshipTypeDao.class)
    			.read(RootConstant.Code.PersonRelationshipType.FAMILY_FATHER));
    	assertEquals("Number of father", 1, fathers.size());
    	father = fathers.iterator().next().getPerson1();
    	
    	Collection<PersonRelationship> mothers = inject(PersonRelationshipDao.class).readByPerson2ByType(son,inject(PersonRelationshipTypeDao.class)
    			.read(RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER));
    	assertEquals("Number of mother", 1, mothers.size());
    	mother = mothers.iterator().next().getPerson1();
    	
    	assertEquals("Father email", "f@m.com", inject(ElectronicMailBusiness.class).findAddress(father));
    	assertEquals("Mother email", "mom@c.com", inject(ElectronicMailBusiness.class).findAddress(mother));
    	
    	Collection<ElectronicMail> parentElectronicMails = inject(ContactBusiness.class).findByCollectionsByClass(Arrays.asList(father.getContactCollection(),mother.getContactCollection())
    			, ElectronicMail.class);
    	contains(ElectronicMail.class, parentElectronicMails, new Object[]{"address"}, new Object[][]{ {"f@m.com"},{"mom@c.com"} });
    
    	son = inject(PersonBusiness.class).find(sonCode);
    	inject(ContactCollectionBusiness.class).load(father.getContactCollection());
    	inject(ContactCollectionBusiness.class).load(mother.getContactCollection());
    	assertEquals("Father email", "f@m.com", inject(ContactCollectionBusiness.class).getElectronicMail(father.getContactCollection()));
    	assertEquals("Mother email", "mom@c.com", inject(ContactCollectionBusiness.class).getElectronicMail(mother.getContactCollection()));
    	inject(ContactCollectionBusiness.class).setElectronicMail(father.getContactCollection(),"f2@m.com");
    	update(son,Arrays.asList(father,mother));
    	assertEquals(electronicMailCountBeforeCreateSon+2, inject(ElectronicMailDao.class).countAll());
    	fathers = inject(PersonRelationshipDao.class).readByPerson2ByType(son,inject(PersonRelationshipTypeDao.class)
    			.read(RootConstant.Code.PersonRelationshipType.FAMILY_FATHER));
    	assertEquals("Number of father", 1, fathers.size());
    	father = fathers.iterator().next().getPerson1();
    	
    	mothers = inject(PersonRelationshipDao.class).readByPerson2ByType(son,inject(PersonRelationshipTypeDao.class)
    			.read(RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER));
    	assertEquals("Number of mother", 1, mothers.size());
    	mother = mothers.iterator().next().getPerson1();
    	
    	assertEquals("Father email", "f2@m.com", inject(ContactDao.class).readByCollectionByClass(father.getContactCollection(), ElectronicMail.class).iterator().next().getAddress());
    	assertEquals("Mother email", "mom@c.com", inject(ContactDao.class).readByCollectionByClass(mother.getContactCollection(), ElectronicMail.class).iterator().next().getAddress());
    	
    	parentElectronicMails = inject(ContactBusiness.class).findByCollectionsByClass(Arrays.asList(father.getContactCollection(),mother.getContactCollection())
    			, ElectronicMail.class);
    	contains(ElectronicMail.class, parentElectronicMails, new Object[]{"address"}, new Object[][]{ {"f2@m.com"},{"mom@c.com"} });
    	
    	son = inject(PersonBusiness.class).find(sonCode);
    	father = inject(PersonBusiness.class).find(fatherCode);
    	mother = inject(PersonBusiness.class).find(motherCode);
    	inject(ContactCollectionBusiness.class).load(father.getContactCollection());
    	inject(ContactCollectionBusiness.class).load(mother.getContactCollection());
    	assertEquals("Father email", "f2@m.com", inject(ContactCollectionBusiness.class).getElectronicMail(father.getContactCollection()));
    	assertEquals("Mother email", "mom@c.com", inject(ContactCollectionBusiness.class).getElectronicMail(mother.getContactCollection()));
    	inject(ContactCollectionBusiness.class).setElectronicMail(father.getContactCollection(),null);
    	update(son,Arrays.asList(father,mother));
    	assertEquals(electronicMailCountBeforeCreateSon+1, inject(ElectronicMailDao.class).countAll());
    	assertEquals("Father email", 0, inject(ContactDao.class).readByCollectionByClass(father.getContactCollection(), ElectronicMail.class).size());
    	assertEquals("Mother email", "mom@c.com", inject(ContactDao.class).readByCollectionByClass(mother.getContactCollection(), ElectronicMail.class).iterator().next().getAddress());
    	
    	son = inject(PersonBusiness.class).find(sonCode);
    	father = inject(PersonBusiness.class).find(fatherCode);
    	mother = inject(PersonBusiness.class).find(motherCode);
    	inject(ContactCollectionBusiness.class).load(father.getContactCollection());
    	inject(ContactCollectionBusiness.class).load(mother.getContactCollection());
    	assertEquals("Father email", null, inject(ContactCollectionBusiness.class).getElectronicMail(father.getContactCollection()));
    	assertEquals("Mother email", "mom@c.com", inject(ContactCollectionBusiness.class).getElectronicMail(mother.getContactCollection()));
    	inject(ContactCollectionBusiness.class).setElectronicMail(father.getContactCollection(),"y@t.o");
    	update(son,Arrays.asList(father,mother));
    	assertEquals(electronicMailCountBeforeCreateSon+2, inject(ElectronicMailDao.class).countAll());
    	assertEquals("Father email", "y@t.o", inject(ContactDao.class).readByCollectionByClass(father.getContactCollection(), ElectronicMail.class).iterator().next().getAddress());
    	assertEquals("Mother email", "mom@c.com", inject(ContactDao.class).readByCollectionByClass(mother.getContactCollection(), ElectronicMail.class).iterator().next().getAddress());
    	*/
    }
    
    //@Test
    public void crud2PersonsSameFatherAndSameMother(){
    	/*Long electronicMailCountBeforeCreateSon = inject(ElectronicMailDao.class).countAll();
    	Person son1 = inject(PersonBusiness.class).instanciateOne(),father,mother;
    	son1.setName("Komenan");
    	son1.setLastnames("Yao Christian");
    	inject(ElectronicMailBusiness.class).setAddress(son1, RootConstant.Code.PersonRelationshipType.FAMILY_FATHER, "fn2@m.com");
    	inject(ElectronicMailBusiness.class).setAddress(son1, RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER, "momn2@c.com");
    	create(son1);
    	assertEquals(electronicMailCountBeforeCreateSon+2, inject(ElectronicMailDao.class).countAll());
    	father = inject(PersonBusiness.class).findOneByPersonByRelationshipType(son1, RootConstant.Code.PersonRelationshipType.FAMILY_FATHER);
    	mother = inject(PersonBusiness.class).findOneByPersonByRelationshipType(son1, RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER);
    	
    	String son1Code = son1.getCode(),fatherCode = father.getCode(),motherCode = mother.getCode();
    	rootBusinessTestHelper.assertCodeExists(Person.class, son1Code);
    	rootBusinessTestHelper.assertCodeExists(Person.class, fatherCode);
    	rootBusinessTestHelper.assertCodeExists(Person.class, motherCode);
    	
    	Collection<PersonRelationship> fathers = inject(PersonRelationshipDao.class).readByPerson2ByType(son1,inject(PersonRelationshipTypeDao.class)
    			.read(RootConstant.Code.PersonRelationshipType.FAMILY_FATHER));
    	assertEquals("Number of father", 1, fathers.size());
    	father = fathers.iterator().next().getPerson1();
    	
    	Collection<PersonRelationship> mothers = inject(PersonRelationshipDao.class).readByPerson2ByType(son1,inject(PersonRelationshipTypeDao.class)
    			.read(RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER));
    	assertEquals("Number of mother", 1, mothers.size());
    	mother = mothers.iterator().next().getPerson1();
    	
    	assertEquals("Father email", "fn2@m.com", inject(ElectronicMailBusiness.class).findAddress(father));
    	assertEquals("Mother email", "momn2@c.com", inject(ElectronicMailBusiness.class).findAddress(mother));
    	
    	Collection<ElectronicMail> parentElectronicMails = inject(ContactBusiness.class).findByCollectionsByClass(Arrays.asList(father.getContactCollection(),mother.getContactCollection())
    			, ElectronicMail.class);
    	contains(ElectronicMail.class, parentElectronicMails, new Object[]{"address"}, new Object[][]{ {"fn2@m.com"},{"momn2@c.com"} });
    
    	Person son2 = inject(PersonBusiness.class).instanciateOne();
    	son2.setName("Komenan");
    	son2.setLastnames("Emmanuel");
    	inject(ElectronicMailBusiness.class).setAddress(son2, RootConstant.Code.PersonRelationshipType.FAMILY_FATHER, "fn2@m.com");
    	inject(ElectronicMailBusiness.class).setAddress(son2, RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER, "momn2@c.com");
    	create(son2);
    	assertEquals(electronicMailCountBeforeCreateSon+2, inject(ElectronicMailDao.class).countAll());
    	father = inject(PersonBusiness.class).findOneByPersonByRelationshipType(son2, RootConstant.Code.PersonRelationshipType.FAMILY_FATHER);
    	mother = inject(PersonBusiness.class).findOneByPersonByRelationshipType(son2, RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER);
    	
    	String son2Code = son2.getCode();
    	rootBusinessTestHelper.assertCodeExists(Person.class, son2Code);
    	rootBusinessTestHelper.assertCodeExists(Person.class, fatherCode);
    	rootBusinessTestHelper.assertCodeExists(Person.class, motherCode);
    	
    	fathers = inject(PersonRelationshipDao.class).readByPerson2ByType(son2,inject(PersonRelationshipTypeDao.class).read(RootConstant.Code.PersonRelationshipType.FAMILY_FATHER));
    	assertEquals("Number of father", 1, fathers.size());
    	father = fathers.iterator().next().getPerson1();
    	
    	mothers = inject(PersonRelationshipDao.class).readByPerson2ByType(son2,inject(PersonRelationshipTypeDao.class).read(RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER));
    	assertEquals("Number of mother", 1, mothers.size());
    	mother = mothers.iterator().next().getPerson1();
    	
    	assertEquals("Father email", "fn2@m.com", inject(ElectronicMailBusiness.class).findAddress(father));
    	assertEquals("Mother email", "momn2@c.com", inject(ElectronicMailBusiness.class).findAddress(mother));
    	
    	parentElectronicMails = inject(ContactBusiness.class).findByCollectionsByClass(Arrays.asList(father.getContactCollection(),mother.getContactCollection())
    			, ElectronicMail.class);
    	contains(ElectronicMail.class, parentElectronicMails, new Object[]{"address"}, new Object[][]{ {"fn2@m.com"},{"momn2@c.com"} });
    	
    	Person son3 = inject(PersonBusiness.class).instanciateOne();
    	son3.setName("Komenan");
    	son3.setLastnames("Agathe");
    	inject(ElectronicMailBusiness.class).setAddress(son3, RootConstant.Code.PersonRelationshipType.FAMILY_FATHER, "fn2@m.com");
    	inject(ElectronicMailBusiness.class).setAddress(son3, RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER, "mom3@c.com");
    	create(son3);
    	assertEquals(electronicMailCountBeforeCreateSon+3, inject(ElectronicMailDao.class).countAll());
    	father = inject(PersonBusiness.class).findOneByPersonByRelationshipType(son3, RootConstant.Code.PersonRelationshipType.FAMILY_FATHER);
    	mother = inject(PersonBusiness.class).findOneByPersonByRelationshipType(son3, RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER);
    	
    	String son3Code = son3.getCode();
    	rootBusinessTestHelper.assertCodeExists(Person.class, son3Code);
    	rootBusinessTestHelper.assertCodeExists(Person.class, fatherCode);
    	rootBusinessTestHelper.assertCodeExists(Person.class, motherCode);
    	
    	fathers = inject(PersonRelationshipDao.class).readByPerson2ByType(son3,inject(PersonRelationshipTypeDao.class).read(RootConstant.Code.PersonRelationshipType.FAMILY_FATHER));
    	assertEquals("Number of father", 1, fathers.size());
    	father = fathers.iterator().next().getPerson1();
    	
    	mothers = inject(PersonRelationshipDao.class).readByPerson2ByType(son3,inject(PersonRelationshipTypeDao.class).read(RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER));
    	assertEquals("Number of mother", 1, mothers.size());
    	mother = mothers.iterator().next().getPerson1();
    	
    	assertEquals("Father email", "fn2@m.com", inject(ElectronicMailBusiness.class).findAddress(father));
    	assertEquals("Mother email", "mom3@c.com", inject(ElectronicMailBusiness.class).findAddress(mother));
    	
    	parentElectronicMails = inject(ContactBusiness.class).findByCollectionsByClass(Arrays.asList(father.getContactCollection(),mother.getContactCollection())
    			, ElectronicMail.class);
    	contains(ElectronicMail.class, parentElectronicMails, new Object[]{"address"}, new Object[][]{ {"fn2@m.com"},{"mom3@c.com"} });
    	*/
    }
    
    //@Test
    public void exceptionCodeExist(){
    	Person person = inject(PersonBusiness.class).instanciateOne();
    	person.setCode("ABC");
    	create(person);
    	
    	final Person person2 = inject(PersonBusiness.class).instanciateOne();
    	person2.setCode("ABC");
    	new org.cyk.utility.common.test.TestEnvironmentListener.Try("Un enregistrement avec pour code = abc existe déja"){ 
			private static final long serialVersionUID = -8176804174113453706L;
			@Override protected void code() {create(person2);}
		}.execute();
    }

}
