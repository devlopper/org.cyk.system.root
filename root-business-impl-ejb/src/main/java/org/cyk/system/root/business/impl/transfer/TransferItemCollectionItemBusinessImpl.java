package org.cyk.system.root.business.impl.transfer;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.transfer.TransferItemCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.transfer.TransferItemCollection;
import org.cyk.system.root.model.transfer.TransferItemCollectionItem;
import org.cyk.system.root.persistence.api.transfer.TransferItemCollectionItemDao;

public class TransferItemCollectionItemBusinessImpl extends AbstractCollectionItemBusinessImpl<TransferItemCollectionItem, TransferItemCollectionItemDao,TransferItemCollection> implements TransferItemCollectionItemBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public TransferItemCollectionItemBusinessImpl(TransferItemCollectionItemDao dao) {
		super(dao); 
	}
	
}
