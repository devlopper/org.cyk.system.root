package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.system.root.model.mathematics.movement.MovementGroupItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryItemDao;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.NumberHelper;

public class MovementCollectionInventoryItemBusinessImpl extends AbstractCollectionItemBusinessImpl<MovementCollectionInventoryItem, MovementCollectionInventoryItemDao,MovementCollectionInventory> implements MovementCollectionInventoryItemBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public MovementCollectionInventoryItemBusinessImpl(MovementCollectionInventoryItemDao dao) {
		super(dao); 
	}
	
	/*@Override
	protected void beforeCrud(MovementCollectionInventoryItem movementCollectionInventoryItem, Crud crud) {
		super.beforeCrud(movementCollectionInventoryItem, crud);
		if(Crud.isCreateOrUpdate(crud)) {
			if(inject(MovementBusiness.class).isNotIdentified(movementCollectionInventoryItem.getValueGapMovement()))
				inject(MovementBusiness.class).create(identifiables);
		}
	}*/
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(MovementCollectionInventoryItem movementCollectionInventoryItem) {
		return CollectionHelper.getInstance().add(super.findRelatedInstanceFieldNames(movementCollectionInventoryItem),MovementCollectionInventoryItem.FIELD_VALUE_GAP_MOVEMENT_GROUP_ITEM);
	}
	
	@Override
	protected void computeChanges(MovementCollectionInventoryItem movementCollectionInventoryItem,LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementCollectionInventoryItem, loggingMessageBuilder);
			
		/*if(movementCollectionInventoryItem.getMovementBeforeExistence() == null) {
			if(movementCollectionInventoryItem.getMovementCollection()!=null) {
				movementCollectionInventoryItem.setMovementBeforeExistence(inject(MovementDao.class).readLatestFromDateAscendingOrderIndexByCollection(
						movementCollectionInventoryItem.getMovementCollection()));
			}
		}*/
		
		if(movementCollectionInventoryItem.getMovementCollection()==null) {
			movementCollectionInventoryItem.setValueGap(null);
		}else {
			movementCollectionInventoryItem.setValueGap(NumberHelper.getInstance().get(BigDecimal.class,NumberHelper.getInstance().subtract(movementCollectionInventoryItem.getValue()
					,movementCollectionInventoryItem.getMovementCollection().getValue())));
		}
		
		/*
		if(BigDecimal.ZERO.equals(movementCollectionInventoryItem.getValueGap())) {
			movementCollectionInventoryItem.setValueGapMovement(null);
		}else {
			if(movementCollectionInventoryItem.getValueGapMovement()==null)
				movementCollectionInventoryItem.setValueGapMovement(instanciateOne(Movement.class)
						.setCollection(movementCollectionInventoryItem.getMovementCollection()));
			
			movementCollectionInventoryItem.getValueGapMovement().setValue(movementCollectionInventoryItem.getValueGap()).setActionFromValue();
		}
		
		if(movementCollectionInventoryItem.getValueGapMovement()!=null)
			inject(MovementBusiness.class).computeChanges(movementCollectionInventoryItem.getValueGapMovement());
		*/
		
		if(BigDecimal.ZERO.equals(movementCollectionInventoryItem.getValueGap())) {
			movementCollectionInventoryItem.setValueGapMovementGroupItem(null);
		}else {
			if(movementCollectionInventoryItem.getValueGapMovementGroupItem()==null){
				movementCollectionInventoryItem.setValueGapMovementGroupItem(instanciateOne(MovementGroupItem.class)
						.setCollection(movementCollectionInventoryItem.getCollection().getMovementGroup())
						.setMovementCollection(movementCollectionInventoryItem.getMovementCollection()));
			}
			
			movementCollectionInventoryItem.getValueGapMovementGroupItem().getMovement().setValue(movementCollectionInventoryItem.getValueGap()).setActionFromValue();
		}
		
		if(movementCollectionInventoryItem.getValueGapMovementGroupItem()!=null)
			InstanceHelper.getInstance().computeChanges(movementCollectionInventoryItem.getValueGapMovementGroupItem());
	}
}
