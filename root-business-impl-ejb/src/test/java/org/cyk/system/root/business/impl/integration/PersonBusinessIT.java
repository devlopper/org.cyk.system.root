package org.cyk.system.root.business.impl.integration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.cyk.system.root.business.api.geography.ContactBusiness;
import org.cyk.system.root.business.api.geography.ElectronicMailBusiness;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.api.message.MessageSendingBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeBusiness;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeDao;
import org.junit.Test;

public class PersonBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void populate() {
    	super.populate();
    	inject(MailBusiness.class).setProperties("smtp.gmail.com", 465, "kycdev@gmail.com", "p@ssw0rd*");
    }
    
    @Override
    protected void businesses() {
    	/*for(int i=0;i<20;i++){
    		Person person = RootRandomDataProvider.getInstance().person();
    		//debug(person.getExtendedInformations());
        	//debug(person.getJobInformations());
    		create(person);
    	}
    	
    	DataReadConfiguration dataReadConfig = new DataReadConfiguration();
    	dataReadConfig.setMaximumResultCount(3l);
    	assertEquals("Count", 3, personBusiness.findAll(dataReadConfig).size());
    	
    	dataReadConfig = new DataReadConfiguration();
    	dataReadConfig.setMaximumResultCount(4l);
    	assertEquals("Count", 4, personBusiness.findAll(dataReadConfig).size());
    	
    	dataReadConfig = new DataReadConfiguration();
    	assertEquals("Count", 21, personBusiness.findAll(dataReadConfig).size());
    	
    	
    	Person person = RootRandomDataProvider.getInstance().oneFromDatabase(Person.class);
    	person = personBusiness.load(person.getIdentifier());
    	//debug(person.getExtendedInformations());
    	//debug(person.getJobInformations());
    	*/
    }

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
    public void sendMailToParents(){
    	Person son = inject(PersonBusiness.class).instanciateOne();
    	son.setCode("P002");
    	son.setName("Komenan");
    	son.setLastnames("Yao Christian");
    	son.addContact(inject(ElectronicMailBusiness.class).instanciateOne((ContactCollection)null, "kycdev1@gmail.com"));
    	
    	Person father = inject(PersonBusiness.class).addRelationship(son, PersonRelationshipType.FAMILY_FATHER).getPerson1();
    	father.setName("Kouagni");
    	father.setLastnames("N'Dri Jean");
    	father.addContact(inject(ElectronicMailBusiness.class).instanciateOne((ContactCollection)null, "kycdev2@gmail.com"));
    	
    	Person mother = inject(PersonBusiness.class).addRelationship(son, PersonRelationshipType.FAMILY_MOTHER).getPerson1();
    	mother.setName("Tchimou");
    	mother.setLastnames("Ahou odette");
    	mother.addContact(inject(ElectronicMailBusiness.class).instanciateOne((ContactCollection)null, "kycdev@gmail.com"));
    	
    	create(son);
    	
    	Collection<ElectronicMail> parentElectronicMails = inject(ContactBusiness.class).findByCollectionsByClass(Arrays.asList(father.getContactCollection(),mother.getContactCollection())
    			, ElectronicMail.class);
    	
    	MessageSendingBusiness.SendOptions.BLOCKING=Boolean.TRUE;
		Notification notification = new Notification();
		notification.setDate(new Date());
		notification.setTitle("TestTitle");
		notification.setMessage("TestMessage");
		inject(MailBusiness.class).send(notification, inject(ElectronicMailBusiness.class).findAddresses(parentElectronicMails));
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
