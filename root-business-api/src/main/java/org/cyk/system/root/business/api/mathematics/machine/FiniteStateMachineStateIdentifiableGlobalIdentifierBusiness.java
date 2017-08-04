package org.cyk.system.root.business.api.mathematics.machine;

import java.util.Collection;

import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateIdentifiableGlobalIdentifier.IdentifiablesSearchCriteria;

public interface FiniteStateMachineStateIdentifiableGlobalIdentifierBusiness extends JoinGlobalIdentifierBusiness<FiniteStateMachineStateIdentifiableGlobalIdentifier,FiniteStateMachineStateIdentifiableGlobalIdentifier.SearchCriteria> {

	FiniteStateMachineStateIdentifiableGlobalIdentifier create(AbstractIdentifiable identifiable,FiniteStateMachineState state);
	Collection<FiniteStateMachineStateIdentifiableGlobalIdentifier> create(Collection<AbstractIdentifiable> identifiables,FiniteStateMachineState state);
	
	<IDENTIFIABLE extends AbstractIdentifiable> Collection<IDENTIFIABLE> findIdentifiablesByCriteria(IdentifiablesSearchCriteria<IDENTIFIABLE> criteria);
	<IDENTIFIABLE extends AbstractIdentifiable> Long countIdentifiablesByCriteria(IdentifiablesSearchCriteria<IDENTIFIABLE> criteria);
	
}
