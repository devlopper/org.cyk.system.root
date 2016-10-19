package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.IdentifiableBusinessService;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.TypedBusiness.CreateReportFileArguments;
import org.cyk.system.root.business.api.file.FileIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier.SearchCriteria;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;
import org.cyk.utility.common.CommonUtils.ReadExcelSheetArguments;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

public abstract class AbstractIdentifiableBusinessServiceImpl<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBusinessServiceImpl implements IdentifiableBusinessService<IDENTIFIABLE, Long>, Serializable {

	private static final long serialVersionUID = 6437552355933877400L;
	
	public static final Map<String,Collection<Class<? extends AbstractIdentifiable>>> AUTO_SET_PROPERTY_VALUE_CLASSES = new HashMap<>();
	
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
	
	public static void addAutoSetPropertyValueClass(String property,Class<? extends AbstractIdentifiable> aClass){
		Collection<Class<? extends AbstractIdentifiable>> collection = AUTO_SET_PROPERTY_VALUE_CLASSES.get(property);
		if(collection==null)
			AUTO_SET_PROPERTY_VALUE_CLASSES.put(property, collection = new HashSet<>());
		collection.add(aClass);
	}
	
	@SuppressWarnings("unchecked")
	public static void addAutoSetPropertyValueClass(String[] properties,Class<?>...classes){
		for(String property : properties)
			for(Class<?> aClass : classes)
				addAutoSetPropertyValueClass(property, (Class<? extends AbstractIdentifiable>) aClass);
	}
	
