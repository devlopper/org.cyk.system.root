package org.cyk.system.root.dao.api;

import org.cyk.system.root.model.Identifiable;

public interface TypedQuery<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends Queryable<IDENTIFIABLE,IDENTIFIER> {
		
	/* Create */	IDENTIFIABLE create(IDENTIFIABLE object);
	
	/* Read */		IDENTIFIABLE read(IDENTIFIER identifier);
	
	/* Update */	IDENTIFIABLE update(IDENTIFIABLE object);
	
	/* Delete */	IDENTIFIABLE delete(IDENTIFIABLE object);

}
