package org.cyk.system.root.persistence.api;

import org.cyk.system.root.model.Identifiable;

public interface TypedPersistenceService<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends PersistenceService<IDENTIFIABLE,IDENTIFIER> {
		
	/* Create */	IDENTIFIABLE create(IDENTIFIABLE object);
	
	/* Read */		IDENTIFIABLE read(IDENTIFIER identifier);
	
	/* Update */	IDENTIFIABLE update(IDENTIFIABLE object);
	
	/* Delete */	IDENTIFIABLE delete(IDENTIFIABLE object);

}
