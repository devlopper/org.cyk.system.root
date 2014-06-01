package org.cyk.system.root.persistence.impl.integration;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.impl.AbstractPersistenceIT;
import org.cyk.system.root.persistence.impl.data.Person;
import org.cyk.utility.common.computation.Function;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;

public class GenericIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;

	@Deployment
	public static Archive<?> createDeployment() {
		return deployment(new Class<?>[]{Person.class}).getArchive();
	} 
	
	private Long createId;
    private static Person identifiable;
	private static Long pid;
		
	@Override
	protected void populate() {
		create(new Person("m01", "ali", "Bamba"));
		create(identifiable = new Person("m103", "Ange", "Kessi"));
		create(new Person("m123", "ali", "milla"));
		create(new Person("m128", "ali1", "milla1"));
		create(new Person("m129", "ali2", "milla2"));
		create(new Person("m130", "ali3", "milla3"));
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
		Assert.assertTrue("Create", getGenericDao().use(Person.class).read(createId)!=null);
	}

	@Override
	protected void read() {
		Assert.assertTrue("Read", getGenericDao().use(Person.class).read(pid)!=null);
	}

	@Override
	protected void update() {
		((Person)identifiable).setName("Jack");
		update(identifiable);
		Person person = (Person) getGenericDao().use(Person.class).read(identifiable.getIdentifier());
		Assert.assertTrue("Update","Jack".equals(person.getName()));
	}

	@Override
	protected void delete() {
		getGenericDao().delete(identifiable);
		Person person = (Person) getGenericDao().use(Person.class).read(identifiable.getIdentifier());
		Assert.assertTrue("Delete",person==null);
	}
	
	@Override
	protected void queries() {
		Assert.assertEquals(6,getGenericDao().use(Person.class).select().all().size());
		Assert.assertEquals(6,getGenericDao().use(Person.class).select(Function.COUNT).oneLong().intValue());
		
		Assert.assertEquals(2,getGenericDao().use(Person.class).select().where("name","ali").all().size());
		Assert.assertEquals(2,getGenericDao().use(Person.class).select(Function.COUNT).where("name","ali").oneLong().intValue());
		
		Assert.assertEquals(0,getGenericDao().use(Person.class).select().where("identifier",identifiable.getIdentifier()).one()==null?0:1);
		Assert.assertEquals(0,getGenericDao().use(Person.class).select().where("name","georges").one()==null?0:1);
		
		getGenericDao().getDataReadConfig().setMaximumResultCount(2l);
		Assert.assertEquals(2,getGenericDao().use(Person.class).select().all().size());
	}
	
	
	
	
}
