package org.cyk.system.root.persistence.api.mathematics.machine;

import java.util.Collection;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineTransition;
import org.cyk.system.root.persistence.api.TypedDao;

public interface FiniteStateMachineTransitionDao extends TypedDao<FiniteStateMachineTransition> {

	FiniteStateMachineTransition readByFromStateByAlphabet(FiniteStateMachineState state,FiniteStateMachineAlphabet alphabet);
	
	Collection<FiniteStateMachineTransition> readByMachine(FiniteStateMachine machine);
	
}
