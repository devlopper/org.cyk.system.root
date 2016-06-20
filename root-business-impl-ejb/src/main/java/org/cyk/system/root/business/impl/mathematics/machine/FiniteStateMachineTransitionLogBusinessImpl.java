package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineTransitionLogBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineTransitionLog;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineTransitionLogDao;

public class FiniteStateMachineTransitionLogBusinessImpl extends AbstractTypedBusinessService<FiniteStateMachineTransitionLog, FiniteStateMachineTransitionLogDao> implements FiniteStateMachineTransitionLogBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public FiniteStateMachineTransitionLogBusinessImpl(FiniteStateMachineTransitionLogDao dao) {
		super(dao); 
	}

	
	
	
}
