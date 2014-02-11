package org.cyk.system.root.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.cyk.system.root.dao.api.IGenericModelAccess;
import org.cyk.system.root.model.AbstractModel;

public class GenericDao extends AbstractDao<AbstractModel> implements IGenericModelAccess {

	private static final long serialVersionUID = 5597848028969504927L;
	
	/*-------------------------------------------------------------------------------------------*/
	
	@Override
	public AbstractModel create(Class<? extends AbstractModel> aClass,AbstractModel object) {
		entityManager.persist(object);
		return object;
	}
	
	@Override
	public AbstractModel read(Class<? extends AbstractModel> aClass,Long identifier) {
		return entityManager.find(aClass, identifier);
	}

	@Override
	public AbstractModel update(Class<? extends AbstractModel> aClass,AbstractModel object) {
		return entityManager.merge(object);
	}

	@Override
	public AbstractModel delete(Class<? extends AbstractModel> aClass,AbstractModel object) {
		entityManager.remove(entityManager.merge(object));
		return object;
	}
	
	/*--------------------------------------------------------------------------------------------*/
	
	@SuppressWarnings("unchecked")
	@Override
	public IGenericModelAccess use(Class<? extends AbstractModel> aClass) {
		clazz = (Class<AbstractModel>) aClass;
		return this;
	}

	@Override
	public IGenericModelAccess select() {
		__buildingQueryString__ = new StringBuilder("SELECT record FROM "+clazz.getSimpleName()+" record");
		parameters=null;
		return this;
	}

	@Override
	public IGenericModelAccess where(String anAttributeName,Object aValue) {
		__buildingQueryString__.append(" "+(parameters==null?"WHERE ":"")+"record."+anAttributeName+" = :"+anAttributeName);
		if(parameters==null)
			parameters = new HashMap<String, Object>();
		parameters.put(anAttributeName, aValue);
		return this;
	}
	
	@Override
	public IGenericModelAccess and() {
		__buildingQueryString__.append(" AND ");
		return this;
	}
	
	@Override
	public IGenericModelAccess or() {
		__buildingQueryString__.append(" OR ");
		return this;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public Collection<AbstractModel> all() {
		return createQuery().getResultList();
	}

	@Override
	public AbstractModel one() {
		try {
			return (AbstractModel) createQuery().getSingleResult();
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
	
	private Query createQuery(){
		Query query = entityManager.createQuery(__buildingQueryString__.toString(), clazz);
		//System.out.println(query);
		if(parameters!=null)
			for(Entry<String, Object> parameter : parameters.entrySet())
				query.setParameter(parameter.getKey(), parameter.getValue());
		return query;
	}
	
}
