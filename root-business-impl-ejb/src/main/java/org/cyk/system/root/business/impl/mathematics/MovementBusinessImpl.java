package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.computation.ArithmeticOperator;

public class MovementBusinessImpl extends AbstractCollectionItemBusinessImpl<Movement, MovementDao,MovementCollection> implements MovementBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private MovementCollectionDao movementCollectionDao;
	
	@Inject
	public MovementBusinessImpl(MovementDao dao) {
		super(dao); 
	}
	
	@Override
	protected Collection<? extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<?>> getListeners() {
		return Listener.COLLECTION;
	}
	
	@Override
	protected Object[] getPropertyValueTokens(Movement movement, String name) {
		if(ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_NAME}, name))
			return new Object[]{movement.getCollection(),movement.getAction()};
		return super.getPropertyValueTokens(movement, name);
	}
	
	@Override
	public Movement create(Movement movement) {
		exceptionUtils().exception(movement.getValue()==null, "exception.value.mustnotbenull");
		exceptionUtils().exception(BigDecimal.ZERO.equals(movement.getValue()), "exception.value.mustnotbezero");
		if(StringUtils.isBlank(movement.getSupportingDocumentIdentifier()))
			movement.setSupportingDocumentIdentifier(null);
		exceptionUtils().exception(movement.getSupportingDocumentIdentifier()!=null && dao.readBySupportingDocumentIdentifier(movement.getSupportingDocumentIdentifier())!=null, "exception.supportingDocumentIdentifierAlreadyUsed");
		MovementAction action = movement.getAction();	
		if(action!=null){
			exceptionUtils().exception(movement.getCollection().getIncrementAction().equals(action) && movement.getValue().signum()==-1, "exception.value.mustbepositive");
			exceptionUtils().exception(movement.getCollection().getDecrementAction().equals(action) && movement.getValue().signum()==1, "exception.value.mustbenegative");
			//exceptionUtils().comparison(action.getInterval().getLow().getValue()!=null && action.getInterval().getLow().getValue().compareTo(movement.getValue().abs())>0
			//		, action.getName(), ArithmeticOperator.GT,action.getInterval().getLow().getValue());
			exceptionUtils().comparison( !inject(IntervalBusiness.class).contains(action.getInterval(), movement.getValue(), 2)
					, action.getName(), ArithmeticOperator.GT,action.getInterval().getLow().getValue());
		}
		updateCollection(movement,Crud.CREATE);
		if(movement.getBirthDate()==null)
			movement.setBirthDate(inject(TimeBusiness.class).findUniversalTimeCoordinated());
		movement =  super.create(movement);
		return movement;
	}
	
	private void updateCollection(Movement movement,Crud crud){
		if(movement.getCollection()==null)
			return;
		LogMessage.Builder logMessageBuilder = new LogMessage.Builder();
		BigDecimal oldValue=movement.getCollection().getValue(),newValue=null;
		logMessageBuilder.setAction(crud.name());
		logMessageBuilder.setSubject("movement");
		logMessageBuilder.addParameters("collection.code",movement.getCollection().getCode(),"collection.value",oldValue,
				"movement.value",movement.getValue(),"action",movement.getAction()==null?Constant.EMPTY_STRING:movement.getAction().getName());
		if(Crud.isCreateOrUpdate(crud)){
			Boolean positive = movement.getValue().signum() == 0 ? null : movement.getValue().signum() == 1 ;
			BigDecimal sign = new BigDecimal((Boolean.TRUE.equals(positive) ? Constant.EMPTY_STRING:"-")+"1");
			exceptionUtils().comparison(positive==null || movement.getValue().multiply(sign).signum() <= 0, movement.getAction()==null?Constant.EMPTY_STRING:movement.getAction().getName(), ArithmeticOperator.GT, BigDecimal.ZERO);
			if(oldValue!=null){
				if(Crud.CREATE.equals(crud)){
					newValue = oldValue.add(movement.getValue());
				}else{
					Movement oldMovement = dao.read(movement.getIdentifier());
					BigDecimal difference = movement.getValue().subtract(oldMovement.getValue());
					newValue = oldValue.add(difference);
					logMessageBuilder.addParameters("movement.oldValue",oldMovement.getValue(),"difference",difference);
				}
				exceptionUtils().comparisonBetween(newValue,movement.getCollection().getInterval(), movement.getCollection().getName());
				movement.getCollection().setValue(newValue);
			}
		}else if(Crud.DELETE.equals(crud)) {
			newValue = oldValue.subtract(movement.getValue());
			movement.getCollection().setValue(newValue);
			//commonUtils.increment(BigDecimal.class, movement.getCollection(), MovementCollection.FIELD_VALUE, movement.getValue().negate());
		}else
			return;
		if(newValue!=null)
			movementCollectionDao.update(movement.getCollection());
		logMessageBuilder.addParameters("collection.newValue",newValue);
		logTrace(logMessageBuilder);
	}
	
	@Override
	public Movement update(Movement movement) {
		updateCollection(movement,Crud.UPDATE);
		return super.update(movement);
	}
	
	@Override
	public Movement delete(Movement movement) {
		updateCollection(movement,Crud.DELETE);
		return super.delete(movement);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Movement instanciateOne(MovementCollection movementCollection,MovementAction movementAction,String value) {
		Movement movement = instanciateOne(movementCollection);
		movement.setValue(commonUtils.getBigDecimal(value));
		movement.setAction(movementAction);
		//movement.setAction(bigDecimal==null || bigDecimal.signum() == 0 ? null : bigDecimal.signum() == 1 ? movementCollection.getIncrementAction() : movementCollection.getDecrementAction());
		return movement;
	}
	
	@Override
	public void completeInstanciationOfOne(Movement movement) {
		super.completeInstanciationOfOne(movement);
		if(movement.getCollection()!=null && movement.getCollection().getIdentifier()==null)
			movement.setCollection(inject(MovementCollectionDao.class).read(movement.getCollection().getCode()));
	}
	
	@Override
	public void completeInstanciationOfOneFromValues(Movement movement,AbstractCompleteInstanciationOfOneFromValuesArguments<Movement> completeInstanciationOfOneFromValuesArguments) {
		super.completeInstanciationOfOneFromValues(movement, completeInstanciationOfOneFromValuesArguments);
		CompleteMovementInstanciationOfOneFromValuesArguments arguments = (CompleteMovementInstanciationOfOneFromValuesArguments) completeInstanciationOfOneFromValuesArguments;
		completeInstanciationOfOneFromValuesBeforeProcessing(movement,arguments.getValues(),arguments.getListener());
		
		if(arguments.getMovementCollectionCodeIndex()!=null){
			movement.setCollection(new MovementCollection());
			movement.getCollection().setCode(arguments.getValues()[arguments.getMovementCollectionCodeIndex()]);
		}
	}
	
	/**/
	
	public static interface Listener extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<Movement>{
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		/**/
		
		public static class Adapter extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.Adapter<Movement> implements Listener, Serializable {
			private static final long serialVersionUID = -1625238619828187690L;
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = -1625238619828187690L;
				
				/**/
				
				public static class EnterpriseResourcePlanning extends Listener.Adapter.Default implements Serializable {
					private static final long serialVersionUID = -1625238619828187690L;
					
					/**/
					
					
				}
			}
		}
		
	}
	
	public static interface CrudListener {
		
		public static class Adapter extends BeanAdapter implements CrudListener, Serializable {
			private static final long serialVersionUID = -1625238619828187690L;
			/**/
			
		}
		
	}
	
	public static interface UpdateListener extends CrudListener {
		
		public static class Adapter extends CrudListener.Adapter implements UpdateListener, Serializable {
			private static final long serialVersionUID = -1625238619828187690L;
			/**/
			
		}
		
	}
}
