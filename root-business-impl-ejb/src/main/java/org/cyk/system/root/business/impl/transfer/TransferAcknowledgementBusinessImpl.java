package org.cyk.system.root.business.impl.transfer;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.transfer.TransferAcknowledgementBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.transfer.TransferAcknowledgement;
import org.cyk.system.root.persistence.api.transfer.TransferAcknowledgementDao;

public class TransferAcknowledgementBusinessImpl extends AbstractTypedBusinessService<TransferAcknowledgement, TransferAcknowledgementDao> implements TransferAcknowledgementBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public TransferAcknowledgementBusinessImpl(TransferAcknowledgementDao dao) {
		super(dao); 
	}  
			
}
