package org.cyk.system.root.model.mathematics.machine;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractLog;

@Getter @Setter @Entity
public class FiniteStateMachineTransitionLog extends AbstractLog implements Serializable {

	private static final long serialVersionUID = 2576023570217657424L;

	@ManyToOne @NotNull private FiniteStateMachineTransition transition;
	
	/**/
	
	public static final String FIELD_TRANSITION = "transition";

}
