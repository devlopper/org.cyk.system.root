package org.cyk.system.root.business.api.mathematics.machine;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.system.root.model.party.person.Person;

public interface FiniteStateMachineStateLogBusiness extends TypedBusiness<FiniteStateMachineStateLog> {

	void create(AbstractIdentifiable identifiable,FiniteStateMachineState state,Person person);
	void create(Collection<AbstractIdentifiable> identifiables,FiniteStateMachineState state,Person person);
	
}
