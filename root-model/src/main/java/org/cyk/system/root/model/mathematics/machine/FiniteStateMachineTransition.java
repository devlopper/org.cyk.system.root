package org.cyk.system.root.model.mathematics.machine;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @Entity
public class FiniteStateMachineTransition extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2576023570217657424L;

	@ManyToOne @NotNull private FiniteStateMachineState fromState;
	
	@ManyToOne @NotNull private FiniteStateMachineAlphabet alphabet;
	
	@ManyToOne @NotNull private FiniteStateMachineState toState;
	
	/**/
	
	public static final String FIELD_FROM_STATE = "fromState";
	public static final String FIELD_ALPHABET = "alphabet";
	public static final String FIELD_TO_STATE = "toState";
}
