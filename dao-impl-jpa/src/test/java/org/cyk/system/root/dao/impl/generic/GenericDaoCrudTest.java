package org.cyk.system.root.dao.impl.generic;

import java.sql.SQLIntegrityConstraintViolationException;

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
public class GenericDaoCrudTest extends AbstractDataAccessTest {
	
	@Inject 
	private IGenericModelAccess genericDao;
	private Long identifier;

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClasses(AbstractDao.class, AbstractTypedDao.class,GenericDao.class,Person.class)
				.addAsResource("test-persistence.xml","META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Override
	protected void _before_() throws Exception {
		super._before_();
		
		transaction.begin();
		
		((GenericDao)genericDao).getEntityManager().joinTransaction();
		((GenericDao)genericDao).getEntityManager().createQuery("delete from Person").executeUpdate();
		
		Person person = new Person("m01", "Ali", "Bamba");
		genericDao.create(Person.class,person);
		
		transaction.commit();
		
		identifier = person.getIdentifier();
	}
	

	
	@Test
	public void create(){
		new DatabaseAccess(transaction,(AbstractDao<?>) genericDao,null) {
			@Override
			public void _execute_() {
				Person person = new Person("m02", "Ali", "Bamba");
				genericDao.create(Person.class,person );
				Assert.assertTrue(genericDao.read(Person.class, person.getIdentifier())!=null);
			}
		}.run();
	}
	
	@Test(expected=RuntimeException.class)
	public void createNoUniqueMatriculeConstraintViolation(){
		new DatabaseAccess(transaction,(AbstractDao<?>) genericDao,SQLIntegrityConstraintViolationException.class) {
			@Override
			public void _execute_() {
				Person person = new Person("m01", "Ali", "Bamba");
				genericDao.create(Person.class,person );
				Assert.assertTrue(genericDao.read(Person.class, person.getIdentifier())!=null);
			}
		}.run();
	}
	
	@Test
	public void read(){
		Assert.assertTrue(genericDao.read(Person.class, identifier)!=null);
	}
	
	@Test
	public void update(){
		new DatabaseAccess(transaction,(AbstractDao<?>) genericDao,null) {
			@Override
			public void _execute_() {
				String newName = "paul";
				Person person = (Person) genericDao.read(Person.class, identifier);
				person.setName(newName);
				genericDao.update(Person.class, person);
				person = (Person) genericDao.read(Person.class, identifier);
				Assert.assertTrue(person.getName().equals(newName));
			}
		}.run();
		
	}
	
	@Test
	public void delete(){
		new DatabaseAccess(transaction,(AbstractDao<?>) genericDao,null) {
			@Override
			public void _execute_() {
				Person person = (Person) genericDao.read(Person.class, identifier);
				genericDao.delete(Person.class, person);
				person = (Person) genericDao.read(Person.class, identifier);
				Assert.assertTrue(person == null);
			}
		}.run();
		
	}
	/*
	@Test
	public void dynamicQuery() {
		genericDao.use(Person.class);
		genericDao.select();
		System.out.println(genericDao.getQueryString());
		genericDao.where("matricule", 5, null);
		System.out.println(genericDao.getQueryString());
		genericDao.where("name", 5, WhereOperator.OR);
		System.out.println(genericDao.getQueryString());
	}*/

}
