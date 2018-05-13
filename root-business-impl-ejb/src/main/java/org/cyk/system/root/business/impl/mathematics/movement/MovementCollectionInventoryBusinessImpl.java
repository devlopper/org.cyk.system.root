package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryItemBusiness;
import org.cyk.system.root.business.impl.helper.FieldHelper;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryItemDao;
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
		return super.instanciateOne().__setBirthDateComputedByUser__(Boolean.FALSE).setItemsSynchonizationEnabled(Boolean.TRUE);
	}
	
	@Override
	protected void computeChanges(MovementCollectionInventory movementCollectionInventory, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementCollectionInventory, loggingMessageBuilder);
		/*
		if(Boolean.TRUE.equals(movementCollectionInventory.getItems().isSynchonizationEnabled())){
			Collection<MovementCollection> movementCollections = new ArrayList<>();
			if(movementCollectionInventory.getParty()==null){
				
			}else{
				movementCollectionInventory.setMovementCollections(ClassHelper.getInstance().instanciateOne(Listener.class).findMovementCollectionByParty(movementCollectionInventory.getParty()));
				CollectionHelper.getInstance().add(movementCollections, Boolean.TRUE, movementCollectionInventory.getMovementCollections());
			}
			
			if(CollectionHelper.getInstance().isNotEmpty(movementCollections)){
				if(movementCollectionInventory.getItems().isEmpty()){
					//add all items
					if(Boolean.TRUE.equals(movementCollectionInventory.getItems().getHasAlreadyContainedElements())){
						
					}else{
						for(MovementCollection index : movementCollections){					
							movementCollectionInventory.getItems().addOne(instanciateOne(MovementCollectionInventoryItem.class)
									.setCollection(movementCollectionInventory).setMovementCollection(index));
						}	
					}
				}else{
					//clean items
					Collection<MovementCollectionInventoryItem> toDelete = new ArrayList<>();
					//remove those not belonging to movement collections 
					for(MovementCollectionInventoryItem index : movementCollectionInventory.getItems().getElements())
						if(!CollectionHelper.getInstance().contains(movementCollections, index.getMovementCollection()))
							toDelete.add(index);
					CollectionHelper.getInstance().remove(movementCollectionInventory.getItems().getElements(), toDelete);
					movementCollectionInventory.getItems().getElements().removeAll(toDelete);
					toDelete.clear();
					
					//add those not belonging to items
					for(MovementCollection index : movementCollections){
						Boolean found = Boolean.FALSE;
						for(MovementCollectionInventoryItem movementCollectionInventoryItemIndex : movementCollectionInventory.getItems().getElements()){
							if(movementCollectionInventoryItemIndex.getMovementCollection().equals(index)){
								found = Boolean.TRUE;
								break;
							}
						}
						if(Boolean.FALSE.equals(found)){
							movementCollectionInventory.getItems().addOne(instanciateOne(MovementCollectionInventoryItem.class)
									.setCollection(movementCollectionInventory).setMovementCollection(index));
						}
					}
				}
			}
		}
		*/
		if(movementCollectionInventory.getMovementGroup()!=null)
			FieldHelper.getInstance().copy(movementCollectionInventory, movementCollectionInventory.getMovementGroup(),Boolean.FALSE
				,org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
						AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE),org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
								AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
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
