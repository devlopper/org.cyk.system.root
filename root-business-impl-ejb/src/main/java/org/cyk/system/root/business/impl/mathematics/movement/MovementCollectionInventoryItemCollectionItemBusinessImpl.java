package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryItemCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItemCollectionItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryItemCollectionItemDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementDao;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.NumberHelper;

public class MovementCollectionInventoryItemCollectionItemBusinessImpl extends AbstractCollectionItemBusinessImpl<MovementCollectionInventoryItemCollectionItem, MovementCollectionInventoryItemCollectionItemDao,MovementCollectionInventoryItemCollection> implements MovementCollectionInventoryItemCollectionItemBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public MovementCollectionInventoryItemCollectionItemBusinessImpl(MovementCollectionInventoryItemCollectionItemDao dao) {
		super(dao); 
	}
	
	/*@Override
	protected void beforeCrud(MovementCollectionInventoryItemCollectionItem movementCollectionInventoryItemCollectionItem, Crud crud) {
		super.beforeCrud(movementCollectionInventoryItemCollectionItem, crud);
		if(Crud.isCreateOrUpdate(crud)) {
			if(inject(MovementBusiness.class).isNotIdentified(movementCollectionInventoryItemCollectionItem.getValueGapMovement()))
				inject(MovementBusiness.class).create(identifiables);
		}
	}*/
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(MovementCollectionInventoryItemCollectionItem movementCollectionInventoryItemCollectionItem) {
		return CollectionHelper.getInstance().add(super.findRelatedInstanceFieldNames(movementCollectionInventoryItemCollectionItem),MovementCollectionInventoryItemCollectionItem.FIELD_VALUE_GAP_MOVEMENT);
	}
	
	@Override
	protected void computeChanges(MovementCollectionInventoryItemCollectionItem movementCollectionInventoryItemCollectionItem,LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementCollectionInventoryItemCollectionItem, loggingMessageBuilder);
			
		if(movementCollectionInventoryItemCollectionItem.getMovementBeforeExistence() == null) {
			if(movementCollectionInventoryItemCollectionItem.getMovementCollection()!=null) {
				movementCollectionInventoryItemCollectionItem.setMovementBeforeExistence(inject(MovementDao.class).readLatestFromDateAscendingOrderIndexByCollection(
						movementCollectionInventoryItemCollectionItem.getMovementCollection()));
			}
		}
		
		if(movementCollectionInventoryItemCollectionItem.getMovementCollection()==null) {
			movementCollectionInventoryItemCollectionItem.setValueGap(null);
		}else {
			movementCollectionInventoryItemCollectionItem.setValueGap(NumberHelper.getInstance().get(BigDecimal.class,NumberHelper.getInstance().subtract(movementCollectionInventoryItemCollectionItem.getValue()
					,movementCollectionInventoryItemCollectionItem.getMovementCollection().getValue())));
		}
		
		if(BigDecimal.ZERO.equals(movementCollectionInventoryItemCollectionItem.getValueGap())) {
			movementCollectionInventoryItemCollectionItem.setValueGapMovement(null);
		}else {
			if(movementCollectionInventoryItemCollectionItem.getValueGapMovement()==null)
				movementCollectionInventoryItemCollectionItem.setValueGapMovement(instanciateOne(Movement.class)
						.setCollection(movementCollectionInventoryItemCollectionItem.getMovementCollection()));
			
			movementCollectionInventoryItemCollectionItem.getValueGapMovement().setValue(movementCollectionInventoryItemCollectionItem.getValueGap()).setActionFromValue();
		}
		
		if(movementCollectionInventoryItemCollectionItem.getValueGapMovement()!=null)
			inject(MovementBusiness.class).computeChanges(movementCollectionInventoryItemCollectionItem.getValueGapMovement());
	}
}
