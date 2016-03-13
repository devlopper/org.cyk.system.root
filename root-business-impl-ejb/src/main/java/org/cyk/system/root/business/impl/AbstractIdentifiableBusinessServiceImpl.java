package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

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
import org.cyk.utility.common.CommonUtils.ReadExcelSheetArguments;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

import lombok.Getter;

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
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Class<IDENTIFIABLE> getClazz(){
		return clazz;
	}
	
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
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long findOneIdentifierRandomly() {
		return getPersistenceService().readOneIdentifierRandomly();
	}
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<Long> findManyIdentifiersRandomly(Integer count) {
		return getPersistenceService().readManyIdentifiersRandomly(count);
	}
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<Long> findAllIdentifiers() {
		return getPersistenceService().readAllIdentifiers();
	}
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IDENTIFIABLE findOneRandomly() {
		return getPersistenceService().readOneRandomly();
	}
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findManyRandomly(Integer count) {
		return getPersistenceService().readManyRandomly(count);
	}
	/**
	 * Utilities methods
	 */
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IDENTIFIABLE instanciateOne(){
		return newInstance(getClazz());
	}
	
	@Override
	public IDENTIFIABLE instanciateOne(ObjectFieldValues arguments) {
		IDENTIFIABLE identifiable = instanciateOne();
		commonUtils.instanciateOne(getClazz(),identifiable, arguments);
		return identifiable;
	}
	
	@Override
	public Collection<IDENTIFIABLE> instanciateMany(Collection<ObjectFieldValues> arguments) {
		Collection<IDENTIFIABLE> identifiables = new ArrayList<>();
		for(ObjectFieldValues o : arguments)
			identifiables.add(instanciateOne(o));
		return identifiables;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void completeInstanciationOfOne(IDENTIFIABLE identifiable) {
		
	}
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void completeInstanciationOfMany(Collection<IDENTIFIABLE> identifiables) {
		for(IDENTIFIABLE identifiable : identifiables)
			completeInstanciationOfOne(identifiable);
	}
	
	@Override
	public List<IDENTIFIABLE> instanciateMany(ReadExcelSheetArguments readExcelSheetArguments,AbstractCompleteInstanciationOfManyFromValuesArguments<IDENTIFIABLE> completeInstanciationOfManyFromValuesArguments) {
		List<String[]> list;
		try {
			list = commonUtils.readExcelSheet(readExcelSheetArguments);
		} catch (Exception e) {
			System.out.println("ERR -------------- : "+e);
			System.out.println("ERR -------------- : "+e.getCause());
			e.printStackTrace();
			exceptionUtils().exception(e);
			return null;
		}
		completeInstanciationOfManyFromValuesArguments.setValues(list);
    	return  completeInstanciationOfManyFromValues(completeInstanciationOfManyFromValuesArguments);
	}

	@Override
	public void completeInstanciationOfOneFromValues(IDENTIFIABLE identifiable,AbstractCompleteInstanciationOfOneFromValuesArguments<IDENTIFIABLE> arguments) {
		
	}

	@Override
	public IDENTIFIABLE completeInstanciationOfOneFromValues(AbstractCompleteInstanciationOfOneFromValuesArguments<IDENTIFIABLE> arguments) {
		return null;
	}
	
	@Override
	public void completeInstanciationOfManyFromValues(List<IDENTIFIABLE> actors,AbstractCompleteInstanciationOfManyFromValuesArguments<IDENTIFIABLE> arguments) {
		
	}

	@Override
	public List<IDENTIFIABLE> completeInstanciationOfManyFromValues(AbstractCompleteInstanciationOfManyFromValuesArguments<IDENTIFIABLE> arguments) {
		List<IDENTIFIABLE> identifiables = new ArrayList<>();
		for(int index = 0; index < arguments.getValues().size(); index++ ){
			IDENTIFIABLE identifiable = newInstance(getClazz());
			identifiables.add(identifiable);
		}
		completeInstanciationOfManyFromValues(identifiables,arguments);
		return identifiables;
	}

	protected void completeInstanciationOfOneFromValuesBeforeProcessing(IDENTIFIABLE identifiable,String[] values,CompleteInstanciationOfOneFromValuesListener<IDENTIFIABLE> listener){
		if(listener!=null)
			listener.beforeProcessing(identifiable,values);
	}
	
	protected void completeInstanciationOfOneFromValuesAfterProcessing(IDENTIFIABLE identifiable,String[] values,CompleteInstanciationOfOneFromValuesListener<IDENTIFIABLE> listener){
		if(listener!=null)
			listener.afterProcessing(identifiable,values);
	}
	
	protected void completeInstanciationOfManyFromValuesBeforeProcessing(List<IDENTIFIABLE> identifiables,List<String[]> values,CompleteInstanciationOfManyFromValuesListener<IDENTIFIABLE> listener){
		if(listener!=null)
			listener.beforeProcessing(identifiables,values);
	}
	protected void completeInstanciationOfManyFromValuesAfterProcessing(List<IDENTIFIABLE> identifiables,List<String[]> values,CompleteInstanciationOfManyFromValuesListener<IDENTIFIABLE> listener){
		if(listener!=null)
			listener.afterProcessing(identifiables,values);
	}

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
		logDebug("Instance created. {}", identifiable.getLogMessage());
	}
	 
	protected void logIdentifiable(String message,AbstractIdentifiable identifiable){
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
