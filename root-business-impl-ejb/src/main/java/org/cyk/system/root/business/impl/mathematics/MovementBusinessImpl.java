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
import org.cyk.system.root.persistence.api.mathematics.MovementActionDao;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.NumberHelper;

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
	public Movement instanciateOne(String collectionCode, String value,String supportingDocumentCode,String supportingDocumentPhysicalCreator,String supportingDocumentContentWriter, String actionCode) {
		MovementCollection movementCollection = inject(MovementCollectionDao.class).read(collectionCode);
		Movement movement = instanciateOne(movementCollection);
		movement.setValue(NumberHelper.getInstance().get(BigDecimal.class, value));
		/*if(StringUtils.isNotBlank(supportingDocumentCode)){
			File supportingDocument = inject(FileBusiness.class).instanciateOne();
			supportingDocument.setCode(supportingDocumentCode);
			supportingDocument.setPhysicalCreator(supportingDocumentPhysicalCreator);
			supportingDocument.setContentWriter(supportingDocumentContentWriter);
			movement.setSupportingDocument(supportingDocument);
		}*/
		if(StringUtils.isNotBlank(actionCode))
			movement.setAction(inject(MovementActionDao.class).read(actionCode));
		return movement;
	}
	
	@Override
	protected void beforeCreate(Movement movement) {
		super.beforeCreate(movement);
		
		//exceptionUtils().exception(BigDecimal.ZERO.equals(movement.getValue()), "exception.value.mustnotbezero");
				/*if(movement.getCollection().getDocumentIdentifierCountInterval()!=null){
					exceptionUtils().comparisonBetween(new BigDecimal(dao.countByGlobalIdentifierSupportingDocumentCode(movement.getSupportingDocument().getCode()))
							, movement.getCollection().getDocumentIdentifierCountInterval(), movement.getCollection().getDocumentIdentifierCountInterval().getName());
				}*/
				//exceptionUtils().exception(movement.getSupportingDocumentIdentifier()!=null && !dao.readBySupportingDocumentIdentifier(movement.getSupportingDocumentIdentifier()).isEmpty(), "exception.supportingDocumentIdentifierAlreadyUsed");
		
		MovementAction action = movement.getAction();	
		if(action!=null){
			exceptionUtils().comparisonBetween(movement.getValue(), action.getInterval(), action.getName());
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
		
		movement.setCumul(movement.getCollection().getValue());
	}
		
	private void updateCollection(Movement movement,Crud crud){
		if(movement.getCollection()==null)
			return;
		LoggingHelper.Message.Builder logMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
		BigDecimal oldValue= commonUtils.getValueIfNotNullElseDefault(movement.getCollection().getValue(),BigDecimal.ZERO),newValue=null;
		logMessageBuilder.addManyParameters(crud.name(),"movement");
		
		logMessageBuilder.addNamedParameters("collection.code",movement.getCollection().getCode(),"collection.value",oldValue,
				"movement.value",movement.getValue(),"action",movement.getAction()==null?Constant.EMPTY_STRING:movement.getAction().getName());
		if(Crud.isCreateOrUpdate(crud)){
			if(Crud.CREATE.equals(crud)){
				newValue = oldValue.add(movement.getValue());
			}else{
				Movement oldMovement = dao.read(movement.getIdentifier());
				BigDecimal difference = movement.getValue().subtract(oldMovement.getValue());
				newValue = oldValue.add(difference);
				logMessageBuilder.addNamedParameters("movement.oldValue",oldMovement.getValue(),"difference",difference);
			}
			exceptionUtils().comparisonBetween(newValue,movement.getCollection().getInterval(), movement.getCollection().getName());
			movement.getCollection().setValue(newValue);
		}else if(Crud.DELETE.equals(crud)) {
			newValue = oldValue.subtract(movement.getValue());
			movement.getCollection().setValue(newValue);
			//commonUtils.increment(BigDecimal.class, movement.getCollection(), MovementCollection.FIELD_VALUE, movement.getValue().negate());
		}else
			return;
		if(newValue!=null)
			movementCollectionDao.update(movement.getCollection());
		logMessageBuilder.addNamedParameters("collection.newValue",newValue);
		logTrace(logMessageBuilder);
	}
	
	@Override
	protected void beforeUpdate(Movement movement) {
		super.beforeUpdate(movement);
		updateCollection(movement,Crud.UPDATE);
		movement.setCumul(movement.getCollection().getValue());//TODO we need to update all the successors
	}
	
	@Override
	protected void beforeDelete(Movement movement) {
		super.beforeDelete(movement);
		updateCollection(movement,Crud.DELETE);
	}
		
	@Override
	protected void deleteFileIdentifiableGlobalIdentifier(Movement identifiable) {
		
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Movement instanciateOne(MovementCollection movementCollection,MovementAction movementAction,String value) {
		Movement movement = instanciateOne(movementCollection);
		movement.setValue(NumberHelper.getInstance().get(BigDecimal.class, value));
		movement.setAction(movementAction);
		//movement.setAction(bigDecimal==null || bigDecimal.signum() == 0 ? null : bigDecimal.signum() == 1 ? movementCollection.getIncrementAction() : movementCollection.getDecrementAction());
		return movement;
	}
	
	@Override
	public Movement instanciateOne(String code,String collectionCode, String value,String actionCode) {
		Movement movement = instanciateOne(code,code);
		movement.setCollection(read(MovementCollection.class, collectionCode));
		movement.setValue(NumberHelper.getInstance().get(BigDecimal.class, value));
		movement.setAction(StringUtils.isBlank(actionCode) ? null : read(MovementAction.class, actionCode));
		return movement;
	}
	
	@Override
	public Movement instanciateOne(String code,String collectionCode, String value) {
		return instanciateOne(code, collectionCode, value, null);
	}
	
	@Override
	public void computeChanges(Movement movement) {
		super.computeChanges(movement);
		if(movement.getCollection()==null)
			movement.setPreviousCumul(null);
		else
			movement.setPreviousCumul(movement.getCollection().getValue());
		
		if(movement.getPreviousCumul()==null || movement.getAction()==null || movement.getValue()==null)
			movement.setCumul(null);
		else{
			BigDecimal value =  movement.getValue();
			if(movement.getAction().equals(movement.getCollection().getDecrementAction()))
				value = value.negate(); 
			movement.setCumul(movement.getPreviousCumul().add(value));
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
	
}
