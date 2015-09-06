package org.cyk.system.root.persistence.api;

import org.cyk.system.root.model.Identifiable;

public interface GenericPersistenceService<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends PersistenceService<IDENTIFIABLE,IDENTIFIER> {

	/* ------------------------ Static methods ---------------------------- */
	
    IDENTIFIABLE refresh(IDENTIFIABLE identifiable);
	
	/* ------------------------ Dynamic methods ---------------------------- */
	
	/* point to */	GenericPersistenceService<IDENTIFIABLE,IDENTIFIER> use(Class<? extends IDENTIFIABLE> aClass);
	
}
