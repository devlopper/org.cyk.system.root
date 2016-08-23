package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.computation.ArithmeticOperator;

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
		if(action!=null){
			exceptionUtils().exception(movement.getCollection().getIncrementAction().equals(action) && movement.getValue().signum()==-1, "exception.value.mustbepositive");
			exceptionUtils().exception(movement.getCollection().getDecrementAction().equals(action) && movement.getValue().signum()==1, "exception.value.mustbenegative");
			exceptionUtils().comparison(action.getInterval().getLow().getValue()!=null && action.getInterval().getLow().getValue().compareTo(movement.getValue().abs())>0
					, action.getName(), ArithmeticOperator.GT,action.getInterval().getLow().getValue());
		}
		updateCollection(movement);
		if(movement.getBirthDate()==null)
			movement.setBirthDate(inject(TimeBusiness.class).findUniversalTimeCoordinated());
		movement =  super.create(movement);
		logIdentifiable("Created", movement);
		return movement;
	}
	
	private void updateCollection(Movement movement){
		//BigDecimal increment = movement.getValue();
		BigDecimal current = movement.getCollection().getValue();
		Boolean positive = movement.getValue().signum() == 0 ? null : movement.getValue().signum() == 1 ;
		BigDecimal sign = new BigDecimal((Boolean.TRUE.equals(positive) ? Constant.EMPTY_STRING:"-")+"1");
		exceptionUtils().comparison(positive==null || movement.getValue().multiply(sign).signum() <= 0, movement.getAction()==null?Constant.EMPTY_STRING:movement.getAction().getName(), ArithmeticOperator.GT, BigDecimal.ZERO);
		logTrace("Current value = {}. {} = {} ", current,movement.getAction()==null?Constant.EMPTY_STRING:movement.getAction().getName(),movement.getValue());
		if(current!=null){
			if(movement.getIdentifier()==null)
				current = current.add(movement.getValue());
			else{
				Movement oldMovement = dao.read(movement.getIdentifier());
				current = current.add(oldMovement.getValue().negate()).add(movement.getValue());
			}
			exceptionUtils().comparisonBetween(current,movement.getCollection().getInterval(), movement.getCollection().getName());
			movement.getCollection().setValue(current);
		}
		movementCollectionDao.update(movement.getCollection());
	}
	
	@Override
	public Movement update(Movement movement) {
		updateCollection(movement);
		return super.update(movement);
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
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Movement instanciateOne(MovementCollection movementCollection,String value) {
		BigDecimal bigDecimal = new BigDecimal(value);
		Movement movement = instanciateOne(movementCollection, bigDecimal.compareTo(BigDecimal.ZERO) >= 0);
		movement.setValue(bigDecimal);
		return movement;
	}
	
}
