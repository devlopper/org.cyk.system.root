package org.cyk.system.root.business.api;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface TypedBusiness<IDENTIFIABLE extends AbstractIdentifiable> extends BusinessService<IDENTIFIABLE, Long> {

	/* predefined query  */
	
    IDENTIFIABLE load(Long identifier);
	
    void load(IDENTIFIABLE identifiable);
    
    Collection<IDENTIFIABLE> findAll(); 
    
    Long countAll();
    
    Collection<IDENTIFIABLE> findAllExclude(Collection<IDENTIFIABLE> identifiables); 
    
    Long countAllExclude(Collection<IDENTIFIABLE> identifiables); 
}
