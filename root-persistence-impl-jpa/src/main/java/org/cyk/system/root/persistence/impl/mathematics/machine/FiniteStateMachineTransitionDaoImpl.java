package org.cyk.system.root.persistence.impl.mathematics.machine;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineTransition;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineTransitionDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class FiniteStateMachineTransitionDaoImpl extends AbstractTypedDao<FiniteStateMachineTransition> implements FiniteStateMachineTransitionDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByFromStateByAlphabet;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByFromStateByAlphabet, _select().where(FiniteStateMachineTransition.FIELD_FROM_STATE).and(FiniteStateMachineTransition.FIELD_ALPHABET));
	}
	
	@Override
	public FiniteStateMachineTransition readByFromStateByAlphabet(FiniteStateMachineState fromState, FiniteStateMachineAlphabet alphabet) {
		return namedQuery(readByFromStateByAlphabet).parameter(FiniteStateMachineTransition.FIELD_FROM_STATE, fromState)
				.parameter(FiniteStateMachineTransition.FIELD_ALPHABET, alphabet).resultOne();
	}

}
 