	public static Boolean isAutoSetPropertyValueClass(String property,Class<? extends AbstractIdentifiable> aClass){
		Collection<Class<? extends AbstractIdentifiable>> collection = AUTO_SET_PROPERTY_VALUE_CLASSES.get(property);
		return collection!=null && collection.contains(aClass);
	}
	
		
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
	public IDENTIFIABLE findByGlobalIdentifier(GlobalIdentifier globalIdentifier) {
	    return getPersistenceService().readByGlobalIdentifier(globalIdentifier);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public IDENTIFIABLE findByGlobalIdentifierCode(String code) {
	    return getPersistenceService().readByGlobalIdentifierCode(code);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findByGlobalIdentifierCodes(Collection<String> codes) {
	    return getPersistenceService().readByGlobalIdentifierCodes(codes);
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
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findByIdentifiers(Collection<Long> identifiers) {
		if(identifiers==null || identifiers.isEmpty())
			return new ArrayList<>();
		return getPersistenceService().readByIdentifiers(identifiers);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public AbstractIdentifiable findParent(IDENTIFIABLE identifiable) {
		return null;
	}
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<AbstractIdentifiable> findParentRecursively(IDENTIFIABLE identifiable) {
		return null;
	}
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void setParents(IDENTIFIABLE identifiable) {

	}
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void setParents(Collection<IDENTIFIABLE> identifiables) {
		
	}
	/**
	 * Utilities methods
	 */
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IDENTIFIABLE instanciateOne(){
		return newInstance(getClazz());
	}
	@Override
	public IDENTIFIABLE instanciateOne(UserAccount userAccount) {
		beforeInstanciateOne(getListeners(), userAccount);
		IDENTIFIABLE identifiable = instanciateOne();
		afterInstanciateOne(getListeners(), userAccount, identifiable);
		return identifiable;
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
	/*
	protected void notifyCrudDone(Crud crud,AbstractIdentifiable identifiable){
		for(BusinessServiceListener listener : BusinessServiceListener.COLLECTION)
			listener.crudDone(crud, identifiable);
	}
	*/
	protected <T extends AbstractIdentifiable> void setCallArgumentsObjects(BusinessServiceCallArguments<T> callArguments,Collection<T> identifiables){
		if(callArguments!=null)
			callArguments.setObjects(identifiables);
	}
	
	@Override
	public Boolean isIdentified(AbstractIdentifiable identifiable){
		return identifiable!=null && identifiable.getIdentifier()!=null;
	}
	
	@Override
	public Boolean isNotIdentified(AbstractIdentifiable identifiable){
		return identifiable!=null && identifiable.getIdentifier()==null;
	}
	
	@Override
	public Collection<IDENTIFIABLE> findByGlobalIdentifierSearchCriteria(SearchCriteria globalIdentifierSearchCriteria) {
		return getPersistenceService().readByGlobalIdentifierSearchCriteria(globalIdentifierSearchCriteria);
	}

	@Override
	public Long countByGlobalIdentifierSearchCriteria(SearchCriteria globalIdentifierSearchCriteria) {
		return getPersistenceService().countByGlobalIdentifierSearchCriteria(globalIdentifierSearchCriteria);
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
	
	public static String computeCodeFromName(String name){
    	return StringUtils.remove(StringUtils.remove(name, Constant.CHARACTER_SPACE),Constant.CHARACTER_DOT);
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
					if(identifiables==null)
	    				return;
					if(getBusiness()!=null)
	    				getBusiness().create(identifiables);
	    			else for(IDENTIFIABLE identifiable : identifiables)
	    				getDao().create(identifiable);
	    		}
	    		
	    		@Override
	    		public void update(Collection<IDENTIFIABLE> identifiables) {
	    			if(identifiables==null)
	    				return;
	    			if(getBusiness()!=null)
	    				getBusiness().update(identifiables);
	    			else for(IDENTIFIABLE identifiable : identifiables)
	    				getDao().update(identifiable);
	    		}
	    		
	    		@Override
	    		public void delete(Collection<IDENTIFIABLE> identifiables) {
	    			if(identifiables==null)
	    				return;
	    			if(getBusiness()!=null)
	    				getBusiness().delete(identifiables);
	    			else for(IDENTIFIABLE identifiable : identifiables)
	    				getDao().delete(identifiable);
	    		}
	    		
	    		@Override
	    		public void operate(Collection<IDENTIFIABLE> identifiables, Crud crud) {
	    			if(identifiables==null)
	    				return;
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
	
	/**/
	
	protected Collection<? extends Listener<?> > getListeners(){
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static void beforeInstanciateOne(@SuppressWarnings("rawtypes") Collection listeners,final UserAccount userAccount){
		Listener.Adapter.beforeInstanciateOne(listeners, userAccount);
	}
	
	@SuppressWarnings("unchecked")
	public static void afterInstanciateOne(@SuppressWarnings("rawtypes") Collection listeners,final UserAccount userAccount,final AbstractIdentifiable identifiable){
		Listener.Adapter.afterInstanciateOne(listeners, userAccount, identifiable);
	}
	
	@SuppressWarnings("unchecked")
	public static void beforeCreate(Collection<? extends Listener<?> > listeners,final AbstractIdentifiable identifiable){
		Listener.Adapter.beforeCreate((Collection<Listener<AbstractIdentifiable>>) listeners, identifiable);
	}
	
	@SuppressWarnings("unchecked")
	public static void afterCreate(@SuppressWarnings("rawtypes") Collection listeners,final AbstractIdentifiable identifiable){
		Listener.Adapter.afterCreate(listeners, identifiable);
	}
	
	@SuppressWarnings("unchecked")
	public static void beforeUpdate(Collection<? extends Listener<?> > listeners,final AbstractIdentifiable identifiable){
		Listener.Adapter.beforeUpdate((Collection<Listener<AbstractIdentifiable>>) listeners, identifiable);
	}
	
	@SuppressWarnings("unchecked")
	public static void afterUpdate(@SuppressWarnings("rawtypes") Collection listeners,final AbstractIdentifiable identifiable){
		Listener.Adapter.afterUpdate(listeners, identifiable);
	}
	
	@SuppressWarnings("unchecked")
	public static void beforeDelete(Collection<? extends Listener<?> > listeners,final AbstractIdentifiable identifiable){
		Listener.Adapter.beforeDelete((Collection<Listener<AbstractIdentifiable>>) listeners, identifiable);
	}
	
	@SuppressWarnings("unchecked")
	public static void afterDelete(@SuppressWarnings("rawtypes") Collection listeners,final AbstractIdentifiable identifiable){
		Listener.Adapter.afterDelete(listeners, identifiable);
	}
	
	/**/
	
	public static interface Listener<IDENTIFIABLE extends AbstractIdentifiable> {
		
		void beforeInstanciateOne(UserAccount userAccount);
		void afterInstanciateOne(UserAccount userAccount,IDENTIFIABLE identifiable);
		
		void beforeCreate(IDENTIFIABLE identifiable);
		void afterCreate(IDENTIFIABLE identifiable);
		void createReportFile(IDENTIFIABLE identifiable,String reportTemplateCode,Boolean updateExisting);
		
		void beforeUpdate(IDENTIFIABLE identifiable);
		void afterUpdate(IDENTIFIABLE identifiable);
		
		void beforeDelete(IDENTIFIABLE identifiable);
		void afterDelete(IDENTIFIABLE identifiable);
		
		Collection<Class<? extends AbstractIdentifiable>> getCascadeToClasses();
		void setCascadeToClasses(Collection<Class<? extends AbstractIdentifiable>> classes);
		
		Listener<IDENTIFIABLE> addCascadeToClass(Class<? extends AbstractIdentifiable> aClass);
		Listener<IDENTIFIABLE> addCascadeToClasses(@SuppressWarnings("unchecked") Class<? extends AbstractIdentifiable>...classes);
		
		Collection<String> getCascadeToReportTemplateCodes();
		void setCascadeToReportTemplateCodes(Collection<String> reportTemplateCodes);
		
		Listener<IDENTIFIABLE> addCascadeToReportTemplateCode(String reportTemplateCode);
		Listener<IDENTIFIABLE> addCascadeToReportTemplateCodes(String...reportTemplateCodes);
		
		AbstractIdentifiable findParent(IDENTIFIABLE identifiable);
	    Collection<AbstractIdentifiable> findParentRecursively(IDENTIFIABLE identifiable);
	    void setParents(IDENTIFIABLE identifiable);
	    void setParents(Collection<? extends AbstractIdentifiable> identifiables);
		
		/**/
		
		@Getter @Setter
		public static class Adapter<IDENTIFIABLE extends AbstractIdentifiable> extends BeanAdapter implements Listener<IDENTIFIABLE>,Serializable{
			private static final long serialVersionUID = 8213436661982661753L;
			
			private Collection<Class<? extends AbstractIdentifiable>> cascadeToClasses;
			private Collection<String> cascadeToReportTemplateCodes;
			
			@Override public void beforeCreate(IDENTIFIABLE identifiable) {}
			@Override public void afterCreate(IDENTIFIABLE identifiable) {}
			
			@Override public void beforeUpdate(IDENTIFIABLE identifiable) {}
			@Override public void afterUpdate(IDENTIFIABLE identifiable) {}
			
			@Override public void beforeDelete(IDENTIFIABLE identifiable) {}
			@Override public void afterDelete(IDENTIFIABLE identifiable) {}
			
			@Override public void beforeInstanciateOne(UserAccount userAccount) {}
			@Override public void afterInstanciateOne(UserAccount userAccount, IDENTIFIABLE identifiable) {}
			
			@Override
			public Adapter<IDENTIFIABLE> addCascadeToClass(Class<? extends AbstractIdentifiable> aClass){
				Collection<Class<? extends AbstractIdentifiable>> classes = new ArrayList<>();
				classes.add(aClass);
				return addCascadeToClasses(classes);
			}
			
			@Override
			public Adapter<IDENTIFIABLE> addCascadeToClasses(@SuppressWarnings("unchecked") Class<? extends AbstractIdentifiable>...classes){
				if(classes!=null){
					addCascadeToClasses(Arrays.asList(classes));
				}
				return this;
			}
			
			public Adapter<IDENTIFIABLE> addCascadeToClasses(Collection<Class<? extends AbstractIdentifiable>> classes){
				if(classes!=null && !classes.isEmpty()){
					if(cascadeToClasses==null)
						cascadeToClasses = new LinkedHashSet<>();
					cascadeToClasses.addAll(classes);
				}
				return this;
			}
			protected Boolean containsCascadeToClass(Class<? extends AbstractIdentifiable> aClass){
				return cascadeToClasses!=null && cascadeToClasses.contains(aClass);
			}
			
			@Override
			public Adapter<IDENTIFIABLE> addCascadeToReportTemplateCode(String reportTemplateCode){
				Collection<String> reportTemplateCodes = new ArrayList<>();
				reportTemplateCodes.add(reportTemplateCode);
				return addCascadeToReportTemplateCodes(reportTemplateCodes);
			}
			
			@Override
			public Adapter<IDENTIFIABLE> addCascadeToReportTemplateCodes(String...reportTemplateCodes){
				if(reportTemplateCodes!=null){
					addCascadeToReportTemplateCodes(Arrays.asList(reportTemplateCodes));
				}
				return this;
			}
			
			public Adapter<IDENTIFIABLE> addCascadeToReportTemplateCodes(Collection<String> reportTemplateCodes){
				if(reportTemplateCodes!=null && !reportTemplateCodes.isEmpty()){
					if(this.cascadeToReportTemplateCodes==null)
						this.cascadeToReportTemplateCodes = new LinkedHashSet<>();
					this.cascadeToReportTemplateCodes.addAll(reportTemplateCodes);
				}
				return this;
			}
			protected Boolean containsCascadeToReportTemplateCode(String reportTemplateCode){
				return cascadeToReportTemplateCodes!=null && cascadeToReportTemplateCodes.contains(reportTemplateCode);
			}
			
			@Override
			public void createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode,Boolean updateExisting) {
				FileIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
		    	searchCriteria.addIdentifiableGlobalIdentifier(identifiable);
		    	searchCriteria.addRepresentationType(inject(FileRepresentationTypeDao.class).read(reportTemplateCode));
		    	Collection<FileIdentifiableGlobalIdentifier> fileIdentifiableGlobalIdentifiers = inject(FileIdentifiableGlobalIdentifierBusiness.class).findByCriteria(searchCriteria);
		    	
		    	CreateReportFileArguments<IDENTIFIABLE> arguments;
				
				File file = null;
		    	if(Boolean.TRUE.equals(updateExisting)){
		    		ExceptionUtils.getInstance().exception(fileIdentifiableGlobalIdentifiers.size() > 1, "too.much.filereport.found.for.update");
		    		if(!fileIdentifiableGlobalIdentifiers.isEmpty())
		    			file = fileIdentifiableGlobalIdentifiers.iterator().next().getFile();
		    	}else{
		    		
		    	}
		    	arguments = new CreateReportFileArguments<IDENTIFIABLE>(reportTemplateCode, identifiable,file);
				inject(BusinessInterfaceLocator.class).injectTypedByObject(identifiable).createReportFile(identifiable, arguments);
				
			}
			
			@Override
			public AbstractIdentifiable findParent(IDENTIFIABLE identifiable) {
				return null;
			}
			@Override
			public Collection<AbstractIdentifiable> findParentRecursively(IDENTIFIABLE identifiable) {
				return null;
			}
			@Override
			public void setParents(IDENTIFIABLE identifiable) {}
			@Override
			public void setParents(Collection<? extends AbstractIdentifiable> identifiables) {}
			
			/**/
			
			public static void beforeInstanciateOne(Collection<Listener<AbstractIdentifiable> > listeners,final UserAccount userAccount){
				ListenerUtils.getInstance().execute(listeners, new ListenerUtils.VoidMethod<Listener<AbstractIdentifiable>>() {
					@Override
					public void execute(Listener<AbstractIdentifiable> listener) {
						listener.beforeInstanciateOne(userAccount);
					}
				});
			}
			
			public static void afterInstanciateOne(Collection<Listener<AbstractIdentifiable> > listeners,final UserAccount userAccount,final AbstractIdentifiable identifiable){
				ListenerUtils.getInstance().execute(listeners, new ListenerUtils.VoidMethod<Listener<AbstractIdentifiable>>() {
					@Override
					public void execute(Listener<AbstractIdentifiable> listener) {
						listener.afterInstanciateOne(userAccount,identifiable);
					}
				});
			}
			
			public static void beforeCreate(Collection<Listener<AbstractIdentifiable> > listeners,final AbstractIdentifiable identifiable){
				ListenerUtils.getInstance().execute(listeners, new ListenerUtils.VoidMethod<Listener<AbstractIdentifiable>>() {
					@Override
					public void execute(Listener<AbstractIdentifiable> listener) {
						listener.beforeCreate(identifiable);
					}
				});
			}
			
			public static void afterCreate(Collection<Listener<AbstractIdentifiable> > listeners,final AbstractIdentifiable identifiable){
				ListenerUtils.getInstance().execute(listeners, new ListenerUtils.VoidMethod<Listener<AbstractIdentifiable>>() {
					@Override
					public void execute(Listener<AbstractIdentifiable> listener) {
						listener.afterCreate(identifiable);
					}
				});
			}
			
			public static void beforeUpdate(Collection<Listener<AbstractIdentifiable> > listeners,final AbstractIdentifiable identifiable){
				ListenerUtils.getInstance().execute(listeners, new ListenerUtils.VoidMethod<Listener<AbstractIdentifiable>>() {
					@Override
					public void execute(Listener<AbstractIdentifiable> listener) {
						listener.beforeUpdate(identifiable);
					}
				});
			}
			
			public static void afterUpdate(Collection<Listener<AbstractIdentifiable> > listeners,final AbstractIdentifiable identifiable){
				ListenerUtils.getInstance().execute(listeners, new ListenerUtils.VoidMethod<Listener<AbstractIdentifiable>>() {
					@Override
					public void execute(Listener<AbstractIdentifiable> listener) {
						listener.afterUpdate(identifiable);
					}
				});
			}
			
			public static void beforeDelete(Collection<Listener<AbstractIdentifiable> > listeners,final AbstractIdentifiable identifiable){
				ListenerUtils.getInstance().execute(listeners, new ListenerUtils.VoidMethod<Listener<AbstractIdentifiable>>() {
					@Override
					public void execute(Listener<AbstractIdentifiable> listener) {
						listener.beforeDelete(identifiable);
					}
				});
			}
			
			public static void afterDelete(Collection<Listener<AbstractIdentifiable> > listeners,final AbstractIdentifiable identifiable){
				ListenerUtils.getInstance().execute(listeners, new ListenerUtils.VoidMethod<Listener<AbstractIdentifiable>>() {
					@Override
					public void execute(Listener<AbstractIdentifiable> listener) {
						listener.afterDelete(identifiable);
					}
				});
			}
			
			
			
			/**/
			
			public static class Default<IDENTIFIABLE extends AbstractIdentifiable> extends Adapter<IDENTIFIABLE> implements Serializable{

				private static final long serialVersionUID = 1L;

				@Override
				public void afterUpdate(IDENTIFIABLE identifiable) {
					super.afterUpdate(identifiable);
					//Update related reports
					for(String reportTemplateCode : getCascadeToReportTemplateCodes()){
						FileIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
						searchCriteria.addIdentifiableGlobalIdentifier(identifiable);
						searchCriteria.addRepresentationType(inject(FileRepresentationTypeDao.class).read(reportTemplateCode));
						Collection<FileIdentifiableGlobalIdentifier> fileIdentifiableGlobalIdentifiers = inject(FileIdentifiableGlobalIdentifierBusiness.class).findByCriteria(searchCriteria);
						if(fileIdentifiableGlobalIdentifiers.isEmpty()){
							
						}else{
							@SuppressWarnings("unchecked")
							Class<AbstractIdentifiable> clazz = (Class<AbstractIdentifiable>) identifiable.getClass();
							TypedBusiness<AbstractIdentifiable> business = inject(BusinessInterfaceLocator.class).injectTyped(clazz);							
							for(FileIdentifiableGlobalIdentifier fileIdentifiableGlobalIdentifier : fileIdentifiableGlobalIdentifiers){
								CreateReportFileArguments<AbstractIdentifiable> arguments = new CreateReportFileArguments<AbstractIdentifiable>(reportTemplateCode,identifiable,fileIdentifiableGlobalIdentifier.getFile());
								business.createReportFile(identifiable, arguments);
							}
						}
					}
				}
			}
			
		}
		
		/**/
		
		public static interface SearchCriteria<IDENTIFIABLE extends AbstractIdentifiable,CRITERIA extends AbstractFieldValueSearchCriteriaSet> extends Listener<IDENTIFIABLE> {
			
			void beforeSearchFind(CRITERIA searchCriteria);
			void afterSearchFind(CRITERIA searchCriteria,Collection<IDENTIFIABLE> identifiables);
			
			/**/
			
			public static class Adapter<IDENTIFIABLE extends AbstractIdentifiable,CRITERIA extends AbstractFieldValueSearchCriteriaSet> extends Listener.Adapter<IDENTIFIABLE> implements SearchCriteria<IDENTIFIABLE,CRITERIA>,Serializable{
				private static final long serialVersionUID = 7753187759402144033L;

				@Override public void beforeSearchFind(CRITERIA searchCriteria) {}
				@Override public void afterSearchFind(CRITERIA searchCriteria,Collection<IDENTIFIABLE> identifiables) {}
				
			}
		}
	}
}
