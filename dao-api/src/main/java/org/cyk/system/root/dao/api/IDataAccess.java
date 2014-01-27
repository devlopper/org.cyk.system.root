package org.cyk.system.root.dao.api;

import java.util.Collection;

import org.cyk.system.root.model.IModel;

public interface IDataAccess<IDENTIFIABLE extends IModel<IDENTIFIER>,IDENTIFIER> {
	
	public enum WhereOperator {OR,AND}
	
	/**
	 * CRUD
	 * 
	 */
	
	/* Create */
	
	void create(IDENTIFIABLE object);
	
	/* Read */
	
	IDENTIFIABLE read(IDENTIFIER identifier);
	
	Collection<IDENTIFIABLE> readAll();
	
	/* Update */
	
	IDENTIFIABLE update(IDENTIFIABLE object);
	
	/* Delete */
	
	void delete(IDENTIFIABLE object);
	
	/* Dynamic query build and execution */
		
	IDataAccess<IDENTIFIABLE, IDENTIFIER> select();
	
	IDataAccess<IDENTIFIABLE, IDENTIFIER> where(String anAttributeName,Object aValue,WhereOperator anOperator);
	
	String getQueryString();
	
	IDataAccess<IDENTIFIABLE, IDENTIFIER> build();
	
	Collection<IDENTIFIABLE> all();
	
	IDENTIFIABLE one();

}
