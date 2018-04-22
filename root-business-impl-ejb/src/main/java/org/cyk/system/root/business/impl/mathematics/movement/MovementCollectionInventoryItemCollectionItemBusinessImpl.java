package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionInventoryItemCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItemCollectionItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionInventoryItemCollectionItemDao;

public class MovementCollectionInventoryItemCollectionItemBusinessImpl extends AbstractCollectionItemBusinessImpl<MovementCollectionInventoryItemCollectionItem, MovementCollectionInventoryItemCollectionItemDao,MovementCollectionInventoryItemCollection> implements MovementCollectionInventoryItemCollectionItemBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public MovementCollectionInventoryItemCollectionItemBusinessImpl(MovementCollectionInventoryItemCollectionItemDao dao) {
		super(dao); 
	}
	
}
