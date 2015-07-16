package org.cyk.system.root.business.api;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface BusinessServiceListener {

	void crudDone(Crud crud,AbstractIdentifiable identifiable);

	/**/
	
	Collection<BusinessServiceListener> COLLECTION = new ArrayList<>();
	
}
