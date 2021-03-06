package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier.SearchCriteria;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteria;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.CriteriaHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.FilterHelper.Filter;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class AbstractTypedDao<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractPersistenceService<IDENTIFIABLE> implements TypedDao<IDENTIFIABLE>,Serializable {

	private static final long serialVersionUID = -2964204372097468908L;

	//TODO try use collection query for single query to see performance difference
	
	protected String readAll,countAll,readDefaulted,readByClasses,countByClasses,readByNotClasses,countByNotClasses,readAllExclude,countAllExclude
		,readAllInclude,countAllInclude,readByGlobalIdentifiers,readByGlobalIdentifierValue,countByGlobalIdentifiers,executeDelete,readByGlobalIdentifier
		,readByGlobalIdentifierCode,readByGlobalIdentifierCodes,readByClosed,countByClosed,readByGlobalIdentifierSearchCriteria,countByGlobalIdentifierSearchCriteria
		,readByGlobalIdentifierSearchCriteriaCodeExcluded,countByGlobalIdentifierByCodeSearchCriteria
		,readByGlobalIdentifierOrderNumber,readDuplicates,countDuplicates,readByCriteria,countByCriteria,readByCriteriaCodeExcluded,countByCriteriaCodeExcluded
		,readByGlobalIdentifierSupportingDocumentCode,countByGlobalIdentifierSupportingDocumentCode,readByIdentifiers,readFirstWhereExistencePeriodFromDateIsLessThan
		,readWhereExistencePeriodFromDateIsLessThan,countWhereExistencePeriodFromDateIsLessThan
		,readWhereExistencePeriodFromDateIsGreaterThan,countWhereExistencePeriodFromDateIsGreaterThan,readWhereExistencePeriodCross,countWhereExistencePeriodCross
		,readWhereOrderNumberIsGreaterThan,countWhereOrderNumberIsGreaterThan
		,readByFilter,countByFilter;
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
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadByIdentifiers()))
			registerNamedQuery(readByIdentifiers, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.identifier IN :identifiers");
		
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadDefaulted()))
			registerNamedQuery(readDefaulted, _select().where(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_DEFAULTED)
					,GlobalIdentifier.FIELD_DEFAULTED));
		
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadByClosed()))
			registerNamedQuery(readByClosed, _select().where(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CLOSED)
					,GlobalIdentifier.FIELD_CLOSED));
		
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
		
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadFirstWhereExistencePeriodFromDateIsLessThan())){
			
		}
		
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadWhereExistencePeriodFromDateIsLessThan())){
			registerNamedQuery(readWhereExistencePeriodFromDateIsLessThan, getJpqlString(readWhereExistencePeriodFromDateIsLessThan));
		}
		
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadWhereExistencePeriodFromDateIsGreaterThan())){
			registerNamedQuery(readWhereExistencePeriodFromDateIsGreaterThan, getJpqlString(readWhereExistencePeriodFromDateIsGreaterThan));
		}
		
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadWhereOrderNumberIsGreaterThan())){
			registerNamedQuery(readWhereOrderNumberIsGreaterThan, getJpqlString(readWhereOrderNumberIsGreaterThan));
		}
		
		String readByGlobalIdentifierSearchCriteriaQuery = null;
		String readByGlobalIdentifierSearchCriteriaCodeExcludedQuery = null;
		String readByGlobalIdentifierSearchCriteriaCodeExcludedQueryWherePart = null;
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadByGlobalIdentifierSearchCriteria())) {
			String codeLike = QueryStringBuilder.getLikeString("record.globalIdentifier.code");
			String nameLike = QueryStringBuilder.getLikeString("record.globalIdentifier.name");
			//String orderNumberIn = QueryStringBuilder.getInString("record.globalIdentifier.orderNumber");
			
			String predicates = StringUtils.join(new String[]{codeLike,nameLike/*,orderNumberIn*/}," OR ");
			
			registerNamedQuery(readByGlobalIdentifierSearchCriteria, readByGlobalIdentifierSearchCriteriaQuery = "SELECT record FROM "+clazz.getSimpleName()
				+" record WHERE "+predicates);
			
			registerNamedQuery(readByFilter, getJpqlString(readByFilter) /*getReadByFilterQueryString()*/);
			
			readByGlobalIdentifierSearchCriteriaCodeExcludedQueryWherePart = predicates;
			registerNamedQuery(readByGlobalIdentifierSearchCriteriaCodeExcluded, readByGlobalIdentifierSearchCriteriaCodeExcludedQuery = "SELECT record FROM "+clazz.getSimpleName()+" record WHERE "
		    		+ " record.globalIdentifier.code NOT IN :excludedCodes "+ " AND ("+ 
		    		getReadByCriteriaQueryCodeExcludedWherePart(readByGlobalIdentifierSearchCriteriaCodeExcludedQueryWherePart) + ")");
		
		}
		
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadBySearchCriteria())) {
			registerNamedQuery(readByCriteria, getReadByCriteriaQuery(readByGlobalIdentifierSearchCriteriaQuery));
			registerNamedQuery(readByCriteriaCodeExcluded, getReadByCriteriaQueryCodeExcluded(readByGlobalIdentifierSearchCriteriaCodeExcludedQuery));
		}
		// record.globalIdentifier.code NOT IN :exceludedCodes OR
		
		if(Boolean.TRUE.equals(allowAll) || Boolean.TRUE.equals(configuration.getReadWhereExistencePeriodCross())) {
			registerNamedQuery(readWhereExistencePeriodCross, StructuredQueryLanguageHelper.getInstance().getBuilder(clazz).setFieldName("globalIdentifier.existencePeriod")
					.where().lp().bw("fromDate").or().bw("toDate").rp().or().lp().lte("fromDate","fromFromDate").and().gte("toDate","toToDate").rp().getParent().execute());	
		}
		
	}
	
	@Override
	protected void listenInstanciateJpqlBuilder(String name,JavaPersistenceQueryLanguage builder) {
		super.listenInstanciateJpqlBuilder(name,builder);
		if(readByFilter.equals(name)){
			builder.setFieldName(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER).where().notIn(GlobalIdentifier.FIELD_CODE).and().lp()
				.lk(GlobalIdentifier.FIELD_CODE)
				.or().lk(GlobalIdentifier.FIELD_NAME)
				.getParent();
			@SuppressWarnings("unchecked")
			Class<FilterHelper.Filter<IDENTIFIABLE>> filterClass = (Class<Filter<IDENTIFIABLE>>) FilterHelper.Filter.ClassLocator.getInstance().locate(clazz);
			Collection<String> fieldNames = FieldHelper.getInstance().getNamesByTypes(filterClass, CriteriaHelper.Criteria.String.class);
			builder.setFieldName(null);
			if(CollectionHelper.getInstance().isNotEmpty(fieldNames))
				for(String fieldName : fieldNames)
					builder.getWhere().or().lk(fieldName);
			processReadByFilterQueryBuilderWhereConditions(builder);
			builder.getWhere().rp();
			builder.setFieldName(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER).where().and().addEqual(GlobalIdentifier.FIELD_CLOSED,Boolean.FALSE,3);
			listenInstanciateJpqlBuilderOrderBy(name, builder);
		}else if(readWhereExistencePeriodFromDateIsLessThan.equals(name)){
			builder.setFieldName(FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD)).where()
				.lt(Period.FIELD_FROM_DATE, Period.FIELD_FROM_DATE).and().getParent().setFieldName(null).where()
				.neq(AbstractIdentifiable.FIELD_IDENTIFIER, AbstractIdentifiable.FIELD_IDENTIFIER);
			builder.orderBy().desc("globalIdentifier.existencePeriod.fromDate");
		}else if(readWhereExistencePeriodFromDateIsGreaterThan.equals(name)){
			builder.setFieldName(FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD)).where()
			.gt(Period.FIELD_FROM_DATE, Period.FIELD_FROM_DATE).and().getParent().setFieldName(null).where()
			.neq(AbstractIdentifiable.FIELD_IDENTIFIER, AbstractIdentifiable.FIELD_IDENTIFIER);
			builder.orderBy().desc("globalIdentifier.existencePeriod.fromDate");
		}else if(readWhereOrderNumberIsGreaterThan.equals(name)){
			builder.setFieldName(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER).where()
			.gt(GlobalIdentifier.FIELD_ORDER_NUMBER, GlobalIdentifier.FIELD_ORDER_NUMBER).and().getParent().setFieldName(null).where()
			.neq(AbstractIdentifiable.FIELD_IDENTIFIER, AbstractIdentifiable.FIELD_IDENTIFIER);
			builder.orderBy().desc("globalIdentifier.existencePeriod.fromDate");
		}
	}
	
	protected void listenInstanciateJpqlBuilderOrderBy(String name,JavaPersistenceQueryLanguage builder){
		if(readByFilter.equals(name)){
			builder.setFieldName(null).orderBy().asc("globalIdentifier.creationDate");	
		}else if(ArrayUtils.contains(new String[]{readWhereExistencePeriodFromDateIsLessThan,readWhereExistencePeriodFromDateIsGreaterThan,readWhereOrderNumberIsGreaterThan}, name)){
			builder.setFieldName(null).orderBy().desc("globalIdentifier.existencePeriod.fromDate");
		}
	}
	
	@Override
	protected <T> void processQueryWrapper(Class<T> aClass, QueryWrapper<T> queryWrapper, String queryName,Object[] arguments) {
		super.processQueryWrapper(aClass, queryWrapper, queryName, arguments);
		if( ArrayUtils.contains(new String[]{readWhereExistencePeriodFromDateIsLessThan,countWhereExistencePeriodFromDateIsLessThan
				,readWhereExistencePeriodFromDateIsGreaterThan,countWhereExistencePeriodFromDateIsGreaterThan}, queryName) ){
			@SuppressWarnings("unchecked")
			IDENTIFIABLE identifiable = (IDENTIFIABLE) arguments[0];
			queryWrapper.parameter(Period.FIELD_FROM_DATE, identifiable.getBirthDate())
			.parameter(AbstractIdentifiable.FIELD_IDENTIFIER, InstanceHelper.getInstance().getIfNotNullElseDefault(identifiable.getIdentifier(),-1l));
		}else if(ArrayUtils.contains(new String[]{readWhereOrderNumberIsGreaterThan,countWhereOrderNumberIsGreaterThan}, queryName)){
			@SuppressWarnings("unchecked")
			IDENTIFIABLE identifiable = (IDENTIFIABLE) arguments[0];
			queryWrapper.parameter(GlobalIdentifier.FIELD_ORDER_NUMBER, identifiable.getOrderNumber())
			.parameter(AbstractIdentifiable.FIELD_IDENTIFIER, InstanceHelper.getInstance().getIfNotNullElseDefault(identifiable.getIdentifier(),-1l));
		}else if(ArrayUtils.contains(new String[]{readByFilter,countByFilter}, queryName)){
			FilterHelper.Filter<?> filter = (FilterHelper.Filter<?>) arguments[0];
			DataReadConfiguration dataReadConfiguration = (DataReadConfiguration) arguments[1];
			GlobalIdentifier.Filter globalIdentifier = ((AbstractIdentifiable.Filter<?>) filter).getGlobalIdentifier();
			queryWrapper.parameterLike(globalIdentifier);
			if(globalIdentifier!=null){
				queryWrapper.parameterInStrings(globalIdentifier.getCode().getExcluded(),AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE);
				for(Integer index = 1 ; index <= 3 ; index++)
					queryWrapper.parameter(StructuredQueryLanguageHelper.Where.Equal.Adapter.Default.getParameterNameEqual(GlobalIdentifier.FIELD_CLOSED)+index
							, CollectionHelper.getInstance().getElementAt(globalIdentifier.getClosed().getValues(), index-1));
			}
			queryWrapper.parameterLike(filter);
			getDataReadConfig().set(dataReadConfiguration);
		}
	}
	
	/*
	@Override
	protected String getJpqlString(String name) {
		if(readWhereExistencePeriodFromDateIsLessThan.equals(name)){
			String dateFieldName = commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE);
			QueryStringBuilder queryStringBuilder = _select().where(dateFieldName,Period.FIELD_FROM_DATE,ArithmeticOperator.LT)
					.and(AbstractIdentifiable.FIELD_IDENTIFIER, ArithmeticOperator.NEQ);
			queryStringBuilder.orderBy(dateFieldName, Boolean.FALSE);
			return queryStringBuilder.getValue();
		}else if(readWhereExistencePeriodFromDateIsGreaterThan.equals(name)){
			String dateFieldName = commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE);
			QueryStringBuilder queryStringBuilder = _select().where(dateFieldName,Period.FIELD_FROM_DATE,ArithmeticOperator.GT)
					.and(AbstractIdentifiable.FIELD_IDENTIFIER, ArithmeticOperator.NEQ);
			queryStringBuilder.orderBy(dateFieldName, Boolean.FALSE);
		}
		return super.getJpqlString(name);
	}
	*/
	/*
	protected String getReadByFilterQueryString(){
		return getReadByFilterQueryBuilder().execute();
	}
	/*
	protected StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage getReadByFilterQueryBuilder(){
		StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage jpql = (JavaPersistenceQueryLanguage) new StructuredQueryLanguageHelper.Builder
				.Adapter.Default.JavaPersistenceQueryLanguage(clazz.getSimpleName()).setFieldName(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER).where()
				.notIn(GlobalIdentifier.FIELD_CODE).and().lp().lk(GlobalIdentifier.FIELD_CODE).or().lk(GlobalIdentifier.FIELD_NAME).getParent();
		@SuppressWarnings("unchecked")
		Class<FilterHelper.Filter<IDENTIFIABLE>> filterClass = (Class<Filter<IDENTIFIABLE>>) FilterHelper.Filter.ClassLocator.getInstance().locate(clazz);
		Collection<String> fieldNames = FieldHelper.getInstance().getNamesByTypes(filterClass, CriteriaHelper.Criteria.String.class);
		jpql.setFieldName(null);
		if(CollectionHelper.getInstance().isNotEmpty(fieldNames))
			for(String fieldName : fieldNames)
				jpql.getWhere().or().lk(fieldName);
		processReadByFilterQueryBuilderWhereConditions(jpql);
		jpql.getWhere().rp();
		return jpql;
	}
	*/
	protected void processReadByFilterQueryBuilderWhereConditions(StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage jpql){}
	
	@Override
	public Collection<IDENTIFIABLE> readWhereExistencePeriodCross(Date from, Date to) {
		return namedQuery(readWhereExistencePeriodCross).parameter("fromFromDate", from).parameter("toFromDate", to)
				.parameter("fromToDate", from).parameter("toToDate", to).resultMany();
	}
	
	@Override
	public Long countWhereExistencePeriodCross(Date from, Date to) {
		return countNamedQuery(countWhereExistencePeriodCross).parameter("fromFromDate", from).parameter("toToDate", to)
				.parameter("fromToDate", from).parameter("toToDate", to).resultOne();
	}
	
	@Override
	public Collection<IDENTIFIABLE> readByGlobalIdentifierSupportingDocumentCode(String supportingDocumentIdentifier) {
		return null;
	}
	
	@Override
	public Long countByGlobalIdentifierSupportingDocumentCode(String supportingDocumentIdentifier) {
		return null;
	}
	
	@Override
	public Collection<IDENTIFIABLE> readByIdentifiers(Collection<Long> identifiers) {
		return namedQuery(readByIdentifiers).parameter(QueryStringBuilder.VAR_IDENTIFIERS, identifiers).resultMany();
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
			queryWrapper.parameterLike(GlobalIdentifier.FIELD_CODE, globalIdentifier.getCode());
			if(!globalIdentifier.getCode().getExcluded().isEmpty())
				queryWrapper.parameter("excludedCodes", globalIdentifier.getCode().getExcluded());
			queryWrapper.parameterLike(GlobalIdentifier.FIELD_NAME, globalIdentifier.getName());
			//queryWrapper.parameter(GlobalIdentifier.FIELD_ORDER_NUMBER, globalIdentifier.getOrderNumber());
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
	
	@Override
	public Collection<IDENTIFIABLE> readByFilter(FilterHelper.Filter<IDENTIFIABLE> filter,DataReadConfiguration dataReadConfiguration) {
		QueryWrapper<IDENTIFIABLE> queryWrapper = namedQuery(//globalIdentifierSearchCriteria.getCode().getExcluded().isEmpty() ? 
				/*readByGlobalIdentifierSearchCriteria : readByGlobalIdentifierSearchCriteriaCodeExcluded*/readByFilter);
		//listenBeforeFilter(queryWrapper, filter,dataReadConfiguration);
		processQueryWrapper(clazz, queryWrapper, readByFilter, new Object[]{filter,dataReadConfiguration});
		return (Collection<IDENTIFIABLE>) queryWrapper.resultMany();
	}

	@Override
	public Long countByFilter(FilterHelper.Filter<IDENTIFIABLE> filter,DataReadConfiguration dataReadConfiguration) {
		QueryWrapper<Long> queryWrapper = countNamedQuery(//globalIdentifierSearchCriteria.getCode().getExcluded().isEmpty() ? 
				/*countByGlobalIdentifierSearchCriteria : countByGlobalIdentifierByCodeSearchCriteria*/countByFilter);
		//listenBeforeFilter(queryWrapper, filter,dataReadConfiguration);
		processQueryWrapper(Long.class, queryWrapper, countByFilter, new Object[]{filter,dataReadConfiguration});
		return (Long) queryWrapper.resultOne();
	}
	
	/*@Override @Deprecated
	protected void listenBeforeFilter(QueryWrapper<?> queryWrapper,FilterHelper.Filter<IDENTIFIABLE> filter,DataReadConfiguration dataReadConfiguration) {
		super.listenBeforeFilter(queryWrapper,filter, dataReadConfiguration);
		GlobalIdentifier.Filter globalIdentifier = ((AbstractIdentifiable.Filter<IDENTIFIABLE>) filter).getGlobalIdentifier();
		queryWrapper.parameterLike(globalIdentifier);
		if(globalIdentifier!=null){
			if(globalIdentifier.getCode().getExcluded().isEmpty())
				globalIdentifier.getCode().getExcluded().add("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");//TODO think better
			if(!globalIdentifier.getCode().getExcluded().isEmpty())
				queryWrapper.parameter(StructuredQueryLanguageHelper.Where.In.Adapter.getParameterNameIn(GlobalIdentifier.FIELD_CODE)
						, globalIdentifier.getCode().getExcluded());
			
		}
		queryWrapper.parameterLike(filter);
		getDataReadConfig().set(dataReadConfiguration);
	}*/
	
	protected <SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> String getSearchCriteriaNamedQuery(SEARCH_CRITERIA searchCriteria,Boolean read){
		if(searchCriteria instanceof AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet){
			AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet identifiableSearchCriteria = (AbstractIdentifiableSearchCriteriaSet) searchCriteria;
			return identifiableSearchCriteria.getCode().getExcluded().isEmpty() ? (read?readByCriteria:countByCriteria) : (read?readByCriteriaCodeExcluded:countByCriteriaCodeExcluded);
		}
		return null;
	}
	
	@Override
	public IDENTIFIABLE readDefaulted() {
		return namedQuery(readDefaulted).parameter(GlobalIdentifier.FIELD_DEFAULTED, Boolean.TRUE).ignoreThrowable(NoResultException.class).resultOne();
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
	public Collection<IDENTIFIABLE> readByClosed(Boolean closed) {
		return namedQuery(readByClosed).parameter(GlobalIdentifier.FIELD_CLOSED, closed).resultMany();
	}
	
	@Override
	public Long countByClosed(Boolean closed) {
		return countNamedQuery(countByClosed).parameter(GlobalIdentifier.FIELD_CLOSED, closed).resultOne();
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
	
	
	
	protected QueryWrapper<IDENTIFIABLE> getReadWhereExistencePeriodFromDateIsLessThanQueryWrapper(IDENTIFIABLE identifiable) {
		QueryWrapper<IDENTIFIABLE> queryWrapper = namedQuery(readWhereExistencePeriodFromDateIsLessThan);
		processQueryWrapper(clazz, queryWrapper, readWhereExistencePeriodFromDateIsLessThan,new Object[]{identifiable});
		return queryWrapper;
	}
	
	protected QueryWrapper<Long> getCountWhereExistencePeriodFromDateIsLessThanQueryWrapper(IDENTIFIABLE identifiable) {
		QueryWrapper<Long> queryWrapper = countNamedQuery(countWhereExistencePeriodFromDateIsLessThan);
		processQueryWrapper(Long.class, queryWrapper, countWhereExistencePeriodFromDateIsLessThan,new Object[]{identifiable});
		return queryWrapper;
	}
	
	@Override
	public IDENTIFIABLE readFirstWhereExistencePeriodFromDateIsLessThan(IDENTIFIABLE identifiable) {
		getDataReadConfig().setMaximumResultCount(1l);
		QueryWrapper<IDENTIFIABLE> queryWrapper  = getReadWhereExistencePeriodFromDateIsLessThanQueryWrapper(identifiable);
		Collection<IDENTIFIABLE> collection = queryWrapper.resultMany();
		return collection.isEmpty() ? null : collection.iterator().next();
	}

	@Override
	public Collection<IDENTIFIABLE> readWhereExistencePeriodFromDateIsLessThan(IDENTIFIABLE identifiable) {
		return getReadWhereExistencePeriodFromDateIsLessThanQueryWrapper(identifiable).resultMany();
	}
	
	@Override
	public Long countWhereExistencePeriodFromDateIsLessThan(IDENTIFIABLE identifiable) {
		return getCountWhereExistencePeriodFromDateIsLessThanQueryWrapper(identifiable).resultOne();
	}
	
	protected QueryWrapper<IDENTIFIABLE> getReadWhereExistencePeriodFromDateIsGreaterThanQueryWrapper(IDENTIFIABLE identifiable) {
		QueryWrapper<IDENTIFIABLE> queryWrapper = namedQuery(readWhereExistencePeriodFromDateIsGreaterThan);
		processQueryWrapper(clazz, queryWrapper, readWhereExistencePeriodFromDateIsGreaterThan,new Object[]{identifiable});
		return queryWrapper;
	}
	
	protected QueryWrapper<Long> getCountWhereExistencePeriodFromDateIsGreaterThanQueryWrapper(IDENTIFIABLE identifiable) {
		QueryWrapper<Long> queryWrapper = countNamedQuery(countWhereExistencePeriodFromDateIsGreaterThan);
		processQueryWrapper(Long.class, queryWrapper, countWhereExistencePeriodFromDateIsGreaterThan,new Object[]{identifiable});
		return queryWrapper;
	}
	
	@Override
	public Collection<IDENTIFIABLE> readWhereExistencePeriodFromDateIsGreaterThan(IDENTIFIABLE identifiable) {
		return getReadWhereExistencePeriodFromDateIsGreaterThanQueryWrapper(identifiable).resultMany();
	}
	
	@Override
	public Long countWhereExistencePeriodFromDateIsGreaterThan(IDENTIFIABLE identifiable) {
		return getCountWhereExistencePeriodFromDateIsGreaterThanQueryWrapper(identifiable).resultOne();
	}
	
	protected QueryWrapper<IDENTIFIABLE> getReadWhereOrderNumberIsGreaterThanQueryWrapper(IDENTIFIABLE identifiable) {
		QueryWrapper<IDENTIFIABLE> queryWrapper = namedQuery(readWhereOrderNumberIsGreaterThan);
		processQueryWrapper(clazz, queryWrapper, readWhereOrderNumberIsGreaterThan,new Object[]{identifiable});
		return queryWrapper;
	}
	
	protected QueryWrapper<Long> getCountWhereOrderNumberIsGreaterThanQueryWrapper(IDENTIFIABLE identifiable) {
		QueryWrapper<Long> queryWrapper = countNamedQuery(countWhereOrderNumberIsGreaterThan);
		processQueryWrapper(Long.class, queryWrapper, countWhereOrderNumberIsGreaterThan,new Object[]{identifiable});
		return queryWrapper;
	}
	
	@Override
	public Collection<IDENTIFIABLE> readWhereOrderNumberIsGreaterThan(IDENTIFIABLE identifiable) {
		return getReadWhereOrderNumberIsGreaterThanQueryWrapper(identifiable).resultMany();
	}
	
	@Override
	public Long countWhereOrderNumberIsGreaterThan(IDENTIFIABLE identifiable) {
		return getCountWhereOrderNumberIsGreaterThanQueryWrapper(identifiable).resultOne();
	}
	
	/**/

	protected <T extends AbstractIdentifiable> T read(Class<T> aClass,String code){
		return inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(code);
	}
	
	protected <T extends AbstractIdentifiable> Collection<T> readMany(Class<T> aClass,Collection<String> codes){
		return inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(codes);
	}
	
	protected <T extends AbstractIdentifiable> Collection<T> readMany(Class<T> aClass,String...codes){
		return readMany(aClass,Arrays.asList(codes));
	}
	/**/
	
	protected String criteriaSearchQueryId(AbstractFieldValueSearchCriteria<?> searchCriteria,String ascendingOrderQueryId,String descendingOrderQueryId){
		if(searchCriteria.getAscendingOrdered()!=null)
			return Boolean.TRUE.equals(searchCriteria.getAscendingOrdered())?ascendingOrderQueryId:descendingOrderQueryId;
		else
			return ascendingOrderQueryId;
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Configuration implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private static final Map<Class<?>, Configuration> MAP = new HashMap<>();
		
		private static final Set<Class<?>> DISALLOWED_ALL_CLASSES = new HashSet<>();
		private static final Set<Package> DISALLOWED_ALL_PACKAGES = new HashSet<>();
		private static final Set<Class<?>> ALLOWED_ALL_CLASSES = new HashSet<>();
		
		private Boolean readAll = Boolean.TRUE;
		private Boolean readDefaulted = Boolean.TRUE;
		private Boolean readByClosed = Boolean.TRUE;
		private Boolean readAllInclude = Boolean.TRUE;
		private Boolean readAllExclude = Boolean.TRUE;
		private Boolean readByGlobalIdentifier = Boolean.TRUE;
		private Boolean readByGlobalIdentifiers = Boolean.TRUE;
		private Boolean readByGlobalIdentifierValue = Boolean.TRUE;
		private Boolean readByGlobalIdentifierCode = Boolean.TRUE;
		private Boolean readByGlobalIdentifierCodes = Boolean.TRUE;
		private Boolean readByGlobalIdentifierOrderNumber = Boolean.TRUE;
		private Boolean readByGlobalIdentifierSupportingDocumentCode = Boolean.FALSE;
		private Boolean readByIdentifiers = Boolean.TRUE;
		private Boolean readFirstWhereExistencePeriodFromDateIsLessThan = Boolean.TRUE;
		private Boolean readWhereExistencePeriodFromDateIsLessThan = Boolean.TRUE;
		private Boolean readWhereExistencePeriodFromDateIsGreaterThan = Boolean.TRUE;
		private Boolean readWhereOrderNumberIsGreaterThan = Boolean.TRUE;
		
		private Boolean readByClasses = Boolean.FALSE;
		private Boolean readByNotClasses = Boolean.FALSE;
		
		private Boolean executeDelete = Boolean.TRUE;
		private Boolean readByGlobalIdentifierSearchCriteria = Boolean.TRUE;
		private Boolean readBySearchCriteria = Boolean.TRUE;
		
		private Boolean readWhereExistencePeriodCross = Boolean.TRUE;
		/**/
		
		/**/
		
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
