package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MovementActionBusiness;
import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionTypeDao;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.utility.common.ObjectFieldValues;

public class MovementCollectionBusinessImpl extends AbstractCollectionBusinessImpl<MovementCollection,Movement, MovementCollectionDao,MovementDao,MovementBusiness> implements MovementCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MovementCollectionBusinessImpl(MovementCollectionDao dao) {
		super(dao); 
	}
	
	@Override
	protected MovementCollection __instanciateOne__(ObjectFieldValues objectFieldValues) {
		MovementCollection movementCollection = super.__instanciateOne__(objectFieldValues);
		movementCollection.setType(inject(MovementCollectionTypeDao.class).readDefaulted());
		movementCollection.setValue(BigDecimal.ZERO);
		return movementCollection;
	}

	/*
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovementCollection instanciateOne(String code,String incrementActionName,String decrementActionName) {
		MovementCollection movementCollection = new MovementCollection(code, BigDecimal.ZERO, inject(IntervalBusiness.class)
				.instanciateOne(code, "0"));
		movementCollection.setIncrementAction(inject(MovementActionBusiness.class)
				.instanciateOne(code+Constant.CHARACTER_UNDESCORE+RootConstant.Code.generateFromString(incrementActionName), incrementActionName));
		movementCollection.setDecrementAction(inject(MovementActionBusiness.class)
				.instanciateOne(code+Constant.CHARACTER_UNDESCORE+RootConstant.Code.generateFromString(decrementActionName), decrementActionName));
		movementCollection.getDecrementAction().getInterval().getHigh().setValue(new BigDecimal("-0.001"));
		movementCollection.getDecrementAction().getInterval().getLow().setValue(null);
		movementCollection.getDecrementAction().getInterval().getLow().setExcluded(Boolean.TRUE);
		return movementCollection;
	}
	*/
	
	/*
	@Override
	public MovementCollection create(MovementCollection movementCollection) {
		createIfNotIdentified(movementCollection.getInterval());
		createIfNotIdentified(movementCollection.getIncrementAction());
		createIfNotIdentified(movementCollection.getDecrementAction());
		return super.create(movementCollection);
	}
	*/
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public BigDecimal computeValue(MovementCollection movementCollection, MovementAction movementAction,BigDecimal increment) {
		return inject(MovementActionBusiness.class).computeValue(movementAction, movementCollection.getValue(), increment);
	}
	/*
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovementCollection instanciateOneRandomly(String code) {
		return instanciateOne(code, RootConstant.Code.MovementAction.INCREMENT,RootConstant.Code.MovementAction.DECREMENT);
	}*/

}
