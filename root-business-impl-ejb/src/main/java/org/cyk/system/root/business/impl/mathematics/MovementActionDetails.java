package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.mathematics.MovementAction;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovementActionDetails extends AbstractEnumerationDetails<MovementAction> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	public MovementActionDetails(MovementAction movementAction) {
		super(movementAction);
		
	}
	
}