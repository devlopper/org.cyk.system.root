package org.cyk.system.root.persistence.impl.mathematics.machine;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog.SearchCriteria;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateLogDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;

public class FiniteStateMachineStateLogDaoImpl extends AbstractTypedDao<FiniteStateMachineStateLog> implements FiniteStateMachineStateLogDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByCriteria, "SELECT r FROM FiniteStateMachineStateLog r WHERE r.state.identifier IN :identifiers");
	}
	
	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameter("identifiers", ids(((SearchCriteria)searchCriteria).getFiniteStateMachineStates()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<FiniteStateMachineStateLog> readByCriteria(SearchCriteria criteria) {
		QueryWrapper<?> queryWrapper = namedQuery(readByCriteria);
		applySearchCriteriaParameters(queryWrapper, criteria);
		return (Collection<FiniteStateMachineStateLog>) queryWrapper.resultMany();
	}

	@Override
	public Long countByCriteria(SearchCriteria searchCriteria) {
		QueryWrapper<?> queryWrapper = countNamedQuery(countByCriteria);
		applySearchCriteriaParameters(queryWrapper, searchCriteria);
		return (Long) queryWrapper.resultOne();
	}

	

	
}
 