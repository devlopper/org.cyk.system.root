package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineStateLogBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.system.root.model.party.Party;
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
	public void create(AbstractIdentifiable identifiable,FiniteStateMachineState finiteStateMachineState, Party party) {
		create(Arrays.asList(identifiable),finiteStateMachineState,party);
	}

	@Override
	public void create(Collection<AbstractIdentifiable> identifiables,FiniteStateMachineState finiteStateMachineState, Party party) {
		Collection<FiniteStateMachineStateLog> finiteStateMachineStateLogs = new ArrayList<>();
		for(AbstractIdentifiable identifiable : identifiables){
			FiniteStateMachineStateLog finiteStateMachineStateLog = new FiniteStateMachineStateLog();
			finiteStateMachineStateLog.setState(finiteStateMachineState);
			finiteStateMachineStateLog.setParty(party);
			finiteStateMachineStateLog.setIdentifiableGlobalIdentifier(identifiable.getGlobalIdentifier());
			finiteStateMachineStateLogs.add(finiteStateMachineStateLog);
		}
		create(finiteStateMachineStateLogs);
	}
	
	
}
