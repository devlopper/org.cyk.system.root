package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.IGenericModelService;
import org.cyk.system.root.business.api.IGenericService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.impl.GenericDaoImpl;

@Stateless
public class GenericService extends AbstractService<AbstractIdentifiable> implements IGenericModelService,Serializable {
	
	private static final long serialVersionUID = -1042342183332719272L;

	@Inject private GenericDaoImpl dao;
	 
	@Override
	public AbstractIdentifiable create(Class<? extends AbstractIdentifiable> aClass,AbstractIdentifiable object) {
		return dao.create(aClass, object);
	}

	@Override
	public AbstractIdentifiable read(Class<? extends AbstractIdentifiable> aClass,Long identifier) {
		return dao.read(aClass, identifier);
	}

	@Override
	public AbstractIdentifiable update(Class<? extends AbstractIdentifiable> aClass,AbstractIdentifiable object) {
		return dao.update(aClass, object);
	}

	@Override
	public AbstractIdentifiable delete(Class<? extends AbstractIdentifiable> aClass,AbstractIdentifiable object) {
		return dao.delete(aClass, object);
	}

	@Override
	public IGenericService<AbstractIdentifiable, Long> use(Class<? extends AbstractIdentifiable> aClass) {
		dao.use(aClass);
		return this;
	}

}
