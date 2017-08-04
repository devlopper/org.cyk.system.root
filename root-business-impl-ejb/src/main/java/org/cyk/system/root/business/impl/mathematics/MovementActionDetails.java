package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.mathematics.MovementAction;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovementActionDetails extends AbstractEnumerationDetails<MovementAction> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	private FieldValue interval;
	
	public MovementActionDetails(MovementAction movementAction) {
		super(movementAction);
		
	}
	
	@Override
	public void setMaster(MovementAction movementAction) {
		super.setMaster(movementAction);
		if(movementAction!=null){
			interval = new FieldValue(movementAction.getInterval());
		}
	}
	
	public static final String FIELD_INTERVAL = "interval";
	
}