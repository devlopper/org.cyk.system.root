package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineFinalStateBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineFinalState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineFinalStateDao;

public class FiniteStateMachineFinalStateBusinessImpl extends AbstractTypedBusinessService<FiniteStateMachineFinalState, FiniteStateMachineFinalStateDao> implements FiniteStateMachineFinalStateBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public FiniteStateMachineFinalStateBusinessImpl(FiniteStateMachineFinalStateDao dao) {
		super(dao); 
	}

	@Override
	public FiniteStateMachineFinalState findByState(FiniteStateMachineState state) {
		return dao.readByState(state);
	}
	
	
}
