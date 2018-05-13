package org.cyk.system.root.persistence.impl.mathematics.movement;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryDao;

public class MovementCollectionInventoryDaoImpl extends AbstractMovementCollectionsDaoImpl<MovementCollectionInventory,MovementCollectionInventoryItem> implements MovementCollectionInventoryDao,Serializable {
	private static final long serialVersionUID = 6152315795314899083L;

}
