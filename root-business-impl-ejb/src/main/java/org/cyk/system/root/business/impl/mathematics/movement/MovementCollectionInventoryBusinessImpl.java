package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryItemBusiness;
import org.cyk.system.root.business.api.time.IdentifiablePeriodBusiness;
import org.cyk.system.root.business.impl.helper.FieldHelper;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryItemDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementDao;
import org.cyk.system.root.persistence.api.party.PartyDao;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodCollectionDao;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.LoggingHelper.Message.Builder;
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
		return super.instanciateOne().setTypeFromCode(RootConstant.Code.MovementCollectionInventoryType.STOCK_REGISTER);
	}
	
	@Override
	protected void beforeCreate(MovementCollectionInventory movementCollectionInventory) {
		super.beforeCreate(movementCollectionInventory);
		movementCollectionInventory.addIdentifiablesPartyIdentifiableGlobalIdentifierFromField(MovementCollectionInventory.FIELD_PARTY
				, RootConstant.Code.BusinessRole.PARTY);
	}
	
	@Override
	protected Boolean isDoesNotBelongsToIdentifiablePeriodVerifiable(MovementCollectionInventory movementCollectionInventory) {
		return Boolean.TRUE;// movementCollectionInventory.getType()!=null && movementCollectionInventory.getType().getIdentifiablePeriodCollectionType()!=null;
	}
	
	@Override
	protected void computeChangesIdentifiablePeriod(MovementCollectionInventory movementCollectionInventory, Builder logMessageBuilder) {
		if(movementCollectionInventory.get__identifiablePeriod__() == null){
			IdentifiablePeriodCollection identifiablePeriodCollection = read(IdentifiablePeriodCollection.class, RootConstant.Code.IdentifiablePeriodCollection.INVENTORY_WORKING_MONTH);
					//CollectionHelper.getInstance().getFirst(inject(IdentifiablePeriodCollectionDao.class)
					//.readByTypeByJoin(movementCollectionInventory.getType().getIdentifiablePeriodCollectionType(), movementCollectionInventory));
			if(identifiablePeriodCollection != null){
				movementCollectionInventory.set__identifiablePeriod__(inject(IdentifiablePeriodBusiness.class).findFirstNotClosedOrInstanciateOneByIdentifiablePeriodCollection(identifiablePeriodCollection));
				if(inject(IdentifiablePeriodBusiness.class).isNotIdentified(movementCollectionInventory.get__identifiablePeriod__())){
					
					//movement.addIdentifiables(movement.get__identifiablePeriod__());
				}
			}
		}
		super.computeChangesIdentifiablePeriod(movementCollectionInventory, logMessageBuilder);
	}
	
	@Override
	protected void computeChanges(MovementCollectionInventory movementCollectionInventory, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementCollectionInventory, loggingMessageBuilder);
		
		if(movementCollectionInventory.getParty() == null){
			movementCollectionInventory.setParty(CollectionHelper.getInstance().getFirst(inject(PartyDao.class)
					.readByIdentifiableByBusinessRoleCode(movementCollectionInventory, RootConstant.Code.BusinessRole.PARTY)));
			if(movementCollectionInventory.getParty() == null){
				if(CollectionHelper.getInstance().isNotEmpty(movementCollectionInventory.getItems().getElements()))
					for(MovementCollectionInventoryItem item : movementCollectionInventory.getItems().getElements()){
						if(item.getMovementCollection()!=null){
							MovementCollectionIdentifiableGlobalIdentifier movementCollectionIdentifiableGlobalIdentifier = CollectionHelper.getInstance()
									.getFirst(inject(MovementCollectionIdentifiableGlobalIdentifierDao.class).readByMovementCollection(item.getMovementCollection()));
							if(movementCollectionIdentifiableGlobalIdentifier!=null){
								movementCollectionInventory.setParty(inject(PartyDao.class).readByGlobalIdentifier(movementCollectionIdentifiableGlobalIdentifier
										.getIdentifiableGlobalIdentifier()));
								break;
							}
						}
					}	
			}			
		}
		
		if(movementCollectionInventory.getMovementGroup()!=null)
			FieldHelper.getInstance().copy(movementCollectionInventory, movementCollectionInventory.getMovementGroup(),Boolean.FALSE
				,org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
						AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE),org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
								AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
		
		new CollectionHelper.Iterator.Adapter.Default<MovementCollectionInventoryItem>(movementCollectionInventory.getItems().getElements()){
			private static final long serialVersionUID = 1L;
			protected void __executeForEach__(MovementCollectionInventoryItem movementCollectionInventoryItem) {
				//f(movementCollectionInventoryItem);
				//TODO we need to recall it in order to perform f. how to do it better
				inject(MovementCollectionInventoryItemBusiness.class).computeChanges(movementCollectionInventoryItem);
			}
		}.execute();
	}
	
	@Deprecated
	private void f(MovementCollectionInventoryItem movementCollectionInventoryItem){
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
					}else
						previous = inject(MovementDao.class).readByCollectionByFromDateAscendingOrderIndex(movementCollectionInventoryItem.getValueGapMovementGroupItem()
							.getMovement().getCollection(),orderNumber-1);	
				}
			}
			
			if(previous == null)
				movementCollectionInventoryItem.setPreviousValue(movementCollectionInventoryItem.getMovementCollection().getInitialValue() == null ? BigDecimal.ZERO 
						: movementCollectionInventoryItem.getMovementCollection().getInitialValue());
			else
				movementCollectionInventoryItem.setPreviousValue(previous.getCumul() == null ? BigDecimal.ZERO : previous.getCumul());
			
		}
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
