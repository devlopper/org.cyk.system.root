package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineStateLogBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.BusinessLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog.IdentifiablesSearchCriteria;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog.SearchCriteria;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateLogDao;

@Stateless
public class FiniteStateMachineStateLogBusinessImpl extends AbstractTypedBusinessService<FiniteStateMachineStateLog, FiniteStateMachineStateLogDao> implements FiniteStateMachineStateLogBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public FiniteStateMachineStateLogBusinessImpl(FiniteStateMachineStateLogDao dao) {
		super(dao); 
	}

	@Override
	public FiniteStateMachineStateLog create(FiniteStateMachineStateLog finiteStateMachineStateLog) {
		if(finiteStateMachineStateLog.getDate()==null)
			finiteStateMachineStateLog.setDate(timeBusiness.findUniversalTimeCoordinated());
		return super.create(finiteStateMachineStateLog);
	}

	@Override
	public void create(AbstractIdentifiable identifiable,FiniteStateMachineState finiteStateMachineState) {
		create(Arrays.asList(identifiable),finiteStateMachineState);
	}

	@Override
	public void create(Collection<AbstractIdentifiable> identifiables,FiniteStateMachineState finiteStateMachineState) {
		Collection<FiniteStateMachineStateLog> finiteStateMachineStateLogs = new ArrayList<>();
		for(AbstractIdentifiable identifiable : identifiables){
			FiniteStateMachineStateLog finiteStateMachineStateLog = new FiniteStateMachineStateLog();
			finiteStateMachineStateLog.setState(finiteStateMachineState);
			finiteStateMachineStateLog.setParty(finiteStateMachineState.getProcessingUser());
			finiteStateMachineStateLog.setDate(finiteStateMachineState.getProcessingDate());
			finiteStateMachineStateLog.setIdentifiableGlobalIdentifier(identifiable.getGlobalIdentifier());
			finiteStateMachineStateLogs.add(finiteStateMachineStateLog);
		}
		create(finiteStateMachineStateLogs);
	}

	@SuppressWarnings("unchecked")
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <IDENTIFIABLE extends AbstractIdentifiable> Collection<IDENTIFIABLE> findIdentifiablesByCriteria(IdentifiablesSearchCriteria<IDENTIFIABLE> criteria) {
		Collection<GlobalIdentifier> globalIdentifiers = new ArrayList<>();
		Collection<FiniteStateMachineStateLog> finiteStateMachineStateLogs = new ArrayList<>();
		if(criteria.getFiniteStateMachineStateLogs()!=null)
			finiteStateMachineStateLogs.addAll(criteria.getFiniteStateMachineStateLogs());
		finiteStateMachineStateLogs.addAll(dao.readByCriteria(criteria.getFiniteStateMachineStateLog()));	
		
		for(FiniteStateMachineStateLog finiteStateMachineStateLog : finiteStateMachineStateLogs)
			globalIdentifiers.add(finiteStateMachineStateLog.getIdentifiableGlobalIdentifier());
		return (Collection<IDENTIFIABLE>) BusinessLocator.getInstance().locate(criteria.getIdentifiableClass()).findByGlobalIdentifiers(globalIdentifiers);
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <IDENTIFIABLE extends AbstractIdentifiable> Long countIdentifiablesByCriteria(IdentifiablesSearchCriteria<IDENTIFIABLE> criteria) {
		return null;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<FiniteStateMachineStateLog> findByCriteria(SearchCriteria searchCriteria) {
		return dao.readByCriteria(searchCriteria);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByCriteria(SearchCriteria searchCriteria) {
		return dao.countByCriteria(searchCriteria);
	}
}
