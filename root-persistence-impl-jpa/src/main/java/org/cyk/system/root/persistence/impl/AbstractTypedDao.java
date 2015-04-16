package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteria;

public abstract class AbstractTypedDao<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractPersistenceService<IDENTIFIABLE> implements TypedDao<IDENTIFIABLE>,Serializable {

	private static final long serialVersionUID = -2964204372097468908L;

	protected String readAll,countAll,readAllExclude,countAllExclude;
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
		registerNamedQuery(readAllExclude, "SELECT record FROM "+clazz.getSimpleName()+" record WHERE record.identifier NOT IN :identifiers");
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
	
	/**/
	
	protected String criteriaSearchQueryId(AbstractFieldValueSearchCriteria<?> searchCriteria,String ascendingOrderQueryId,String descendingOrderQueryId){
		if(searchCriteria.getAscendingOrdered()!=null)
			return Boolean.TRUE.equals(searchCriteria.getAscendingOrdered())?ascendingOrderQueryId:descendingOrderQueryId;
		else
			return ascendingOrderQueryId;
	}
	
}
