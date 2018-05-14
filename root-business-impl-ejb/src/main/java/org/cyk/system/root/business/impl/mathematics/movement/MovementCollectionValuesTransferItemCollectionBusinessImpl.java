package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionValuesTransferItemCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionValuesTransferItemCollectionItemBusiness;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollectionItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionValuesTransferItemCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionValuesTransferItemCollectionItemDao;
import org.cyk.utility.common.helper.FieldHelper;

public class MovementCollectionValuesTransferItemCollectionBusinessImpl extends AbstractMovementCollectionsBusinessImpl<MovementCollectionValuesTransferItemCollection,MovementCollectionValuesTransferItemCollectionItem,MovementCollectionValuesTransferItemCollectionDao,MovementCollectionValuesTransferItemCollectionItemDao,MovementCollectionValuesTransferItemCollectionItemBusiness> implements MovementCollectionValuesTransferItemCollectionBusiness,Serializable {
	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MovementCollectionValuesTransferItemCollectionBusinessImpl(MovementCollectionValuesTransferItemCollectionDao dao) {
        super(dao);
    } 
	
	@Override
	protected String getMovementCollectionFieldName() {
		return FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_COLLECTION);
	}
}
