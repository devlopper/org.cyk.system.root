package org.cyk.system.root.service.impl;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.dao.api.TypedDao;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.service.api.IModelService;
import org.cyk.system.root.service.api.IServiceable;

public abstract class AbstractTypedService<IDENTIFIABLE extends AbstractIdentifiable, TYPED_DAO extends TypedDao<IDENTIFIABLE>> extends AbstractService implements
		IModelService<IDENTIFIABLE>, Serializable {

	private static final long serialVersionUID = 6437552355933877400L;

	protected TYPED_DAO dao;

	public AbstractTypedService(TYPED_DAO dao) {
		super();
		this.dao = dao;
	}

	@Override
	public IServiceable<IDENTIFIABLE, Long> find() {
		dao.select();
		return this;
	}

	@Override
	public IServiceable<IDENTIFIABLE, Long> where(String anAttributeName, Object aValue) {
		dao.where(anAttributeName, aValue);
		return this;
	}

	@Override
	public IServiceable<IDENTIFIABLE, Long> and() {
		dao.and();
		return this;
	}

	@Override
	public IServiceable<IDENTIFIABLE, Long> or() {
		dao.or();
		return this;
	}

	@Override
	public Collection<IDENTIFIABLE> all() {
		return dao.all();
	}

	@Override
	public IDENTIFIABLE one() {
		return dao.one();
	}

	@Override
	public IDENTIFIABLE create(IDENTIFIABLE object) {
		return dao.create(object);
	}

	@Override
	public IDENTIFIABLE read(Long identifier) {
		return dao.read(identifier);
	}

	@Override
	public IDENTIFIABLE update(IDENTIFIABLE object) {
		return dao.update(object);
	}

	@Override
	public IDENTIFIABLE delete(IDENTIFIABLE object) {
		return dao.delete(object);
	}



}
