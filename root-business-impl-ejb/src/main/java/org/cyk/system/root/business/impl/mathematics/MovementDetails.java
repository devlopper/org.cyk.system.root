package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;

@Getter @Setter
public class MovementDetails extends AbstractMovementDetails<Movement,MovementCollection> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	public MovementDetails(Movement movement) {
		super(movement);
	}

	@Override
	protected Movement getMovement() {
		return master;
	}
	
	@Override
	protected MovementCollection getCollection() {
		return getMovement().getCollection();
	}
}