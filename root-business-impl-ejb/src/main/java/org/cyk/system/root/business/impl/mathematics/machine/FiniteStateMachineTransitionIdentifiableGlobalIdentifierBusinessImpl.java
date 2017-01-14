package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineTransitionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineTransitionIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineTransitionIdentifiableGlobalIdentifierDao;

public class FiniteStateMachineTransitionIdentifiableGlobalIdentifierBusinessImpl extends AbstractTypedBusinessService<FiniteStateMachineTransitionIdentifiableGlobalIdentifier, FiniteStateMachineTransitionIdentifiableGlobalIdentifierDao> implements FiniteStateMachineTransitionIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public FiniteStateMachineTransitionIdentifiableGlobalIdentifierBusinessImpl(FiniteStateMachineTransitionIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}

}
