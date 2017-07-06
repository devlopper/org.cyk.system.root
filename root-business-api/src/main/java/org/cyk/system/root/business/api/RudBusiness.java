package org.cyk.system.root.business.api;

import org.cyk.system.root.model.Rud;

public interface RudBusiness {

	Boolean isReadable(Rud rud);
	Boolean isUpdatable(Rud rud);
	Boolean isDeletable(Rud rud);
	
}
