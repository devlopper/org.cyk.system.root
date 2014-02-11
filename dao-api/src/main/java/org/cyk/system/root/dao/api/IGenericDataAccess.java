package org.cyk.system.root.dao.api;

import org.cyk.system.root.model.IModel;

public interface IGenericDataAccess<IDENTIFIABLE extends IModel<IDENTIFIER>,IDENTIFIER> extends IQueryable<IDENTIFIABLE,IDENTIFIER> {

	/* ------------------------ Static methods ---------------------------- */
	
	/* Create */	IDENTIFIABLE create(Class<? extends IDENTIFIABLE> aClass,IDENTIFIABLE object);
	
	/* Read */ 		IDENTIFIABLE read(Class<? extends IDENTIFIABLE> aClass,IDENTIFIER identifier);
	
	/* Update */	IDENTIFIABLE update(Class<? extends IDENTIFIABLE> aClass,IDENTIFIABLE object);
	
	/* Delete */	IDENTIFIABLE delete(Class<? extends IDENTIFIABLE> aClass,IDENTIFIABLE object);
	
	/* ------------------------ Dynamic methods ---------------------------- */
	
	/* point to */	IGenericDataAccess<IDENTIFIABLE,IDENTIFIER> use(Class<? extends IDENTIFIABLE> aClass);
	
}
