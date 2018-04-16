package org.cyk.system.root.business.impl.mathematics.movement;

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
import org.cyk.system.root.business.api.mathematics.movement.MovementBusiness;
import org.cyk.system.root.business.api.time.IdentifiablePeriodBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementAction;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementActionDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementDao;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodCollectionDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.LoggingHelper.Message.Builder;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;

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
		if(StringUtils.isNotBlank(actionCode))
			movement.setAction(inject(MovementActionDao.class).read(actionCode));
		return movement;
	}
	
	@Override
	protected Movement __instanciateOne__(ObjectFieldValues objectFieldValues) {
		Movement movement =  super.__instanciateOne__(objectFieldValues);
		movement.__setBirthDateComputedByUser__(Boolean.FALSE);
		return movement;
	}
	
	@Override
	protected void beforeCrud(Movement movement, Crud crud) {
		super.beforeCrud(movement, crud);
		if(Crud.isCreateOrUpdate(crud)){
			MovementAction action = movement.getAction();	
			if(action!=null){
				exceptionUtils().comparisonBetween(movement.getValue(), action.getInterval(), action.getName());
				exceptionUtils().comparison( !inject(IntervalBusiness.class).contains(action.getInterval(), movement.getValue(), 2)
						, action.getName(), ArithmeticOperator.GT,action.getInterval().getLow().getValue());
			}else{
				throw__(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldObject(movement).setFieldName(Movement.FIELD_VALUE)
						.setValue2(BigDecimal.ZERO).setEqual(Boolean.TRUE));	
			}
			
			/*if(movement.getCollection()!=null && movement.getCollection().getType().getIdentifiablePeriodType()!=null){
				exceptionUtils().exception(movement.getIdentifiablePeriod() == null, "identifiable_period_required");
				
				throw__(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("movdate")
						.setDomainNameIdentifier("movement").setNumber1(movement.getBirthDate().getTime())
						.setNumber2(movement.getIdentifiablePeriod().getBirthDate().getTime()).setEqual(Boolean.FALSE).setGreater(Boolean.FALSE));
				
				throw__(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("movdate")
						.setDomainNameIdentifier("movement").setNumber1(movement.getBirthDate().getTime())
						.setNumber2(movement.getIdentifiablePeriod().getDeathDate().getTime()).setEqual(Boolean.FALSE).setGreater(Boolean.TRUE));
			}*/			
			
			updateCollection(movement,crud);
			
			if(Crud.CREATE.equals(crud)){
				
			}else{
				updateCumulWhereExistencePeriodFromDateIsGreaterThan(movement,crud);
			}
		}else {
			if(Crud.DELETE.equals(crud)){
				updateCollection(movement,crud);
				updateCumulWhereExistencePeriodFromDateIsGreaterThan(movement,crud);
			}
		}
	}
	
	@Override
	protected void afterCrud(Movement movement, Crud crud) {
		super.afterCrud(movement, crud);
		if(Crud.CREATE.equals(crud)){
			updateCumulWhereExistencePeriodFromDateIsGreaterThan(movement,crud);
			if(movement.getDestinationMovement()==null){
				if(movement.getDestinationMovementCollection()!=null){
					movement.setDestinationMovement(inject(MovementBusiness.class).instanciateOne(movement.getDestinationMovementCollection()));
					if(movement.getCollection()!=null && movement.getAction()!=null){
						movement.getDestinationMovement().setValueSettableFromAbsolute(movement.getValueSettableFromAbsolute());
						movement.getDestinationMovement().setValueAbsolute(movement.getValueAbsolute());
						movement.getDestinationMovement().setValue(NumberHelper.getInstance().negate(movement.getValue()));
						if(movement.getAction().equals(movement.getCollection().getType().getIncrementAction())){
							movement.getDestinationMovement().setAction(movement.getDestinationMovementCollection().getType().getDecrementAction());
						}else{
							movement.getDestinationMovement().setAction(movement.getDestinationMovementCollection().getType().getIncrementAction());
						}
					}
					create(movement.getDestinationMovement());
					//movement.getGlobalIdentifier().setDestination(movement.getDestinationMovement().getGlobalIdentifier());
					//movement.getDestinationMovement().getGlobalIdentifier().setSource(movement.getGlobalIdentifier());
					//inject(GlobalIdentifierDao.class).update(movement.getGlobalIdentifier());
					//inject(GlobalIdentifierDao.class).update(movement.getDestinationMovement().getGlobalIdentifier());
				}
			}
		}
		
		if(!Crud.DELETE.equals(crud)){
			/*if(Crud.CREATE.equals(crud)){
				if(movement.get__identifiablePeriod__()!=null){
					IdentifiablePeriodIdentifiableGlobalIdentifier identifiablePeriodIdentifiableGlobalIdentifier = 
							inject(IdentifiablePeriodIdentifiableGlobalIdentifierBusiness.class).instanciateOne();
					identifiablePeriodIdentifiableGlobalIdentifier.setIdentifiablePeriod(movement.get__identifiablePeriod__());
					identifiablePeriodIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifier(movement.getGlobalIdentifier());
					inject(IdentifiablePeriodIdentifiableGlobalIdentifierBusiness.class).create(identifiablePeriodIdentifiableGlobalIdentifier);
				}	
			}*/
			
			Collection<Movement> children = dao.readByFilter(new Movement.Filter().addMaster(movement), null);
			if(CollectionHelper.getInstance().isNotEmpty(children)){
				BigDecimal sum = NumberHelper.getInstance().get(BigDecimal.class
						,NumberHelper.getInstance().sum(MethodHelper.getInstance().callGet(children, BigDecimal.class, Movement.FIELD_VALUE)),BigDecimal.ZERO);
				
				if(Boolean.TRUE.equals(movement.getParentActionIsOppositeOfChildAction()))
					sum = NumberHelper.getInstance().negate(sum);
				
				throw__(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldObject(movement).setFieldName(Movement.FIELD_VALUE)						
						.setValue2(sum).setEqual(Boolean.FALSE));	
			}
			
		}
	}
	
	@Override
	protected void beforeDelete(Movement movement) {
		super.beforeDelete(movement);
		delete(dao.readByFilter(new Movement.Filter().addMaster(movement), null));
	}
	
	private void updateCumulWhereExistencePeriodFromDateIsGreaterThan(Movement movement,Crud crud){
		Collection<Movement> movements = dao.readWhereExistencePeriodFromDateIsGreaterThan(movement);
		if(CollectionHelper.getInstance().isNotEmpty(movements)){
			LoggingHelper.Message.Builder logMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
			logMessageBuilder.addManyParameters("update successors");
			
			BigDecimal increment = null;
			if(Crud.CREATE.equals(crud))
				increment = movement.getValue();
			else if(Crud.UPDATE.equals(crud))
				increment = movement.getValue().subtract(dao.read(movement.getIdentifier()).getValue());
			else if(Crud.DELETE.equals(crud))
				increment = movement.getValue().negate();
			logMessageBuilder.addNamedParameters("#",movements.size(),"cum inc",increment);
			
			for(Movement index : movements){
				NumberHelper.getInstance().add(BigDecimal.class, index, Movement.FIELD_CUMUL, increment);
				dao.update(index);
			}
			logTrace(logMessageBuilder);	
		}
	}
	
	//TODO should be moved to MovementCollectionBusinessImpl
	private void updateCollection(Movement movement,Crud crud){
		LoggingHelper.Message.Builder logMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
		if(movement.getCollection()==null)
			return;
		MovementCollection movementCollectionDatabase = movement.getCollection().getIdentifier() == null ? null : inject(MovementCollectionDao.class).read(movement.getCollection().getIdentifier());
		BigDecimal oldValue = InstanceHelper.getInstance().getIfNotNullElseDefault(movementCollectionDatabase == null 
				? movement.getCollection().getValue() 
				: movementCollectionDatabase.getValue(),BigDecimal.ZERO),newValue=null;
		logMessageBuilder.addManyParameters("update col");
		
		logMessageBuilder.addNamedParameters("cod",movement.getCollection().getCode(),"val",oldValue,
				"mov val",movement.getValue(),"mov act",movement.getAction()==null?Constant.EMPTY_STRING:movement.getAction().getName());
		if(Crud.isCreateOrUpdate(crud)){
			if(Crud.CREATE.equals(crud)){
				newValue = oldValue.add(movement.getValue());
			}else{
				Movement oldMovement = dao.read(movement.getIdentifier());
				BigDecimal difference = movement.getValue().subtract(oldMovement.getValue());
				newValue = oldValue.add(difference);
				logMessageBuilder.addNamedParameters("mov old val",oldMovement.getValue(),"diff",difference);
			}
			exceptionUtils().comparisonBetween(newValue,movement.getCollection().getType().getInterval(), movement.getCollection().getName());
			movement.getCollection().setValue(newValue);
		}else if(Crud.DELETE.equals(crud)) {
			newValue = oldValue.subtract(movement.getValue());
			movement.getCollection().setValue(newValue);
			//commonUtils.increment(BigDecimal.class, movement.getCollection(), MovementCollection.FIELD_VALUE, movement.getValue().negate());
		}else
			return;
		if(newValue!=null)
			movementCollectionDao.update(movement.getCollection());
		logMessageBuilder.addNamedParameters("col new val",newValue);
		logTrace(logMessageBuilder);
	}
			
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Movement instanciateOne(MovementCollection movementCollection,MovementAction movementAction,String value) {
		Movement movement = instanciateOne(movementCollection);
		movement.setValue(NumberHelper.getInstance().get(BigDecimal.class, value));
		movement.setAction(movementAction);
		return movement;
	}
	
	@Override
	public Movement instanciateOne(String code,String collectionCode, String value,Boolean increment) {
		Movement movement = instanciateOne(code,code);
		movement.setCollection(read(MovementCollection.class, collectionCode));
		movement.setValue(NumberHelper.getInstance().get(BigDecimal.class, value));
		movement.setAction(increment == null ? null : increment ? movement.getCollection().getType().getIncrementAction() : movement.getCollection().getType().getDecrementAction());
		return movement;
	}
	
	@Override
	public Movement instanciateOne(String code,String collectionCode, String value) {
		return instanciateOne(code, collectionCode, value, null);
	}
	
	@Override
	protected Boolean isDoesNotBelongsToIdentifiablePeriodVerifiable(Movement movement) {
		return movement.getCollection()!=null && movement.getCollection().getType()!=null && movement.getCollection().getType().getIdentifiablePeriodCollectionType()!=null;
	}
	
	@Override
	protected void computeChangesIdentifiablePeriod(Movement movement, Builder logMessageBuilder) {
		if(movement.getCollection()!=null && movement.getCollection().getType()!=null && movement.getCollection().getType().getIdentifiablePeriodCollectionType()!=null){
			if(movement.get__identifiablePeriod__() == null){
				IdentifiablePeriodCollection identifiablePeriodCollection = CollectionHelper.getInstance().getFirst(inject(IdentifiablePeriodCollectionDao.class)
						.readByTypeByJoin(movement.getCollection().getType().getIdentifiablePeriodCollectionType(), movement.getCollection()));
				
				if(identifiablePeriodCollection != null){
					movement.set__identifiablePeriod__(inject(IdentifiablePeriodBusiness.class).findFirstNotClosedOrInstanciateOneByIdentifiablePeriodCollection(identifiablePeriodCollection));
					if(inject(IdentifiablePeriodBusiness.class).isNotIdentified(movement.get__identifiablePeriod__())){
						
						//movement.addIdentifiables(movement.get__identifiablePeriod__());
					}
				}
			}
		}
		super.computeChangesIdentifiablePeriod(movement, logMessageBuilder);
	}
	
	@Override
	public void computeChanges(Movement movement,LoggingHelper.Message.Builder logMessageBuilder) {		
		super.computeChanges(movement,logMessageBuilder);
		
		if(movement.getAction() == null){
			if(movement.getActionIsIncrementation() == null){
				if(movement.getParent()!=null){
					Boolean parentActionIsOppositeOfChildAction = movement.getParent().getParentActionIsOppositeOfChildAction();
					if(movement.getParent().getCollection().getType().getIncrementAction().equals(movement.getParent().getAction()))
						if(Boolean.TRUE.equals(parentActionIsOppositeOfChildAction))
							movement.setAction(movement.getCollection().getType().getDecrementAction());
						else
							movement.setAction(movement.getCollection().getType().getIncrementAction());
					else if(movement.getParent().getCollection().getType().getDecrementAction().equals(movement.getParent().getAction()))
						if(Boolean.TRUE.equals(parentActionIsOppositeOfChildAction))
							movement.setAction(movement.getCollection().getType().getIncrementAction());	
						else
							movement.setAction(movement.getCollection().getType().getDecrementAction());
				}	
			}else{
				if(movement.getCollection()!=null)
					movement.setActionFromIncrementation(movement.getActionIsIncrementation());
			}
		}
		logMessageBuilder.addNamedParameters("col",movement.getCollection(),"act",movement.getAction(),"prev cum",movement.getPreviousCumul(),"val abs"
				,movement.getValueAbsolute(),"use abs",movement.getValueSettableFromAbsolute(),"cum",movement.getCumul());
		//previous cumul
		if(movement.getCollection()==null)
			movement.setPreviousCumul(null);
		else{
			//previous cumul  = sum of previous value
			Boolean computePreviousCumul = movement.getBirthDate()!=null;
			logMessageBuilder.addNamedParameters("compute prev cum",computePreviousCumul);
			if(computePreviousCumul){
				movement.setPreviousCumul(NumberHelper.getInstance().get(BigDecimal.class
						, NumberHelper.getInstance().add(movement.getCollection().getInitialValue(),dao.sumValueWhereExistencePeriodFromDateIsLessThan(movement))));
			}else
				movement.setPreviousCumul(movement.getCollection().getValue());
			/*
			if(Boolean.TRUE.equals(isNotIdentified(movement)) || movement.getBirthDate() == null){
				movement.setPreviousCumul(movement.getCollection().getValue());
			}else 
				movement.setPreviousCumul(dao.sumValueWhereExistencePeriodFromDateIsLessThan(movement));
			*/
			//System.out.println("MovementBusinessImpl.computeChanges() : "+movement.getPreviousCumul());
		}
		//value
		if(Boolean.TRUE.equals(movement.getValueSettableFromAbsolute())){
			if(movement.getValueAbsolute()==null || movement.getAction()==null){
				movement.setValue(null);
			}else{
				if(movement.getAction().equals(movement.getCollection().getType().getIncrementAction()))
					movement.setValue(movement.getValueAbsolute());
				else
					movement.setValue(movement.getValueAbsolute().negate());
			}
		}
		//cumul
		if(movement.getPreviousCumul()==null || movement.getValue()==null)
			movement.setCumul(null);
		else{
			movement.setCumul(movement.getPreviousCumul().add(movement.getValue()));
		}
		logMessageBuilder.addNamedParameters("prev cum",movement.getPreviousCumul(),"val",movement.getValue(),"cum",movement.getCumul());
	}
	
	@Override
	public Movement instanciateOne(MovementCollection movementCollection,String typeCode,Crud crud,AbstractIdentifiable identifiable,String valueFieldName,Boolean isPositiveDecrement,String destinationMovementCollectionCode){
		//BigDecimal systemValue,BigDecimal userValue
		
		Movement movement = inject(MovementBusiness.class).instanciateOne(movementCollection);
		if(movementCollection.getType().getCode().equals(typeCode)){
			movement.setValueAbsolute((BigDecimal) FieldHelper.getInstance().read(identifiable, valueFieldName));
		}
		movement.setValueSettableFromAbsolute(Boolean.TRUE);
		if(Crud.DELETE.equals(crud)){
			movement.setAction(movementCollection.getType().getIncrementAction());
		}else{
			AbstractIdentifiable identifiableDB = Crud.CREATE.equals(crud) ? null : inject(PersistenceInterfaceLocator.class).injectTyped(identifiable.getClass()).read(identifiable.getIdentifier());
			movement.setValueAbsolute( (BigDecimal)NumberHelper.getInstance().subtract(movement.getValueAbsolute()
					,identifiableDB == null ? null : (Number)FieldHelper.getInstance().read(identifiableDB, valueFieldName)));
			if(movement.getValueAbsolute().signum() == (isPositiveDecrement ? 1 : -1))
				movement.setAction(movementCollection.getType().getDecrementAction());
			else if(movement.getValueAbsolute().signum() == (isPositiveDecrement ? -1 : 1))
				movement.setAction(movementCollection.getType().getIncrementAction());
		}
		if(movement.getAction() != null){
			movement.setValueAbsolute(movement.getValueAbsolute().abs());
			if(StringHelper.getInstance().isNotBlank(destinationMovementCollectionCode))
				movement.setDestinationMovementCollection(read(MovementCollection.class, destinationMovementCollectionCode));
		}
		return movement;
	}
	
	@Override
	public Movement createIfActionIsNotNull(Movement movement) {
		if(movement.getAction() != null){
			create(movement);
		}
		return movement;
	}
	
	@Override
	public void create(AbstractIdentifiable identifiableJoin,String typeCode,Crud crud,AbstractIdentifiable valueIdentifiable,String valueFieldName
			,Boolean isPositiveDecrement,String destinationMovementCollectionCode){
		Collection<MovementCollectionIdentifiableGlobalIdentifier> movementCollectionIdentifiableGlobalIdentifiers = inject(MovementCollectionIdentifiableGlobalIdentifierDao.class)
				.readByIdentifiableGlobalIdentifier(identifiableJoin);
		for(MovementCollectionIdentifiableGlobalIdentifier index : movementCollectionIdentifiableGlobalIdentifiers){
			Movement movement = inject(MovementBusiness.class).instanciateOne(index.getMovementCollection(), typeCode
					, crud, valueIdentifiable,valueFieldName,isPositiveDecrement,destinationMovementCollectionCode);
			createIfActionIsNotNull(movement);
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
