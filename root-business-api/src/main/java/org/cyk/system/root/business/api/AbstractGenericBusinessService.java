package org.cyk.system.root.business.api;

import org.cyk.system.root.model.Identifiable;

public interface AbstractGenericBusinessService<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends BusinessService<IDENTIFIABLE,IDENTIFIER> {

	/* ------------------------ Static methods ---------------------------- */
	
	/* ------------------------ Dynamic methods ---------------------------- */
	
	/* point to */	AbstractGenericBusinessService<IDENTIFIABLE,IDENTIFIER> use(Class<? extends IDENTIFIABLE> aClass);
	
}

