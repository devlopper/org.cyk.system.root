package org.cyk.system.root.dao.impl;

import java.io.Serializable;
import java.util.List;

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
	public List<T> resultMany() {
		return query.getResultList();
	}

	public Object getSingleResult() {
		return query.getSingleResult();
	}
	
	
	
}
