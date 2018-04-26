package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionValuesTransferBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransfer;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollection;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionValuesTransferDao;
import org.cyk.utility.common.helper.CollectionHelper;

public class MovementCollectionValuesTransferBusinessImpl extends AbstractTypedBusinessService<MovementCollectionValuesTransfer, MovementCollectionValuesTransferDao> implements MovementCollectionValuesTransferBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MovementCollectionValuesTransferBusinessImpl(MovementCollectionValuesTransferDao dao) {
		super(dao); 
	}  

	@Override
	public MovementCollectionValuesTransfer instanciateOne() {
		return super.instanciateOne().setItems(instanciateOne(MovementCollectionValuesTransferItemCollection.class).setItemsSynchonizationEnabled(Boolean.TRUE))
				.addCascadeOperationToMasterFieldNames(MovementCollectionValuesTransfer.FIELD_ITEMS).setItemsDestinationMovementCollectionIsBuffer(Boolean.TRUE);
	}
	
	@Override
	protected void beforeCrud(MovementCollectionValuesTransfer movementsTransfer, Crud crud) {
		super.beforeCrud(movementsTransfer, crud);
		movementsTransfer.addIdentifiablesPartyIdentifiableGlobalIdentifierFromField(MovementCollectionValuesTransfer.FIELD_SENDER,RootConstant.Code.BusinessRole.SENDER);
		movementsTransfer.addIdentifiablesPartyIdentifiableGlobalIdentifierFromField(MovementCollectionValuesTransfer.FIELD_RECEIVER,RootConstant.Code.BusinessRole.RECEIVER);
	}
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(MovementCollectionValuesTransfer identifiable) {
		return CollectionHelper.getInstance().add(super.findRelatedInstanceFieldNames(identifiable), Boolean.TRUE, MovementCollectionValuesTransfer.FIELD_ITEMS);
	}
	
	/*@Override
	protected void computeChanges(MovementCollectionValuesTransfer movementCollectionValuesTransfer, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementCollectionValuesTransfer, loggingMessageBuilder);
		FieldHelper.getInstance().copy(movementCollectionValuesTransfer, movementCollectionValuesTransfer.getPartyCompany(),Boolean.FALSE
				,org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
						AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE),org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
								AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
	}*/
}
