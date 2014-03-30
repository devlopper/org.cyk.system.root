package org.cyk.system.root.dao.impl;

import org.cyk.system.root.dao.api.TypedIdentifiableQuery;
import org.cyk.system.root.dao.impl.GenericDao;
import org.cyk.system.root.model.AbstractIdentifiable;

public abstract class AbstractQueryTest<MODEL extends AbstractIdentifiable> extends AbstractDataAccessTest {
	
	private Boolean data = Boolean.FALSE;
	
	protected abstract TypedIdentifiableQuery<MODEL> dao(); 
	
	@Override
	protected void _before_() throws Exception {
		super._before_();
		
		if(Boolean.TRUE.equals(data))
			return;
		
		transaction.begin();
		
		((GenericDao)genericDao).getEntityManager().joinTransaction();
		for(Class<?> from : entitiesToDelete())
			((GenericDao)genericDao).getEntityManager().createQuery("delete from "+from.getSimpleName()).executeUpdate();
		
		populate();
		transaction.commit();
		afterCommit();
		data = Boolean.TRUE;
	}
	
	protected abstract Class<? extends AbstractIdentifiable>[] entitiesToDelete();
	
	protected abstract void populate();
	
	protected void afterCommit(){}
	
	protected void create(AbstractIdentifiable object){
		genericDao.create(object.getClass(), object);
	}
	
	protected void update(AbstractIdentifiable object){
		genericDao.create(object.getClass(), object);
	}
}
