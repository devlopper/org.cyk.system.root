package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.GlobalIdentifier;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteria;
import org.cyk.system.root.persistence.api.TypedDao;

public abstract class AbstractTypedDao<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractPersistenceService<IDENTIFIABLE> implements TypedDao<IDENTIFIABLE>,Serializable {

	private static final long serialVersionUID = -2964204372097468908L;

	protected String readAll,countAll,readByClasses,countByClasses,readByNotClasses,countByNotClasses,readAllExclude,countAllExclude
		,readAllInclude,countAllInclude,readByGlobalIdentifiers,readByGlobalIdentifierValue,countByGlobalIdentifiers,executeDelete;
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
		registerNamedQuery(readAll, _select()+(StringUtils.isEmpty(readAllOrderByString())?"":" "+readAllOrderByString()));
		//registerNamedQuery(readAllExclude, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.identifier NOT IN :identifiers");
		
		registerNamedQuery(readAllInclude, _select().whereIdentifierIn());
		registerNamedQuery(readAllExclude, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.identifier NOT IN :identifiers");
		registerNamedQuery(readByGlobalIdentifiers, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.globalIdentifier.identifier IN :identifiers");
		registerNamedQuery(readByGlobalIdentifierValue, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.globalIdentifier.identifier = :identifier");
		
		if(Boolean.TRUE.equals(readByClassEnabled())){
			registerNamedQuery(readByClasses, _select().whereClassIn().orderBy("identifier",Boolean.TRUE));
			registerNamedQuery(readByNotClasses, _select().whereClassNotIn());
		}
		
		registerNamedQuery(executeDelete, "DELETE FROM "+clazz.getSimpleName()+" record WHERE record.identifier IN :identifiers");
	}
	
	protected Boolean readByClassEnabled(){
		return Boolean.FALSE;
	}
	
	protected String readAllOrderByString(){
		return null;
	}
	
	/**/
	
	@Override
	public Collection<IDENTIFIABLE> readAll() {
		return namedQuery(readAll).resultMany();
	}
	
	@Override
	public Long countAll() {
		return namedQuery(countAll,Long.class).resultOne();
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
	
	/**/
	
	protected String criteriaSearchQueryId(AbstractFieldValueSearchCriteria<?> searchCriteria,String ascendingOrderQueryId,String descendingOrderQueryId){
		if(searchCriteria.getAscendingOrdered()!=null)
			return Boolean.TRUE.equals(searchCriteria.getAscendingOrdered())?ascendingOrderQueryId:descendingOrderQueryId;
		else
			return ascendingOrderQueryId;
	}
	
}
