package org.cyk.system.root.business.api;

import org.cyk.system.root.model.security.Installation;

public interface BusinessLayerListener {

	void beforeInstall(BusinessLayer businessLayer,Installation installation);
	void afterInstall(BusinessLayer businessLayer,Installation installation);
	
	void handleObjectToInstall(BusinessLayer businessLayer,Object object);
}
