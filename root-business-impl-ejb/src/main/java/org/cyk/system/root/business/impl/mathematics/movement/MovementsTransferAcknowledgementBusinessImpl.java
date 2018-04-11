package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementsTransferAcknowledgementBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementsTransferItemCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.mathematics.movement.MovementsTransferAcknowledgement;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementsTransferAcknowledgementDao;
import org.cyk.utility.common.helper.CollectionHelper;

public class MovementsTransferAcknowledgementBusinessImpl extends AbstractTypedBusinessService<MovementsTransferAcknowledgement, MovementsTransferAcknowledgementDao> implements MovementsTransferAcknowledgementBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MovementsTransferAcknowledgementBusinessImpl(MovementsTransferAcknowledgementDao dao) {
		super(dao); 
	}  
	
	@Override
	public MovementsTransferAcknowledgement instanciateOne() {
		return super.instanciateOne().setItems(inject(MovementsTransferItemCollectionBusiness.class).instanciateOne().setItemsSynchonizationEnabled(Boolean.TRUE))
				.addCascadeOperationToMasterFieldNames(MovementsTransferAcknowledgement.FIELD_ITEMS);
	}
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(MovementsTransferAcknowledgement identifiable) {
		return CollectionHelper.getInstance().add(super.findRelatedInstanceFieldNames(identifiable), Boolean.TRUE, MovementsTransferAcknowledgement.FIELD_ITEMS);
	}
	
}
