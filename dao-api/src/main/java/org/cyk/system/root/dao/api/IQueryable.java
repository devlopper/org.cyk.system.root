package org.cyk.system.root.dao.api;

import java.util.Collection;

import org.cyk.system.root.model.IModel;

public interface IQueryable<IDENTIFIABLE extends IModel<IDENTIFIER>,IDENTIFIER> {
	
	/* select */		IQueryable<IDENTIFIABLE,IDENTIFIER> select();
	
	/* filter */		IQueryable<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue);
	
	/* operator */		IQueryable<IDENTIFIABLE,IDENTIFIER> and();
	
						IQueryable<IDENTIFIABLE,IDENTIFIER> or();
	
	/* read all*/		Collection<IDENTIFIABLE> all();
	
	/* read one*/		IDENTIFIABLE one();	
	
	/* query */			String getQueryString();
	
}
