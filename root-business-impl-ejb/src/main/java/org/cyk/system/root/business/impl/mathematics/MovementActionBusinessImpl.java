package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.MovementActionBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.persistence.api.mathematics.MovementActionDao;

public class MovementActionBusinessImpl extends AbstractEnumerationBusinessImpl<MovementAction, MovementActionDao> implements MovementActionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MovementActionBusinessImpl(MovementActionDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovementAction instanciateOne(String code,String name) {
		MovementAction movementAction = new MovementAction(code, name);
		movementAction.setInterval(inject(IntervalBusiness.class).instanciateOne(code, "0"));
		return movementAction;
	}
	
	@Override
	protected void beforeCreate(MovementAction movementAction) {
		super.beforeCreate(movementAction);
		createIfNotIdentified(movementAction.getInterval());
	}
		
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public BigDecimal computeValue(String movementActionCode,BigDecimal value,BigDecimal increment) {
		if(increment==null)
			return value;
		BigDecimal temp = increment;
		increment = increment.abs();
		if(RootConstant.Code.MovementAction.INCREMENT.equals(movementActionCode))
			return value.add(increment);
		else if(RootConstant.Code.MovementAction.DECREMENT.equals(movementActionCode))
			return value.subtract(increment);
		return value.add(temp);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public BigDecimal computeValue(MovementAction movementAction,BigDecimal value,BigDecimal increment) {
		return computeValue(movementAction == null ? null : movementAction.getCode(), value, increment);
	}
	
	@Override
	protected MovementAction __instanciateOne__(String[] values,InstanciateOneListener<MovementAction> listener) {
		super.__instanciateOne__(values, listener);
		set(listener.getSetListener().setIndex(10), MovementAction.FIELD_INTERVAL);
		return listener.getInstance();
	}
	
}
