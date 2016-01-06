package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineStateBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateDao;

public class FiniteStateMachineStateBusinessImpl extends AbstractEnumerationBusinessImpl<FiniteStateMachineState, FiniteStateMachineStateDao> implements FiniteStateMachineStateBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public FiniteStateMachineStateBusinessImpl(FiniteStateMachineStateDao dao) {
		super(dao); 
	}
	
	
}
