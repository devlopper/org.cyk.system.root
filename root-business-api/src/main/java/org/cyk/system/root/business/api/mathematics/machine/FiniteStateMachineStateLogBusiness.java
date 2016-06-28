package org.cyk.system.root.business.api.mathematics.machine;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog.IdentifiablesSearchCriteria;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog.SearchCriteria;

public interface FiniteStateMachineStateLogBusiness extends TypedBusiness<FiniteStateMachineStateLog> {

	void create(AbstractIdentifiable identifiable,FiniteStateMachineState state);
	void create(Collection<AbstractIdentifiable> identifiables,FiniteStateMachineState state);
	
	Collection<FiniteStateMachineStateLog> findByCriteria(SearchCriteria searchCriteria);
	Long countByCriteria(SearchCriteria searchCriteria);
	
	<IDENTIFIABLE extends AbstractIdentifiable> Collection<IDENTIFIABLE> findIdentifiablesByCriteria(IdentifiablesSearchCriteria<IDENTIFIABLE> criteria);
	<IDENTIFIABLE extends AbstractIdentifiable> Long countIdentifiablesByCriteria(IdentifiablesSearchCriteria<IDENTIFIABLE> criteria);
	
}
