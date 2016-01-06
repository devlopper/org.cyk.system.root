package org.cyk.system.root.persistence.api.mathematics.machine;

import java.util.Collection;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;

public interface FiniteStateMachineStateDao extends AbstractEnumerationDao<FiniteStateMachineState> {

	Collection<FiniteStateMachineState> readByMachine(FiniteStateMachine machine);
}
