package org.cyk.system.root.dao.impl.generic;

import javax.inject.Inject;

import org.cyk.system.root.dao.api.IGenericModelAccess;
import org.cyk.system.root.dao.impl.AbstractTypedDao;
import org.cyk.system.root.dao.impl.AbstractDao;
import org.cyk.system.root.dao.impl.GenericDao;
import org.cyk.system.root.dao.impl.Person;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class GenericDaoReadQueryTest extends AbstractDataAccessTest {
	
	@Inject 
	private IGenericModelAccess genericDao;
	private Boolean data = Boolean.FALSE;
	private Long identifier;

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClasses(AbstractDao.class, AbstractTypedDao.class,GenericDao.class,Person.class)
				.addAsResource("test-persistence.xml","META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	
	private void sampleData() throws Exception{
		if(Boolean.TRUE.equals(data))
			return;
		transaction.begin();
		Person p = null;
		((GenericDao)genericDao).getEntityManager().joinTransaction();
		((GenericDao)genericDao).getEntityManager().createQuery("delete from Person").executeUpdate();
		genericDao.create(Person.class,new Person("m01", "ali", "Bamba"));
		genericDao.create(Person.class,p = new Person("m21", "Roger", "milla"));
		genericDao.create(Person.class,new Person("m103", "Ange", "Kessi"));
		genericDao.create(Person.class,new Person("m123", "ali", "milla"));
		
		transaction.commit();
		
		identifier = p.getIdentifier();
		data = Boolean.TRUE;
	}
	
	@Override
	protected void _before_() throws Exception {
		super._before_();
		sampleData();
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
