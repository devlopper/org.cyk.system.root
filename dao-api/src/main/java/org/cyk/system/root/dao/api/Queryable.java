package org.cyk.system.root.dao.api;

import java.util.Collection;

import org.cyk.system.root.model.Identifiable;

public interface Queryable<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> {
	
	/* select */		Queryable<IDENTIFIABLE,IDENTIFIER> select();
	
	/* filter */		Queryable<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue);
	
	/* operator */		Queryable<IDENTIFIABLE,IDENTIFIER> and();
	
						Queryable<IDENTIFIABLE,IDENTIFIER> or();
	
	/* read all*/		Collection<IDENTIFIABLE> all();
	
	/* read one*/		IDENTIFIABLE one();	
	
	/* query */			String getQueryString();
	
}
