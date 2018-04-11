package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementsTransferItemCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementsTransferItemCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.MovementsTransferItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementsTransferItemCollectionItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementsTransferItemCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementsTransferItemCollectionItemDao;

public class MovementsTransferItemCollectionBusinessImpl extends AbstractCollectionBusinessImpl<MovementsTransferItemCollection,MovementsTransferItemCollectionItem,MovementsTransferItemCollectionDao,MovementsTransferItemCollectionItemDao,MovementsTransferItemCollectionItemBusiness> implements MovementsTransferItemCollectionBusiness,Serializable {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MovementsTransferItemCollectionBusinessImpl(MovementsTransferItemCollectionDao dao) {
        super(dao);
    } 

}
