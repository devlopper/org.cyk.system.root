package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.MovementActionBusiness;
import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.utility.common.Constant;

public class MovementCollectionBusinessImpl extends AbstractCollectionBusinessImpl<MovementCollection,Movement, MovementCollectionDao,MovementDao,MovementBusiness> implements MovementCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private MovementDao movementDao;
	
	@Inject
	public MovementCollectionBusinessImpl(MovementCollectionDao dao) {
		super(dao); 
	}
	
	@Override
	protected MovementBusiness getItemBusiness() {
		return inject(MovementBusiness.class);
	}
		
	@Override
	protected MovementDao getItemDao() {
		return movementDao;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovementCollection instanciateOne(String code,String incrementActionName,String decrementActionName) {
		MovementCollection movementCollection = new MovementCollection(code, BigDecimal.ZERO, inject(IntervalBusiness.class)
				.instanciateOne(null, code, "0", null));
		movementCollection.setIncrementAction(inject(MovementActionBusiness.class)
				.instanciateOne(code+Constant.CHARACTER_UNDESCORE+computeCode(incrementActionName), incrementActionName));
		movementCollection.setDecrementAction(inject(MovementActionBusiness.class)
				.instanciateOne(code+Constant.CHARACTER_UNDESCORE+computeCode(decrementActionName), decrementActionName));
		return movementCollection;
	}
	
	@Override
	public MovementCollection create(MovementCollection movementCollection) {
		if(isNotIdentified(movementCollection.getInterval()))
			inject(IntervalBusiness.class).create(movementCollection.getInterval());
		if(isNotIdentified(movementCollection.getIncrementAction()))
			inject(MovementActionBusiness.class).create(movementCollection.getIncrementAction());
		if(isNotIdentified(movementCollection.getDecrementAction()))
			inject(MovementActionBusiness.class).create(movementCollection.getDecrementAction());
		return super.create(movementCollection);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public BigDecimal computeValue(MovementCollection movementCollection, MovementAction movementAction,BigDecimal increment) {
		return inject(MovementActionBusiness.class).computeValue(movementAction, movementCollection.getValue(), increment);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovementCollection instanciateOneRandomly(String code) {
		return instanciateOne(code, MovementAction.INCREMENT,MovementAction.DECREMENT);
	}

}
