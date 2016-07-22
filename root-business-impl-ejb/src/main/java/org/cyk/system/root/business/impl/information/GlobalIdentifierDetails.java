package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class GlobalIdentifierDetails<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractOutputDetails<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 1708181273704661027L;

	@Input @InputText private String creationDate,createdBy,readable,updatable,deletable;
	
	public GlobalIdentifierDetails(IDENTIFIABLE identifiable) {
		super(identifiable);
		this.creationDate = formatDateTime(identifiable.getGlobalIdentifier().getCreationDate());
		this.createdBy = formatUsingBusiness(identifiable.getGlobalIdentifier().getCreatedBy());
		
		this.readable = formatResponse(rootBusinessLayer.getGlobalIdentifierBusiness().isReadable(identifiable));
		this.updatable = formatResponse(rootBusinessLayer.getGlobalIdentifierBusiness().isUpdatable(identifiable));
		this.deletable = formatResponse(rootBusinessLayer.getGlobalIdentifierBusiness().isDeletable(identifiable));
	}

}
