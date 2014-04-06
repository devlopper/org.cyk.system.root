package org.cyk.system.root.dao.api;

import java.util.Collection;

import org.cyk.system.root.model.Identifiable;

public interface PersistenceService<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> {
	
	/* select */		PersistenceService<IDENTIFIABLE,IDENTIFIER> select();
	
	/* filter */		PersistenceService<IDENTIFIABLE,IDENTIFIER> where(String anAttributeName,Object aValue);
	
	/* operator */		PersistenceService<IDENTIFIABLE,IDENTIFIER> and();
	
						PersistenceService<IDENTIFIABLE,IDENTIFIER> or();
	
	/* read all*/		Collection<IDENTIFIABLE> all();
	
	/* read one*/		IDENTIFIABLE one();	
	
	/* query */			String getQueryString();
	
}
