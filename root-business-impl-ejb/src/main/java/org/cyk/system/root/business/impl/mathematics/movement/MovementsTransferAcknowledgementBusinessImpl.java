package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.mathematics.movement.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementsTransferAcknowledgementBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementsTransferItemCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementsTransferAcknowledgement;
import org.cyk.system.root.model.mathematics.movement.MovementsTransferItemCollectionItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementsTransferAcknowledgementDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementsTransferItemCollectionItemDao;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.NumberHelper;

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
	protected void afterCrud(MovementsTransferAcknowledgement movementsTransferAcknowledgement, Crud crud) {
		super.afterCrud(movementsTransferAcknowledgement, crud);
		if(Crud.isCreateOrUpdate(crud)){
			if(Crud.CREATE.equals(crud)){
				Collection<MovementsTransferItemCollectionItem> transfered = inject(MovementsTransferItemCollectionItemDao.class).readByCollection(movementsTransferAcknowledgement.getMovementsTransfer().getItems());
				Collection<MovementsTransferItemCollectionItem> acknownledged = inject(MovementsTransferItemCollectionItemDao.class).readByCollection(movementsTransferAcknowledgement.getItems());
				for(MovementsTransferItemCollectionItem indexTransfered : transfered){
					for(MovementsTransferItemCollectionItem indexAcknownledged : acknownledged){
						BigDecimal gap = indexTransfered.getValue().subtract(indexAcknownledged.getValue());
						if(NumberHelper.getInstance().isGreaterThanZero(gap)){
							inject(MovementBusiness.class).create(instanciateOne(Movement.class).setCollection(indexTransfered.getSourceMovementCollection())
									.setValue(gap).setActionFromValue());
							inject(MovementBusiness.class).create(instanciateOne(Movement.class).setCollection(indexAcknownledged.getSourceMovementCollection())
									.setValue(gap.negate()).setActionFromValue());
						}
					}	
				}
			}
		}
	}
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(MovementsTransferAcknowledgement identifiable) {
		return CollectionHelper.getInstance().add(super.findRelatedInstanceFieldNames(identifiable), Boolean.TRUE, MovementsTransferAcknowledgement.FIELD_ITEMS);
	}
	
}
