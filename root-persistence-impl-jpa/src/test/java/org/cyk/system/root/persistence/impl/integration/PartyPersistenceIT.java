package org.cyk.system.root.persistence.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.model.party.Person;
import org.cyk.system.root.model.party.PersonSearchCriteria;
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
	
	private void person(String firstName,String lastName){
		Person person = new Person(firstName, lastName);
		person.setContactCollection(null);
		create(person);
	}
		
	@Override
	protected void populate() {
		person("Paul", "Zadi");
		person("Komenan", "NDri");
		person("Zoko", "Fula");
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
	}

	@Override
	protected void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void read() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
