package org.cyk.system.root.service.impl;

import javax.ejb.EJB;

import org.cyk.system.root.dao.impl.AbstractQueryable;
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
public class GenericServiceCrudTest extends AbstractServiceTest {
	
	@EJB
	private IGenericModelService service;
	private Long identifier;

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClasses(AbstractBean.class,AbstractService.class,GenericService.class,Person.class,
						IGenericService.class,IGenericModelService.class,AbstractQueryable.class, AbstractTypedDao.class,GenericDao.class)
				.addAsResource("test-persistence.xml","META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Override
	protected void _initialData_() throws Exception {
		entityManager.createQuery("delete from Person").executeUpdate();
		Person person = new Person("m01", "Ali", "Bamba");
		entityManager.persist(person);
		identifier = person.getIdentifier();
	}
		
	@Test
	public void create(){
		Person person = new Person("m02", "Ali", "Bamba");
		service.create(Person.class, person);
		Assert.assertTrue(service.read(Person.class, person.getIdentifier())!=null);
	}
	
	@Test(expected=RuntimeException.class)
	public void createNoUniqueMatriculeConstraintViolation(){
		Person person = new Person("m01", "Ali", "Bamba");
		service.create(Person.class, person);
		Assert.assertTrue(service.read(Person.class, person.getIdentifier())!=null);
	}
	
	@Test
	public void read(){
		Assert.assertTrue(service.read(Person.class, identifier)!=null);
	}
	
	@Test
	public void update(){
		String newName = "paul";
		Person person = (Person) service.read(Person.class, identifier);
		person.setName(newName);
		service.update(Person.class, person);
		person = (Person) service.read(Person.class, identifier);
		Assert.assertTrue(person.getName().equals(newName));
			
		
	}
	
	@Test
	public void delete(){
		Person person = (Person) service.read(Person.class, identifier);
		service.delete(Person.class, person);
		person = (Person) service.read(Person.class, identifier);
		Assert.assertTrue(person == null);
	}
	

}
