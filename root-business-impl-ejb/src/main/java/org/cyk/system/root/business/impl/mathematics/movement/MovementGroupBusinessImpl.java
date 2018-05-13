package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementGroupBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementGroupItemBusiness;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementGroup;
import org.cyk.system.root.model.mathematics.movement.MovementGroupItem;
import org.cyk.system.root.model.mathematics.movement.MovementGroupType;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementGroupDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementGroupItemDao;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.MethodHelper;

public class MovementGroupBusinessImpl extends AbstractMovementCollectionsBusinessImpl<MovementGroup,MovementGroupItem,MovementGroupDao,MovementGroupItemDao,MovementGroupItemBusiness> implements MovementGroupBusiness,Serializable {
	private static final long serialVersionUID = -5970296090669949506L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class, Boolean.FALSE);
	}
	
	@Inject
    public MovementGroupBusinessImpl(MovementGroupDao dao) {
        super(dao);
    } 
	
	@Override
	protected String getMovementCollectionFieldName() {
		return FieldHelper.getInstance().buildPath(MovementGroupItem.FIELD_MOVEMENT,Movement.FIELD_COLLECTION);
	}
	
	@Override
	public MovementGroup instanciateOne() {
		return super.instanciateOne().setType(InstanceHelper.getInstance().getDefaultUsingBusinessIdentifier(MovementGroupType.class))
				.__setBirthDateComputedByUser__(Boolean.FALSE);
	}
	
	/*@Override
	protected void computeChanges(MovementGroup movementGroup, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementGroup, loggingMessageBuilder);
		
		if(Boolean.TRUE.equals(movementGroup.getItems().isSynchonizationEnabled())){
			Collection<MovementCollection> movementCollections = new ArrayList<>();
			if(movementGroup.getParty()==null){
				
			}else{
				movementGroup.setMovementCollections(ClassHelper.getInstance().instanciateOne(Listener.class).findMovementCollectionByParty(movementGroup.getParty()));
				CollectionHelper.getInstance().add(movementCollections, Boolean.TRUE, movementGroup.getMovementCollections());
			}
			
			if(CollectionHelper.getInstance().isNotEmpty(movementCollections)){
				if(movementGroup.getItems().isEmpty()){
					//add all items
					if(Boolean.TRUE.equals(movementGroup.getItems().getHasAlreadyContainedElements())){
						
					}else{
						for(MovementCollection index : movementCollections){					
							movementGroup.getItems().addOne(instanciateOne(MovementGroupItem.class)
									.setCollection(movementGroup).setMovementCollection(index));
						}	
					}
				}else{
					//clean items
					Collection<MovementGroupItem> toDelete = new ArrayList<>();
					//remove those not belonging to movement collections 
					for(MovementGroupItem index : movementGroup.getItems().getElements())
						if(!CollectionHelper.getInstance().contains(movementCollections, index.getMovement().getCollection()))
							toDelete.add(index);
					CollectionHelper.getInstance().remove(movementGroup.getItems().getElements(), toDelete);
					movementGroup.getItems().getElements().removeAll(toDelete);
					toDelete.clear();
					
					//add those not belonging to items
					for(MovementCollection index : movementCollections){
						Boolean found = Boolean.FALSE;
						for(MovementGroupItem movementGroupItemIndex : movementGroup.getItems().getElements()){
							if(movementGroupItemIndex.getMovement().getCollection().equals(index)){
								found = Boolean.TRUE;
								break;
							}
						}
						if(Boolean.FALSE.equals(found)){
							movementGroup.getItems().addOne(instanciateOne(MovementGroupItem.class)
									.setCollection(movementGroup).setMovementCollection(index));
						}
					}
				}
			}
		}
	}*/
	
	/**/
	
	public static interface Listener {
		
		Collection<MovementCollection> findMovementCollectionByParty(Party party);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Collection<MovementCollection> findMovementCollectionByParty(Party party) {
				return null;
			}
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Collection<MovementCollection> findMovementCollectionByParty(Party party) {
					return MethodHelper.getInstance().callGet(inject(MovementCollectionIdentifiableGlobalIdentifierDao.class).readByIdentifiableGlobalIdentifier(party)
							, MovementCollection.class, MovementCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION);
				}				
			}
		}
	}
}
