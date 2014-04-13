package org.cyk.system.root.service.impl;

import java.io.Serializable;

import org.cyk.system.root.dao.api.TypedDao;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.service.api.IModelService;

public  class AbstractTypedService<IDENTIFIABLE extends AbstractIdentifiable, TYPED_DAO extends TypedDao<IDENTIFIABLE>> extends AbstractService<IDENTIFIABLE> implements
		IModelService<IDENTIFIABLE>, Serializable {

	private static final long serialVersionUID = 6437552355933877400L;

	protected TYPED_DAO dao;

	public AbstractTypedService(TYPED_DAO dao) {
		super();
		this.dao = dao;
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
