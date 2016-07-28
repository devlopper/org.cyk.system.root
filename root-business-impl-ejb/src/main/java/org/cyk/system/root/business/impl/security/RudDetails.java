package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.globalidentification.GlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.Rud;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class RudDetails implements Serializable {

	private static final long serialVersionUID = 1307822857551633645L;

	@Input @InputText private String readable,updatable,deletable;
	
	public RudDetails(Rud rud) {
		this.readable = RootBusinessLayer.getInstance().getLanguageBusiness().findResponseText(GlobalIdentifierBusinessImpl.isReadable(rud));
		this.updatable = RootBusinessLayer.getInstance().getLanguageBusiness().findResponseText(GlobalIdentifierBusinessImpl.isUpdatable(rud));
		this.deletable = RootBusinessLayer.getInstance().getLanguageBusiness().findResponseText(GlobalIdentifierBusinessImpl.isDeletable(rud));
	}
}
