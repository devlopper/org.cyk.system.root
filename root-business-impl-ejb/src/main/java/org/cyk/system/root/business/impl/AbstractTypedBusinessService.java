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
import org.cyk.utility.common.computation.DataReadConfiguration;

public abstract class AbstractTypedBusinessService<IDENTIFIABLE extends AbstractIdentifiable, TYPED_DAO extends TypedDao<IDENTIFIABLE>> extends AbstractIdentifiableBusinessServiceImpl<IDENTIFIABLE> implements
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
	public void create(Collection<IDENTIFIABLE> identifiables) {
	    for(IDENTIFIABLE identifiable : identifiables)
	    	create(identifiable);
	}

	@Override
	public IDENTIFIABLE update(IDENTIFIABLE object) {
		return dao.update(object);
	}
	
	@Override
	public void update(Collection<IDENTIFIABLE> identifiables) {
	    for(IDENTIFIABLE identifiable : identifiables)
	    	update(identifiable);
	}

	@Override
	public IDENTIFIABLE delete(IDENTIFIABLE object) {
		return dao.delete(object);
	}
	
	@Override
	public void delete(Collection<IDENTIFIABLE> identifiables) {
	    for(IDENTIFIABLE identifiable : identifiables)
	    	delete(identifiable);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IDENTIFIABLE load(Long identifier) {
		IDENTIFIABLE identifiable = find(identifier);
		load(identifiable);
		return identifiable;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void load(IDENTIFIABLE identifiable) {
		__load__(identifiable);
	}
	
	protected void __load__(IDENTIFIABLE identifiable) {}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void load(Collection<IDENTIFIABLE> identifiables) {
		for(IDENTIFIABLE identifiable : identifiables)
			load(identifiable);
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<IDENTIFIABLE> findAll() {
		return dao.readAll();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countAll() {
		return dao.countAll();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<IDENTIFIABLE> findAll(DataReadConfiguration dataReadConfig) {
		dao.getDataReadConfig().set(dataReadConfig);
		return dao.readAll();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countAll(DataReadConfiguration dataReadConfig) {
		return dao.countAll();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findAllExclude(Collection<IDENTIFIABLE> identifiables) {
		//FIXME find how to handle pagination
		//applyDataReadConfigToDao(getDataReadConfig());
		return dao.readAllExclude(identifiables);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countAllExclude(Collection<IDENTIFIABLE> identifiables) {
		return dao.countAllExclude(identifiables); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findByClasses(Collection<Class<?>> classes) {
		return dao.readByClasses(classes);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByClasses(Collection<Class<?>> classes) {
		return dao.countByClasses(classes);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findByNotClasses(Collection<Class<?>> classes) {
		return dao.readByNotClasses(classes);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByNotClasses(Collection<Class<?>> classes) {
		return dao.countByNotClasses(classes);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public <T extends IDENTIFIABLE> Collection<T> findByClass(Class<T> aClass) {
		return dao.readByClass(aClass);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByClass(Class<?> aClass) {
		return dao.countByClass(aClass);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findByNotClass(Class<?> aClass) {
		return dao.readByNotClass(aClass);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByNotClass(Class<?> aClass) {
		return dao.countByNotClass(aClass);
	}

	protected void applyDataReadConfigToDao(DataReadConfiguration dataReadConfig){
		dao.getDataReadConfig().setFirstResultIndex(dataReadConfig.getFirstResultIndex());
		dao.getDataReadConfig().setMaximumResultCount(dataReadConfig.getMaximumResultCount());
	}


}
