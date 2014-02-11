package org.cyk.system.root.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.cyk.system.root.dao.api.IDataAccess;
import org.cyk.system.root.dao.api.IModelAccess;
import org.cyk.system.root.model.AbstractModel;

public abstract class AbstractTypedDao<IDENTIFIABLE extends AbstractModel> extends AbstractDao<IDENTIFIABLE> implements IModelAccess<IDENTIFIABLE>,Serializable {

	private static final long serialVersionUID = -2964204372097468908L;

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void postConstruct(){ 
		clazz = (Class<IDENTIFIABLE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
	public IDataAccess<IDENTIFIABLE, Long> select() {
		__buildingQueryString__ = new StringBuilder("SELECT record FROM "+clazz.getSimpleName()+" record");
		return this;
	}

	@Override
	public IDataAccess<IDENTIFIABLE, Long> where(String anAttributeName,Object aValue) {
		__buildingQueryString__.append(" "+(parameters==null?"WHERE ":"")+"record."+anAttributeName+" = :"+anAttributeName);
		if(parameters==null)
			parameters = new HashMap<String, Object>();
		parameters.put(anAttributeName, aValue);
		return this;
	}
	
	@Override
	public IDataAccess<IDENTIFIABLE, Long> and() {
		__buildingQueryString__.append(" AND ");
		return this;
	}
	
	@Override
	public IDataAccess<IDENTIFIABLE, Long>or() {
		__buildingQueryString__.append(" OR ");
		return this;
	}
	
	@Override
	public String getQueryString() {
		return __buildingQueryString__.toString();
	}
	
	private Query createQuery(){
		Query query = entityManager.createQuery(/*getQuery()*/"", clazz);
		if(parameters!=null)
			for(Entry<String, Object> parameter : parameters.entrySet())
				query.setParameter(parameter.getKey(), parameter.getValue());
		return query;
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

	
	
}
