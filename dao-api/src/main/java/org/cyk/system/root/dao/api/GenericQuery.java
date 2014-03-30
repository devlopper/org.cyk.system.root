package org.cyk.system.root.dao.api;

import org.cyk.system.root.model.Identifiable;

public interface GenericQuery<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends Queryable<IDENTIFIABLE,IDENTIFIER> {

	/* ------------------------ Static methods ---------------------------- */
	
	/* Create */	IDENTIFIABLE create(Class<? extends IDENTIFIABLE> aClass,IDENTIFIABLE object);
	
	/* Read */ 		IDENTIFIABLE read(Class<? extends IDENTIFIABLE> aClass,IDENTIFIER identifier);
	
	/* Update */	IDENTIFIABLE update(Class<? extends IDENTIFIABLE> aClass,IDENTIFIABLE object);
	
	/* Delete */	IDENTIFIABLE delete(Class<? extends IDENTIFIABLE> aClass,IDENTIFIABLE object);
	
	/* ------------------------ Dynamic methods ---------------------------- */
	
	/* point to */	GenericQuery<IDENTIFIABLE,IDENTIFIER> use(Class<? extends IDENTIFIABLE> aClass);
	
}
