package org.cyk.system.root.persistence.api.mathematics.machine;

import java.util.Collection;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineFinalState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.persistence.api.TypedDao;

public interface FiniteStateMachineFinalStateDao extends TypedDao<FiniteStateMachineFinalState> {

	Collection<FiniteStateMachineFinalState> readByMachine(FiniteStateMachine machine);

	FiniteStateMachineFinalState readByState(FiniteStateMachineState state);
	
}
