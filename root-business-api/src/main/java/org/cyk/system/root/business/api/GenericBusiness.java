package org.cyk.system.root.business.api;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface GenericBusiness extends AbstractGenericBusinessService<AbstractIdentifiable, Long> {
	
    void flushEntityManager();
    
}
