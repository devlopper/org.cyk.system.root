package org.cyk.system.root.persistence.impl.transfer;

import java.io.Serializable;

import org.cyk.system.root.model.transfer.TransferItemCollection;
import org.cyk.system.root.model.transfer.TransferItemCollectionItem;
import org.cyk.system.root.persistence.api.transfer.TransferItemCollectionDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;

public class TransferItemCollectionDaoImpl extends AbstractCollectionDaoImpl<TransferItemCollection,TransferItemCollectionItem> implements TransferItemCollectionDao,Serializable {

	private static final long serialVersionUID = 6152315795314899083L;

}
