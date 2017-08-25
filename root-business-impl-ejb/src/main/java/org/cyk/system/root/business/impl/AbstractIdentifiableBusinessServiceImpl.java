package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.IdentifiableBusinessService;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.TypedBusiness.CreateReportFileArguments;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.FileIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier.SearchCriteria;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.accessor.InstanceFieldSetter;
import org.cyk.utility.common.builder.InstanceCopyBuilder;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;
import org.cyk.utility.common.file.ExcelSheetReader;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.MicrosoftExcelHelper;

import lombok.Getter;
import lombok.Setter;

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
		try {
			String name = getClass().getName();
			name = StringUtils.replace(name, "business.impl", "model");
			name = StringUtils.substringBefore(name, "BusinessImpl");
			return Class.forName(name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} //inject(ModelClassLocator.class).locate(getClass());
		//return (Class<?>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Class<IDENTIFIABLE> getClazz(){
		return clazz;
	}
	
	@Override
	public IDENTIFIABLE save(IDENTIFIABLE identifiable) {
		if(identifiable.getIdentifier()==null)
			create(identifiable);
		else
			update(identifiable);
		return identifiable;
	}

	@Override
	public void save(Collection<IDENTIFIABLE> identifiables) {
		for(IDENTIFIABLE identifiable : identifiables)
			save(identifiable);
	}
		
	@Override @Deprecated
	public void synchronize(ExcelSheetReader excelSheetReader,InstanceFieldSetter.TwoDimensionObjectArray<IDENTIFIABLE> setter) {
		logTrace("Synchronize {} from excel", clazz.getSimpleName());
		excelSheetReader.execute();
		setter.setInput(excelSheetReader.getValues());
		setter.execute();
		save(setter.getOutput());
		logTrace("Synchronization of {} from excel done.", clazz.getSimpleName());
	}
	
	@Override
	public Collection<IDENTIFIABLE> instanciateMany(MicrosoftExcelHelper.Workbook.Sheet sheet,InstanceHelper.Builder.OneDimensionArray<IDENTIFIABLE> instanceBuilder) {
		Collection<IDENTIFIABLE> identifiables = new ArrayList<>();
		InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<IDENTIFIABLE> instancesBuilder = new InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<IDENTIFIABLE>(null);
		instancesBuilder.setOneDimensionArray(instanceBuilder);
		
		if(sheet.getValues()!=null)
			identifiables.addAll((Collection<IDENTIFIABLE>)instancesBuilder.setInput(sheet.getValues()).execute());
		if(sheet.getIgnoreds()!=null)
			identifiables.addAll((Collection<IDENTIFIABLE>)instancesBuilder.setInput(sheet.getIgnoreds()).execute());
		return identifiables;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void synchronize(MicrosoftExcelHelper.Workbook.Sheet sheet,InstanceHelper.Builder.OneDimensionArray<IDENTIFIABLE> instanceBuilder) {
		logTrace("Synchronize {} from excel sheet", clazz.getSimpleName());
		InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<IDENTIFIABLE> instancesBuilder = new InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<IDENTIFIABLE>(null);
		instancesBuilder.setOneDimensionArray(instanceBuilder);
		
		if(sheet.getValues()!=null)
			inject(GenericBusiness.class).create((Collection<AbstractIdentifiable>)instancesBuilder.setInput(sheet.getValues()).execute());
		if(sheet.getIgnoreds()!=null)
			inject(GenericBusiness.class).update((Collection<AbstractIdentifiable>)instancesBuilder.setInput(sheet.getIgnoreds()).execute());
		logTrace("Synchronization of {} from excel sheet done.", clazz.getSimpleName());
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
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<IDENTIFIABLE> find(Collection<IDENTIFIABLE> identifiables, Collection<String> codes) {
		Collection<IDENTIFIABLE> collection = new ArrayList<>();
		for(IDENTIFIABLE identifiable : identifiables)
			for(String code: codes)
				if(code.equals(identifiable.getCode()))
					collection.add(identifiable);
		return collection;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<IDENTIFIABLE> find(Collection<IDENTIFIABLE> identifiables, String code) {
		return find(identifiables,Arrays.asList(code));
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IDENTIFIABLE findOne(Collection<IDENTIFIABLE> identifiables, String code) {
		Collection<IDENTIFIABLE> collection = find(identifiables, code);
		return collection.isEmpty() ? null : collection.iterator().next();
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
	
	@Override
	public <SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> Collection<IDENTIFIABLE> findBySearchCriteria(SEARCH_CRITERIA searchCriteria) {
		if(StringUtils.isBlank(((AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet)searchCriteria).getName().getValue())){
    		return findAll(searchCriteria.getReadConfig());
    	}
    	prepareFindByCriteria(searchCriteria);
    	return getPersistenceService().readBySearchCriteria(searchCriteria);
		//return null;
	}

	@Override
	public <SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> Long countBySearchCriteria(SEARCH_CRITERIA searchCriteria) {
		if(StringUtils.isBlank(((AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet)searchCriteria).getName().getValue()))
    		return countAll();
    	prepareFindByCriteria(searchCriteria);
    	return getPersistenceService().countBySearchCriteria(searchCriteria);
	}

	@Override
	public Collection<IDENTIFIABLE> findByString(String string,Collection<IDENTIFIABLE> excludedIdentifiables,DataReadConfiguration dataReadConfiguration) {
		StringSearchCriteria stringSearchCriteria = new StringSearchCriteria(string);
		stringSearchCriteria.excludeCode(excludedIdentifiables);
		return findByString(stringSearchCriteria,dataReadConfiguration);
	}
	
	@Override
	public Collection<IDENTIFIABLE> findByString(String string,Collection<IDENTIFIABLE> excludedIdentifiables) {
		DataReadConfiguration dataReadConfiguration = new DataReadConfiguration();
		return findByString(string,excludedIdentifiables,dataReadConfiguration);
	}
	
	@Override
	public Collection<IDENTIFIABLE> findByString(StringSearchCriteria stringSearchCriteria,DataReadConfiguration dataReadConfiguration) {
		GlobalIdentifier.SearchCriteria searchCriteria = new GlobalIdentifier.SearchCriteria();
		searchCriteria.setCode(new StringSearchCriteria(stringSearchCriteria));
		searchCriteria.setName(new StringSearchCriteria(stringSearchCriteria));
		searchCriteria.setReadConfig(dataReadConfiguration);
		return findByGlobalIdentifierSearchCriteria(searchCriteria);
	}
	
	@Override
	public Collection<IDENTIFIABLE> findByString(StringSearchCriteria stringSearchCriteria) {
		DataReadConfiguration dataReadConfiguration = new DataReadConfiguration();
		return findByString(stringSearchCriteria, dataReadConfiguration);
	}
	
	@Override
	public Long countByString(String string,Collection<IDENTIFIABLE> excludedIdentifiables) {
		StringSearchCriteria stringSearchCriteria = new StringSearchCriteria(string);
		stringSearchCriteria.excludeCode(excludedIdentifiables);
		return countByString(stringSearchCriteria);
	}
	
	@Override
	public Long countByString(String string) {
		return countByString(string,null);
	}
	
	@Override
	public Long countByString(StringSearchCriteria stringSearchCriteria) {
		GlobalIdentifier.SearchCriteria searchCriteria = new GlobalIdentifier.SearchCriteria();
		searchCriteria.set(new StringSearchCriteria(stringSearchCriteria));
		return countByGlobalIdentifierSearchCriteria(searchCriteria);
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
		IDENTIFIABLE identifiable = newInstance(getClazz());
		if(!GlobalIdentifier.EXCLUDED.contains(clazz))
			identifiable.getGlobalIdentifierCreateIfNull();
		return identifiable;
	}
	@Override
	public IDENTIFIABLE instanciateOne(UserAccount userAccount) {
		beforeInstanciateOne(getListeners(), userAccount);
		IDENTIFIABLE identifiable = instanciateOne();
		afterInstanciateOne(getListeners(), userAccount, identifiable);
		return identifiable;
	}
	
	@Override
	public IDENTIFIABLE instanciateOne(UserAccount userAccount,IDENTIFIABLE copy) {
		beforeInstanciateOne(getListeners(), userAccount/*,copy*/);
		IDENTIFIABLE identifiable = copy == null ? instanciateOne() : duplicate(copy);
		afterInstanciateOne(getListeners(), userAccount, identifiable/*,copy*/);
		return identifiable;
	}

	@Override
	public IDENTIFIABLE instanciateOne(ObjectFieldValues arguments) {
		IDENTIFIABLE identifiable = instanciateOne();
		commonUtils.instanciateOne(getClazz(),identifiable, arguments);
		return identifiable;
	}

	public InstanceCopyBuilder<IDENTIFIABLE> getInstanceCopyBuilder(){
		return new InstanceCopyBuilder<IDENTIFIABLE>().addIgnoredFieldAnnotationClasses(javax.persistence.Id.class,javax.persistence.OneToOne.class
				,javax.persistence.Transient.class,javax.persistence.GeneratedValue.class);
	}
	
	@Override
	public IDENTIFIABLE duplicate(IDENTIFIABLE identifiable) {
		IDENTIFIABLE duplicated = getInstanceCopyBuilder().setSource(identifiable).build();
		return duplicated;
	}
	
	@Override
	public Collection<IDENTIFIABLE> instanciateMany(Collection<ObjectFieldValues> arguments) {
		Collection<IDENTIFIABLE> identifiables = new ArrayList<>();
		for(ObjectFieldValues o : arguments)
			identifiables.add(instanciateOne(o));
		return identifiables;
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
	public Boolean isIdentified(IDENTIFIABLE identifiable){
		return identifiable!=null && identifiable.getIdentifier()!=null;
	}
	
	@Override
	public Boolean isNotIdentified(IDENTIFIABLE identifiable){
		return identifiable!=null && identifiable.getIdentifier()==null;
	}
	
	protected void createIfNotIdentified(AbstractIdentifiable identifiable){
		if(identifiable==null)
			return;
		@SuppressWarnings("unchecked")
		Class<AbstractIdentifiable> aClass = (Class<AbstractIdentifiable>) identifiable.getClass();
		TypedBusiness<AbstractIdentifiable> business = getBusiness(aClass);
		if(business.isNotIdentified(identifiable))
			business.create(identifiable);
	}
	
	protected void updateIfNotIdentified(AbstractIdentifiable identifiable){
		if(identifiable==null)
			return;
		@SuppressWarnings("unchecked")
		Class<AbstractIdentifiable> aClass = (Class<AbstractIdentifiable>) identifiable.getClass();
		TypedBusiness<AbstractIdentifiable> business = getBusiness(aClass);
		if(business.isNotIdentified(identifiable))
			business.create(identifiable);
		else
			business.update(identifiable);
	}
	
	protected void createIfIdentified(AbstractIdentifiable identifiable){
		@SuppressWarnings("unchecked")
		Class<AbstractIdentifiable> aClass = (Class<AbstractIdentifiable>) identifiable.getClass();
		TypedBusiness<AbstractIdentifiable> business = getBusiness(aClass);
		if(business.isIdentified(identifiable))
			business.delete(identifiable);
	}
	
	protected <T extends AbstractIdentifiable> TypedBusiness<T> getBusiness(Class<T> aClass) {
		return inject(BusinessInterfaceLocator.class).injectTyped(aClass);
	}
	
	protected <T extends AbstractIdentifiable> TypedDao<T> getPersistence(Class<T> aClass) {
		return inject(PersistenceInterfaceLocator.class).injectTyped(aClass);
	}
	
	@Override
	public Collection<IDENTIFIABLE> findByGlobalIdentifierSearchCriteria(SearchCriteria globalIdentifierSearchCriteria) {
		Collection<IDENTIFIABLE> results;
		prepareFindByCriteria(globalIdentifierSearchCriteria);
		getPersistenceService().getDataReadConfig().set(globalIdentifierSearchCriteria.getReadConfig());
		results = getPersistenceService().readByGlobalIdentifierSearchCriteria(globalIdentifierSearchCriteria);	
		logTrace("Find {} by global identifier search criteria {}. Found {}", clazz.getSimpleName(),globalIdentifierSearchCriteria,results.size());
		return results;
	}

	@Override
	public Long countByGlobalIdentifierSearchCriteria(SearchCriteria globalIdentifierSearchCriteria) {
		Long count;
		prepareFindByCriteria(globalIdentifierSearchCriteria);
		count = getPersistenceService().countByGlobalIdentifierSearchCriteria(globalIdentifierSearchCriteria);	
		logTrace("Count {} by global identifier search criteria {}. Found {}", clazz.getSimpleName(),globalIdentifierSearchCriteria,count);
		return count;
	}
	
	@Override
	public IDENTIFIABLE findFirstWhereExistencePeriodFromDateIsLessThan(IDENTIFIABLE identifiable) {
		return getPersistenceService().readFirstWhereExistencePeriodFromDateIsLessThan(identifiable);
	}

	@Override
	public IDENTIFIABLE find(String code,Boolean throwableIfNull) {
		IDENTIFIABLE identifiable = getPersistenceService().read(code);
		if(identifiable==null && Boolean.TRUE.equals(throwableIfNull))
			exceptionUtils().exception(Boolean.TRUE, "codedoesnotexist", code);
		return identifiable;
	}

	@Override
	public IDENTIFIABLE findFirstWhereExistencePeriodFromDateIsLessThan(String code) {
		return findFirstWhereExistencePeriodFromDateIsLessThan(find(code, Boolean.TRUE));
	}

	@Override
	public Collection<IDENTIFIABLE> findWhereExistencePeriodFromDateIsLessThan(IDENTIFIABLE identifiable) {
		return getPersistenceService().readWhereExistencePeriodFromDateIsLessThan(identifiable);
	}
	
	@Override
	public Collection<IDENTIFIABLE> findWhereExistencePeriodFromDateIsLessThan(String code) {
		return findWhereExistencePeriodFromDateIsLessThan(find(code, Boolean.TRUE));
	}
	
	@Override
	public Long countWhereExistencePeriodFromDateIsLessThan(IDENTIFIABLE identifiable) {
		return getPersistenceService().countWhereExistencePeriodFromDateIsLessThan(identifiable);
	}
	
	@Override
	public Long countWhereExistencePeriodFromDateIsLessThan(String code) {
		return countWhereExistencePeriodFromDateIsLessThan(find(code, Boolean.TRUE));
	}
	
	@Override
	public Collection<IDENTIFIABLE> findWhereExistencePeriodFromDateIsGreaterThan(IDENTIFIABLE identifiable) {
		return getPersistenceService().readWhereExistencePeriodFromDateIsGreaterThan(identifiable);
	}
	
	@Override
	public Collection<IDENTIFIABLE> findWhereExistencePeriodFromDateIsGreaterThan(String code) {
		return findWhereExistencePeriodFromDateIsGreaterThan(find(code, Boolean.TRUE));
	}
	
	@Override
	public Long countWhereExistencePeriodFromDateIsGreaterThan(IDENTIFIABLE identifiable) {
		return getPersistenceService().countWhereExistencePeriodFromDateIsGreaterThan(identifiable);
	}
	
	@Override
	public Long countWhereExistencePeriodFromDateIsGreaterThan(String code) {
		return countWhereExistencePeriodFromDateIsGreaterThan(find(code, Boolean.TRUE));
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
	public static void beforeGetPropertyValueTokens(@SuppressWarnings("rawtypes") Collection listeners,final AbstractIdentifiable identifiable,final String name){
		Listener.Adapter.beforeGetPropertyValueTokens(listeners, identifiable, name);
	}
	
	@SuppressWarnings("unchecked")
	public static Object[] afterGetPropertyValueTokens(@SuppressWarnings("rawtypes") Collection listeners,final AbstractIdentifiable identifiable,final String name, final Object[] tokens){
		return Listener.Adapter.afterGetPropertyValueTokens(listeners, identifiable, name, tokens);
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
	
	protected LogMessage.Builder createLogMessageBuilder(Object action){
		LogMessage.Builder logMessageBuilder = new LogMessage.Builder(action,clazz);
		return logMessageBuilder;
	}
	
	protected void addLogMessageBuilderParameters(LogMessage.Builder logMessageBuilder,Object...objects){
		if(logMessageBuilder!=null)
			logMessageBuilder.addParameters(objects);
	}
	
	@Override
	public void setRelatedIdentifiables(IDENTIFIABLE identifiable,Boolean image,Set<String> relatedIdentifiableFieldNames){
		for(String fieldName : relatedIdentifiableFieldNames){
			setRelatedIdentifiable(identifiable, fieldName);
		}
		if(Boolean.TRUE.equals(image)){
			inject(FileBusiness.class).findInputStream(identifiable.getImage(), Boolean.TRUE);
		}
	}
	
	@Override
	public void setRelatedIdentifiables(IDENTIFIABLE identifiable,Set<String> relatedIdentifiableFieldNames){
		setRelatedIdentifiables(identifiable,Boolean.FALSE, relatedIdentifiableFieldNames);
	}
	
	@Override
	public void setRelatedIdentifiables(IDENTIFIABLE identifiable,Boolean image,String...relatedIdentifiableFieldNames){
		setRelatedIdentifiables(identifiable,image, new HashSet<>(Arrays.asList(relatedIdentifiableFieldNames)));
		
	}
	
	@Override
	public void setRelatedIdentifiables(IDENTIFIABLE identifiable,String...relatedIdentifiableFieldNames){
		setRelatedIdentifiables(identifiable,Boolean.FALSE, relatedIdentifiableFieldNames);
	}
	
	protected void setRelatedIdentifiable(IDENTIFIABLE identifiable,String relatedIdentifiableFieldName){
		throwNotYetImplemented();
	}
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(IDENTIFIABLE identifiable){
		return null;
	}
	
	@Override
	public Collection<AbstractIdentifiable> findRelatedInstances(IDENTIFIABLE identifiable,Boolean setNewValue,Object newValue){
		Collection<AbstractIdentifiable> relatedInstances = null;
		Collection<String> relatedInstanceFieldNames = findRelatedInstanceFieldNames(identifiable);
		if(relatedInstanceFieldNames!=null){
			relatedInstances = new ArrayList<>();
			FieldHelper fieldHelper = FieldHelper.getInstance();
			for(String relatedInstanceFieldName : relatedInstanceFieldNames){
				AbstractIdentifiable value = (AbstractIdentifiable) fieldHelper.read(identifiable, relatedInstanceFieldName);
				if(Boolean.TRUE.equals(setNewValue)){
					fieldHelper.writeField(fieldHelper.get(clazz, relatedInstanceFieldName), identifiable, newValue);
				}if(value!=null)
					relatedInstances.add(value);
			}
		}
		//logTrace("find related instance. instance {} , field names {} , result {}", identifiable,relatedInstanceFieldNames,relatedInstances);
		return relatedInstances;
	}
	
	@Override
	public Collection<AbstractIdentifiable> findRelatedInstances(IDENTIFIABLE identifiable){
		return findRelatedInstances(identifiable, Boolean.FALSE, null);
	}
	
	protected void setRandomValues(IDENTIFIABLE identifiable,String...fieldNames){
		
	}
	
	/**/
	
	public static interface Listener<IDENTIFIABLE extends AbstractIdentifiable> {
		
		void beforeInstanciateOne(UserAccount userAccount);
		void afterInstanciateOne(UserAccount userAccount,IDENTIFIABLE identifiable);
		
		void beforeGetPropertyValueTokens(IDENTIFIABLE identifiable,String name);
		Object[] afterGetPropertyValueTokens(IDENTIFIABLE identifiable,String name,Object[] tokens);
		
		void beforeCreate(IDENTIFIABLE identifiable);
		void afterCreate(IDENTIFIABLE identifiable);
		void createReportFile(CreateReportFileArguments<IDENTIFIABLE> arguments);
		
		void beforeUpdate(IDENTIFIABLE identifiable);
		void afterUpdate(IDENTIFIABLE identifiable);
		
		void beforeDelete(IDENTIFIABLE identifiable);
		void afterDelete(IDENTIFIABLE identifiable);
		
		void beforeCrud(IDENTIFIABLE identifiable,Crud crud);
		void afterCrud(IDENTIFIABLE identifiable,Crud crud);
		
		Collection<Class<? extends AbstractIdentifiable>> getCascadeToClasses();
		void setCascadeToClasses(Collection<Class<? extends AbstractIdentifiable>> classes);
		
		Listener<IDENTIFIABLE> addCascadeToClass(Class<? extends AbstractIdentifiable> aClass);
		Listener<IDENTIFIABLE> addCascadeToClasses(@SuppressWarnings("unchecked") Class<? extends AbstractIdentifiable>...classes);
		
		Collection<String> getCascadeToReportTemplateCodes();
		void setCascadeToReportTemplateCodes(Collection<String> reportTemplateCodes);
		
		Listener<IDENTIFIABLE> addCascadeToReportTemplateCode(String reportTemplateCode);
		Listener<IDENTIFIABLE> addCascadeToReportTemplateCodes(String...reportTemplateCodes);
		
		Collection<String> getMetricValueMetricCollectionCodes();
		void setMetricValueMetricCollectionCodes(Collection<String> codes);
		
		Listener<IDENTIFIABLE> addMetricValueMetricCollectionCodes(Collection<String> codes);
		Listener<IDENTIFIABLE> addMetricValueMetricCollectionCodes(String...codes);
		
		AbstractIdentifiable findParent(IDENTIFIABLE identifiable);
	    Collection<AbstractIdentifiable> findParentRecursively(IDENTIFIABLE identifiable);
	    void setParents(IDENTIFIABLE identifiable);
	    void setParents(Collection<? extends AbstractIdentifiable> identifiables);
		
	    String getCodePrefix();
	    Listener<IDENTIFIABLE> setCodePrefix(String prefix);
	    
		/**/
		
		@Getter @Setter
		public static class Adapter<IDENTIFIABLE extends AbstractIdentifiable> extends BeanAdapter implements Listener<IDENTIFIABLE>,Serializable{
			private static final long serialVersionUID = 8213436661982661753L;
			
			private Collection<Class<? extends AbstractIdentifiable>> cascadeToClasses;
			private Collection<String> cascadeToReportTemplateCodes,metricValueMetricCollectionCodes;
			private String codePrefix;
			
			@Override public void beforeGetPropertyValueTokens(IDENTIFIABLE identifiable, String name) {}
			@Override public Object[] afterGetPropertyValueTokens(IDENTIFIABLE identifiable,String name, Object[] tokens) {return tokens;}
			
			@Override public void beforeCreate(IDENTIFIABLE identifiable) {}
			@Override public void afterCreate(IDENTIFIABLE identifiable) {}
			
			@Override public void beforeUpdate(IDENTIFIABLE identifiable) {}
			@Override public void afterUpdate(IDENTIFIABLE identifiable) {}
			
			@Override public void beforeDelete(IDENTIFIABLE identifiable) {}
			@Override public void afterDelete(IDENTIFIABLE identifiable) {}
			
			@Override public void beforeCrud(IDENTIFIABLE identifiable,Crud crud) {}
			@Override public void afterCrud(IDENTIFIABLE identifiable,Crud crud) {}
			
			@Override public void beforeInstanciateOne(UserAccount userAccount) {}
			@Override public void afterInstanciateOne(UserAccount userAccount, IDENTIFIABLE identifiable) {}
			
			@Override
			public Adapter<IDENTIFIABLE> setCodePrefix(String prefix){
				this.codePrefix = prefix;
				return this;
			}
			
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
			
			@Override
			public Adapter<IDENTIFIABLE> addMetricValueMetricCollectionCodes(Collection<String> codes){
				if(codes!=null && !codes.isEmpty()){
					if(this.metricValueMetricCollectionCodes==null)
						this.metricValueMetricCollectionCodes = new LinkedHashSet<>();
					this.metricValueMetricCollectionCodes.addAll(codes);
				}
				return this;
			}
			
			@Override
			public Adapter<IDENTIFIABLE> addMetricValueMetricCollectionCodes(String...codes){
				if(codes!=null){
					addMetricValueMetricCollectionCodes(Arrays.asList(codes));
				}
				return this;
			}
			
			protected Boolean containsCascadeToReportTemplateCode(String reportTemplateCode){
				return cascadeToReportTemplateCodes!=null && cascadeToReportTemplateCodes.contains(reportTemplateCode);
			}
			
			@Override
			public void createReportFile(CreateReportFileArguments<IDENTIFIABLE> arguments) {
				/*FileIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
		    	searchCriteria.addIdentifiableGlobalIdentifier(arguments.getIdentifiable());
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
		    	*/
				inject(BusinessInterfaceLocator.class).injectTypedByObject(arguments.getIdentifiable()).createReportFile(arguments);
				
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
			
			public static void beforeGetPropertyValueTokens(Collection<Listener<AbstractIdentifiable> > listeners,final AbstractIdentifiable identifiable,final String name){
				ListenerUtils.getInstance().execute(listeners, new ListenerUtils.VoidMethod<Listener<AbstractIdentifiable>>() {
					@Override
					public void execute(Listener<AbstractIdentifiable> listener) {
						listener.beforeGetPropertyValueTokens(identifiable, name);
					}
				});
			}
			
			public static Object[] afterGetPropertyValueTokens(Collection<Listener<AbstractIdentifiable> > listeners,final AbstractIdentifiable identifiable,final String name, final Object[] tokens){
				return ListenerUtils.getInstance().getValue(Object[].class,listeners, new ListenerUtils.ResultMethod<Listener<AbstractIdentifiable>,Object[]>() {
					@Override
					public Object[] execute(Listener<AbstractIdentifiable> listener) {
						return listener.afterGetPropertyValueTokens(identifiable, name, tokens);
					}

					@Override
					public Object[] getNullValue() {
						return null;
					}
				});
			}
			
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
				public Object[] afterGetPropertyValueTokens(IDENTIFIABLE identifiable, String name, Object[] tokens) {
					return tokens;
				}
				
				@Override
				public void afterUpdate(IDENTIFIABLE identifiable) {
					super.afterUpdate(identifiable);
					//Update related reports
					Collection<String> reportTemplateCodes = getCascadeToReportTemplateCodes();
					if(reportTemplateCodes!=null)
						for(String reportTemplateCode : getCascadeToReportTemplateCodes()){
							FileIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
							searchCriteria.addIdentifiableGlobalIdentifier(identifiable);
							searchCriteria.addRepresentationType(inject(FileRepresentationTypeDao.class).read(reportTemplateCode));
							Collection<FileIdentifiableGlobalIdentifier> fileIdentifiableGlobalIdentifiers = inject(FileIdentifiableGlobalIdentifierBusiness.class).findByCriteria(searchCriteria);
							if(fileIdentifiableGlobalIdentifiers.isEmpty()){
								
							}else{
								@SuppressWarnings("unchecked")
								Class<IDENTIFIABLE> clazz = (Class<IDENTIFIABLE>) identifiable.getClass();
								TypedBusiness<IDENTIFIABLE> business = inject(BusinessInterfaceLocator.class).injectTyped(clazz);							
								for(FileIdentifiableGlobalIdentifier fileIdentifiableGlobalIdentifier : fileIdentifiableGlobalIdentifiers){
									CreateReportFileArguments<IDENTIFIABLE> arguments = 
											new CreateReportFileArguments.Builder<IDENTIFIABLE>(identifiable)
											.setReportTemplate(reportTemplateCode).setFile(fileIdentifiableGlobalIdentifier.getFile()).build(); 
									business.createReportFile(arguments);
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
	
	public static class OneDimensionArrayBuilderFromGlobalIdentifier<IDENTIFIABLE extends AbstractIdentifiable> extends org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<IDENTIFIABLE>{
		private static final long serialVersionUID = 1L;
		
		public OneDimensionArrayBuilderFromGlobalIdentifier(Class<IDENTIFIABLE> outputClass) {
			super(outputClass);
		}
		
		@Override
		protected IDENTIFIABLE __execute__() {
			IDENTIFIABLE identifiable = super.__execute__();
			if(identifiable.getIdentifier()==null)
				onIdentifierIsNull(identifiable);
			else{
				onIdentifierIsNotNull(identifiable);
			}
			return identifiable;
		}
		
		protected void onIdentifierIsNull(IDENTIFIABLE identifiable){
			GlobalIdentifier globalIdentifier = InstanceHelper.Pool.getInstance().get(GlobalIdentifier.class, getInput()[0]);
			identifiable.setGlobalIdentifier(new InstanceHelper.Copy.Adapter.Default<GlobalIdentifier>(globalIdentifier).execute());
			identifiable.getGlobalIdentifier().setIdentifier(null);
		}
		
		protected void onIdentifierIsNotNull(IDENTIFIABLE identifiable){
			identifiable.getGlobalIdentifier().setIdentifiable(identifiable);
			identifiable.getGlobalIdentifier().setIdentifier(InstanceHelper.getInstance().generateFieldValue(identifiable.getGlobalIdentifier(), GlobalIdentifier.FIELD_IDENTIFIER, String.class));
		}
	}
}
