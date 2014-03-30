package org.cyk.system.root.dao.impl;

import java.lang.reflect.ParameterizedType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.cyk.system.root.dao.impl.AbstractQueryable;
import org.cyk.system.root.dao.impl.GenericDao;
import org.cyk.system.root.dao.impl.generic.DatabaseAccess;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractCrudTest<MODEL extends AbstractIdentifiable> extends AbstractDataAccessTest {
	
	protected Class<MODEL> clazz;
	protected Long identifier;

	@SuppressWarnings("unchecked")
	public AbstractCrudTest() {
		clazz = (Class<MODEL>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	protected void _before_() throws Exception {
		super._before_();
		
		transaction.begin();
		
		((GenericDao)genericDao).getEntityManager().joinTransaction();
		((GenericDao)genericDao).getEntityManager().createQuery("delete from "+clazz.getSimpleName()).executeUpdate();
		
		MODEL model = sampleOne();
		if(model!=null)
			genericDao.create(clazz,model);
		
		transaction.commit();
		
		if(model!=null)
			identifier = model.getIdentifier();
	}
	
	protected abstract MODEL sampleOne();
	
	@Test
	public void create(){
		new DatabaseAccess(transaction,(AbstractQueryable<?>) genericDao,null) {
			@Override
			public void _execute_() {
				MODEL model = _create_();
				genericDao.create(clazz,model);
				Assert.assertTrue(genericDao.read(clazz, model.getIdentifier())!=null);
			}
		}.run();
	}
	
	protected abstract MODEL _create_();
	
	@Test
	public void read(){
		Assert.assertTrue(genericDao.read(clazz, identifier)!=null);
	}
	
	@Test
	public void update(){
		new DatabaseAccess(transaction,(AbstractQueryable<?>) genericDao,null) {
			@SuppressWarnings("unchecked")
			@Override
			public void _execute_() {
				MODEL modelMemory = (MODEL) genericDao.read(clazz, identifier);
				_update_(modelMemory);
				genericDao.update(clazz, modelMemory);
				MODEL modelDb = (MODEL) genericDao.read(clazz, identifier);
				Assert.assertTrue( EqualsBuilder.reflectionEquals(modelMemory, modelDb, false));
			}
		}.run();
		
	}
	
	protected abstract void _update_(MODEL model);
		
	@Test
	public void delete(){
		new DatabaseAccess(transaction,(AbstractQueryable<?>) genericDao,null) {
			@SuppressWarnings("unchecked")
			@Override
			public void _execute_() {
				MODEL model = (MODEL) genericDao.read(clazz, identifier);
				genericDao.delete(clazz, model);
				model = (MODEL) genericDao.read(clazz, identifier);
				Assert.assertTrue(model == null);
			}
		}.run();
		
	}
	
	/**/
	
	public static Archive<?> createCrudDeployment(Package...packages){
		return ShrinkWrap
				.create(JavaArchive.class)
				.addPackage(AbstractQueryable.class.getPackage())
				.addPackages(false,packages)
				.addAsResource("test-persistence.xml","META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
}
