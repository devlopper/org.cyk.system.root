package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.business.impl.helper.FieldHelper;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryItemDao;
import org.cyk.utility.common.helper.LoggingHelper;

public class MovementCollectionInventoryBusinessImpl extends AbstractCollectionBusinessImpl<MovementCollectionInventory,MovementCollectionInventoryItem,MovementCollectionInventoryDao,MovementCollectionInventoryItemDao,MovementCollectionInventoryItemBusiness> implements MovementCollectionInventoryBusiness,Serializable {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MovementCollectionInventoryBusinessImpl(MovementCollectionInventoryDao dao) {
        super(dao);
    } 
	
	@Override
	protected void computeChanges(MovementCollectionInventory movementCollectionInventory, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementCollectionInventory, loggingMessageBuilder);
		if(movementCollectionInventory.getMovementGroup()!=null)
			FieldHelper.getInstance().copy(movementCollectionInventory, movementCollectionInventory.getMovementGroup(),Boolean.FALSE
				,org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
						AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE),org.cyk.utility.common.helper.FieldHelper.getInstance().buildPath(
								AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
	}
	
}
