package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier.SearchCriteria;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteria;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet;
import org.cyk.system.root.persistence.api.TypedDao;

public abstract class AbstractTypedDao<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractPersistenceService<IDENTIFIABLE> implements TypedDao<IDENTIFIABLE>,Serializable {

	private static final long serialVersionUID = -2964204372097468908L;

	//TODO try use collection query for single query to see performance difference
	
	protected String readAll,countAll,readByClasses,countByClasses,readByNotClasses,countByNotClasses,readAllExclude,countAllExclude
		,readAllInclude,countAllInclude,readByGlobalIdentifiers,readByGlobalIdentifierValue,countByGlobalIdentifiers,executeDelete,readByGlobalIdentifier
		,readByGlobalIdentifierCode,readByGlobalIdentifierCodes,readByGlobalIdentifierSearchCriteria,countByGlobalIdentifierSearchCriteria
		,readByGlobalIdentifierSearchCriteriaCodeExcluded,countByGlobalIdentifierByCodeSearchCriteria
		,readByGlobalIdentifierOrderNumber,readDuplicates,countDuplicates,readByCriteria,countByCriteria,readByCriteriaCodeExcluded,countByCriteriaCodeExcluded
		,readByGlobalIdentifierSupportingDocumentCode,countByGlobalIdentifierSupportingDocumentCode;
	/*
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		clazz = (Class<IDENTIFIABLE>) parameterizedClass();
		//(Class<IDENTIFIABLE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		super.initialisation();
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	protected void beforeInitialisation() {
		clazz = (Class<IDENTIFIABLE>) parameterizedClass();
		//(Class<IDENTIFIABLE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		super.beforeInitialisation();
	}
	
	protected Class<?> parameterizedClass(){
	    return (Class<?>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		Configuration configuration = getConfiguration();
		if(Boolean.TRUE.equals(Configuration.isDisallowAll(clazz)))
			return;
		Boolean allowAll = Configuration.isAllowAll(clazz);
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadAll()))
			registerNamedQuery(readAll, _select()+(StringUtils.isEmpty(readAllOrderByString())?"":" "+readAllOrderByString()));
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadAllInclude()))
			registerNamedQuery(readAllInclude, _select().whereIdentifierIn());
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadAllExclude()))
			registerNamedQuery(readAllExclude, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.identifier NOT IN :identifiers");
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadByGlobalIdentifier()))
			registerNamedQuery(readByGlobalIdentifier, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.globalIdentifier.identifier = :identifier");
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadByGlobalIdentifiers()))
			registerNamedQuery(readByGlobalIdentifiers, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.globalIdentifier.identifier IN :identifiers");
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadByGlobalIdentifierValue()))
			registerNamedQuery(readByGlobalIdentifierValue, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.globalIdentifier.identifier = :identifier");
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadByGlobalIdentifierCode()))
			registerNamedQuery(readByGlobalIdentifierCode, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.globalIdentifier.code = :code");
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadByGlobalIdentifierCodes()))
			registerNamedQuery(readByGlobalIdentifierCodes, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.globalIdentifier.code IN :code");
		
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadByGlobalIdentifierOrderNumber()))
			registerNamedQuery(readByGlobalIdentifierOrderNumber, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.globalIdentifier.orderNumber = :orderNumber");
		
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadByGlobalIdentifierSupportingDocumentCode()))
			registerNamedQuery(readByGlobalIdentifierSupportingDocumentCode, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.globalIdentifier.supportingDocument.code = :supportingDocumentCode");
		
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadByClasses()))
			registerNamedQuery(readByClasses, _select().whereClassIn().orderBy(AbstractIdentifiable.FIELD_IDENTIFIER,Boolean.TRUE));
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadByNotClasses()))
			registerNamedQuery(readByNotClasses, _select().whereClassNotIn());
		
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getExecuteDelete()))
			registerNamedQuery(executeDelete, "DELETE FROM "+clazz.getSimpleName()+" record WHERE record.identifier IN :identifiers");
		
		String readByGlobalIdentifierSearchCriteriaQuery = null;
		String readByGlobalIdentifierSearchCriteriaCodeExcludedQuery = null;
		String readByGlobalIdentifierSearchCriteriaCodeExcludedQueryWherePart = null;
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadByGlobalIdentifierSearchCriteria())) {
			String codeLike = QueryStringBuilder.getLikeString("record.globalIdentifier.code", ":code");
			String nameLike = QueryStringBuilder.getLikeString("record.globalIdentifier.name", ":name");
			
			registerNamedQuery(readByGlobalIdentifierSearchCriteria, readByGlobalIdentifierSearchCriteriaQuery = "SELECT record FROM "+clazz.getSimpleName()+" record WHERE "+codeLike+" OR "+nameLike);
			
			readByGlobalIdentifierSearchCriteriaCodeExcludedQueryWherePart = codeLike+" OR "+nameLike;
			registerNamedQuery(readByGlobalIdentifierSearchCriteriaCodeExcluded, readByGlobalIdentifierSearchCriteriaCodeExcludedQuery = "SELECT record FROM "+clazz.getSimpleName()+" record WHERE "
		    		+ " record.globalIdentifier.code NOT IN :excludedCodes "+ " AND ("+ 
		    		getReadByCriteriaQueryCodeExcludedWherePart(readByGlobalIdentifierSearchCriteriaCodeExcludedQueryWherePart) + ")");
		
		}
		
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadBySearchCriteria())) {
			registerNamedQuery(readByCriteria, getReadByCriteriaQuery(readByGlobalIdentifierSearchCriteriaQuery));
			registerNamedQuery(readByCriteriaCodeExcluded, getReadByCriteriaQueryCodeExcluded(readByGlobalIdentifierSearchCriteriaCodeExcludedQuery));
		}
		// record.globalIdentifier.code NOT IN :exceludedCodes OR
	}
	
	@Override
	public Collection<IDENTIFIABLE> readByGlobalIdentifierSupportingDocumentCode(String supportingDocumentIdentifier) {
		return null;
	}
	
	@Override
	public Long countByGlobalIdentifierSupportingDocumentCode(String supportingDocumentIdentifier) {
		return null;
	}
	
	public Configuration getConfiguration(){
		return Configuration.get(this.getClass());
	}
	
	protected String readAllOrderByString(){
		return null;
	}
	
	protected String getReadByCriteriaQuery(String query){
		return query;
	}
	
	protected String getReadByCriteriaQueryCodeExcluded(String query){
		return query;
	}
	protected String getReadByCriteriaQueryCodeExcludedWherePart(String where){
		return where;
	}
	
	/**/
	
	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		GlobalIdentifier.SearchCriteria globalIdentifier = null;
		if(searchCriteria instanceof GlobalIdentifier.SearchCriteria)
			globalIdentifier = (SearchCriteria) searchCriteria;
		else if(searchCriteria instanceof AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet)
			globalIdentifier = ((AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet) searchCriteria).getGlobalIdentifier();
		
