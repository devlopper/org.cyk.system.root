package org.cyk.system.root.business.api;

import org.cyk.system.root.model.Identifiable;

public interface AbstractGenericBusinessService<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends IdentifiableBusinessService<IDENTIFIABLE,IDENTIFIER> {

	/* ------------------------ Static methods ---------------------------- */
	/**
	 * Save identifiable in the system. If it does not exist then it is created
	 * else it is updated
	 * @param identifiable
	 * @return
	 */
    IDENTIFIABLE save(IDENTIFIABLE identifiable);
    @Deprecated
    <T extends IDENTIFIABLE> T load(Class<T> aClass,IDENTIFIER identifier);
    
    IDENTIFIABLE refresh(IDENTIFIABLE identifiable);
    
	/* ------------------------ Dynamic methods ---------------------------- */
	
	/* point to */	AbstractGenericBusinessService<IDENTIFIABLE,IDENTIFIER> use(Class<? extends IDENTIFIABLE> aClass);
	
}

