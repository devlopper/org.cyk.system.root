package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementsTransferBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementsTransferItemCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.mathematics.movement.MovementsTransfer;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementsTransferDao;
import org.cyk.utility.common.helper.CollectionHelper;

public class MovementsTransferBusinessImpl extends AbstractTypedBusinessService<MovementsTransfer, MovementsTransferDao> implements MovementsTransferBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MovementsTransferBusinessImpl(MovementsTransferDao dao) {
		super(dao); 
	}  

	@Override
	public MovementsTransfer instanciateOne() {
		return super.instanciateOne().setItems(inject(MovementsTransferItemCollectionBusiness.class).instanciateOne().setItemsSynchonizationEnabled(Boolean.TRUE))
				.addCascadeOperationToMasterFieldNames(MovementsTransfer.FIELD_ITEMS);
	}
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(MovementsTransfer identifiable) {
		return CollectionHelper.getInstance().add(super.findRelatedInstanceFieldNames(identifiable), Boolean.TRUE, MovementsTransfer.FIELD_ITEMS);
	}
}
