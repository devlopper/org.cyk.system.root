package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.cyk.system.root.business.api.RudBusiness;
import org.cyk.system.root.model.Rud;
import org.cyk.utility.common.cdi.AbstractBean;

public class RudBusinessImpl extends AbstractBean implements RudBusiness,Serializable {

	private static final long serialVersionUID = 1L;

	public Boolean isReadable(Rud rud) {
		return rud.getReadable()==null || rud.getReadable();
	}

	public Boolean isUpdatable(Rud rud) {
		return rud.getUpdatable()==null || rud.getUpdatable();
	}

	public Boolean isDeletable(Rud rud) {
		return rud.getDeletable()==null || rud.getDeletable();
	}
	
}
