package org.cyk.system.root.service.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.dao.impl.GenericDaoImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.service.api.IGenericModelService;
import org.cyk.system.root.service.api.IGenericService;
import org.cyk.system.root.service.api.IServiceable;

@Stateless
public class GenericService extends AbstractService implements IGenericModelService,Serializable {
	
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

	@Override
	public IServiceable<AbstractIdentifiable, Long> find() {
		dao.select();
		return this;
	}

	@Override
	public IServiceable<AbstractIdentifiable, Long> where(String anAttributeName,Object aValue) {
		dao.where(anAttributeName, aValue);
		return this;
	}

	@Override
	public IServiceable<AbstractIdentifiable, Long> and() {
		dao.and();
		return this;
	}

	@Override
	public IServiceable<AbstractIdentifiable, Long> or() {
		dao.or();
		return this;
	}

	@Override
	public Collection<AbstractIdentifiable> all() {
		return dao.all();
	}

	@Override
	public AbstractIdentifiable one() {
		return dao.one();
	}


}
