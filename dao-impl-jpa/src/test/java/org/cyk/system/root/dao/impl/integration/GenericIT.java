package org.cyk.system.root.dao.impl.integration;

import org.cyk.system.root.dao.impl.AbstractPersistenceIT;
import org.cyk.system.root.dao.impl.Person;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.computation.Function;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;

public class GenericIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;

	@Deployment
	public static Archive<?> createDeployment() {
		return createDeployment(new Class<?>[]{Person.class});
	}
	
	private Long createId;
	private static Long pid;
		
	@Override
	protected void populate() {
		create(new Person("m01", "ali", "Bamba"));
		create(identifiable = new Person("m103", "Ange", "Kessi"));
		create(new Person("m123", "ali", "milla"));
		pid = identifiable.getIdentifier();
	}
					
	@Test(expected=RuntimeException.class)
	public void oneManyResult() {
		getGenericDao().use(Person.class).select().where("name","ali").one();
	}

	// CRUD 
	
	@Override
	protected void create() {
		AbstractIdentifiable identifiable;
		create(identifiable = new Person("m21", "Roger", "milla"));
		createId = identifiable.getIdentifier();
		Assert.assertTrue("Create", getGenericDao().read(Person.class, createId)!=null);
	}

	@Override
	protected void read() {
		Assert.assertTrue("Read", getGenericDao().read(Person.class, pid)!=null);
	}

	@Override
	protected void update() {
		((Person)identifiable).setName("Jack");
		update(identifiable);
		Person person = (Person) getGenericDao().read(Person.class, identifiable.getIdentifier());
		Assert.assertTrue("Update","Jack".equals(person.getName()));
	}

	@Override
	protected void delete() {
		getGenericDao().delete(Person.class, identifiable);
		Person person = (Person) getGenericDao().read(Person.class, identifiable.getIdentifier());
		Assert.assertTrue("Delete",person==null);
	}
	
	@Override
	protected void queries() {
		Assert.assertEquals(3,getGenericDao().use(Person.class).select().all().size());
		Assert.assertEquals(3,getGenericDao().use(Person.class).select(Function.COUNT).oneLong().intValue());
		
		Assert.assertEquals(2,getGenericDao().use(Person.class).select().where("name","ali").all().size());
		Assert.assertEquals(2,getGenericDao().use(Person.class).select(Function.COUNT).where("name","ali").oneLong().intValue());
		
		Assert.assertEquals(0,getGenericDao().use(Person.class).select().where("identifier",identifiable.getIdentifier()).one()==null?0:1);
		Assert.assertEquals(0,getGenericDao().use(Person.class).select().where("name","georges").one()==null?0:1);
	}
	
}
