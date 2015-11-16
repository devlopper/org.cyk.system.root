package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.Getter;

import org.cyk.system.root.business.api.BusinessServiceListener;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.IdentifiableBusinessService;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.system.root.persistence.api.TypedDao;
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
	protected Class<IDENTIFIABLE>  clazz;
	
	protected abstract PersistenceService<IDENTIFIABLE, Long> getPersistenceService();
	/*
	public DataReadConfig getDataReadConfig(){
		return getPersistenceService().getDataReadConfig();
	}
	*/
		
	@SuppressWarnings("unchecked")
	@Override
	protected void beforeInitialisation() {
		clazz = (Class<IDENTIFIABLE>) parameterizedClass();
		super.beforeInitialisation();
	}
	protected Class<?> parameterizedClass(){
	    return (Class<?>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Class<IDENTIFIABLE> getClazz(){
		return clazz;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IdentifiableBusinessService<IDENTIFIABLE, Long> find() {
		getPersistenceService().select();
		return this;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IDENTIFIABLE find(Long identifier) {
	    return getPersistenceService().read(identifier);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IdentifiableBusinessService<IDENTIFIABLE, Long> find(Function function) {
		getPersistenceService().select(function);
		return this;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IdentifiableBusinessService<IDENTIFIABLE, Long> where(LogicalOperator aLogicalOperator, String anAttributeName, Object aValue, ArithmeticOperator anArithmeticOperator) {
		getPersistenceService().where(aLogicalOperator, anAttributeName, aValue, anArithmeticOperator);
		return this;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IdentifiableBusinessService<IDENTIFIABLE, Long> where(String anAttributeName, Object aValue, ArithmeticOperator anArithmeticOperator) {
		getPersistenceService().where(anAttributeName, aValue, anArithmeticOperator);
		return this;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IdentifiableBusinessService<IDENTIFIABLE, Long> where(String anAttributeName,Object aValue) {
		return where(anAttributeName, aValue, ArithmeticOperator.EQ);
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <RESULT_TYPE> RESULT_TYPE one(Class<RESULT_TYPE> resultType) {
		return getPersistenceService().one(resultType);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<IDENTIFIABLE> all() {
		return getPersistenceService().all();
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IDENTIFIABLE one() {
		return getPersistenceService().one();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Long oneLong() {
		return getPersistenceService().oneLong();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Long findOneIdentifierRandomly() {
		return getPersistenceService().readOneIdentifierRandomly();
	}
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<Long> findManyIdentifiersRandomly(Integer count) {
		return getPersistenceService().readManyIdentifiersRandomly(count);
	}
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<Long> findAllIdentifiers() {
		return getPersistenceService().readAllIdentifiers();
	}
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IDENTIFIABLE findOneRandomly() {
		return getPersistenceService().readOneRandomly();
	}
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
	
	/**/
	
	protected void logInstanciate(){
		logDebug("Instanciate {}",getClazz().getSimpleName());
	}
	
	protected void logInstanceCreated(IDENTIFIABLE identifiable){
		logDebug("Instance of {} created. {}", identifiable.getClass().getSimpleName(),identifiable.getLogMessage());
	}
	 
	protected void logIdentifiable(String message,IDENTIFIABLE identifiable){
		logDebug("{} : {}",message,identifiable.getLogMessage());
	}
	
	/**/
	
	public static interface CascadeOperationListener<IDENTIFIABLE extends AbstractIdentifiable,DAO extends TypedDao<IDENTIFIABLE>,BUSINESS extends TypedBusiness<IDENTIFIABLE>>{
    	
		DAO getDao();
		BUSINESS getBusiness();
		
		void create(Collection<IDENTIFIABLE> identifiables);
    	void update(Collection<IDENTIFIABLE> identifiables);
    	void delete(Collection<IDENTIFIABLE> identifiables);
    	
    	void operate(Collection<IDENTIFIABLE> identifiables,Crud crud);
    	
    	/**/
    	
    	@Getter
    	public static class Adapter<IDENTIFIABLE extends AbstractIdentifiable,DAO extends TypedDao<IDENTIFIABLE>,BUSINESS extends TypedBusiness<IDENTIFIABLE>> implements CascadeOperationListener<IDENTIFIABLE, DAO, BUSINESS>{

    		private DAO dao;
    		private BUSINESS business;
    		
			public Adapter(DAO dao, BUSINESS business) {
				super();
				this.dao = dao;
				this.business = business;
			}
			@Override public void create(Collection<IDENTIFIABLE> identifiables) {}
			@Override public void update(Collection<IDENTIFIABLE> identifiables) {}
			@Override public void delete(Collection<IDENTIFIABLE> identifiables) {}
			@Override public void operate(Collection<IDENTIFIABLE> identifiables, Crud crud) {}
			
			/**/
			
			public static class Default<IDENTIFIABLE extends AbstractIdentifiable,DAO extends TypedDao<IDENTIFIABLE>,BUSINESS extends TypedBusiness<IDENTIFIABLE>> extends Adapter<IDENTIFIABLE, DAO, BUSINESS>{
	    		
	    		public Default(DAO dao, BUSINESS business) {
					super(dao, business);
				}

				@Override
	    		public void create(Collection<IDENTIFIABLE> identifiables) {
	    			if(getBusiness()!=null)
	    				getBusiness().create(identifiables);
	    			else for(IDENTIFIABLE identifiable : identifiables)
	    				getDao().create(identifiable);
	    		}
	    		
	    		@Override
	    		public void update(Collection<IDENTIFIABLE> identifiables) {
	    			if(getBusiness()!=null)
	    				getBusiness().update(identifiables);
	    			else for(IDENTIFIABLE identifiable : identifiables)
	    				getDao().update(identifiable);
	    		}
	    		
	    		@Override
	    		public void delete(Collection<IDENTIFIABLE> identifiables) {
	    			if(getBusiness()!=null)
	    				getBusiness().delete(identifiables);
	    			else for(IDENTIFIABLE identifiable : identifiables)
	    				getDao().delete(identifiable);
	    		}
	    		
	    		@Override
	    		public void operate(Collection<IDENTIFIABLE> identifiables, Crud crud) {
	    			if(Crud.CREATE.equals(crud))
						create(identifiables);
					else if(Crud.READ.equals(crud))
						;
					else if(Crud.UPDATE.equals(crud))
						update(identifiables);
					else if(Crud.DELETE.equals(crud))
						delete(identifiables);
	    		}
	    	}
			
    	}
    	
    	
    }
	
}
