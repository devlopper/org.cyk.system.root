package org.cyk.system.root.business.api;

import org.cyk.system.root.model.Identifiable;

public interface IGenericService<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends BusinessService<IDENTIFIABLE,IDENTIFIER> {

	/* ------------------------ Static methods ---------------------------- */
	
	/* Create */	IDENTIFIABLE create(Class<? extends IDENTIFIABLE> aClass,IDENTIFIABLE object);
	
	/* Read */ 		IDENTIFIABLE read(Class<? extends IDENTIFIABLE> aClass,IDENTIFIER identifier);
	
	/* Update */	IDENTIFIABLE update(Class<? extends IDENTIFIABLE> aClass,IDENTIFIABLE object);
	
	/* Delete */	IDENTIFIABLE delete(Class<? extends IDENTIFIABLE> aClass,IDENTIFIABLE object);
	
	/* ------------------------ Dynamic methods ---------------------------- */
	
	/* point to */	IGenericService<IDENTIFIABLE,IDENTIFIER> use(Class<? extends IDENTIFIABLE> aClass);
	
}

