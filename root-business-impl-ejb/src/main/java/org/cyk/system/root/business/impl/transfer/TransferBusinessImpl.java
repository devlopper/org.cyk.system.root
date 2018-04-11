package org.cyk.system.root.business.impl.transfer;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.transfer.TransferBusiness;
import org.cyk.system.root.business.api.transfer.TransferItemCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.transfer.Transfer;
import org.cyk.system.root.persistence.api.transfer.TransferDao;
import org.cyk.utility.common.helper.CollectionHelper;

public class TransferBusinessImpl extends AbstractTypedBusinessService<Transfer, TransferDao> implements TransferBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public TransferBusinessImpl(TransferDao dao) {
		super(dao); 
	}  

	@Override
	public Transfer instanciateOne() {
		return super.instanciateOne().setItems(inject(TransferItemCollectionBusiness.class).instanciateOne())
				.addCascadeOperationToMasterFieldNames(Transfer.FIELD_ITEMS);
	}
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(Transfer identifiable) {
		return CollectionHelper.getInstance().add(super.findRelatedInstanceFieldNames(identifiable), Boolean.TRUE, Transfer.FIELD_ITEMS);
	}
}
