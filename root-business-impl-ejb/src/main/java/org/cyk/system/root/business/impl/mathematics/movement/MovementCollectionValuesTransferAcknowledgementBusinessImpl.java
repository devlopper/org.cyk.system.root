package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.mathematics.movement.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionValuesTransferAcknowledgementBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionValuesTransferItemCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferAcknowledgement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollectionItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionValuesTransferAcknowledgementDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionValuesTransferItemCollectionItemDao;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.NumberHelper;

public class MovementCollectionValuesTransferAcknowledgementBusinessImpl extends AbstractTypedBusinessService<MovementCollectionValuesTransferAcknowledgement, MovementCollectionValuesTransferAcknowledgementDao> implements MovementCollectionValuesTransferAcknowledgementBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MovementCollectionValuesTransferAcknowledgementBusinessImpl(MovementCollectionValuesTransferAcknowledgementDao dao) {
		super(dao); 
	}  
	
	@Override
	public MovementCollectionValuesTransferAcknowledgement instanciateOne() {
		return super.instanciateOne().setItems(inject(MovementCollectionValuesTransferItemCollectionBusiness.class).instanciateOne()
				.setItemsSynchonizationEnabled(Boolean.TRUE)).addCascadeOperationToMasterFieldNames(MovementCollectionValuesTransferAcknowledgement.FIELD_ITEMS)
				.setItemsSourceMovementCollectionIsBuffer(Boolean.TRUE)
				.setItemsMustBeSubSetOfTransferItems(Boolean.TRUE)
				.__setBirthDateComputedByUser__(Boolean.FALSE);
	}
	
	@Override
	protected void afterCrud(MovementCollectionValuesTransferAcknowledgement movementsTransferAcknowledgement, Crud crud) {
		super.afterCrud(movementsTransferAcknowledgement, crud);
		if(Crud.isCreateOrUpdate(crud)){
			if(Crud.CREATE.equals(crud)){
				Collection<MovementCollectionValuesTransferItemCollectionItem> acknownledged = inject(MovementCollectionValuesTransferItemCollectionItemDao.class).readByCollection(movementsTransferAcknowledgement.getItems());
				for(MovementCollectionValuesTransferItemCollectionItem index : acknownledged){
					BigDecimal gap = index.getTransfered().getValue().subtract(index.getValue());
					if(NumberHelper.getInstance().isGreaterThanZero(gap)){
						inject(MovementBusiness.class).create(instanciateOne(Movement.class).setCollection(index.getTransfered().getSource().getCollection())
								.setValue(gap).setActionFromValue().setReasonFromCode(RootConstant.Code.MovementReason.TRANSFER_BACK));
						inject(MovementBusiness.class).create(instanciateOne(Movement.class).setCollection(index.getSource().getCollection())
								.setValue(gap.negate()).setActionFromValue().setReasonFromCode(RootConstant.Code.MovementReason.TRANSFER_BACK));
					}
				}	
			}
		}
	}
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(MovementCollectionValuesTransferAcknowledgement identifiable) {
		return CollectionHelper.getInstance().add(super.findRelatedInstanceFieldNames(identifiable), Boolean.TRUE, MovementCollectionValuesTransferAcknowledgement.FIELD_ITEMS);
	}
	
	protected void addOneItem(MovementCollectionValuesTransferAcknowledgement movementCollectionValuesTransferAcknowledgement,MovementCollectionValuesTransferItemCollectionItem transfered){
		movementCollectionValuesTransferAcknowledgement.getItems().getItems().addOne(instanciateOne(MovementCollectionValuesTransferItemCollectionItem.class)
				.setTransfered(transfered).setSource(instanciateOne(Movement.class).setCollection(transfered.getDestination().getCollection())
						.setReasonFromCode(RootConstant.Code.MovementReason.TRANSFER_ACKNOWLEDGMENT).setValueSettableFromAbsolute(Boolean.TRUE)
						.setActionFromIncrementation(Boolean.FALSE)));
	}
	
	@Override
	protected void computeChanges(MovementCollectionValuesTransferAcknowledgement movementCollectionValuesTransferAcknowledgement, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementCollectionValuesTransferAcknowledgement, loggingMessageBuilder);
		
		if(Boolean.TRUE.equals(movementCollectionValuesTransferAcknowledgement.getItems().getItems().isSynchonizationEnabled())) {
			Collection<MovementCollectionValuesTransferItemCollectionItem> transferedItems = movementCollectionValuesTransferAcknowledgement.getTransfer() == null ? null
					: inject(MovementCollectionValuesTransferItemCollectionItemDao.class).readByCollection(movementCollectionValuesTransferAcknowledgement.getTransfer().getItems());
			Collection<MovementCollectionValuesTransferItemCollectionItem> acknowledgedItems = movementCollectionValuesTransferAcknowledgement.getItems().getItems().getElements();
			
			if(movementCollectionValuesTransferAcknowledgement.getItems().getItems().isEmpty()){				
				//
				if(Boolean.TRUE.equals(movementCollectionValuesTransferAcknowledgement.getItemsMustBeSubSetOfTransferItems())){
					//add all transfered items
					if(Boolean.TRUE.equals(movementCollectionValuesTransferAcknowledgement.getItems().getItems().getHasAlreadyContainedElements())){
						
					}else{
						if(CollectionHelper.getInstance().isEmpty(transferedItems)){
							
						}else{
							for(MovementCollectionValuesTransferItemCollectionItem index : transferedItems){		
								addOneItem(movementCollectionValuesTransferAcknowledgement, index);
								/*movementCollectionValuesTransferAcknowledgement.getItems().getItems().addOne(instanciateOne(MovementCollectionValuesTransferItemCollectionItem.class)
										.setTransfered(index).setSource(instanciateOne(Movement.class).setCollection(index.getDestination().getCollection())
												.setReasonFromCode(RootConstant.Code.MovementReason.TRANSFER_ACKNOWLEDGMENT).setValueSettableFromAbsolute(Boolean.TRUE)
												.setActionFromIncrementation(Boolean.FALSE)));
								*/
							}		
						}
						
					}
					
				}
			}else {
				//clean acknowledged items
				if(Boolean.TRUE.equals(movementCollectionValuesTransferAcknowledgement.getItemsMustBeSubSetOfTransferItems())){
					Collection<MovementCollectionValuesTransferItemCollectionItem> notTransferedItems = new ArrayList<>();
					if(CollectionHelper.getInstance().isEmpty(transferedItems)){
						
					}else {
						//remove those not transfered	
						for(MovementCollectionValuesTransferItemCollectionItem index : acknowledgedItems){
							Boolean transfered = Boolean.FALSE;
							if(index.getSource()!=null && index.getSource().getCollection()!=null){								
								for(MovementCollectionValuesTransferItemCollectionItem indexTransferedItem : transferedItems){
									if(index.getSource().getCollection().equals(indexTransferedItem.getDestination().getCollection())){
										transfered = Boolean.TRUE;
										break;
									}
								}
								if(Boolean.FALSE.equals(transfered))
									notTransferedItems.add(index);
							}
						}
					}
					
					CollectionHelper.getInstance().remove(acknowledgedItems, notTransferedItems);
					notTransferedItems.clear();
					
					//remove those where value is zero
					for(MovementCollectionValuesTransferItemCollectionItem index : acknowledgedItems)
						if(index.getSource()!=null && NumberHelper.getInstance().isZero(index.getSource().getValue()))
							notTransferedItems.add(index);						
					CollectionHelper.getInstance().remove(acknowledgedItems, notTransferedItems);
					notTransferedItems.clear();
					
					//add those not belonging to items
					for(MovementCollectionValuesTransferItemCollectionItem index : transferedItems){
						Boolean found = Boolean.FALSE;
						for(MovementCollectionValuesTransferItemCollectionItem acknowlegdedIndex : acknowledgedItems){
							if(acknowlegdedIndex.getSource().getCollection().equals(index.getDestination().getCollection())){
								found = Boolean.TRUE;
								break;
							}
						}
						if(Boolean.FALSE.equals(found))
							addOneItem(movementCollectionValuesTransferAcknowledgement, index);
						
					}
				}
			}
			
			inject(MovementCollectionValuesTransferItemCollectionBusiness.class).computeChanges(movementCollectionValuesTransferAcknowledgement.getItems());
			
			for(MovementCollectionValuesTransferItemCollectionItem index : movementCollectionValuesTransferAcknowledgement.getItems().getItems().getElements()){
				if(index.getSource()!=null)
					index.getSource().setReasonFromCode(RootConstant.Code.MovementReason.TRANSFER_ACKNOWLEDGMENT);
				if(index.getDestination()!=null)
					index.getDestination().setReasonFromCode(RootConstant.Code.MovementReason.TRANSFER_ACKNOWLEDGMENT);
			}
			
		}
	}
	
}
