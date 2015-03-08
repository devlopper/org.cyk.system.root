package org.cyk.system.root.persistence.impl;

import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_JPQL_COUNT;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_JPQL_FROM;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_JPQL_ORDER_BY;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_JPQL_SELECT;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_NQ_COUNT;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_NQ_READ;
import static org.cyk.system.root.persistence.impl.QueryStringBuilder.KW_NQ_SUM;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import lombok.Getter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.DataReadConfig;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

@Log
public abstract class AbstractPersistenceService<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements Serializable,PersistenceService<IDENTIFIABLE, Long> {

	private static final long serialVersionUID = -8198334103295401293L;
	
	private final static Set<Class<?>> NAMED_QUERIES_INITIALIZED = new HashSet<>();
	
	protected static final String ORDER_BY_FORMAT = "ORDER BY %s";
	
	private QueryWrapper<?> __queryWrapper__;
	@Getter private DataReadConfig dataReadConfig = new DataReadConfig();
	
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
		//Named queries name initialisation
		for(Field field : namedQueriesFields)
			if(field.getName().startsWith(KW_NQ_READ) || field.getName().startsWith(KW_NQ_COUNT) || field.getName().startsWith(KW_NQ_SUM))
				try {
					FieldUtils.writeField(field, this, addPrefix(field.getName()), true);
				} catch (IllegalAccessException e) {
					log.log(Level.SEVERE, e.toString(), e);
				}
	}
		
	@Override
	protected final void initialisation() {
		super.initialisation();
		if(NAMED_QUERIES_INITIALIZED.contains(getClass()))
			;
		else{
			namedQueriesInitialisation();
			NAMED_QUERIES_INITIALIZED.add(getClass());
		}
	}
	
	protected void namedQueriesInitialisation(){
	    //registerNamedQuery(readByIdentifier, _select().where("identifier"));
		
	}
	
	@Override
    public IDENTIFIABLE create(IDENTIFIABLE object) {
	    entityManager.persist(object);
        return object;
    }
    
	@Override
    public IDENTIFIABLE read(Long identifier) {
        return entityManager.find(clazz, identifier);
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
	public PersistenceService<IDENTIFIABLE, Long> where(LogicalOperator aLogicalOperator,String anAttributeName,Object aValue,ArithmeticOperator anArithmeticOperator) {
		queryStringBuilder.where(aLogicalOperator,anAttributeName,anAttributeName,anArithmeticOperator);
		if(parameters==null) 
			parameters = new HashMap<String, Object>();
		parameters.put(anAttributeName, aValue);
		return this;
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
		return createQuery().getResultList();
	}

	@Override
	public <RESULT_TYPE> RESULT_TYPE one(Class<RESULT_TYPE> aClass) {
		return createQuery(aClass).getSingleResult();
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
		return query;
	}
	
	protected TypedQuery<IDENTIFIABLE> createQuery(){
		return createQuery(clazz);
	}
	
	/**/
	
	public enum QueryType{JPQL,NAMED_JPQL,NATIVE,NAMED_NATIVE}
	
	@SuppressWarnings("unchecked")
	protected <RESULT_CLASS> QueryWrapper<RESULT_CLASS> query(String value,QueryType type,Class<RESULT_CLASS> aResultClass){
		switch(type){
		case JPQL:__queryWrapper__ = new QueryWrapper<RESULT_CLASS>(entityManager.createQuery(value, aResultClass),getDataReadConfig());break;
		case NAMED_JPQL:__queryWrapper__ = new QueryWrapper<RESULT_CLASS>(entityManager.createNamedQuery(value, aResultClass),getDataReadConfig());break;
		default:__queryWrapper__ = null;log.severe("Query <"+value+"> cannot be built for "+type);break;
		}
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
	
	protected QueryStringBuilder _select(String variableName){
		return queryStringBuilder.init().from(clazz).select();
	} 
	
	protected QueryStringBuilder _select(){
		return _select(QueryStringBuilder.VAR);
	}

}
