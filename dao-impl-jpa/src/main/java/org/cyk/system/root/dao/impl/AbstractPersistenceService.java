package org.cyk.system.root.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import lombok.Getter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.dao.api.PersistenceService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractBean;

@Log
public abstract class AbstractPersistenceService<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements Serializable,PersistenceService<IDENTIFIABLE, Long> {

	private static final long serialVersionUID = -8198334103295401293L;
	
	@PersistenceContext @Getter
	protected EntityManager entityManager;
	protected Class<IDENTIFIABLE> clazz;

	protected StringBuilder __buildingQueryString__;
	protected Map<String, Object> parameters;
	private QueryWrapper<IDENTIFIABLE> __queryWrapper__;
	
	@Override
	protected void beforeInitialisation() {
		super.beforeInitialisation();
		Collection<Field> namedQueriesFields = commonUtils.getAllFields(getClass());
		//Named queries initialisation
		for(Field field : namedQueriesFields)
			if(field.getName().startsWith("read"))
				try {
					FieldUtils.writeField(field, this, addPrefix(field.getName()), true);
				} catch (IllegalAccessException e) {
					log.log(Level.SEVERE, e.toString(), e);
				}
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		namedQueriesInitialisation();
	}
	
	protected void namedQueriesInitialisation(){
		
	}
	
	@Override
	public PersistenceService<IDENTIFIABLE, Long> select() {
		__buildingQueryString__ = new StringBuilder("SELECT record FROM "+clazz.getSimpleName()+" record");
		parameters=null;
		return this;
	}
	
	@Override
	public PersistenceService<IDENTIFIABLE, Long> where(String anAttributeName,Object aValue) {
		__buildingQueryString__.append(" "+(parameters==null?"WHERE ":"")+"record."+anAttributeName+" = :"+anAttributeName);
		if(parameters==null)
			parameters = new HashMap<String, Object>();
		parameters.put(anAttributeName, aValue);
		return this;
	}
	
	@Override
	public PersistenceService<IDENTIFIABLE, Long> and() {
		__buildingQueryString__.append(" AND ");
		return this;
	}
	
	@Override
	public PersistenceService<IDENTIFIABLE, Long> or() {
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
	
	protected QueryWrapper<IDENTIFIABLE> query(String value,QueryType type){
		switch(type){
		case JPQL:__queryWrapper__ = new QueryWrapper<IDENTIFIABLE>(entityManager.createQuery(value, clazz));break;
		case NAMED_JPQL:__queryWrapper__ = new QueryWrapper<IDENTIFIABLE>(entityManager.createNamedQuery(value, clazz));break;
		default:__queryWrapper__ = null;log.severe("Query <"+value+"> cannot be built for "+type);break;
		}
		return __queryWrapper__;
	}
	
	protected QueryWrapper<IDENTIFIABLE> namedQuery(String value){
		return query(value, QueryType.NAMED_JPQL);
	}
	
	protected void registerNamedQuery(String name,String query){
		entityManager.getEntityManagerFactory().addNamedQuery(name, entityManager.createQuery(query, clazz));
	}
	
	protected String addPrefix(String value){
		return getClass().getSimpleName()+"."+value;
	}
	
	protected Collection<Long> ids(Collection<? extends AbstractIdentifiable> identifiables){
		Collection<Long> ids = new HashSet<>();
		for(AbstractIdentifiable identifiable : identifiables)
			ids.add(identifiable.getIdentifier());
		return ids;
	}
	
	protected String entityName(){
		return clazz.getSimpleName();
	}
	
	protected String select(String variableName){
		return "SELECT "+variableName+" FROM "+entityName()+" "+variableName+" ";
	}
	
	protected String _select(){
		return select("r");
	}

}
