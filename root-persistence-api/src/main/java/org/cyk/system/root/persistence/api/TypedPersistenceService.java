package org.cyk.system.root.persistence.api;

import java.util.Collection;

import org.cyk.system.root.model.Identifiable;

public interface TypedPersistenceService<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends PersistenceService<IDENTIFIABLE,IDENTIFIER> {
		
	/* predefined query  */
    
    Collection<IDENTIFIABLE> readAll(); 
    
    Long countAll();
	
}
