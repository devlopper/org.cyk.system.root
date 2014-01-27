package org.cyk.system.root.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.cyk.system.root.dao.api.IDataAccess;
import org.cyk.system.root.dao.api.IModelAccess;
import org.cyk.system.root.model.AbstractModel;

public class JpaDaoImpl<MODEL extends AbstractModel> implements IModelAccess<MODEL> {

	//@Inject
	protected EntityManager entityManager;
	protected Class<MODEL> clazz;

	private StringBuilder __buildingQueryString__ ;
	private Map<String,Object> parameters;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	private void init(){
		clazz = (Class<MODEL>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override 
	public void create(MODEL object) {
		entityManager.persist(object);
	}

	@Override
	public MODEL read(Long identifier) {
		return entityManager.find(clazz, identifier);
	}

	@Override
	public MODEL update(MODEL object) {
		return entityManager.merge(object);
	}

	@Override
	public void delete(MODEL object) {
		entityManager.remove(entityManager.merge(object));
	}

	@Override
	public List<MODEL> readAll() {
		return entityManager.createQuery("SELECT record FROM "+clazz.getSimpleName()+" record", clazz).getResultList();
	}

	@Override
	public IDataAccess<MODEL, Long> select() {
		__buildingQueryString__ = new StringBuilder("SELECT record FROM "+clazz.getSimpleName()+" record");
		return this;
	}

	@Override
	public IDataAccess<MODEL, Long> where(String anAttributeName,Object aValue,WhereOperator anOperator) {
		__buildingQueryString__.append(" "+(parameters==null?"WHERE ":anOperator==null?"":anOperator)+" record."+anAttributeName+" = :"+anAttributeName);
		if(parameters==null)
			parameters = new HashMap<String, Object>();
		parameters.put(anAttributeName, aValue);
		return this;
	}
	
	@Override
	public String getQueryString() {
		return __buildingQueryString__.toString();
	}
	
	@Override
	public IDataAccess<MODEL, Long> build() {
		// TODO Auto-generated method stub
		return null;
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
	public Collection<MODEL> all() {
		return createQuery().getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public MODEL one() {
		return (MODEL) createQuery().getSingleResult();
	}

	
	
}
