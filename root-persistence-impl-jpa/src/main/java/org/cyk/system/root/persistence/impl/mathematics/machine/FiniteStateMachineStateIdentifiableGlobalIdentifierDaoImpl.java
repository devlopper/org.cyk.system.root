package org.cyk.system.root.persistence.impl.mathematics.machine;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.globalidentification.AbstractJoinGlobalIdentifierDaoImpl;

public class FiniteStateMachineStateIdentifiableGlobalIdentifierDaoImpl extends AbstractJoinGlobalIdentifierDaoImpl<FiniteStateMachineStateIdentifiableGlobalIdentifier,FiniteStateMachineStateIdentifiableGlobalIdentifier.SearchCriteria> implements FiniteStateMachineStateIdentifiableGlobalIdentifierDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	/*
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByCriteria, "SELECT r FROM FiniteStateMachineStateLog r WHERE r.state.identifier IN :identifiers");
	}*/
	
}
 