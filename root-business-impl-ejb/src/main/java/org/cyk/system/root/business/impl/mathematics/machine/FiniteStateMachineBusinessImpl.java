package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineFinalState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineTransition;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineAlphabetDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineFinalStateDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineTransitionDao;
import org.cyk.utility.common.Constant;

@Stateless
public class FiniteStateMachineBusinessImpl extends AbstractEnumerationBusinessImpl<FiniteStateMachine, FiniteStateMachineDao> implements FiniteStateMachineBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private FiniteStateMachineAlphabetDao alphabetDao;
	@Inject private FiniteStateMachineStateDao stateDao;
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
	
	@Override 
	public FiniteStateMachine clone(FiniteStateMachine machineModel, String machineCode) {
		FiniteStateMachine machine = new FiniteStateMachine();
		machine.setCode(machineCode);
		machine.setName(machineModel.getName());
		create(machine);
		Map<Long, Long> identifierMap = new HashMap<>();
		for(FiniteStateMachineAlphabet alphabetModel : alphabetDao.readByMachine(machineModel)){
			FiniteStateMachineAlphabet alphabet = new FiniteStateMachineAlphabet();
			alphabet.setCode(machine.getCode()+Constant.CHARACTER_UNDESCORE+computeCode(alphabetModel.getName()));
			alphabet.setName(alphabetModel.getName());
			alphabet.setMachine(machine);
			alphabetDao.create(alphabet);
			identifierMap.put(alphabetModel.getIdentifier(), alphabet.getIdentifier());
		}
		for(FiniteStateMachineState stateModel : stateDao.readByMachine(machineModel)){
			FiniteStateMachineState state = new FiniteStateMachineState();
			state.setCode(machine.getCode()+Constant.CHARACTER_UNDESCORE+computeCode(stateModel.getName()));
			state.setName(stateModel.getName());
			state.setMachine(machine);
			stateDao.create(state);
			identifierMap.put(stateModel.getIdentifier(), state.getIdentifier());
		}
		for(FiniteStateMachineTransition transitionModel : transitionDao.readByMachine(machineModel)){
			FiniteStateMachineTransition transition = new FiniteStateMachineTransition();
			transition.setFromState(stateDao.read(identifierMap.get(transitionModel.getFromState().getIdentifier())));
			transition.setAlphabet(alphabetDao.read(identifierMap.get(transitionModel.getAlphabet().getIdentifier())));
			transition.setToState(stateDao.read(identifierMap.get(transitionModel.getToState().getIdentifier())));
			transitionDao.create(transition);
		}
		machine.setInitialState(stateDao.read(identifierMap.get(machineModel.getInitialState().getIdentifier())));
		machine.setCurrentState(stateDao.read(identifierMap.get(machineModel.getCurrentState().getIdentifier())));
		update(machine);
		return machine;
	}
	
}
