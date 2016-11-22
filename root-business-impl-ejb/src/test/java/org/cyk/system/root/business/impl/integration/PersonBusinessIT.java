package org.cyk.system.root.business.impl.integration;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.cyk.system.root.business.api.geography.ContactBusiness;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.geography.ElectronicMailBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeBusiness;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.api.geography.ElectronicMailDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeDao;
import org.junit.Test;

public class PersonBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void businesses() {}

    @Test
    public void relationship(){
    	String p1="p1",p2="p2";
    	Set<String> codes = new LinkedHashSet<>();
    	codes.add(p1);
    	codes.add(p2);
    	create(inject(PersonBusiness.class).instanciateManyRandomly(codes));
    	
    	PersonRelationship personRelationship1;
    	create( personRelationship1 = new PersonRelationship(inject(PersonBusiness.class).find(p1),inject(PersonRelationshipTypeBusiness.class).find(PersonRelationshipType.FAMILY_FATHER)
    			,inject(PersonBusiness.class).find(p2)));
    	
    	delete(personRelationship1);
    }
    
    @Test
    public void crudPersonAndRelationships(){
    	Person son = inject(PersonBusiness.class).instanciateOne();
    	son.setCode("P001");
    	son.setName("Komenan");
    	son.setLastnames("Yao Christian");
    	son.addContact(inject(ElectronicMailBusiness.class).instanciateOne((ContactCollection)null, "stud@gmail.com"));
    	
    	Person father = inject(PersonBusiness.class).addRelationship(son, PersonRelationshipType.FAMILY_FATHER).getPerson1();
    	father.setName("KOMENAN");
    	father.setLastnames("N'Dri Jean");
    	father.addContact(inject(ElectronicMailBusiness.class).instanciateOne((ContactCollection)null, "dad@gmail.com"));
    	
    	Person mother = inject(PersonBusiness.class).addRelationship(son, PersonRelationshipType.FAMILY_MOTHER).getPerson1();
    	mother.setName("komenan");
    	mother.setLastnames("Ahou odette");
    	mother.addContact(inject(ElectronicMailBusiness.class).instanciateOne((ContactCollection)null, "mom@gmail.com"));
    	
    	create(son);
    	
    	assertThat("P001 exists", (son=inject(PersonBusiness.class).find("P001"))!=null);
    	Collection<PersonRelationship> fathers = inject(PersonRelationshipDao.class).readByPerson2ByType(son,inject(PersonRelationshipTypeDao.class)
    			.read(PersonRelationshipType.FAMILY_FATHER));
    	assertEquals("Number of father", 1, fathers.size());
    	father = fathers.iterator().next().getPerson1();
    	
    	Collection<PersonRelationship> mothers = inject(PersonRelationshipDao.class).readByPerson2ByType(son,inject(PersonRelationshipTypeDao.class)
    			.read(PersonRelationshipType.FAMILY_MOTHER));
    	assertEquals("Number of mother", 1, mothers.size());
    	mother = mothers.iterator().next().getPerson1();
    	
    	assertEquals("Son email", "stud@gmail.com", inject(ContactDao.class).readByCollectionByClass(son.getContactCollection(), ElectronicMail.class).iterator().next().getAddress());
    	assertEquals("Father email", "dad@gmail.com", inject(ContactDao.class).readByCollectionByClass(father.getContactCollection(), ElectronicMail.class).iterator().next().getAddress());
    	assertEquals("Mother email", "mom@gmail.com", inject(ContactDao.class).readByCollectionByClass(mother.getContactCollection(), ElectronicMail.class).iterator().next().getAddress());
    	
    	Collection<ElectronicMail> parentElectronicMails = inject(ContactBusiness.class).findByCollectionsByClass(Arrays.asList(father.getContactCollection(),mother.getContactCollection())
    			, ElectronicMail.class);
    	contains(ElectronicMail.class, parentElectronicMails, new Object[]{"address"}, new Object[][]{ {"dad@gmail.com"},{"mom@gmail.com"} });
    
    }
    
    @Test
    public void crudPersonFatherAndMother(){
    	Long electronicMailCountBeforeCreateSon = inject(ElectronicMailDao.class).countAll();
    	Person son = inject(PersonBusiness.class).instanciateOne(),father,mother;
    	son.setName("Komenan");
    	son.setLastnames("Yao Christian");
    	inject(ElectronicMailBusiness.class).setAddress(son, PersonRelationshipType.FAMILY_FATHER, "f@m.com");
    	inject(ElectronicMailBusiness.class).setAddress(son, PersonRelationshipType.FAMILY_MOTHER, "mom@c.com");
    	create(son);
    	assertEquals(electronicMailCountBeforeCreateSon+2, inject(ElectronicMailDao.class).countAll());
    	father = inject(PersonBusiness.class).findOneByPersonByRelationshipType(son, PersonRelationshipType.FAMILY_FATHER);
    	mother = inject(PersonBusiness.class).findOneByPersonByRelationshipType(son, PersonRelationshipType.FAMILY_MOTHER);
    	
    	String sonCode = son.getCode(),fatherCode = father.getCode(),motherCode = mother.getCode();
    	rootBusinessTestHelper.assertCodeExists(Person.class, sonCode);
    	rootBusinessTestHelper.assertCodeExists(Person.class, fatherCode);
    	rootBusinessTestHelper.assertCodeExists(Person.class, motherCode);
    	
    	Collection<PersonRelationship> fathers = inject(PersonRelationshipDao.class).readByPerson2ByType(son,inject(PersonRelationshipTypeDao.class)
    			.read(PersonRelationshipType.FAMILY_FATHER));
    	assertEquals("Number of father", 1, fathers.size());
    	father = fathers.iterator().next().getPerson1();
    	
    	Collection<PersonRelationship> mothers = inject(PersonRelationshipDao.class).readByPerson2ByType(son,inject(PersonRelationshipTypeDao.class)
    			.read(PersonRelationshipType.FAMILY_MOTHER));
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
    			.read(PersonRelationshipType.FAMILY_FATHER));
    	assertEquals("Number of father", 1, fathers.size());
    	father = fathers.iterator().next().getPerson1();
    	
    	mothers = inject(PersonRelationshipDao.class).readByPerson2ByType(son,inject(PersonRelationshipTypeDao.class)
    			.read(PersonRelationshipType.FAMILY_MOTHER));
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
    }
    
    @Test
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
