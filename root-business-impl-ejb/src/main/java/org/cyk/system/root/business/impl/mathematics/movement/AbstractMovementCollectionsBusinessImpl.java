package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.business.api.mathematics.movement.AbstractMovementCollectionsBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.mathematics.movement.AbstractMovementCollections;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;
import org.cyk.system.root.persistence.api.mathematics.movement.AbstractMovementCollectionsDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.MethodHelper;

public abstract class AbstractMovementCollectionsBusinessImpl<COLLECTION extends AbstractMovementCollections<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>
,DAO extends AbstractMovementCollectionsDao<COLLECTION, ITEM>,ITEM_DAO extends AbstractCollectionItemDao<ITEM,COLLECTION>
,ITEM_BUSINESS extends AbstractCollectionItemBusiness<ITEM,COLLECTION>> extends AbstractCollectionBusinessImpl<COLLECTION,ITEM,DAO,ITEM_DAO,ITEM_BUSINESS> implements AbstractMovementCollectionsBusiness<COLLECTION,ITEM>,Serializable {
	private static final long serialVersionUID = -5970296090669949506L;

	public AbstractMovementCollectionsBusinessImpl(DAO dao) {
        super(dao);
    } 
	
	@Override
	public Collection<MovementCollection> findMovementCollectionByParty(Party party) {
		return MethodHelper.getInstance().callGet(inject(MovementCollectionIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(party)
				, MovementCollection.class, MovementCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION);
	}		
	
	protected String getMovementCollectionFieldName(){
		return MovementCollection.VARIABLE_NAME;
	}
	
	protected void setMovementCollection(Object instance,MovementCollection movementCollection){
		org.cyk.utility.common.helper.FieldHelper.getInstance().set(instance, movementCollection, getMovementCollectionFieldName());
	}
	
	protected MovementCollection getMovementCollection(Object instance){
		return (MovementCollection) org.cyk.utility.common.helper.FieldHelper.getInstance().read(instance, getMovementCollectionFieldName());
	}
	
	protected void addOneItem(COLLECTION collection,MovementCollection movementCollection){
		@SuppressWarnings("unchecked")
		ITEM item = (ITEM) instanciateOne(itemClass).setCollection(collection);
		setMovementCollection(item, movementCollection);
		collection.getItems().addOne(item);
	}
	
	@Override
	protected void computeChanges(COLLECTION collection, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(collection, loggingMessageBuilder);
		
		if(Boolean.TRUE.equals(collection.getItems().isSynchonizationEnabled())){
			Collection<MovementCollection> movementCollections = new ArrayList<>();
			if(collection.getParty()==null){
				
			}else{
				collection.setMovementCollections(findMovementCollectionByParty(collection.getParty()));
				CollectionHelper.getInstance().add(movementCollections, Boolean.TRUE, collection.getMovementCollections());
			}
			
			if(CollectionHelper.getInstance().isNotEmpty(movementCollections)){
				if(collection.getItems().isEmpty()){
					//add all items
					if(Boolean.TRUE.equals(collection.getItems().getHasAlreadyContainedElements())){
						
					}else{
						for(MovementCollection index : movementCollections)
							addOneItem(collection, index);
					}
				}else{
					//clean items
					Collection<ITEM> toDelete = new ArrayList<>();
					//remove those not belonging to movement collections 
					for(ITEM index : collection.getItems().getElements())
						if(!CollectionHelper.getInstance().contains(movementCollections, getMovementCollection(index)))
							toDelete.add(index);
					CollectionHelper.getInstance().remove(collection.getItems().getElements(), toDelete);
					collection.getItems().getElements().removeAll(toDelete);
					toDelete.clear();
					
					/*
					//remove those where value is zero
					for(MovementCollectionValuesTransferItemCollectionItem index : acknowledgedItems)
						if(index.getSource()!=null && NumberHelper.getInstance().isZero(index.getSource().getValue()))
							notTransferedItems.add(index);						
					CollectionHelper.getInstance().remove(acknowledgedItems, notTransferedItems);
					notTransferedItems.clear();
					*/
					//add those not belonging to items
					for(MovementCollection index : movementCollections){
						Boolean found = Boolean.FALSE;
						for(ITEM movementCollectionInventoryItemIndex : collection.getItems().getElements()){
							if(getMovementCollection(movementCollectionInventoryItemIndex).equals(index)){
								found = Boolean.TRUE;
								break;
							}
						}
						if(Boolean.FALSE.equals(found))
							addOneItem(collection, index);
						
					}
				}
			}
		}
	}
	
	/**/
	
	/*public static interface Listener {
		
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
	}*/
	
}
