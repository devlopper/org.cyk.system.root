package org.cyk.system.root.model.mathematics.machine;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractLog;
import org.cyk.system.root.model.GlobalIdentifier;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public class FiniteStateMachineStateLog extends AbstractLog implements Serializable {

	private static final long serialVersionUID = 2576023570217657424L;

	@ManyToOne @NotNull private FiniteStateMachineState state;
	
	@ManyToOne private GlobalIdentifier identifiableGlobalIdentifier;
	
	/**/
	
	public static final String FIELD_STATE = "state";

}
