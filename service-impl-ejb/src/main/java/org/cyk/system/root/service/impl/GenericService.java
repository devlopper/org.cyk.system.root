package org.cyk.system.root.service.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.dao.impl.GenericDao;
import org.cyk.system.root.model.AbstractModel;
import org.cyk.system.root.service.api.IGenericModelService;
import org.cyk.system.root.service.api.IGenericService;
import org.cyk.system.root.service.api.IServiceable;

@Stateless
public class GenericService extends AbstractService implements IGenericModelService,Serializable {
	
	private static final long serialVersionUID = -1042342183332719272L;

	@Inject private GenericDao dao;
	 
	@Override
	public AbstractModel create(Class<? extends AbstractModel> aClass,AbstractModel object) {
		return dao.create(aClass, object);
	}

	@Override
	public AbstractModel read(Class<? extends AbstractModel> aClass,Long identifier) {
		return dao.read(aClass, identifier);
	}

	@Override
	public AbstractModel update(Class<? extends AbstractModel> aClass,AbstractModel object) {
		return dao.update(aClass, object);
	}

	@Override
	public AbstractModel delete(Class<? extends AbstractModel> aClass,AbstractModel object) {
		return dao.delete(aClass, object);
	}

	@Override
	public IGenericService<AbstractModel, Long> use(Class<? extends AbstractModel> aClass) {
		dao.use(aClass);
		return this;
	}

	@Override
	public IServiceable<AbstractModel, Long> find() {
		dao.select();
		return this;
	}

	@Override
	public IServiceable<AbstractModel, Long> where(String anAttributeName,Object aValue) {
		dao.where(anAttributeName, aValue);
		return this;
	}

	@Override
	public IServiceable<AbstractModel, Long> and() {
		dao.and();
		return this;
	}

	@Override
	public IServiceable<AbstractModel, Long> or() {
		dao.or();
		return this;
	}

	@Override
	public Collection<AbstractModel> all() {
		return dao.all();
	}

	@Override
	public AbstractModel one() {
		return dao.one();
	}


}
