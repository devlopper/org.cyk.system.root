package org.cyk.system.root.persistence.impl.mathematics.machine;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineAlphabetDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public class FiniteStateMachineAlphabetDaoImpl extends AbstractEnumerationDaoImpl<FiniteStateMachineAlphabet> implements FiniteStateMachineAlphabetDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByMachine;

	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByMachine, _select().where(FiniteStateMachineAlphabet.FIELD_MACHINE));
	}
	
    @Override
    public Collection<FiniteStateMachineAlphabet> readByMachine(FiniteStateMachine machine) {
    	return namedQuery(readByMachine).parameter(FiniteStateMachineAlphabet.FIELD_MACHINE, machine).resultMany();
    }
}
 