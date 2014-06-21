package org.cyk.system.root.persistence.impl.integration;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.impl.AbstractPersistenceIT;
import org.cyk.system.root.persistence.impl.data.PersonTest;
import org.cyk.utility.common.computation.Function;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;

public class GenericIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;

	@Deployment
	public static Archive<?> createDeployment() {
		return deployment(new Class<?>[]{PersonTest.class}).getArchive();
	} 
	
	private Long createId;
    private static PersonTest identifiable;
	private static Long pid;
		
	@Override
	protected void populate() {
		create(new PersonTest("m01", "ali", "Bamba"));
		create(identifiable = new PersonTest("m103", "Ange", "Kessi"));
		create(new PersonTest("m123", "ali", "milla"));
		create(new PersonTest("m128", "ali1", "milla1"));
		create(new PersonTest("m129", "ali2", "milla2"));
		create(new PersonTest("m130", "ali3", "milla3"));
		pid = identifiable.getIdentifier();
	}
					
	@Test(expected=RuntimeException.class)
	public void oneManyResult() {
		getGenericDao().use(PersonTest.class).select().where("name","ali").one();
	}

	// CRUD 
	
	@Override
	protected void create() {
		AbstractIdentifiable identifiable;
		create(identifiable = new PersonTest("m21", "Roger", "milla"));
		createId = identifiable.getIdentifier();
		Assert.assertTrue("Create", getGenericDao().use(PersonTest.class).read(createId)!=null);
	}

	@Override
	protected void read() {
		Assert.assertTrue("Read", getGenericDao().use(PersonTest.class).read(pid)!=null);
		//Assert.assertTrue("Exist 1", getGenericDao().exist(pid));
		//Assert.assertTrue("Exist 2", getGenericDao().exist(123456l));
	}

	@Override
	protected void update() {
		((PersonTest)identifiable).setName("Jack");
		update(identifiable);
		PersonTest person = (PersonTest) getGenericDao().use(PersonTest.class).read(identifiable.getIdentifier());
		Assert.assertTrue("Update","Jack".equals(person.getName()));
	}

	@Override
	protected void delete() {
		getGenericDao().delete(identifiable);
		PersonTest person = (PersonTest) getGenericDao().use(PersonTest.class).read(identifiable.getIdentifier());
		Assert.assertTrue("Delete",person==null);
	}
	
	@Override
	protected void queries() {
		Assert.assertEquals(6,getGenericDao().use(PersonTest.class).select().all().size());
		Assert.assertEquals(6,getGenericDao().use(PersonTest.class).select(Function.COUNT).oneLong().intValue());
		
		Assert.assertEquals(2,getGenericDao().use(PersonTest.class).select().where("name","ali").all().size());
		Assert.assertEquals(2,getGenericDao().use(PersonTest.class).select(Function.COUNT).where("name","ali").oneLong().intValue());
		
		Assert.assertEquals(0,getGenericDao().use(PersonTest.class).select().where("identifier",identifiable.getIdentifier()).one()==null?0:1);
		Assert.assertEquals(0,getGenericDao().use(PersonTest.class).select().where("name","georges").one()==null?0:1);
		
		getGenericDao().getDataReadConfig().setMaximumResultCount(2l);
		Assert.assertEquals(2,getGenericDao().use(PersonTest.class).select().all().size());
	}
	
	
	
	
}
