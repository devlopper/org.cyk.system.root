package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Query;

import org.cyk.utility.common.computation.DataReadConfig;

import lombok.Getter;

@Getter
public class QueryWrapper<T> implements Serializable {

	private static final long serialVersionUID = 5699283157667217854L;

	private Query query;
	private DataReadConfig readConfig;

	public QueryWrapper(Query query,DataReadConfig readConfig) {
		super();
		this.query = query;
		this.readConfig = readConfig;
	}  
	
	public QueryWrapper<T> parameter(String name,Object value){
		query.setParameter(name, value);
		return this;
	}

	@SuppressWarnings("unchecked")
	public Collection<T> resultMany() {
	    applyReadConfig(query,readConfig);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public T resultOne() {
		return (T) query.getSingleResult();
	}
	
	
	public static void applyReadConfig(Query query,DataReadConfig readConfig){
	    if(readConfig.getFirstResultIndex()!=null && readConfig.getFirstResultIndex()>0)
	        query.setFirstResult(readConfig.getFirstResultIndex().intValue());
	    if(readConfig.getMaximumResultCount()!=null && readConfig.getMaximumResultCount()>0)
            query.setMaxResults(readConfig.getMaximumResultCount().intValue());
	}
	
	
}