		if(globalIdentifier!=null){
			//System.out.println("AbstractTypedDao.applySearchCriteriaParameters() : "+((GlobalIdentifier.SearchCriteria)searchCriteria).getCode().getLikeValue());
			queryWrapper.parameterLike(GlobalIdentifier.FIELD_CODE, globalIdentifier.getCode());
			//queryWrapper.parameterLike(GlobalIdentifier.FIELD_CODE, ((GlobalIdentifier.SearchCriteria)searchCriteria).getCode().getLikeValue());
			//queryWrapper.parameter(QueryStringBuilder.getLengthParameterName(GlobalIdentifier.FIELD_CODE), ((GlobalIdentifier.SearchCriteria)searchCriteria).getCode().getPreparedValue().length());
			if(!globalIdentifier.getCode().getExcluded().isEmpty())
				queryWrapper.parameter("excludedCodes", globalIdentifier.getCode().getExcluded());
			queryWrapper.parameterLike(GlobalIdentifier.FIELD_NAME, globalIdentifier.getName());
			//queryWrapper.parameterLike(GlobalIdentifier.FIELD_NAME, ((GlobalIdentifier.SearchCriteria)searchCriteria).getName().getLikeValue());
			//queryWrapper.parameter(QueryStringBuilder.getLengthParameterName(GlobalIdentifier.FIELD_NAME), ((GlobalIdentifier.SearchCriteria)searchCriteria).getName().getPreparedValue().length());
		}
		getDataReadConfig().set(searchCriteria.getReadConfig());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<IDENTIFIABLE> readByGlobalIdentifierSearchCriteria(SearchCriteria globalIdentifierSearchCriteria) {
		QueryWrapper<?> queryWrapper = namedQuery(globalIdentifierSearchCriteria.getCode().getExcluded().isEmpty() ? 
				readByGlobalIdentifierSearchCriteria : readByGlobalIdentifierSearchCriteriaCodeExcluded);
		applySearchCriteriaParameters(queryWrapper, globalIdentifierSearchCriteria);
		return (Collection<IDENTIFIABLE>) queryWrapper.resultMany();
	}

