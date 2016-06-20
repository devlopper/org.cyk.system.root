package org.cyk.system.root.business.api.mathematics.machine;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.system.root.model.party.Party;

public interface FiniteStateMachineStateLogBusiness extends TypedBusiness<FiniteStateMachineStateLog> {

	void create(AbstractIdentifiable identifiable,FiniteStateMachineState state,Party party);
	void create(Collection<AbstractIdentifiable> identifiables,FiniteStateMachineState state,Party party);
	
}
