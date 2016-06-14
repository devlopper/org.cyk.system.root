package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineAlphabetBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineAlphabetDao;

public class FiniteStateMachineAlphabetBusinessImpl extends AbstractEnumerationBusinessImpl<FiniteStateMachineAlphabet, FiniteStateMachineAlphabetDao> implements FiniteStateMachineAlphabetBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public FiniteStateMachineAlphabetBusinessImpl(FiniteStateMachineAlphabetDao dao) {
		super(dao); 
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<FiniteStateMachineAlphabet> findByMachine(FiniteStateMachine machine) {
		return dao.readByMachine(machine);
	}
	
	
}
