package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionValuesTransferBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionValuesTransferItemCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransfer;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollectionItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionValuesTransferDao;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.LoggingHelper;

public class MovementCollectionValuesTransferBusinessImpl extends AbstractTypedBusinessService<MovementCollectionValuesTransfer, MovementCollectionValuesTransferDao> implements MovementCollectionValuesTransferBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MovementCollectionValuesTransferBusinessImpl(MovementCollectionValuesTransferDao dao) {
		super(dao); 
	}  

	@Override
	public MovementCollectionValuesTransfer instanciateOne() {
		return super.instanciateOne().setItems(instanciateOne(MovementCollectionValuesTransferItemCollection.class).setItemsSynchonizationEnabled(Boolean.TRUE))
				.addCascadeOperationToMasterFieldNames(MovementCollectionValuesTransfer.FIELD_ITEMS).setItemsDestinationMovementCollectionIsBuffer(Boolean.TRUE)
				.__setBirthDateComputedByUser__(Boolean.FALSE);
	}
	
	@Override
	protected void beforeCrud(MovementCollectionValuesTransfer movementCollectionValuesTransfer, Crud crud) {
		super.beforeCrud(movementCollectionValuesTransfer, crud);
		movementCollectionValuesTransfer.addIdentifiablesPartyIdentifiableGlobalIdentifierFromField(MovementCollectionValuesTransfer.FIELD_SENDER,RootConstant.Code.BusinessRole.SENDER);
		movementCollectionValuesTransfer.addIdentifiablesPartyIdentifiableGlobalIdentifierFromField(MovementCollectionValuesTransfer.FIELD_RECEIVER,RootConstant.Code.BusinessRole.RECEIVER);
	}
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(MovementCollectionValuesTransfer identifiable) {
		return CollectionHelper.getInstance().add(super.findRelatedInstanceFieldNames(identifiable), Boolean.TRUE, MovementCollectionValuesTransfer.FIELD_ITEMS);
	}
	
	@Override
	protected void computeChanges(MovementCollectionValuesTransfer movementCollectionValuesTransfer, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementCollectionValuesTransfer, loggingMessageBuilder);
		inject(MovementCollectionValuesTransferItemCollectionBusiness.class).computeChanges(movementCollectionValuesTransfer.getItems());
		if(Boolean.TRUE.equals(movementCollectionValuesTransfer.getItems().getItems().isSynchonizationEnabled()))
			for(MovementCollectionValuesTransferItemCollectionItem index : movementCollectionValuesTransfer.getItems().getItems().getElements()){
				if(index.getSource()!=null)
					index.getSource().setReasonFromCode(RootConstant.Code.MovementReason.TRANSFER);
				if(index.getDestination()!=null)
					index.getDestination().setReasonFromCode(RootConstant.Code.MovementReason.TRANSFER);
			}
				
	}
}
