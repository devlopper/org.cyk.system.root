package org.cyk.system.root.dao.api;

import org.cyk.system.root.model.IModel;

public interface IDataAccess<IDENTIFIABLE extends IModel<IDENTIFIER>,IDENTIFIER> extends IQueryable<IDENTIFIABLE,IDENTIFIER> {
		
	/* Create */	IDENTIFIABLE create(IDENTIFIABLE object);
	
	/* Read */		IDENTIFIABLE read(IDENTIFIER identifier);
	
	/* Update */	IDENTIFIABLE update(IDENTIFIABLE object);
	
	/* Delete */	IDENTIFIABLE delete(IDENTIFIABLE object);

}