	@Override
	public Long countByGlobalIdentifierSearchCriteria(SearchCriteria globalIdentifierSearchCriteria) {
		QueryWrapper<?> queryWrapper = countNamedQuery(globalIdentifierSearchCriteria.getCode().getExcluded().isEmpty() ? 
				countByGlobalIdentifierSearchCriteria : countByGlobalIdentifierByCodeSearchCriteria);
		applySearchCriteriaParameters(queryWrapper, globalIdentifierSearchCriteria);
		return (Long) queryWrapper.resultOne();
	}
	
	protected <SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> String getSearchCriteriaNamedQuery(SEARCH_CRITERIA searchCriteria,Boolean read){
		if(searchCriteria instanceof AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet){
			AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet identifiableSearchCriteria = (AbstractIdentifiableSearchCriteriaSet) searchCriteria;
			return identifiableSearchCriteria.getCode().getExcluded().isEmpty() ? (read?readByCriteria:countByCriteria) : (read?readByCriteriaCodeExcluded:countByCriteriaCodeExcluded);
		}
		return null;
	}
	
	@Override
	public IDENTIFIABLE readByGlobalIdentifier(GlobalIdentifier globalIdentifier) {
		return namedQuery(readByGlobalIdentifier).parameter(AbstractIdentifiable.FIELD_IDENTIFIER, globalIdentifier.getIdentifier())
				.ignoreThrowable(NoResultException.class).resultOne();
	}
	
	@Override
	public IDENTIFIABLE readByGlobalIdentifierCode(String globalIdentifierCode) {
		return namedQuery(readByGlobalIdentifierCode).parameter(GlobalIdentifier.FIELD_CODE, globalIdentifierCode)
				.ignoreThrowable(NoResultException.class).resultOne();
	}
	
	@Override
	public Collection<IDENTIFIABLE> readByGlobalIdentifierCodes(Collection<String> globalIdentifierCodes) {
		return namedQuery(readByGlobalIdentifierCodes).parameter(GlobalIdentifier.FIELD_CODE, globalIdentifierCodes).resultMany();
	}
	
	@Override
	public Collection<IDENTIFIABLE> readByGlobalIdentifierOrderNumber(Long orderNumber) {
		return namedQuery(readByGlobalIdentifierOrderNumber).parameter(GlobalIdentifier.FIELD_ORDER_NUMBER, orderNumber)
				.resultMany();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> Collection<IDENTIFIABLE> readBySearchCriteria(SEARCH_CRITERIA searchCriteria) {
		QueryWrapper<?> queryWrapper = namedQuery(getSearchCriteriaNamedQuery(searchCriteria, Boolean.TRUE));
		applySearchCriteriaParameters(queryWrapper, searchCriteria);
		return (Collection<IDENTIFIABLE>) queryWrapper.resultMany();
	}

	@Override
	public <SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> Long countBySearchCriteria(SEARCH_CRITERIA searchCriteria) {
		QueryWrapper<?> queryWrapper = countNamedQuery(getSearchCriteriaNamedQuery(searchCriteria, Boolean.FALSE));
		applySearchCriteriaParameters(queryWrapper, searchCriteria);
		return (Long) queryWrapper.resultOne();
	}

	public IDENTIFIABLE read(String globalIdentifierCode) {
		return readByGlobalIdentifierCode(globalIdentifierCode);
	}
	
	public Collection<IDENTIFIABLE> read(Collection<String> globalIdentifierCodes) {
		return readByGlobalIdentifierCodes(globalIdentifierCodes);
	}
	
	@Override
	public Collection<IDENTIFIABLE> readAll() {
		return namedQuery(readAll).resultMany();
	}
	
	@Override
	public Long countAll() {
		return namedQuery(countAll,Long.class).resultOne();
	}
	
	@Override
	public Collection<IDENTIFIABLE> readDuplicates() {
		return namedQuery(readDuplicates).resultMany();
	}
	
	@Override
	public Long countDuplicates() {
		return namedQuery(countDuplicates,Long.class).resultOne();
	}
	
	@Override
	public Collection<IDENTIFIABLE> readAllExclude(Collection<IDENTIFIABLE> identifiables) {
		return namedQuery(readAllExclude).parameterIdentifiers(identifiables).resultMany();
	}
	
	@Override
	public Long countAllExclude(Collection<IDENTIFIABLE> identifiables) {
		return namedQuery(countAllExclude,Long.class).parameterIdentifiers(identifiables).resultOne();
	}
	
	@Override
	public Collection<IDENTIFIABLE> readByGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers) {
		return namedQuery(readByGlobalIdentifiers).parameterGlobalIdentifiers(globalIdentifiers).resultMany();
	}
	
