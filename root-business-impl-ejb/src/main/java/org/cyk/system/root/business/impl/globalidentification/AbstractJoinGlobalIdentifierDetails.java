package org.cyk.system.root.business.impl.globalidentification;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.Input.RendererStrategy;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public abstract class AbstractJoinGlobalIdentifierDetails<IDENTIFIABLE extends AbstractJoinGlobalIdentifier> extends AbstractOutputDetails<IDENTIFIABLE> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input(rendererStrategy=RendererStrategy.ADMINISTRATION) @InputText protected String identifiableGlobalIdentifier;
	
	public AbstractJoinGlobalIdentifierDetails(IDENTIFIABLE identifiable) {
		super(identifiable);
		identifiableGlobalIdentifier = identifiable.getIdentifiableGlobalIdentifier().getIdentifier();
	}
	
	public static final String FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER = "identifiableGlobalIdentifier";
}