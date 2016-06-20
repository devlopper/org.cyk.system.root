package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineStateBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineTransitionDao;

public class FiniteStateMachineStateBusinessImpl extends AbstractEnumerationBusinessImpl<FiniteStateMachineState, FiniteStateMachineStateDao> implements FiniteStateMachineStateBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private FiniteStateMachineTransitionDao transitionDao;
	
	@Inject
	public FiniteStateMachineStateBusinessImpl(FiniteStateMachineStateDao dao) {
		super(dao); 
	}
	/*
	@Override
	public FiniteStateMachineState create(FiniteStateMachineState finiteStateMachineState,Person person) {
		createFiniteStateMachineStateLog(finiteStateMachineState,person);
		return super.create(finiteStateMachineState);
	}
	
	@Override
	public void create(Collection<FiniteStateMachineState> finiteStateMachineStates,Person person) {
		for(FiniteStateMachineState finiteStateMachineState : finiteStateMachineStates)
			finiteStateMachineState.set
	}
	
	@Override
	public FiniteStateMachineState update(FiniteStateMachineState finiteStateMachineState,Person person) {
		createFiniteStateMachineStateLog(finiteStateMachineState,person);
		return super.update(finiteStateMachineState);
	}
	
	private void createFiniteStateMachineStateLog(FiniteStateMachineState finiteStateMachineState,Person person){
		FiniteStateMachineStateLog finiteStateMachineStateLog = new FiniteStateMachineStateLog();
		finiteStateMachineStateLog.setState(finiteStateMachineState);
		finiteStateMachineStateLog.setPerson(person);
		finiteStateMachineStateLog.setDate(timeBusiness.findUniversalTimeCoordinated());
		stateLogBusiness.create(finiteStateMachineStateLog);
	}*/

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public FiniteStateMachineState findByFromStateByAlphabet(FiniteStateMachineState fromState,FiniteStateMachineAlphabet alphabet) {
		return transitionDao.readByFromStateByAlphabet(fromState, alphabet).getToState();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<FiniteStateMachineState> findByMachine(FiniteStateMachine machine) {
		return dao.readByMachine(machine);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<FiniteStateMachineState> findFromByMachineByAlphabet(FiniteStateMachine machine, FiniteStateMachineAlphabet alphabet) {
		return dao.readFromByMachineByAlphabet(machine, alphabet);
	}
}
