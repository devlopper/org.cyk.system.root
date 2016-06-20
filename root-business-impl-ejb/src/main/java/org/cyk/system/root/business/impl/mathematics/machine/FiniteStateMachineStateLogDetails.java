package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractLogDetails;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class FiniteStateMachineStateLogDetails extends AbstractLogDetails<FiniteStateMachineStateLog> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText protected String identifier,state;
	
	public FiniteStateMachineStateLogDetails(FiniteStateMachineStateLog finiteStateMachineStateLog) {
		super(finiteStateMachineStateLog);
		identifier = finiteStateMachineStateLog.getIdentifiableGlobalIdentifier().getIdentifier();
		state = formatUsingBusiness(finiteStateMachineStateLog.getState());
	}
	
}