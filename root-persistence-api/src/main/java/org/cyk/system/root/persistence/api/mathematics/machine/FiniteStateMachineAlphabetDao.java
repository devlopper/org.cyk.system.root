package org.cyk.system.root.persistence.api.mathematics.machine;

import java.util.Collection;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;

public interface FiniteStateMachineAlphabetDao extends AbstractEnumerationDao<FiniteStateMachineAlphabet> {

	Collection<FiniteStateMachineAlphabet> readByMachine(FiniteStateMachine machine);
	
}
