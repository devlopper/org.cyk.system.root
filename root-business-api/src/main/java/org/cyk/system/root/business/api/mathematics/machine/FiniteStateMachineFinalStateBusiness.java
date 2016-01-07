package org.cyk.system.root.business.api.mathematics.machine;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineFinalState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;

public interface FiniteStateMachineFinalStateBusiness extends TypedBusiness<FiniteStateMachineFinalState> {

	FiniteStateMachineFinalState findByState(FiniteStateMachineState state);
	
}
