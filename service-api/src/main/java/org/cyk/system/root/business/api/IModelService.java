package org.cyk.system.root.business.api;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface IModelService<IDENTIFIABLE extends AbstractIdentifiable> extends BusinessService<IDENTIFIABLE, Long> {

	/* Create */	IDENTIFIABLE create(IDENTIFIABLE object);
	
	/* Read */ 		IDENTIFIABLE read(Long identifier);
	
	/* Update */	IDENTIFIABLE update(IDENTIFIABLE object);
	
	/* Delete */	IDENTIFIABLE delete(IDENTIFIABLE object);	
	
}
