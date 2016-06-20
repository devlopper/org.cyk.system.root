package org.cyk.system.root.business.api.mathematics.machine;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;

public interface FiniteStateMachineStateBusiness extends AbstractEnumerationBusiness<FiniteStateMachineState> {

	FiniteStateMachineState findByFromStateByAlphabet(FiniteStateMachineState fromState,FiniteStateMachineAlphabet alphabet);
	
	Collection<FiniteStateMachineState> findByMachine(FiniteStateMachine machine);
	Collection<FiniteStateMachineState> findFromByMachineByAlphabet(FiniteStateMachine machine,FiniteStateMachineAlphabet alphabet);
	
}
