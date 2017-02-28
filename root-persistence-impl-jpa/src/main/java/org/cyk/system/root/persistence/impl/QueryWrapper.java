package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.DataReadConfiguration;

import lombok.Getter;

@Getter
public class QueryWrapper<T> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 5699283157667217854L;

	private Query query;  
	private DataReadConfiguration readConfig;
	private Collection<Class<? extends Throwable>> ignoreThrowables = new HashSet<>();
	private T resultOneNullValue;
	private Collection<T> resultManyNullValue=new ArrayList<T>();
	
	/*
	 * To avoid java.sql.SQLSyntaxErrorException: unexpected token: ) when one of the parameter is a collection and is empty
	 */
	private Boolean returnPredefinedNullValue,returnPredefinedNullValueIfOneParameterCollectionIsEmpty=Boolean.TRUE;
	
	public QueryWrapper(Query query,DataReadConfiguration readConfig) {
		super();
		this.query = query;
		this.readConfig = readConfig;
	}  
	
	public QueryWrapper<T> parameter(String name,Object value){
		if(Boolean.TRUE.equals(returnPredefinedNullValueIfOneParameterCollectionIsEmpty) && !Boolean.TRUE.equals(returnPredefinedNullValue)){
			if(value instanceof Collection<?> && ((Collection<?>)value).isEmpty())
				returnPredefinedNullValue = Boolean.TRUE;
		}
		logTrace("Setting query parameter {} to {}", name,value);
		if(value instanceof Date)
		    query.setParameter(name, (Date)value,TemporalType.TIMESTAMP);
		else
		    query.setParameter(name, value);
		
		return this;
	}
	
	public QueryWrapper<T> parameterIdentifiers(String name,Collection<? extends AbstractIdentifiable> identifiables){
		parameter(name, Utils.ids(identifiables));
		return this;
	}
	
	public QueryWrapper<T> parameterAttributeIdentifiers(String name,Collection<? extends AbstractIdentifiable> identifiables){
		parameterIdentifiers(QueryStringBuilder.getVarNameMany(name), identifiables);
		return this;
	}
	
	public QueryWrapper<T> parameterGlobalIdentifiers(String name,Collection<GlobalIdentifier> globalIdentifiers){
		parameter(name, Utils.getGlobalIdentfierValues(globalIdentifiers));
		return this;
	}
	public QueryWrapper<T> parameterGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers){
		parameterGlobalIdentifiers(QueryStringBuilder.VAR_IDENTIFIERS, globalIdentifiers);
		return this;
	}
	public QueryWrapper<T> parameterIdentifiers(Collection<? extends AbstractIdentifiable> identifiables){
		return parameterIdentifiers(QueryStringBuilder.VAR_IDENTIFIERS, identifiables);
	}
	
	
	public QueryWrapper<T> parameterClasses(Collection<Class<?>> classes){
		parameter(QueryStringBuilder.VAR_CLASS, classes);
		return this;
	}
	public QueryWrapper<T> parameterClass(Class<?> aClass){
		Collection<Class<?>> classes = new ArrayList<>();
		classes.add(aClass);
		return parameterClasses(classes);
	}
	
	public QueryWrapper<T> parameterLike(String name,String value){
		parameter(name, StringUtils.isBlank(value)?QueryStringBuilder.PERCENTAGE:value);
		return this;
	}
	
	public QueryWrapper<T> parameterLike(String name,StringSearchCriteria value){
		parameterLike(name, value.getLikeValue());
		parameter(QueryStringBuilder.getLengthParameterName(name), value.getPreparedValue().length());
		return this;
	}
	
	protected void parameterBetween(Object fromValue,Object toValue){
		parameter(QueryStringBuilder.VAR_BETWEEN_FROM,fromValue);
		parameter(QueryStringBuilder.VAR_BETWEEN_TO,toValue);
	}
	
	public QueryWrapper<T> ignoreThrowable(Class<? extends Throwable> throwableClass){
        ignoreThrowables.add(throwableClass);
        return this;
    }
	
	public QueryWrapper<T> nullable(){
        return ignoreThrowable(NoResultException.class);
    }
	
	public QueryWrapper<T> nullValue(T nullValue){
		this.resultOneNullValue = nullValue;
		return this;
	}
	
	public QueryWrapper<T> nullValue(Collection<T> nullValue){
		this.resultManyNullValue = nullValue;
		return this;
	}
	
	public QueryWrapper<T> returnPredefinedNullValueIfOneParameterCollectionIsEmpty(Boolean value){
		this.returnPredefinedNullValueIfOneParameterCollectionIsEmpty = value;
		return this;
	}

	@SuppressWarnings("unchecked")
	public Collection<T> resultMany() {
		Collection<T> result = null;
		if(Boolean.TRUE.equals(returnPredefinedNullValue))
			result = resultManyNullValue;
		else{
		    applyReadConfig(query,readConfig);
		    result = query.getResultList();
		}
		clear();
		return result;
	}

	@SuppressWarnings("unchecked")
	public T resultOne() {
		T result;
		if(Boolean.TRUE.equals(returnPredefinedNullValue))
			result = resultOneNullValue;
		else
			try {
	            T value = (T) query.getSingleResult();
	            if(value==null)
	            	result = resultOneNullValue;
	            else
	            	result = value;
	        } catch (Exception e) {
	            if(ignoreThrowables.contains(e.getClass()))
	            	result = null;
	            else
	            	throw e;
	        }
		clear();
		return result;
	}
	
	public void update(){
		query.executeUpdate();
	}
	
	public static void applyReadConfig(Query query,DataReadConfiguration readConfig){
	    if(readConfig.getFirstResultIndex()!=null && readConfig.getFirstResultIndex()>=0)
	        query.setFirstResult(readConfig.getFirstResultIndex().intValue());
	    if(readConfig.getMaximumResultCount()!=null && readConfig.getMaximumResultCount()>0)
            query.setMaxResults(readConfig.getMaximumResultCount().intValue());
	    //new RuntimeException().printStackTrace();
	}
	
	private void clear(){
		if(Boolean.TRUE.equals(readConfig.getAutoClear()))
	    	readConfig.clear();
	}
}
