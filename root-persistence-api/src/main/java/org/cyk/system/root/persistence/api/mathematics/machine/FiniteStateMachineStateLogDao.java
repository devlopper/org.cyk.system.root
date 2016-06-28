package org.cyk.system.root.persistence.api.mathematics.machine;

import java.util.Collection;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog.SearchCriteria;
import org.cyk.system.root.persistence.api.TypedDao;

public interface FiniteStateMachineStateLogDao extends TypedDao<FiniteStateMachineStateLog> {

	Collection<FiniteStateMachineStateLog> readByCriteria(SearchCriteria searchCriteria);
	Long countByCriteria(SearchCriteria searchCriteria);
	
}
