package org.cyk.system.root.persistence.impl.mathematics.machine;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineFinalState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineFinalStateDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class FiniteStateMachineFinalStateDaoImpl extends AbstractTypedDao<FiniteStateMachineFinalState> implements FiniteStateMachineFinalStateDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readByMachine,readByState;

	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByMachine, _select().where(commonUtils.attributePath(FiniteStateMachineFinalState.FIELD_STATE, FiniteStateMachineState.FIELD_MACHINE)
				,FiniteStateMachineState.FIELD_MACHINE ));
		registerNamedQuery(readByState, _select().where(FiniteStateMachineFinalState.FIELD_STATE));
	}
	
    @Override
    public Collection<FiniteStateMachineFinalState> readByMachine(FiniteStateMachine machine) {
    	return namedQuery(readByMachine).parameter(FiniteStateMachineState.FIELD_MACHINE, machine).resultMany();
    }

	@Override
	public FiniteStateMachineFinalState readByState(FiniteStateMachineState state) {
		return namedQuery(readByState).parameter(FiniteStateMachineFinalState.FIELD_STATE, state).ignoreThrowable(NoResultException.class).resultOne();
	}
   

}
 