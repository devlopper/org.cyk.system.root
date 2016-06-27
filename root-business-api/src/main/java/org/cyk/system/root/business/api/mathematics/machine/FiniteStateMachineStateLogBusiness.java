package org.cyk.system.root.business.api.mathematics.machine;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;

public interface FiniteStateMachineStateLogBusiness extends TypedBusiness<FiniteStateMachineStateLog> {

	void create(AbstractIdentifiable identifiable,FiniteStateMachineState state);
	void create(Collection<AbstractIdentifiable> identifiables,FiniteStateMachineState state);
	
	<T extends AbstractIdentifiable> Collection<T> findByClass(Collection<FiniteStateMachineStateLog> finiteStateMachineStateLogs,Class<T> aClass);
	
}
