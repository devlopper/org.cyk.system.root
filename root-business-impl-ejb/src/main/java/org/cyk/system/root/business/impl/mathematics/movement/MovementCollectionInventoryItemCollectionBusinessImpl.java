package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryItemCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryItemCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItemCollectionItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryItemCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryItemCollectionItemDao;

public class MovementCollectionInventoryItemCollectionBusinessImpl extends AbstractCollectionBusinessImpl<MovementCollectionInventoryItemCollection,MovementCollectionInventoryItemCollectionItem,MovementCollectionInventoryItemCollectionDao,MovementCollectionInventoryItemCollectionItemDao,MovementCollectionInventoryItemCollectionItemBusiness> implements MovementCollectionInventoryItemCollectionBusiness,Serializable {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MovementCollectionInventoryItemCollectionBusinessImpl(MovementCollectionInventoryItemCollectionDao dao) {
        super(dao);
    } 
	
}
