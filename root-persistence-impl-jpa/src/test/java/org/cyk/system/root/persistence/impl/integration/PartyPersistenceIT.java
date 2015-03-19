package org.cyk.system.root.persistence.impl.integration;

import java.util.Date;

import javax.inject.Inject;

import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;
import org.cyk.system.root.persistence.api.party.PersonDao;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class PartyPersistenceIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;

	@Deployment
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	} 
	 
	@Inject private PersonDao personDao;
	
	private void person(String code,String firstName,String lastName,String email){
		Person person = new Person(firstName, lastName);
		person.setCode(code);
		person.setCreationDate(new Date());
		create(new ElectronicMail(person.getContactCollection(), email));
		create(person.getContactCollection());
		create(person);
	}
		
	@Override
	protected void populate() {
		person("1","Paul", "Zadi","a@m.com");
		person("2","Komenan", "NDri","b@m.com");
		person("3","Zoko", "Fula","a@d.com");
	}
	
	private void assertPersonSearch(int expected,String name){
		PersonSearchCriteria searchCriteria = new PersonSearchCriteria(name);
		Assert.assertEquals(expected, personDao.countByCriteria(searchCriteria).intValue());
	}
	
	@Override
	protected void queries() {
		assertPersonSearch(1, "Paul");
		assertPersonSearch(1, "paul");
		assertPersonSearch(1, "pAuL");
		assertPersonSearch(3, "a");
		assertPersonSearch(2, "o");
		assertPersonSearch(1, "e");
		assertPersonSearch(2, "i");
		
		Assert.assertEquals("Komenan NDri", personDao.readByEmail("b@m.com").getNames());
		
	}

	@Override protected void create() {}
	@Override protected void delete() {}
	@Override protected void read() {}
	@Override protected void update() {}
	
	
	
	
}
