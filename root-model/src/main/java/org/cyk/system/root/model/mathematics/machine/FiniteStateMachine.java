package org.cyk.system.root.model.mathematics.machine;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.Constant;

@Getter @Setter @Entity
public class FiniteStateMachine extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = 2576023570217657424L;

	@ManyToOne private FiniteStateMachineState initialState;
	
	@ManyToOne private FiniteStateMachineState currentState;
	
	@Override
	public String getLogMessage() {
		return String.format(DEBUG_FORMAT,code,initialState==null ? Constant.EMPTY_STRING:initialState.getCode()
				,currentState==null ? Constant.EMPTY_STRING:currentState.getCode());
	}
	
	private static final String DEBUG_FORMAT = "FiniteStateMachine(CODE=%s ISTATE=%s CSTATE=%s)";
	
	/**/
	
	public static final String FIELD_INITIAL_STATE = "initialState";
	public static final String FIELD_CURRENT_STATE = "currentState";
}
