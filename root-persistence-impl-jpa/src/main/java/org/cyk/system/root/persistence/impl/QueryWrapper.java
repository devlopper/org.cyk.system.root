package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import lombok.Getter;

import org.cyk.utility.common.computation.DataReadConfig;

@Getter
public class QueryWrapper<T> implements Serializable {

	private static final long serialVersionUID = 5699283157667217854L;

	private Query query;  
	private DataReadConfig readConfig;
	private Collection<Class<? extends Throwable>> ignoreThrowables = new HashSet<>();

	public QueryWrapper(Query query,DataReadConfig readConfig) {
		super();
		this.query = query;
		this.readConfig = readConfig;
	}  
	
	public QueryWrapper<T> parameter(String name,Object value){
		if(value instanceof Date)
		    query.setParameter(name, (Date)value,TemporalType.TIMESTAMP);
		else
		    query.setParameter(name, value);
		return this;
	}
	
	public QueryWrapper<T> ignoreThrowable(Class<? extends Throwable> throwableClass){
        ignoreThrowables.add(throwableClass);
        return this;
    }
	
	public QueryWrapper<T> nullable(){
        return ignoreThrowable(NoResultException.class);
    }

	@SuppressWarnings("unchecked")
	public Collection<T> resultMany() {
	    applyReadConfig(query,readConfig);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public T resultOne() {
		try {
            return (T) query.getSingleResult();
        } catch (Exception e) {
            if(ignoreThrowables.contains(e.getClass()))
                return null;
            throw e;
        }
	}
	
	
	public static void applyReadConfig(Query query,DataReadConfig readConfig){
	    if(readConfig.getFirstResultIndex()!=null && readConfig.getFirstResultIndex()>0)
	        query.setFirstResult(readConfig.getFirstResultIndex().intValue());
	    if(readConfig.getMaximumResultCount()!=null && readConfig.getMaximumResultCount()>0)
            query.setMaxResults(readConfig.getMaximumResultCount().intValue());
	}
	
	
}
