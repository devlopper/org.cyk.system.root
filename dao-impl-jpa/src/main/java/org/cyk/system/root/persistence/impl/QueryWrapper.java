package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Query;

import lombok.Getter;

@Getter
public class QueryWrapper<T> implements Serializable {

	private static final long serialVersionUID = 5699283157667217854L;

	private Query query;

	public QueryWrapper(Query query) {
		super();
		this.query = query;
	}  
	
	public QueryWrapper<T> parameter(String name,Object value){
		query.setParameter(name, value);
		return this;
	}

	@SuppressWarnings("unchecked")
	public Collection<T> resultMany() {
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public T resultOne() {
		return (T) query.getSingleResult();
	}
	
	
	
}
