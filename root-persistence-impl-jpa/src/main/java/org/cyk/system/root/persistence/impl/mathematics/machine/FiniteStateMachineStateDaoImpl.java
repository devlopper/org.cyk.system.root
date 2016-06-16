package org.cyk.system.root.persistence.impl.mathematics.machine;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineTransition;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public class FiniteStateMachineStateDaoImpl extends AbstractEnumerationDaoImpl<FiniteStateMachineState> implements FiniteStateMachineStateDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readByMachine,readFromByMachineByAlphabet;

	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByMachine, _select().where(FiniteStateMachineState.FIELD_MACHINE));
		//registerNamedQuery(readFromByMachineByAlphabet, _select().where(FiniteStateMachineState.FIELD_MACHINE).and()
		//		.openSubQueryStringBuilder(FiniteStateMachineTransition.class).and(FiniteStateMachineTransition.FIELD_ALPHABET));
		registerNamedQuery(readFromByMachineByAlphabet, "SELECT r1 FROM FiniteStateMachineState r1 WHERE r1.machine = :machine AND EXISTS("
				+ " SELECT r2 FROM FiniteStateMachineTransition r2 WHERE r2.fromState = r1 AND r2.alphabet = :alphabet "
				+ ")");
	}
	
    @Override
    public Collection<FiniteStateMachineState> readByMachine(FiniteStateMachine machine) {
    	return namedQuery(readByMachine).parameter(FiniteStateMachineState.FIELD_MACHINE, machine).resultMany();
    }

	@Override
	public Collection<FiniteStateMachineState> readFromByMachineByAlphabet(FiniteStateMachine machine, FiniteStateMachineAlphabet alphabet) {
		return namedQuery(readFromByMachineByAlphabet).parameter(FiniteStateMachineState.FIELD_MACHINE, machine)
				.parameter(FiniteStateMachineTransition.FIELD_ALPHABET, alphabet).resultMany();
	}
    
   

}
 