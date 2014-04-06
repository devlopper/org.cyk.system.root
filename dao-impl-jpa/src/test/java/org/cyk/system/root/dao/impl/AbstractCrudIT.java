package org.cyk.system.root.dao.impl;

import java.lang.reflect.ParameterizedType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.cyk.system.root.dao.api.TypedDao;
import org.cyk.system.root.dao.impl.generic.Transaction;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractCrudIT<MODEL extends AbstractIdentifiable> extends AbstractPersistenceIT {
	
	protected Class<MODEL> clazz;
	protected Long identifier;

	@SuppressWarnings("unchecked")
	public AbstractCrudIT() {
		clazz = (Class<MODEL>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	protected void _before_() throws Exception {
		super._before_();
		
		transaction.begin();
		
		((GenericDaoImpl)genericDao).getEntityManager().joinTransaction();
		/*for(Class<?> from : entitiesToDelete()){
			((GenericDaoImpl)genericDao).getEntityManager().createQuery("delete from "+from.getSimpleName()).executeUpdate();
			//System.out.println("Entities "+from.getSimpleName()+" deleted");
		}*/
		
		cleanDatabase();
		
		MODEL model = sampleOne();
		if(model!=null)
			if(dao()==null)
				genericDao.create(clazz,model);
			else
				dao().create(model);
		
		transaction.commit();
		
		if(model!=null)
			identifier = model.getIdentifier();
	}
	
	protected TypedDao<MODEL> dao(){
		return null;
	}
	
	protected abstract MODEL sampleOne();
	
	@Test
	public void create(){
		new Transaction(transaction,(AbstractPersistenceService<?>) genericDao,null) {
			@Override
			public void _execute_() {
				MODEL model = _create_();
				if(dao()==null)
					genericDao.create(clazz,model);
				else
					dao().create(model);
				Assert.assertTrue(genericDao.read(clazz, model.getIdentifier())!=null);
			}
		}.run();
	}
	
	protected abstract MODEL _create_();
	
	@Test
	public void read(){
		Assert.assertTrue( genericDao.read(clazz, identifier)!=null);
	}
	
	@Test
	public void update(){
		new Transaction(transaction,(AbstractPersistenceService<?>) genericDao,null) {
			@SuppressWarnings("unchecked")
			@Override
			public void _execute_() {
				MODEL modelMemory = (MODEL) genericDao.read(clazz, identifier);
				_update_(modelMemory);
				if(dao()==null)
					genericDao.update(clazz, modelMemory);
				else
					dao().update(modelMemory);
				MODEL modelDb = (MODEL) genericDao.read(clazz, identifier);
				Assert.assertTrue( EqualsBuilder.reflectionEquals(modelMemory, modelDb, false));
			}
		}.run();
		
	}
	
	protected abstract void _update_(MODEL model);
		
	@Test
	public void delete(){
		new Transaction(transaction,(AbstractPersistenceService<?>) genericDao,null) {
			@SuppressWarnings("unchecked")
			@Override
			public void _execute_() {
				MODEL model = (MODEL) genericDao.read(clazz, identifier);
				if(dao()==null)
					genericDao.delete(clazz, model);
				else
					dao().delete(model);
				model = (MODEL) genericDao.read(clazz, identifier);
				Assert.assertTrue(model == null);
			}
		}.run();
	}
	
	//@Test
	public void deleteCascade(){
		new Transaction(transaction,(AbstractPersistenceService<?>) genericDao,null) {
			@SuppressWarnings("unchecked")
			@Override
			public void _execute_() {
				MODEL model = _createCascade_();
				if(model==null)
					return;
				identifier = model.getIdentifier();
				dao().delete(model);
				model = (MODEL) genericDao.read(clazz, identifier);
				Assert.assertTrue(model == null);
			}
		}.run();
	}
	
	protected MODEL _createCascade_(){
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends AbstractIdentifiable>[] entitiesToDelete(){
		return (Class<? extends AbstractIdentifiable>[]) new Class<?>[]{clazz};
	}
}
