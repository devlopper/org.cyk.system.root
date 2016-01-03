package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.computation.ArithmeticOperator;

@Stateless
public class MovementBusinessImpl extends AbstractCollectionItemBusinessImpl<Movement, MovementDao,MovementCollection> implements MovementBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private MovementCollectionDao movementCollectionDao;
	
	@Inject
	public MovementBusinessImpl(MovementDao dao) {
		super(dao); 
	}
	
	@Override
	public Movement create(Movement movement) {
		MovementAction action = movement.getAction();
		exceptionUtils().comparison(action.getInterval().getLow().getValue()!=null && action.getInterval().getLow().getValue().compareTo(movement.getValue().abs())>0
				, movement.getAction().getName(), ArithmeticOperator.GT,action.getInterval().getLow().getValue());
		BigDecimal increment = movement.getValue();
		BigDecimal current = movement.getCollection().getValue();
		Boolean positive = increment.signum() == 0 ? null : increment.signum() == 1 ;
		BigDecimal sign = new BigDecimal((Boolean.TRUE.equals(positive) ? Constant.EMPTY_STRING:"-")+"1");
		exceptionUtils().comparison(positive==null || increment.multiply(sign).signum() <= 0, movement.getAction().getName(), ArithmeticOperator.GT, BigDecimal.ZERO);
		current = current.add(increment);
		exceptionUtils().comparisonBetween(current,movement.getCollection().getInterval(), movement.getAction().getName());
		
		movement.getCollection().setValue(current);
		movementCollectionDao.update(movement.getCollection());
		if(movement.getDate()==null)
			movement.setDate(universalTimeCoordinated());
		movement =  super.create(movement);
		logTrace(movement.getLogMessage());
		return movement;
	}
	
}