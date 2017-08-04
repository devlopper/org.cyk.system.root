package org.cyk.system.root.business.api.mathematics.machine;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;

public interface FiniteStateMachineAlphabetBusiness extends AbstractEnumerationBusiness<FiniteStateMachineAlphabet> {

	Collection<FiniteStateMachineAlphabet> findByMachine(FiniteStateMachine machine);
	
}
