package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
		if(StringUtils.isBlank(movement.getSupportingDocumentIdentifier()))
			movement.setSupportingDocumentIdentifier(null);
		exceptionUtils().exception(movement.getSupportingDocumentIdentifier()!=null && dao.readBySupportingDocumentIdentifier(movement.getSupportingDocumentIdentifier())!=null, "exception.supportingDocumentIdentifierAlreadyUsed");
		MovementAction action = movement.getAction();	
		BigDecimal increment = movement.getValue();
		if(action!=null){
			exceptionUtils().exception(movement.getCollection().getIncrementAction().equals(action) && increment.signum()==-1, "exception.value.mustbepositive");
			exceptionUtils().exception(movement.getCollection().getDecrementAction().equals(action) && increment.signum()==1, "exception.value.mustbenegative");
			exceptionUtils().comparison(action.getInterval().getLow().getValue()!=null && action.getInterval().getLow().getValue().compareTo(increment.abs())>0
					, action.getName(), ArithmeticOperator.GT,action.getInterval().getLow().getValue());
		}
		//BigDecimal increment = movement.getValue();
		BigDecimal current = movement.getCollection().getValue();
		Boolean positive = increment.signum() == 0 ? null : increment.signum() == 1 ;
		BigDecimal sign = new BigDecimal((Boolean.TRUE.equals(positive) ? Constant.EMPTY_STRING:"-")+"1");
		exceptionUtils().comparison(positive==null || increment.multiply(sign).signum() <= 0, action==null?Constant.EMPTY_STRING:action.getName(), ArithmeticOperator.GT, BigDecimal.ZERO);
		logTrace("Current value = {}. {} = {} ", current,action==null?Constant.EMPTY_STRING:action.getName(),increment);
		if(current!=null){
			current = current.add(increment);
			exceptionUtils().comparisonBetween(current,movement.getCollection().getInterval(), movement.getCollection().getName());
			movement.getCollection().setValue(current);
		}
		movementCollectionDao.update(movement.getCollection());
		movement =  super.create(movement);
		logIdentifiable("Created", movement);
		return movement;
	}
	
	@Override
	public Movement delete(Movement movement) {
		commonUtils.increment(BigDecimal.class, movement.getCollection(), MovementCollection.FIELD_VALUE, movement.getValue().negate());
		movementCollectionDao.update(movement.getCollection());
		return super.delete(movement);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Movement instanciateOne(MovementCollection movementCollection, Boolean increment) {
		Movement movement = new Movement();
		movement.setCollection(movementCollection);
		movement.setCode(movementCollection.getCode()+"_"+System.currentTimeMillis()+"_"+RandomStringUtils.randomAlphabetic(10));
		movement.setName(movementCollection.getName());
		movement.setAction(increment==null || increment ? movementCollection.getIncrementAction() : movementCollection.getDecrementAction());
		return movement;
	}
	
	@Override
	public Movement instanciateOne(MovementCollection movementCollection,String value) {
		BigDecimal bigDecimal = new BigDecimal(value);
		Movement movement = instanciateOne(movementCollection, bigDecimal.compareTo(BigDecimal.ZERO) >= 0);
		movement.setValue(bigDecimal);
		return movement;
	}
	
}
