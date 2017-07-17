package org.cyk.system.root.persistence.impl.integration;

import org.cyk.system.root.persistence.impl.data.PersonTest;
import org.cyk.utility.common.computation.Function;
import org.junit.Assert;
import org.junit.Test;

public class GenericIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;

	private static PersonTest identifiable;
	private static Long pid;
		
	@Override
	protected void populate() {

		/*create(new PersonTest("m01", "ali", "Bamba"));
		create(identifiable = new PersonTest("m103", "Ange", "Kessi"));
		create(new PersonTest("m123", "ali", "milla"));
		create(new PersonTest("m128", "ali1", "milla1"));
		create(new PersonTest("m129", "ali2", "milla2"));
		create(new PersonTest("m130", "ali3", "milla3"));
		*/
		pid = identifiable.getIdentifier();

	}
					
	@Test(expected=RuntimeException.class)
	public void oneManyResult() {
		getGenericDao().use(PersonTest.class).select().where("name","ali").one();
	}

	// CRUD 
	
	@Override
	protected void create() {

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
		
		System.out.println(getGenericDao().readAllIdentifiers());
		
		System.out.println("Random Identifier");
		System.out.println(getGenericDao().readOneIdentifierRandomly());
		System.out.println(getGenericDao().readOneIdentifierRandomly());
		System.out.println(getGenericDao().readOneIdentifierRandomly());
		
		System.out.println("Random identifiers");
		System.out.println(getGenericDao().readManyIdentifiersRandomly(3));
		System.out.println(getGenericDao().readManyIdentifiersRandomly(3));
		System.out.println(getGenericDao().readManyIdentifiersRandomly(3));
		
		System.out.println("Random Object");
		System.out.println(getGenericDao().readOneRandomly());
		System.out.println(getGenericDao().readOneRandomly());
		System.out.println(getGenericDao().readOneRandomly());
		
		System.out.println("Random Objects");
		System.out.println(getGenericDao().readManyRandomly(2));
		System.out.println(getGenericDao().readManyRandomly(2));
		System.out.println(getGenericDao().readManyRandomly(2));
	}
	
	
	
	
}
