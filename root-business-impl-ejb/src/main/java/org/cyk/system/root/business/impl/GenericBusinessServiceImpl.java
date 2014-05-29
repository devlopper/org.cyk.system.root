package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.AbstractGenericBusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.PersistenceService;

@Stateless
public class GenericBusinessServiceImpl extends AbstractBusinessService<AbstractIdentifiable> implements GenericBusiness,Serializable {
	
	private static final long serialVersionUID = -1042342183332719272L;

	@Inject private GenericDao dao;
	
	@Override
	protected PersistenceService<AbstractIdentifiable, Long> getPersistenceService() {
	    return dao;
	}
	
	@Override
	public AbstractIdentifiable create(/*Class<? extends AbstractIdentifiable> aClass,*/AbstractIdentifiable anObject) {
		return dao.create(anObject.getClass(), anObject);
	}

	@Override
	public AbstractIdentifiable read(Class<? extends AbstractIdentifiable> aClass,Long identifier) {
		return dao.read(aClass, identifier);
	}

	@Override
	public AbstractIdentifiable update(Class<? extends AbstractIdentifiable> aClass,AbstractIdentifiable anObject) {
		return dao.update(aClass, anObject);
	}

	@Override
	public AbstractIdentifiable delete(Class<? extends AbstractIdentifiable> aClass,AbstractIdentifiable anObject) {
		return dao.delete(aClass, anObject);
	}

	@Override
	public AbstractGenericBusinessService<AbstractIdentifiable, Long> use(Class<? extends AbstractIdentifiable> aClass) {
		dao.use(aClass);
		return this;
	}

}
