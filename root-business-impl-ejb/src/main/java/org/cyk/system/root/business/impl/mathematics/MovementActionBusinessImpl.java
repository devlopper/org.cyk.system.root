package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.MovementActionBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.persistence.api.mathematics.MovementActionDao;

public class MovementActionBusinessImpl extends AbstractTypedBusinessService<MovementAction, MovementActionDao> implements MovementActionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MovementActionBusinessImpl(MovementActionDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovementAction instanciateOne(String code,String name) {
		MovementAction movementAction = new MovementAction(code, name);
		movementAction.setInterval(inject(IntervalBusiness.class).instanciateOne(null, code, "0", null));
		return movementAction;
	}
	
	@Override
	public MovementAction create(MovementAction movementAction) {
		inject(IntervalBusiness.class).create(movementAction.getInterval());
		return super.create(movementAction);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public BigDecimal computeValue(String movementActionCode,BigDecimal value,BigDecimal increment) {
		if(increment==null)
			return value;
		BigDecimal temp = increment;
		increment = increment.abs();
		if(MovementAction.INCREMENT.equals(movementActionCode))
			return value.add(increment);
		else if(MovementAction.DECREMENT.equals(movementActionCode))
			return value.subtract(increment);
		return value.add(temp);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public BigDecimal computeValue(MovementAction movementAction,BigDecimal value,BigDecimal increment) {
		return computeValue(movementAction == null ? null : movementAction.getCode(), value, increment);
	}
	
}
