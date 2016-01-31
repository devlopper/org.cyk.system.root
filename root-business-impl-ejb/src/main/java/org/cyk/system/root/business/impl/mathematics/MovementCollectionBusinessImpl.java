package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.utility.common.Constant;

@Stateless
public class MovementCollectionBusinessImpl extends AbstractCollectionBusinessImpl<MovementCollection,Movement, MovementCollectionDao,MovementDao> implements MovementCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private MovementDao movementDao;
	
	@Inject
	public MovementCollectionBusinessImpl(MovementCollectionDao dao) {
		super(dao); 
	}
		
	@Override
	protected MovementDao getItemDao() {
		return movementDao;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovementCollection instanciate(String code,String incrementActionName,String decrementActionName) {
		MovementCollection movementCollection = new MovementCollection(code, BigDecimal.ZERO, RootBusinessLayer.getInstance().getIntervalBusiness()
				.instanciate(null, code, "0", null));
		movementCollection.setIncrementAction(RootBusinessLayer.getInstance().getMovementActionBusiness()
				.instanciate(code+Constant.CHARACTER_UNDESCORE+computeCode(incrementActionName), incrementActionName));
		movementCollection.setDecrementAction(RootBusinessLayer.getInstance().getMovementActionBusiness()
				.instanciate(code+Constant.CHARACTER_UNDESCORE+computeCode(decrementActionName), decrementActionName));
		return movementCollection;
	}
	
	@Override
	public MovementCollection create(MovementCollection movementCollection) {
		RootBusinessLayer.getInstance().getIntervalBusiness().create(movementCollection.getInterval());
		RootBusinessLayer.getInstance().getMovementActionBusiness().create(movementCollection.getIncrementAction());
		RootBusinessLayer.getInstance().getMovementActionBusiness().create(movementCollection.getDecrementAction());
		return super.create(movementCollection);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public BigDecimal computeValue(MovementCollection movementCollection, MovementAction movementAction,BigDecimal increment) {
		increment = increment.abs();
		if(movementCollection.getIncrementAction().equals(movementAction))
			return movementCollection.getValue().add(increment);
		else if(movementCollection.getDecrementAction().equals(movementAction))
			return movementCollection.getValue().subtract(increment);
		return null;
	}

}
