package org.cyk.system.root.persistence.impl;

import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_JPQL_COUNT;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_JPQL_FROM;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_JPQL_ORDER_BY;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_JPQL_SELECT;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_NQ_COMPUTE;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_NQ_COUNT;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_NQ_EXECUTE;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_NQ_READ;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_NQ_SUM;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.AbstractPeriodSearchCriteria;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;
import org.cyk.utility.common.generator.RandomDataProvider;

import lombok.Getter;

public abstract class AbstractPersistenceService<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements Serializable,PersistenceService<IDENTIFIABLE, Long> {

	private static final long serialVersionUID = -8198334103295401293L;
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPersistenceService.class);
	
	public final static Set<Class<?>> NAMED_QUERIES_INITIALIZED = new HashSet<>();
	private final static String SELECT_IDENTIFIER_FORMAT = "SELECT record.identifier FROM %s record";
	//private final static String SELECT_BYIDENTIFIER_FORMAT = "SELECT record FROM %s record WHERE record.identifier IN :identifiers";
	
	protected static final String ORDER_BY_FORMAT = "ORDER BY %s";
	
	//private QueryWrapper<?> __queryWrapper__;
	@Getter private DataReadConfiguration dataReadConfig = new DataReadConfiguration();
	
	@PersistenceContext @Getter
	protected EntityManager entityManager;
	protected Class<IDENTIFIABLE> clazz;

	@Inject protected QueryStringBuilder queryStringBuilder;
	protected Map<String, Object> parameters;
	protected Function selectFunction;
	
	//private String readByIdentifier,countByIdentifier;
	  
	@Override 
	protected void beforeInitialisation() {
		super.beforeInitialisation();
		Collection<Field> namedQueriesFields = commonUtils.getAllFields(getClass());
		//Named queries name initialization
		for(Field field : namedQueriesFields)
			//TODO use an array to hold those values
			if(field.getName().startsWith(KW_NQ_READ) || field.getName().startsWith(KW_NQ_COUNT) 
					|| field.getName().startsWith(KW_NQ_SUM) || field.getName().startsWith(KW_NQ_COMPUTE) || field.getName().startsWith(KW_NQ_EXECUTE))
				try {
					FieldUtils.writeField(field, this, addPrefix(field.getName()), true);
				} catch (IllegalAccessException e) {
					__logger__().error(e.toString(), e);
				}
	}
		
	@Override
	protected final void initialisation() {
		super.initialisation();
		if(NAMED_QUERIES_INITIALIZED.contains(getClass()))
			;
		else{
			namedQueriesInitialisation();
			NAMED_QUERIES_INITIALIZED.add(getClass());//TODO i think synchronization is needed to avoid concurrent add
			//System.out.println("AbstractPersistenceService.initialisation() : "+this.getClass());
		}
	}
	
	protected void namedQueriesInitialisation(){
	    //registerNamedQuery(readByIdentifier, _select().where("identifier"));
		
	}
	
	@Override
    public IDENTIFIABLE create(IDENTIFIABLE object) {
	    entityManager.persist(object);
	    logTrace("{} persisted. {}",object.getClass().getSimpleName(),ToStringBuilder.reflectionToString(object, ToStringStyle.SHORT_PREFIX_STYLE));
        return object;
    }
    
	@Override
    public IDENTIFIABLE read(Long identifier) {
        return entityManager.find(clazz, identifier);
    }
	
	@Override
	public IDENTIFIABLE readByGlobalIdentifier(GlobalIdentifier globalIdentifier) {
		return null;
	}
	
	@Override
	public IDENTIFIABLE readByGlobalIdentifierCode(String code) {
		return null;
	}
	
	@Override
	public Collection<IDENTIFIABLE> readByGlobalIdentifierCodes(Collection<String> codes) {
		return null;
	}

    @Override
    public IDENTIFIABLE update(IDENTIFIABLE object) {
        return entityManager.merge(object);
    }

    @Override
    public IDENTIFIABLE delete(IDENTIFIABLE object) {
        entityManager.remove(entityManager.merge(object));
        return object;
    }
    
    @Override
    public Boolean exist(IDENTIFIABLE anIdentifiable) {
        return entityManager.find(anIdentifiable.getClass(), anIdentifiable.getIdentifier())!=null; //countNamedQuery(countByIdentifier).parameter("identifier", anIdentifier).resultOne()==1;
    }
	 
	@Override
	public PersistenceService<IDENTIFIABLE, Long> select(Function function) {
		queryStringBuilder.init().from(clazz).select(selectFunction = function);
		parameters=null;
		return this;
	}
	
	@Override
	public PersistenceService<IDENTIFIABLE, Long> select() {
		return select(null);
	}
	
	@Override
	public PersistenceService<IDENTIFIABLE, Long> where(LogicalOperator aLogicalOperator,String anAttributeName,String aVarName,Object aValue,ArithmeticOperator anArithmeticOperator) {
		queryStringBuilder.where(aLogicalOperator,anAttributeName,aVarName,anArithmeticOperator);
		if(parameters==null) 
			parameters = new HashMap<String, Object>();
		parameters.put(aVarName, aValue);
		return this;
	}
	
	@Override
	public PersistenceService<IDENTIFIABLE, Long> where(LogicalOperator aLogicalOperator,String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator) {
		return where(aLogicalOperator, anAttributeName, anAttributeName, aValue, anArithmeticOperator);
	}
	
	@Override
	public PersistenceService<IDENTIFIABLE, Long> where(String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator) {
		return where(null, anAttributeName, aValue, anArithmeticOperator);
	}
	
	@Override
	public PersistenceService<IDENTIFIABLE, Long> where(String anAttributeName,Object aValue) {
		return where(anAttributeName, aValue, ArithmeticOperator.EQ);
	}
			
	@Override
	public Collection<IDENTIFIABLE> all() {
		Collection<IDENTIFIABLE> result = createQuery().getResultList();
		clear();
		return result;
	}

	@Override
	public <RESULT_TYPE> RESULT_TYPE one(Class<RESULT_TYPE> aClass) {
		RESULT_TYPE result = createQuery(aClass).getSingleResult();
		clear();
		return result;
	}
	
	@Override
	public IDENTIFIABLE one() {
		try {
			return one(clazz);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public Long oneLong() {
		return one(Long.class);
	}
		
	@Override
	public String getQueryString() {
		return queryStringBuilder.getValue();
	}
	
	/**/
	
	/**/
	
	protected <T> TypedQuery<T> createQuery(Class<T> resultType){
		TypedQuery<T> query = entityManager.createQuery(getQueryString(), resultType);
		if(parameters!=null)
			for(Entry<String, Object> parameter : parameters.entrySet())
				query.setParameter(parameter.getKey(), parameter.getValue());
		QueryWrapper.applyReadConfig(query, getDataReadConfig());
		//debug(dataReadConfig);
		//dataReadConfig = new DataReadConfig();//A new one for the next coming request
		return query;
	}
	
	protected TypedQuery<IDENTIFIABLE> createQuery(){
		return createQuery(clazz);
	}
	
	/**/
	
	public enum QueryType{JPQL,NAMED_JPQL,NATIVE,NAMED_NATIVE}
	
	@SuppressWarnings("unchecked")
	protected <RESULT_CLASS> QueryWrapper<RESULT_CLASS> query(String value,QueryType type,Class<RESULT_CLASS> aResultClass){
		QueryWrapper<?> __queryWrapper__;
		if(StringUtils.isBlank(value)){
			throw new IllegalArgumentException("Value cannot be blank <<"+value+">>");
		}
		switch(type){
		case JPQL:__queryWrapper__ = new QueryWrapper<RESULT_CLASS>(entityManager.createQuery(value, aResultClass),getDataReadConfig());break;
		case NAMED_JPQL:__queryWrapper__ = new QueryWrapper<RESULT_CLASS>(entityManager.createNamedQuery(value, aResultClass),getDataReadConfig());break;
		default:__queryWrapper__ = null;logError("Query <{}> cannot be built for {}",value,type);break;
		}
		/*
		__queryWrapper__.getReadConfig().setFirstResultIndex(null);
		__queryWrapper__.getReadConfig().setMaximumResultCount(null);
		*/
		return (QueryWrapper<RESULT_CLASS>) __queryWrapper__;
	}
	
	protected <RESULT_CLASS> QueryWrapper<RESULT_CLASS> namedQuery(String value,Class<RESULT_CLASS> aResultClass){
		return query(value, QueryType.NAMED_JPQL,aResultClass);
	}
	protected QueryWrapper<IDENTIFIABLE> namedQuery(String value){
		return namedQuery(value, clazz);
	}
	protected QueryWrapper<Long> countNamedQuery(String value){
        return namedQuery(value, Long.class).nullValue(0l);
    }
	protected QueryWrapper<Long> sumNamedQuery(String value){
        return namedQuery(value, Long.class).nullValue(0l);
    }
	
	protected void registerNamedQuery(String name,String query,Class<?> aResultClass){
		entityManager.getEntityManagerFactory().addNamedQuery(name, entityManager.createQuery(query, aResultClass));	
	}
	protected void registerNamedQuery(String name,String query){
		registerNamedQuery(name, query, clazz);
		Field countField = FieldUtils.getField(getClass(), KW_NQ_COUNT+StringUtils.substringAfter(name, "."+KW_NQ_READ), true);
		if(countField!=null){
			String var = StringUtils.substringAfter(StringUtils.substringBefore(query, KW_JPQL_FROM),KW_JPQL_SELECT).trim();
			if(var.toLowerCase().startsWith("new ")){
				String afterFrom = StringUtils.substringAfter(query, KW_JPQL_FROM+" ").trim();
				var = StringUtils.split(afterFrom)[1];
			}
			query = KW_JPQL_SELECT+" "+KW_JPQL_COUNT+"(" +var.trim()+")"+" "+KW_JPQL_FROM+" "+
			(StringUtils.contains(query, KW_JPQL_ORDER_BY)?StringUtils.substringBetween(query, KW_JPQL_FROM,KW_JPQL_ORDER_BY):StringUtils.substringAfter(query, KW_JPQL_FROM));
			registerNamedQuery(StringUtils.replaceOnce(name, "."+KW_NQ_READ, "."+KW_NQ_COUNT), query, Long.class);
		}
	}
	protected void registerNamedQuery(String name,QueryStringBuilder builder){
		registerNamedQuery(name, builder.getValue());
	}
	
	protected String addPrefix(String value){
		return getClass().getSimpleName()+"."+value;
	}
	
	protected Collection<Long> ids(Collection<? extends AbstractIdentifiable> identifiables){
		return Utils.ids(identifiables);
	}
	 
	protected String entityName(){
		return clazz.getSimpleName();
	}   
	
	protected String attribute(String name){
		return commonUtils.attributePath(queryStringBuilder.getRootEntityVariableName(), name);
	}
	
	protected String sumAttributes(String...names){
		List<String> r = new ArrayList<>();
		for(String name : names)
			r.add("SUM("+attribute(name)+")");
		return StringUtils.join(r,Constant.CHARACTER_COMA);
	}
	
	protected QueryStringBuilder _selectString(String string){
		return queryStringBuilder.init().from(clazz).selectString(string);
	} 
	
	protected QueryStringBuilder _select(String variableName){
		return queryStringBuilder.init().from(clazz).select();
	} 
	
	protected QueryStringBuilder _select(){
		return _select(QueryStringBuilder.VAR);
	}

	@Override
	public void clear() {
		if(Boolean.TRUE.equals(getDataReadConfig().getAutoClear()))
			getDataReadConfig().clear();
	}
	
	@Override
	public void detach(IDENTIFIABLE identifiable) {
		entityManager.detach(identifiable);
	}
	
	@Override
	public void flush() {
		entityManager.flush();
	}
	
	@Override
	public Collection<Long> readAllIdentifiers() {
		List<Long> identifiers = entityManager.createQuery(String.format(SELECT_IDENTIFIER_FORMAT, entityName()),Long.class).getResultList();
		return identifiers;
	}
	
	@Override
	public Collection<Long> readManyIdentifiersRandomly(Integer count) {
		Integer numberOfRows = select(Function.COUNT).oneLong().intValue();
		if(count>numberOfRows)
			count = numberOfRows;
		
		return entityManager.createQuery(String.format(SELECT_IDENTIFIER_FORMAT, entityName()),Long.class)
				.setFirstResult(RandomDataProvider.getInstance().randomInt(0, numberOfRows - count))
				.setMaxResults(count)
				.getResultList();
	}
	
	@Override
	public Long readOneIdentifierRandomly() {
		Collection<Long> collection = readManyIdentifiersRandomly(1);
		return collection.isEmpty()?null:collection.iterator().next();
	}
	
	@Override
	public Collection<IDENTIFIABLE> readManyRandomly(Integer count) {
		/*return entityManager.createQuery(_select().in(null).getValue(),clazz)
				.setParameter(QueryStringBuilder.VAR_IDENTIFIERS, readManyIdentifiersRandomly(count))
				.setMaxResults(count)
				.getResultList();
		*/
		return readByIdentifiers(readManyIdentifiersRandomly(count));
	}
	
	@Override
	public IDENTIFIABLE readOneRandomly() {
		Collection<IDENTIFIABLE> collection = readManyRandomly(1);
		return collection.isEmpty()?null:collection.iterator().next();
	}
	
	@Override
	public Collection<IDENTIFIABLE> readByIdentifiers(Collection<Long> identifiers) {
		return entityManager.createQuery(_select().in(null).getValue(),clazz)
				.setParameter(QueryStringBuilder.VAR_IDENTIFIERS, identifiers)
				.getResultList();
	}
	
	
	
	/**/
	
	protected void applyPeriodSearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractPeriodSearchCriteria searchCriteria){
		queryWrapper.parameterBetween(searchCriteria.getFromDateSearchCriteria().getPreparedValue(), 
				searchCriteria.getToDateSearchCriteria().getPreparedValue());
		//queryWrapper.parameter(QueryStringBuilder.VAR_BETWEEN_FROM,searchCriteria.getFromDateSearchCriteria().getPreparedValue());
		//queryWrapper.parameter(QueryStringBuilder.VAR_BETWEEN_TO,searchCriteria.getToDateSearchCriteria().getPreparedValue());
	}
	
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria){}
	
	/**/
	
	protected void throwExecuteUpdateExceptionIfAny(Integer expectedNumberOfEntities,Integer actualNumberOfEntities){
		if(expectedNumberOfEntities!=actualNumberOfEntities)
			throw new RuntimeException(expectedNumberOfEntities+" entities expected to be updated but only "+actualNumberOfEntities+" updated");
	}
	
	protected String getGlobalIdentifierFieldPath(String...fieldNames){
		return commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, fieldNames);
	}
	
	

	public static final String PARAMETER_INDEX = "pindex";
}
