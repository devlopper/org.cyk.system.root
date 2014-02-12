package org.cyk.system.root.service.api;

import org.cyk.system.root.model.AbstractModel;

public interface IModelService<IDENTIFIABLE extends AbstractModel> extends IServiceable<IDENTIFIABLE, Long> {

	/* Create */	IDENTIFIABLE create(IDENTIFIABLE object);
	
	/* Read */ 		IDENTIFIABLE read(Long identifier);
	
	/* Update */	IDENTIFIABLE update(IDENTIFIABLE object);
	
	/* Delete */	IDENTIFIABLE delete(IDENTIFIABLE object);	
	
}
