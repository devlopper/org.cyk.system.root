package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryItemDao;

public class MovementCollectionInventoryBusinessImpl extends AbstractCollectionBusinessImpl<MovementCollectionInventory,MovementCollectionInventoryItem,MovementCollectionInventoryDao,MovementCollectionInventoryItemDao,MovementCollectionInventoryItemBusiness> implements MovementCollectionInventoryBusiness,Serializable {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MovementCollectionInventoryBusinessImpl(MovementCollectionInventoryDao dao) {
        super(dao);
    } 
	
}
