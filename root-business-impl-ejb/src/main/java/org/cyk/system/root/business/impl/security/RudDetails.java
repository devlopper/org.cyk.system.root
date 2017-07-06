package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.cyk.system.root.business.api.RudBusiness;
import org.cyk.system.root.business.impl.AbstractModelElementOutputDetails;
import org.cyk.system.root.model.Rud;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RudDetails extends AbstractModelElementOutputDetails<Rud> implements Serializable {

	private static final long serialVersionUID = 1307822857551633645L;

	@Input @InputText private String readable,updatable,deletable;
	
	public RudDetails(Rud rud) {
		super(rud);	
	}
	
	@Override
	public void setMaster(Rud rud) {
		super.setMaster(rud);
		this.readable = formatResponse(inject(RudBusiness.class).isReadable(rud));
		this.updatable = formatResponse(inject(RudBusiness.class).isUpdatable(rud));
		this.deletable = formatResponse(inject(RudBusiness.class).isDeletable(rud));
	}
}
