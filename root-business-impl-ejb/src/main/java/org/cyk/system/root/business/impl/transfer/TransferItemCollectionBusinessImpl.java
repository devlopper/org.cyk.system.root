package org.cyk.system.root.business.impl.transfer;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.transfer.TransferItemCollectionBusiness;
import org.cyk.system.root.business.api.transfer.TransferItemCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.transfer.TransferItemCollection;
import org.cyk.system.root.model.transfer.TransferItemCollectionItem;
import org.cyk.system.root.persistence.api.transfer.TransferItemCollectionDao;
import org.cyk.system.root.persistence.api.transfer.TransferItemCollectionItemDao;

public class TransferItemCollectionBusinessImpl extends AbstractCollectionBusinessImpl<TransferItemCollection,TransferItemCollectionItem,TransferItemCollectionDao,TransferItemCollectionItemDao,TransferItemCollectionItemBusiness> implements TransferItemCollectionBusiness,Serializable {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public TransferItemCollectionBusinessImpl(TransferItemCollectionDao dao) {
        super(dao);
    } 

}
