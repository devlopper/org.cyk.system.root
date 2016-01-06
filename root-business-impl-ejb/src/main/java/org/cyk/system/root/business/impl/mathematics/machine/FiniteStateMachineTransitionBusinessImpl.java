package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineTransitionBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineTransition;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineTransitionDao;

public class FiniteStateMachineTransitionBusinessImpl extends AbstractTypedBusinessService<FiniteStateMachineTransition, FiniteStateMachineTransitionDao> implements FiniteStateMachineTransitionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public FiniteStateMachineTransitionBusinessImpl(FiniteStateMachineTransitionDao dao) {
		super(dao); 
	}

	
	
	
}
