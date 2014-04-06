package org.cyk.system.root.dao.impl;

import org.cyk.system.root.dao.api.TypedDao;
import org.cyk.system.root.dao.impl.GenericDaoImpl;
import org.cyk.system.root.model.AbstractIdentifiable;

public abstract class AbstractQueryIT<MODEL extends AbstractIdentifiable> extends AbstractPersistenceIT {
	
	private Boolean data = Boolean.FALSE;
	
	protected abstract TypedDao<MODEL> dao(); 
	 
	@Override
	protected void _before_() throws Exception {
		super._before_();
		
		if(Boolean.TRUE.equals(data))
			return;
		
		transaction.begin();
		
		((GenericDaoImpl)genericDao).getEntityManager().joinTransaction();
		cleanDatabase();
		
		populate();
		transaction.commit();
		afterCommit();
		data = Boolean.TRUE;
	}
	
	protected abstract void populate();
	
	protected void afterCommit(){}
	
	protected void create(AbstractIdentifiable object){
		genericDao.create(object.getClass(), object);
	}
	
	protected void update(AbstractIdentifiable object){
		genericDao.create(object.getClass(), object);
	}
}
