package org.cyk.system.root.dao.impl.generic;

import java.sql.SQLIntegrityConstraintViolationException;

import org.cyk.system.root.dao.impl.AbstractCrudIT;
import org.cyk.system.root.dao.impl.AbstractPersistenceService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class GenericDaoCrudIT extends AbstractCrudIT<Person> {
	
	@Deployment
	public static Archive<?> createDeployment() {
		return createDeployment(Person.class/*,PersonDao.class*/);
	}
		
	@Override
	protected Person sampleOne() {
		return new Person("m01", "Ali", "Bamba");
	}
	
	@Override
	protected Person _create_() {
		return new Person("m02", "Ali", "Bamba");
	}
	
	@Test(expected=RuntimeException.class)
	public void createNoUniqueMatriculeConstraintViolation(){
		new Transaction(transaction,(AbstractPersistenceService<?>) genericDao,SQLIntegrityConstraintViolationException.class) {
			@Override
			public void _execute_() {
				Person person = new Person("m01", "Ali", "Bamba");
				genericDao.create(Person.class,person );
				Assert.assertTrue(genericDao.read(Person.class, person.getIdentifier())!=null);
			}
		}.run();
	}
		
	@Override
	protected void _update_(Person model) {
		model.setName("Paul");
	}


}
