package org.cyk.system.root.persistence.impl.mathematics.machine;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public class FiniteStateMachineStateDaoImpl extends AbstractEnumerationDaoImpl<FiniteStateMachineState> implements FiniteStateMachineStateDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readByMachine;

	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByMachine, _select().where(FiniteStateMachineState.FIELD_MACHINE));
	}
	
    @Override
    public Collection<FiniteStateMachineState> readByMachine(FiniteStateMachine machine) {
    	return namedQuery(readByMachine).parameter(FiniteStateMachineState.FIELD_MACHINE, machine).resultMany();
    }
    
   

}
 