package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessServiceListener;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.IdentifiableBusinessService;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

public abstract class AbstractIdentifiableBusinessServiceImpl<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBusinessServiceImpl implements IdentifiableBusinessService<IDENTIFIABLE, Long>, Serializable {

	private static final long serialVersionUID = 6437552355933877400L;
	
	//How to resolve circular dependency AbstractBusinessService -> ValidationPolicy -> LanguageBusiness which inherits of AbstractBusinessService
	//Singleton has been use to solve previous issue
	@Inject protected ValidationPolicy validationPolicy;	
	//@Getter private DataReadConfig dataReadConfig = new DataReadConfig();
	
	@Inject protected GenericDao genericDao;
	
	protected abstract PersistenceService<IDENTIFIABLE, Long> getPersistenceService();
	/*
	public DataReadConfig getDataReadConfig(){
		return getPersistenceService().getDataReadConfig();
	}
	*/
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IdentifiableBusinessService<IDENTIFIABLE, Long> find() {
		getPersistenceService().select();
		return this;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IDENTIFIABLE find(Long identifier) {
	    return getPersistenceService().read(identifier);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IdentifiableBusinessService<IDENTIFIABLE, Long> find(Function function) {
		getPersistenceService().select(function);
		return this;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IdentifiableBusinessService<IDENTIFIABLE, Long> where(LogicalOperator aLogicalOperator, String anAttributeName, Object aValue, ArithmeticOperator anArithmeticOperator) {
		getPersistenceService().where(aLogicalOperator, anAttributeName, aValue, anArithmeticOperator);
		return this;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IdentifiableBusinessService<IDENTIFIABLE, Long> where(String anAttributeName, Object aValue, ArithmeticOperator anArithmeticOperator) {
		getPersistenceService().where(anAttributeName, aValue, anArithmeticOperator);
		return this;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IdentifiableBusinessService<IDENTIFIABLE, Long> where(String anAttributeName,Object aValue) {
		return where(anAttributeName, aValue, ArithmeticOperator.EQ);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public <RESULT_TYPE> RESULT_TYPE one(Class<RESULT_TYPE> resultType) {
		return getPersistenceService().one(resultType);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> all() {
		return getPersistenceService().all();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IDENTIFIABLE one() {
		return getPersistenceService().one();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long oneLong() {
		return getPersistenceService().oneLong();
	}
	
	@Override
	public Long findOneIdentifierRandomly() {
		return getPersistenceService().readOneIdentifierRandomly();
	}
	@Override
	public Collection<Long> findManyIdentifiersRandomly(Integer count) {
		return getPersistenceService().readManyIdentifiersRandomly(count);
	}
	@Override
	public Collection<Long> findAllIdentifiers() {
		return getPersistenceService().readAllIdentifiers();
	}
	@Override
	public IDENTIFIABLE findOneRandomly() {
		return getPersistenceService().readOneRandomly();
	}
	@Override
	public Collection<IDENTIFIABLE> findManyRandomly(Integer count) {
		return getPersistenceService().readManyRandomly(count);
	}
	/**
	 * Utilities methods
	 */
	
	protected void prepareFindByCriteria(AbstractFieldValueSearchCriteriaSet searchCriteria){
		getPersistenceService().getDataReadConfig().set(searchCriteria.getReadConfig());
	}
	
	protected void notifyCrudDone(Crud crud,AbstractIdentifiable identifiable){
		for(BusinessServiceListener listener : BusinessServiceListener.COLLECTION)
			listener.crudDone(crud, identifiable);
	}
	
}
