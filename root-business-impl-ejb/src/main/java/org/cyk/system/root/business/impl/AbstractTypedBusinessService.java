package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.utility.common.computation.DataReadConfig;

public abstract class AbstractTypedBusinessService<IDENTIFIABLE extends AbstractIdentifiable, TYPED_DAO extends TypedDao<IDENTIFIABLE>> extends AbstractBusinessService<IDENTIFIABLE> implements
		TypedBusiness<IDENTIFIABLE>, Serializable {

	private static final long serialVersionUID = 6437552355933877400L;

	@Inject protected GenericDao genericDao;
	protected TYPED_DAO dao;

	public AbstractTypedBusinessService(TYPED_DAO dao) {
		super();
		this.dao = dao;
	}
	
	@Override
	protected PersistenceService<IDENTIFIABLE, Long> getPersistenceService() {
	    return dao;
	}

	@Override
	public IDENTIFIABLE create(IDENTIFIABLE object) {
	    validationPolicy.validateCreate(object);
        return dao.create(object);
	}

	@Override
	public IDENTIFIABLE update(IDENTIFIABLE object) {
		return dao.update(object);
	}

	@Override
	public IDENTIFIABLE delete(IDENTIFIABLE object) {
		return dao.delete(object);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IDENTIFIABLE load(Long identifier) {
	    return find(identifier);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findAll() {
		applyDataReadConfigToDao(getDataReadConfig());
		return dao.readAll();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countAll() {
		return dao.countAll();
	}

	protected void applyDataReadConfigToDao(DataReadConfig dataReadConfig){
		dao.getDataReadConfig().setFirstResultIndex(dataReadConfig.getFirstResultIndex());
		dao.getDataReadConfig().setMaximumResultCount(dataReadConfig.getMaximumResultCount());
	}


}
