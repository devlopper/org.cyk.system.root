package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractLogDetails;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class FiniteStateMachineStateLogDetails extends AbstractLogDetails<FiniteStateMachineStateLog> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText protected String identifiableGlobalIdentifier,state;
	
	public FiniteStateMachineStateLogDetails(FiniteStateMachineStateLog finiteStateMachineStateLog) {
		super(finiteStateMachineStateLog);
		identifiableGlobalIdentifier = finiteStateMachineStateLog.getIdentifiableGlobalIdentifier().getIdentifier();
		state = formatUsingBusiness(finiteStateMachineStateLog.getState());
	}
	
	public static final String FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER = "identifiableGlobalIdentifier";
	public static final String FIELD_STATE = "state";
	
}