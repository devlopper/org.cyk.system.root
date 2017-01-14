package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;

import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierDetails;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateIdentifiableGlobalIdentifier;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FiniteStateMachineStateIdentifiableGlobalIdentifierDetails extends AbstractJoinGlobalIdentifierDetails<FiniteStateMachineStateIdentifiableGlobalIdentifier> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText protected String finiteStateMachineState;
	
	public FiniteStateMachineStateIdentifiableGlobalIdentifierDetails(FiniteStateMachineStateIdentifiableGlobalIdentifier finiteStateMachineStateIdentifiableGlobalIdentifier) {
		super(finiteStateMachineStateIdentifiableGlobalIdentifier);
		finiteStateMachineState = formatUsingBusiness(finiteStateMachineStateIdentifiableGlobalIdentifier.getFiniteStateMachineState());
	}
	
	public static final String FIELD_FINITE_STATE_MACHINE_STATE = "finiteStateMachineState";
	
}