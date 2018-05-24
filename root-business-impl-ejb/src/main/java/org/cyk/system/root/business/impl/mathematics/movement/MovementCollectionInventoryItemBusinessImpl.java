package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryItemBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementGroupBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.business.impl.helper.FieldHelper;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.system.root.model.mathematics.movement.MovementGroup;
import org.cyk.system.root.model.mathematics.movement.MovementGroupItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryItemDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementDao;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.NumberHelper;

public class MovementCollectionInventoryItemBusinessImpl extends AbstractCollectionItemBusinessImpl<MovementCollectionInventoryItem, MovementCollectionInventoryItemDao,MovementCollectionInventory> implements MovementCollectionInventoryItemBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public MovementCollectionInventoryItemBusinessImpl(MovementCollectionInventoryItemDao dao) {
		super(dao); 
	}
	
	@Override
	protected void beforeCrud(MovementCollectionInventoryItem movementCollectionInventoryItem, Crud crud) {
		super.beforeCrud(movementCollectionInventoryItem, crud);
		if(Crud.isCreateOrUpdate(crud)) {
			if(movementCollectionInventoryItem.getValueGapMovementGroupItem()!=null 
					&& movementCollectionInventoryItem.getValueGapMovementGroupItem().getCollection()!=null
					&& inject(MovementGroupBusiness.class).isNotIdentified(movementCollectionInventoryItem.getValueGapMovementGroupItem().getCollection())){
				inject(MovementGroupBusiness.class).create(movementCollectionInventoryItem.getValueGapMovementGroupItem().getCollection());
				inject(MovementCollectionInventoryDao.class).update(movementCollectionInventoryItem.getCollection());
			}
		}
		
		/*if(movementCollectionInventoryItem.getValueGapMovementGroupItem()!=null){
			Movement movement = movementCollectionInventoryItem.getValueGapMovementGroupItem().getMovement();
			throw__(ConditionHelper.Condition.getBuilderComparison(movement, inject(MovementDao.class).countByCollection(movement.getCollection())
					-inject(MovementDao.class).countWhereExistencePeriodFromDateIsGreaterThan(movement), null, Boolean.FALSE, org.cyk.utility.common.helper.FieldHelper
					.getInstance().buildPath(Movement.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_ORDER_NUMBER)));
		}*/
		
	}
	
	@Override
	protected void computeChanges(MovementCollectionInventoryItem movementCollectionInventoryItem,LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementCollectionInventoryItem, loggingMessageBuilder);
			
		if(movementCollectionInventoryItem.getMovementCollection()==null) {
			movementCollectionInventoryItem.setValueGap(null);
		}else {
			if(isIdentified(movementCollectionInventoryItem))
				;
			else{
				if(movementCollectionInventoryItem.getValue() == null)
					;
				else
					movementCollectionInventoryItem.setValueGap(NumberHelper.getInstance().get(BigDecimal.class,NumberHelper.getInstance().subtract(movementCollectionInventoryItem.getValue()
							,movementCollectionInventoryItem.getMovementCollection().getValue())));	
			}
		}
		
		if(movementCollectionInventoryItem.getValueGap() == null || BigDecimal.ZERO.equals(movementCollectionInventoryItem.getValueGap())) {
			movementCollectionInventoryItem.setValueGapMovementGroupItem(null);
		}else if(movementCollectionInventoryItem.getCollection()!=null) {
			if(movementCollectionInventoryItem.getCollection().getMovementGroup() == null){
				movementCollectionInventoryItem.getCollection().setMovementGroup(instanciateOne(MovementGroup.class)
						.setCode(movementCollectionInventoryItem.getCollection().getCode())
						.setTypeFromCode(RootConstant.Code.MovementGroupType.INVENTORY));
			}
			if(movementCollectionInventoryItem.getValueGapMovementGroupItem()==null){
				movementCollectionInventoryItem.setValueGapMovementGroupItem(instanciateOne(MovementGroupItem.class)
						.setCollection(movementCollectionInventoryItem.getCollection().getMovementGroup())
						.setMovementCollection(movementCollectionInventoryItem.getMovementCollection()));
			}
			movementCollectionInventoryItem.getValueGapMovementGroupItem().getMovement().setValue(movementCollectionInventoryItem.getValueGap()).computeAndSetActionFromValue();
		}
		
		if(movementCollectionInventoryItem.getValueGapMovementGroupItem()!=null)
			InstanceHelper.getInstance().computeChanges(movementCollectionInventoryItem.getValueGapMovementGroupItem());
		
		if(movementCollectionInventoryItem.getValueGapMovementGroupItem()!=null && movementCollectionInventoryItem.getValueGapMovementGroupItem().getCollection()!=null)
			FieldHelper.getInstance().copy(movementCollectionInventoryItem.getCollection(), movementCollectionInventoryItem.getValueGapMovementGroupItem().getCollection(),Boolean.FALSE
				,org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
						AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE),org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
								AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
		
		Movement previous;
		if(movementCollectionInventoryItem.getMovementCollectionValue() == null)
			movementCollectionInventoryItem.computeAndSetMovementCollectionValueFromObject(0);
		if(movementCollectionInventoryItem.getValueGapMovementGroupItem()!=null){
			Long count = inject(MovementDao.class).countByCollection(movementCollectionInventoryItem.getValueGapMovementGroupItem().getMovement().getCollection());
			if(count == 0)
				previous = null;
			else{
				//if(movementCollectionInventoryItem.getValueGapMovementGroupItem()!=null){
					//previous = inject(MovementDao.class).readByCollectionByFromDateAscendingOrderIndex(movementCollectionInventoryItem.getValueGapMovementGroupItem()
					//		.getMovement().getCollection(),movementCollectionInventoryItem.getValueGapMovementGroupItem().getMovement().getOrderNumber()-1);	
				//}
				
				if(movementCollectionInventoryItem.getValueGapMovementGroupItem()==null){
					previous = null;
					//previous = inject(MovementDao.class).readByCollectionByFromDateAscendingOrderIndex(movementCollectionInventoryItem
					//		.getMovementCollection(),movementCollectionInventoryItem.getOrderNumber()-1);
				}else{
					Long orderNumber = movementCollectionInventoryItem.getValueGapMovementGroupItem().getMovement().getOrderNumber();
					if(orderNumber == null){
						System.out.println(
								"MovementCollectionInventoryBusinessImpl.computeChanges(...).new Default() {...}.__executeForEach__() NUULLLL");
						//orderNumber = inject(MovementDao.class).countByCollection(movementCollectionInventoryItem.getValueGapMovementGroupItem()
						//	.getMovement().getCollection());
						
						previous = null;
					}else{
						//TODO this code should be moved to MovementBusiness
						previous = inject(MovementDao.class).readByCollectionByFromDateAscendingOrderIndex(movementCollectionInventoryItem.getValueGapMovementGroupItem()
							.getMovement().getCollection(),orderNumber-1);	
					}
				}
			}
			
			if(previous == null)
				movementCollectionInventoryItem.setPreviousValue(movementCollectionInventoryItem.getMovementCollection().getInitialValue() == null ? BigDecimal.ZERO 
						: movementCollectionInventoryItem.getMovementCollection().getInitialValue());
			else
				movementCollectionInventoryItem.setPreviousValue(previous.getCumul() == null ? BigDecimal.ZERO : previous.getCumul());
			
		}
		
	}
	
}
