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
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.system.root.model.mathematics.movement.MovementGroup;
import org.cyk.system.root.model.mathematics.movement.MovementGroupItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryItemDao;
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
			movementCollectionInventoryItem.getValueGapMovementGroupItem().getMovement().setValue(movementCollectionInventoryItem.getValueGap()).setActionFromValue();
		}
		
		if(movementCollectionInventoryItem.getValueGapMovementGroupItem()!=null)
			InstanceHelper.getInstance().computeChanges(movementCollectionInventoryItem.getValueGapMovementGroupItem());
		
		if(movementCollectionInventoryItem.getValueGapMovementGroupItem()!=null && movementCollectionInventoryItem.getValueGapMovementGroupItem().getCollection()!=null)
			FieldHelper.getInstance().copy(movementCollectionInventoryItem.getCollection(), movementCollectionInventoryItem.getValueGapMovementGroupItem().getCollection(),Boolean.FALSE
				,org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
						AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE),org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
								AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
	}
}
