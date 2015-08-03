package org.cyk.system.root.business.api;

import java.io.Serializable;

import org.cyk.system.root.model.security.Installation;
import org.cyk.utility.common.cdi.AbstractBean;

public class BusinessLayerListenerAdapter extends AbstractBean implements Serializable, BusinessLayerListener {

	private static final long serialVersionUID = -3142367274228861058L;

	@Override
	public void beforeInstall(BusinessLayer businessLayer, Installation installation) {
		
	}

	@Override
	public void afterInstall(BusinessLayer businessLayer, Installation installation) {
		
	}

	@Override
	public void handleObjectToInstall(BusinessLayer businessLayer,Object object) {
		
	}

}
