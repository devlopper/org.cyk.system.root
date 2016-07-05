package org.cyk.system.root.business.api;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractBean;

@Deprecated
public class BusinessServiceAdapter extends AbstractBean implements BusinessServiceListener {

	private static final long serialVersionUID = 5130185664261469119L;

	@Override public void crudDone(Crud crud, AbstractIdentifiable identifiable) {}
	
}