package org.cyk.system.root.business.api.mathematics.machine;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;

public interface FiniteStateMachineBusiness extends AbstractEnumerationBusiness<FiniteStateMachine> {

	//FiniteStateMachineState findState(FiniteStateMachine machine,FiniteStateMachineState currentState,FiniteStateMachineAlphabet alphabet);
	
	void read(FiniteStateMachine machine,FiniteStateMachineAlphabet alphabet);
	
	//will be used when implemented on typed business
	//FiniteStateMachine clone(FiniteStateMachine machineModel,String machineCode);
	
}
