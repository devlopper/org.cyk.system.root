package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementGroupItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementGroup;
import org.cyk.system.root.model.mathematics.movement.MovementGroupItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementGroupItemDao;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;

public class MovementGroupItemBusinessImpl extends AbstractCollectionItemBusinessImpl<MovementGroupItem, MovementGroupItemDao,MovementGroup> implements MovementGroupItemBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public MovementGroupItemBusinessImpl(MovementGroupItemDao dao) {
		super(dao); 
	}
	
	@Override
	public MovementGroupItem instanciateOne() {
		return super.instanciateOne().setMovement(instanciateOne(Movement.class));
	}
	
	@Override
	protected void computeChanges(MovementGroupItem movementGroupItem, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementGroupItem, loggingMessageBuilder);
		if(movementGroupItem.getMovement() != null) {
			if(movementGroupItem.getCollection()!=null && movementGroupItem.getCollection().getType()!=null)
				movementGroupItem.getMovement().setReasonFromCode(movementGroupItem.getCollection().getType().getCode());
			InstanceHelper.getInstance().computeChanges(movementGroupItem.getMovement());
			if(movementGroupItem.getMovement().getAction() == null && movementGroupItem.getMovement().getValue()!=null)
				movementGroupItem.getMovement().computeAndSetActionFromValue();
		}
		
	}
	
}