	@Override
	public Long countByGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers) {
		return namedQuery(countByGlobalIdentifiers,Long.class).parameterGlobalIdentifiers(globalIdentifiers).resultOne();
	}
	
	@Override
	public IDENTIFIABLE readByGlobalIdentifierValue(String globalIdentifier) {
		return namedQuery(readByGlobalIdentifierValue).parameter(GlobalIdentifier.FIELD_IDENTIFIER,globalIdentifier).ignoreThrowable(NoResultException.class).resultOne();
	}
	
	@Override
	public Collection<IDENTIFIABLE> readByClasses(Collection<Class<?>> classes) {
		return namedQuery(readByClasses).parameterClasses(classes).resultMany();
	}
	@Override
	public Long countByClasses(Collection<Class<?>> classes) {
		return countNamedQuery(countByClasses).parameterClasses(classes).resultOne();
	}
	
	@Override
	public Collection<IDENTIFIABLE> readByNotClasses(Collection<Class<?>> classes) {
		return namedQuery(readByNotClasses).parameterClasses(classes).resultMany();
	}
	@Override
	public Long countByNotClasses(Collection<Class<?>> classes) {
		return countNamedQuery(countByNotClasses).parameterClasses(classes).resultOne();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IDENTIFIABLE> Collection<T> readByClass(Class<T> aClass) {
		Collection<Class<?>> classes = new ArrayList<>();
		classes.add(aClass);
		return (Collection<T>) readByClasses(classes);
	}
	@Override
	public Long countByClass(Class<?> aClass) {
		Collection<Class<?>> classes = new ArrayList<>();
		classes.add(aClass);
		return countByClasses(classes);
	} 
	@Override
	public Collection<IDENTIFIABLE> readByNotClass(Class<?> aClass) {
		Collection<Class<?>> classes = new ArrayList<>();
		classes.add(aClass);
		return readByNotClasses(classes);
	}
	@Override
	public Long countByNotClass(Class<?> aClass) {
		Collection<Class<?>> classes = new ArrayList<>();
		classes.add(aClass);
		return countByNotClasses(classes);
	}
	
	@Override
	public void executeDelete(Collection<IDENTIFIABLE> identifiables) {
		namedQuery(executeDelete).parameter(QueryStringBuilder.VAR_IDENTIFIERS, ids(identifiables));	
	}
	
	@Override
	public Collection<IDENTIFIABLE> readDuplicates(IDENTIFIABLE identifiable) {
		return null;
	}
	
	/**/
	
	protected String criteriaSearchQueryId(AbstractFieldValueSearchCriteria<?> searchCriteria,String ascendingOrderQueryId,String descendingOrderQueryId){
		if(searchCriteria.getAscendingOrdered()!=null)
			return Boolean.TRUE.equals(searchCriteria.getAscendingOrdered())?ascendingOrderQueryId:descendingOrderQueryId;
		else
			return ascendingOrderQueryId;
	}
	
	/**/
	
	@Getter
	public static class Configuration implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private static final Map<Class<?>, Configuration> MAP = new HashMap<>();
		
		private static final Set<Class<?>> DISALLOWED_ALL_CLASSES = new HashSet<>();
		private static final Set<Package> DISALLOWED_ALL_PACKAGES = new HashSet<>();
		private static final Set<Class<?>> ALLOWED_ALL_CLASSES = new HashSet<>();
		
		private Boolean readAll = Boolean.TRUE;
		private Boolean readAllInclude = Boolean.TRUE;
		private Boolean readAllExclude = Boolean.TRUE;
		private Boolean readByGlobalIdentifier = Boolean.TRUE;
		private Boolean readByGlobalIdentifiers = Boolean.TRUE;
		private Boolean readByGlobalIdentifierValue = Boolean.TRUE;
		private Boolean readByGlobalIdentifierCode = Boolean.TRUE;
		private Boolean readByGlobalIdentifierCodes = Boolean.TRUE;
		private Boolean readByGlobalIdentifierOrderNumber = Boolean.TRUE;
		private Boolean readByGlobalIdentifierSupportingDocumentCode = Boolean.FALSE;
		
		private Boolean readByClasses = Boolean.FALSE;
		private Boolean readByNotClasses = Boolean.FALSE;
		
		private Boolean executeDelete = Boolean.TRUE;
		private Boolean readByGlobalIdentifierSearchCriteria = Boolean.TRUE;
		private Boolean readBySearchCriteria = Boolean.TRUE;
		
		/**/
		
		/**/
		
		public Configuration setReadAll(Boolean readAll) {
			this.readAll = readAll;
			return this;
		}
		
		public Configuration setReadAllExclude(Boolean readAllExclude) {
			this.readAllExclude = readAllExclude;
			return this;
		}
		
		public Configuration setReadAllInclude(Boolean readAllInclude) {
			this.readAllInclude = readAllInclude;
			return this;
		}
		
		public Configuration setReadByGlobalIdentifier(Boolean readByGlobalIdentifier) {
			this.readByGlobalIdentifier = readByGlobalIdentifier;
			return this;
		}
		
		public Configuration setReadByGlobalIdentifiers(Boolean readByGlobalIdentifiers) {
			this.readByGlobalIdentifiers = readByGlobalIdentifiers;
			return this;
		}
		
		public Configuration setReadByGlobalIdentifierValue(Boolean readByGlobalIdentifierValue) {
			this.readByGlobalIdentifierValue = readByGlobalIdentifierValue;
			return this;
		}
		
		public Configuration setReadByGlobalIdentifierCode(Boolean readByGlobalIdentifierCode) {
			this.readByGlobalIdentifierCode = readByGlobalIdentifierCode;
			return this;
		}
		
		public Configuration setReadByGlobalIdentifierCodes(Boolean readByGlobalIdentifierCodes) {
			this.readByGlobalIdentifierCodes = readByGlobalIdentifierCodes;
			return this;
		}
		
		public Configuration setReadByGlobalIdentifierOrderNumber(Boolean readByGlobalIdentifierOrderNumber) {
			this.readByGlobalIdentifierOrderNumber = readByGlobalIdentifierOrderNumber;
			return this;
		}
		
		public Configuration setReadByClasses(Boolean readByClasses) {
			this.readByClasses = readByClasses;
			return this;
		}
		
		public Configuration setReadByNotClasses(Boolean readByNotClasses) {
			this.readByNotClasses = readByNotClasses;
			return this;
		}
		
		public Configuration setExecuteDelete(Boolean executeDelete) {
			this.executeDelete = executeDelete;
			return this;
		}
		
		public Configuration setReadByGlobalIdentifierSearchCriteria(Boolean readByGlobalIdentifierSearchCriteria) {
			this.readByGlobalIdentifierSearchCriteria = readByGlobalIdentifierSearchCriteria;
			return this;
		}
		
		/**/
		
		public static Configuration get(Class<?> aClass){
			Configuration configuration = MAP.get(aClass);
			if(configuration==null){
				MAP.put(aClass, configuration = new Configuration());
			}
			return configuration;
		}
		
		public static void disallowAll(Class<?>[] classes){
			for(Class<?> aClass : classes)
				Configuration.DISALLOWED_ALL_CLASSES.add(aClass);
		}
		public static void disallowAll(Package[] packages){
			for(Package aPackage : packages)
				Configuration.DISALLOWED_ALL_PACKAGES.add(aPackage);
		}
		
		public static Boolean isAllowAll(Class<?> aClass){
			return ALLOWED_ALL_CLASSES.contains(aClass) ? Boolean.TRUE : null;
		}
		
		public static Boolean isDisallowAll(Class<?> aClass){
			return DISALLOWED_ALL_PACKAGES.contains(aClass.getPackage()) || DISALLOWED_ALL_CLASSES.contains(aClass) ? Boolean.TRUE : null;
		}
	}
	
}
