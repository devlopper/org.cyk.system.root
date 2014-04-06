package org.cyk.system.root.dao.impl.generic;

import org.cyk.system.root.dao.api.TypedDao;
import org.cyk.system.root.dao.impl.AbstractQueryIT;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;

public class GenericDaoQueryIT extends AbstractQueryIT<Person> {
	
	private Person p;
	private Long identifier;

	@Deployment
	public static Archive<?> createDeployment() {
		return createDeployment(Person.class);
	}
	
	@Override
	protected TypedDao<Person> dao() {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends AbstractIdentifiable>[] entitiesToDelete() {
		return (Class<? extends AbstractIdentifiable>[]) new Class<?>[]{Person.class};
	}
		
	@Override
	protected void populate() {
		create(new Person("m01", "ali", "Bamba"));
		create(p = new Person("m21", "Roger", "milla"));
		create(new Person("m103", "Ange", "Kessi"));
		create(new Person("m123", "ali", "milla"));
	}
	
	@Override
	protected void afterCommit() {
		identifier = p.getIdentifier();
	}
	
	@Test
	public void all() {
		Assert.assertTrue(genericDao.use(Person.class).select().all().size()==4);
	}
	
	@Test
	public void allBy() {
		Assert.assertTrue(!genericDao.use(Person.class).select().where("name","ali").all().isEmpty());
	}
	
	@Test
	public void oneOneResult() {
		Assert.assertTrue(genericDao.use(Person.class).select().where("identifier",identifier).one()!=null);
	}
	
	@Test
	public void oneNoResult() {
		Assert.assertTrue(genericDao.use(Person.class).select().where("name","georges").one()==null);
	}
	
	@Test(expected=RuntimeException.class)
	public void oneManyResult() {
		genericDao.use(Person.class).select().where("name","ali").one();
	}	

}
