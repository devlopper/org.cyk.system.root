package org.cyk.system.root.persistence.impl.mathematics.movement;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.movement.MovementsTransferItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementsTransferItemCollectionItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementsTransferItemCollectionDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;

public class MovementsTransferItemCollectionDaoImpl extends AbstractCollectionDaoImpl<MovementsTransferItemCollection,MovementsTransferItemCollectionItem> implements MovementsTransferItemCollectionDao,Serializable {

	private static final long serialVersionUID = 6152315795314899083L;

}
