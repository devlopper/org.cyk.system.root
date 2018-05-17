package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryItemBusiness;
import org.cyk.system.root.business.impl.helper.FieldHelper;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryItemDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementDao;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.MethodHelper;

public class MovementCollectionInventoryBusinessImpl extends AbstractMovementCollectionsBusinessImpl<MovementCollectionInventory,MovementCollectionInventoryItem,MovementCollectionInventoryDao,MovementCollectionInventoryItemDao,MovementCollectionInventoryItemBusiness> implements MovementCollectionInventoryBusiness,Serializable {
	private static final long serialVersionUID = -5970296090669949506L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class, Boolean.FALSE);
	}
	
	@Inject
    public MovementCollectionInventoryBusinessImpl(MovementCollectionInventoryDao dao) {
        super(dao);
    } 
	
	@Override
	public MovementCollectionInventory instanciateOne() {
		return super.instanciateOne();
	}
	
	@Override
	protected void computeChanges(MovementCollectionInventory movementCollectionInventory, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementCollectionInventory, loggingMessageBuilder);
		
		if(movementCollectionInventory.getMovementGroup()!=null)
			FieldHelper.getInstance().copy(movementCollectionInventory, movementCollectionInventory.getMovementGroup(),Boolean.FALSE
				,org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
						AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE),org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
								AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
		
		new CollectionHelper.Iterator.Adapter.Default<MovementCollectionInventoryItem>(movementCollectionInventory.getItems().getElements()){
			private static final long serialVersionUID = 1L;
			protected void __executeForEach__(MovementCollectionInventoryItem movementCollectionInventoryItem) {
				Movement previous;
				if(movementCollectionInventoryItem.getValueGapMovementGroupItem()!=null){
					Long count = inject(MovementDao.class).countByCollection(movementCollectionInventoryItem.getValueGapMovementGroupItem().getMovement().getCollection());
					if(count == 0)
						previous = null;
					else
						previous = inject(MovementDao.class).readByCollectionByFromDateAscendingOrderIndex(movementCollectionInventoryItem.getValueGapMovementGroupItem()
								.getMovement().getCollection(),movementCollectionInventoryItem.getValueGapMovementGroupItem().getMovement().getOrderNumber()-1);
					
					if(previous == null)
						movementCollectionInventoryItem.setPreviousValue(movementCollectionInventoryItem.getMovementCollection().getInitialValue());
					else
						movementCollectionInventoryItem.setPreviousValue(previous.getCumul());
				}
			}
		}.execute();
	}
	
	/**/
	
	public static interface Listener {
		
		Collection<MovementCollection> findMovementCollectionByParty(Party party);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
								
				@Override
				public Collection<MovementCollection> findMovementCollectionByParty(Party party) {
					return MethodHelper.getInstance().callGet(inject(MovementCollectionIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(party)
							, MovementCollection.class, MovementCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION);
				}				
			}
		
			@Override
			public Collection<MovementCollection> findMovementCollectionByParty(Party party) {
				return null;
			}
		}
	}
	
}
