package org.cyk.system.root.service.impl;

import javax.ejb.EJB;

import org.cyk.system.root.dao.impl.AbstractDao;
import org.cyk.system.root.dao.impl.AbstractTypedDao;
import org.cyk.system.root.dao.impl.GenericDao;
import org.cyk.system.root.service.api.IGenericModelService;
import org.cyk.system.root.service.api.IGenericService;
import org.cyk.utility.common.cdi.AbstractBean;
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
public class GenericServiceFindTest extends AbstractServiceTest {
	
	@EJB
	private IGenericModelService service;
	private Long identifier;
	

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClasses(AbstractBean.class,AbstractService.class,GenericService.class,Person.class,
						IGenericService.class,IGenericModelService.class,AbstractDao.class, AbstractTypedDao.class,GenericDao.class)
				.addAsResource("test-persistence.xml","META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Override
	protected void _initialData_() throws Exception {
		Person p = null;
		entityManager.createQuery("delete from Person").executeUpdate();
		entityManager.persist(new Person("m01", "ali", "Bamba"));
		entityManager.persist(p = new Person("m21", "Roger", "milla"));
		entityManager.persist(new Person("m103", "Ange", "Kessi"));
		entityManager.persist(new Person("m123", "ali", "milla"));
		identifier = p.getIdentifier();
	}
	
	@Override
	protected Boolean initDataOnce() {
		return Boolean.TRUE;
	}

	@Test
	public void all() {
		Assert.assertTrue(service.use(Person.class).find().all().size()==4);
	}
	
	@Test
	public void allBy() {
		Assert.assertTrue(!service.use(Person.class).find().where("name","ali").all().isEmpty());
	}
	
	@Test
	public void oneOneResult() {
		Assert.assertTrue(service.use(Person.class).find().where("identifier",identifier).one()!=null);
	}
	
	@Test
	public void oneNoResult() {
		Assert.assertTrue(service.use(Person.class).find().where("name","georges").one()==null);
	}
	
	@Test(expected=RuntimeException.class)
	public void oneManyResult() {
		service.use(Person.class).find().where("name","ali").one();
	}

}
