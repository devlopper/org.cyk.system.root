package org.cyk.system.root.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import lombok.Getter;
import lombok.extern.java.Log;

import org.cyk.system.root.dao.api.Queryable;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractBean;

@Log
public abstract class AbstractQueryable<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements Serializable,Queryable<IDENTIFIABLE, Long> {

	private static final long serialVersionUID = -8198334103295401293L;
	
	@PersistenceContext @Getter
	protected EntityManager entityManager;
	protected Class<IDENTIFIABLE> clazz;

	protected StringBuilder __buildingQueryString__;
	protected Map<String, Object> parameters;
	private Query __query__;
	
	@Override
	public Queryable<IDENTIFIABLE, Long> select() {
		__buildingQueryString__ = new StringBuilder("SELECT record FROM "+clazz.getSimpleName()+" record");
		parameters=null;
		return this;
	}
	
	@Override
	public Queryable<IDENTIFIABLE, Long> where(String anAttributeName,Object aValue) {
		__buildingQueryString__.append(" "+(parameters==null?"WHERE ":"")+"record."+anAttributeName+" = :"+anAttributeName);
		if(parameters==null)
			parameters = new HashMap<String, Object>();
		parameters.put(anAttributeName, aValue);
		return this;
	}
	
	@Override
	public Queryable<IDENTIFIABLE, Long> and() {
		__buildingQueryString__.append(" AND ");
		return this;
	}
	
	@Override
	public Queryable<IDENTIFIABLE, Long> or() {
		__buildingQueryString__.append(" OR ");
		return this;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public Collection<IDENTIFIABLE> all() {
		return createQuery().getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public IDENTIFIABLE one() {
		try {
			return (IDENTIFIABLE) createQuery().getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		}
	}

	@Override
	public String getQueryString() {
		return __buildingQueryString__.toString();
	}
	
	/**/
	
	protected Query createQuery(){
		Query query = entityManager.createQuery(__buildingQueryString__.toString(), clazz);
		//System.out.println(query);
		if(parameters!=null)
			for(Entry<String, Object> parameter : parameters.entrySet())
				query.setParameter(parameter.getKey(), parameter.getValue());
		return query;
	}
	
	/**/
	
	public enum QueryType{JPQL,NAMED_JPQL,NATIVE,NAMED_NATIVE}
	
	protected AbstractQueryable<IDENTIFIABLE> query(String value,QueryType type){
		switch(type){
		case JPQL:__query__ = entityManager.createQuery(value, clazz);break;
		case NAMED_JPQL:__query__ = entityManager.createNamedQuery(value, clazz);break;
		default:__query__ = null;log.severe("Query <"+value+"> cannot be built for "+type);break;
		}
		
		return this;
	}
	
	public AbstractQueryable<IDENTIFIABLE> queryParameter(String name,Object value){
		__query__.setParameter(name, value);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<IDENTIFIABLE> queryResultMany(){
		return __query__.getResultList();
	}

}
