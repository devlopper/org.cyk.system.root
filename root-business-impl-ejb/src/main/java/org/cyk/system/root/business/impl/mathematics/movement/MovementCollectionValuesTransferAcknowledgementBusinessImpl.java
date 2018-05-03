package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;
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
				.setItemsSourceMovementCollectionIsBuffer(Boolean.TRUE);
	}
	
	@Override
	protected void afterCrud(MovementCollectionValuesTransferAcknowledgement movementsTransferAcknowledgement, Crud crud) {
		super.afterCrud(movementsTransferAcknowledgement, crud);
		if(Crud.isCreateOrUpdate(crud)){
			if(Crud.CREATE.equals(crud)){
				Collection<MovementCollectionValuesTransferItemCollectionItem> transfered = inject(MovementCollectionValuesTransferItemCollectionItemDao.class).readByCollection(movementsTransferAcknowledgement.getTransfer().getItems());
				Collection<MovementCollectionValuesTransferItemCollectionItem> acknownledged = inject(MovementCollectionValuesTransferItemCollectionItemDao.class).readByCollection(movementsTransferAcknowledgement.getItems());
				for(MovementCollectionValuesTransferItemCollectionItem indexTransfered : transfered){
					for(MovementCollectionValuesTransferItemCollectionItem indexAcknownledged : acknownledged){
						BigDecimal gap = indexTransfered.getValue().subtract(indexAcknownledged.getValue());
						if(NumberHelper.getInstance().isGreaterThanZero(gap)){
							inject(MovementBusiness.class).create(instanciateOne(Movement.class).setCollection(indexTransfered.getSource().getCollection())
									.setValue(gap).setActionFromValue().setReasonFromCode(RootConstant.Code.MovementReason.TRANSFER_BACK));
							inject(MovementBusiness.class).create(instanciateOne(Movement.class).setCollection(indexAcknownledged.getSource().getCollection())
									.setValue(gap.negate()).setActionFromValue().setReasonFromCode(RootConstant.Code.MovementReason.TRANSFER_BACK));
						}
					}	
				}
			}
		}
	}
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(MovementCollectionValuesTransferAcknowledgement identifiable) {
		return CollectionHelper.getInstance().add(super.findRelatedInstanceFieldNames(identifiable), Boolean.TRUE, MovementCollectionValuesTransferAcknowledgement.FIELD_ITEMS);
	}
	
	@Override
	protected void computeChanges(MovementCollectionValuesTransferAcknowledgement movementCollectionValuesTransferAcknowledgement, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementCollectionValuesTransferAcknowledgement, loggingMessageBuilder);
		if(Boolean.TRUE.equals(movementCollectionValuesTransferAcknowledgement.getItems().getItems().isSynchonizationEnabled())) {
			if(movementCollectionValuesTransferAcknowledgement.getItems().getItems().isEmpty()){
				for(MovementCollectionValuesTransferItemCollectionItem index : inject(MovementCollectionValuesTransferItemCollectionItemDao.class).readByCollection(movementCollectionValuesTransferAcknowledgement.getTransfer().getItems())){					
					movementCollectionValuesTransferAcknowledgement.getItems().getItems().addOne(instanciateOne(MovementCollectionValuesTransferItemCollectionItem.class)
							.setTransfered(index).setSource(instanciateOne(Movement.class).setCollection(index.getDestination().getCollection())
									.setReasonFromCode(RootConstant.Code.MovementReason.TRANSFER_ACKNOWLEDGMENT).setValueSettableFromAbsolute(Boolean.TRUE)
									.setActionFromIncrementation(Boolean.FALSE)));
				}
			}
			
			for(MovementCollectionValuesTransferItemCollectionItem index : movementCollectionValuesTransferAcknowledgement.getItems().getItems().getElements()){
				if(index.getSource()!=null)
					index.getSource().setReasonFromCode(RootConstant.Code.MovementReason.TRANSFER_ACKNOWLEDGMENT);
				if(index.getDestination()!=null)
					index.getDestination().setReasonFromCode(RootConstant.Code.MovementReason.TRANSFER_ACKNOWLEDGMENT);
			}
			
			
		}
	}
	
}
