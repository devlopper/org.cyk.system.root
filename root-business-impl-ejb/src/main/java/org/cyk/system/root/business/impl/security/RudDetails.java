package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.AbstractModelElementOutputDetails;
import org.cyk.system.root.business.impl.globalidentification.GlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.Rud;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class RudDetails extends AbstractModelElementOutputDetails<Rud> implements Serializable {

	private static final long serialVersionUID = 1307822857551633645L;

	@Input @InputText private String readable,updatable,deletable;
	
	public RudDetails(Rud rud) {
		super(rud);
		this.readable = inject(LanguageBusiness.class).findResponseText(GlobalIdentifierBusinessImpl.isReadable(rud));
		this.updatable = inject(LanguageBusiness.class).findResponseText(GlobalIdentifierBusinessImpl.isUpdatable(rud));
		this.deletable = inject(LanguageBusiness.class).findResponseText(GlobalIdentifierBusinessImpl.isDeletable(rud));
	}
}
