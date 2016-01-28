package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class MovementDetails extends AbstractEnumerationDetails<Movement> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String action,value,date;
	
	public MovementDetails(Movement movement) {
		super(movement);
		action = movement.getAction().getName();
		date = formatDateTime(movement.getDate());
		value = formatNumber(movement.getValue());
	}
}