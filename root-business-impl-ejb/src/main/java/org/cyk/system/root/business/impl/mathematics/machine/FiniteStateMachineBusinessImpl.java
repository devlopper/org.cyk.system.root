package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineFinalState;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineFinalStateDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineTransitionDao;

@Stateless
public class FiniteStateMachineBusinessImpl extends AbstractEnumerationBusinessImpl<FiniteStateMachine, FiniteStateMachineDao> implements FiniteStateMachineBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private FiniteStateMachineFinalStateDao finalStateDao;
	@Inject private FiniteStateMachineTransitionDao transitionDao;
	
	@Inject
	public FiniteStateMachineBusinessImpl(FiniteStateMachineDao dao) {
		super(dao); 
	}
	
	@Override
	public void read(FiniteStateMachine machine,FiniteStateMachineAlphabet alphabet) {
		logIdentifiable("Reading", machine);
		logTrace("Alphabet={}",alphabet.getCode());
		exceptionUtils().exception(!machine.equals(alphabet.getMachine()), "exception.machine.notequals");
		Collection<FiniteStateMachineFinalState> finalStates = finalStateDao.readByMachine(machine);
		exceptionUtils().exception(finalStates.contains(machine.getCode()), "exception.machine.state.final.cannotread");
		machine.setCurrentState(transitionDao.readByFromStateByAlphabet(machine.getCurrentState(), alphabet).getToState());
		dao.update(machine);
		logIdentifiable("Read", machine);
	}
	
}
