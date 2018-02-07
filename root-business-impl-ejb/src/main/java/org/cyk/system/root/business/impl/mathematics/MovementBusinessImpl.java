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
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
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
	protected void setAutoSettedProperties(Movement movement, Crud crud) {
		super.setAutoSettedProperties(movement, crud);
		computeChanges(movement);
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
			}
			
			if(movement.getBirthDate()==null)
				movement.setBirthDate(inject(TimeBusiness.class).findUniversalTimeCoordinated());
			
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
		}
		
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
			
	private void updateCollection(Movement movement,Crud crud){
		LoggingHelper.Message.Builder logMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
		if(movement.getCollection()==null)
			return;
		BigDecimal oldValue= InstanceHelper.getInstance().getIfNotNullElseDefault(movement.getCollection().getValue(),BigDecimal.ZERO),newValue=null;
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
	public void computeChanges(Movement movement,LoggingHelper.Message.Builder logMessageBuilder) {		
		super.computeChanges(movement,logMessageBuilder);
		logMessageBuilder.addNamedParameters("col",movement.getCollection(),"act",movement.getAction(),"prev cum",movement.getPreviousCumul(),"val abs"
				,movement.getValueAbsolute(),"use abs",movement.getValueSettableFromAbsolute(),"cum",movement.getCumul());
		//previous cumul
		if(movement.getCollection()==null)
			movement.setPreviousCumul(null);
		else{
			//previous cumul  = sum of previous value
			if(movement.getBirthDate() == null)
				movement.setPreviousCumul(movement.getCollection().getValue());
			else 
				movement.setPreviousCumul(dao.sumValueWhereExistencePeriodFromDateIsLessThan(movement));
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
