package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.mathematics.movement.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementsTransferItemCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementsTransferItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementsTransferItemCollectionItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementsTransferItemCollectionItemDao;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.LoggingHelper;

public class MovementsTransferItemCollectionItemBusinessImpl extends AbstractCollectionItemBusinessImpl<MovementsTransferItemCollectionItem, MovementsTransferItemCollectionItemDao,MovementsTransferItemCollection> implements MovementsTransferItemCollectionItemBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public MovementsTransferItemCollectionItemBusinessImpl(MovementsTransferItemCollectionItemDao dao) {
		super(dao); 
	}
	
	@Override
	protected void beforeCrud(MovementsTransferItemCollectionItem movementsTransferItemCollectionItem, Crud crud) {
		super.beforeCrud(movementsTransferItemCollectionItem, crud);
		if(Crud.isCreateOrUpdate(crud)){
			if(inject(MovementBusiness.class).isNotIdentified(movementsTransferItemCollectionItem.getSource()))
				inject(MovementBusiness.class).create(movementsTransferItemCollectionItem.getSource());
			if(inject(MovementBusiness.class).isNotIdentified(movementsTransferItemCollectionItem.getDestination()))
				inject(MovementBusiness.class).create(movementsTransferItemCollectionItem.getDestination());	
		}
	}
	
	@Override
	protected void afterDelete(MovementsTransferItemCollectionItem movementsTransferItemCollectionItem) {
		super.afterDelete(movementsTransferItemCollectionItem);
		inject(MovementBusiness.class).create(instanciateOne(Movement.class).setCollectionAndValueUsingOppositeAndAction(movementsTransferItemCollectionItem.getSource()));
		inject(MovementBusiness.class).create(instanciateOne(Movement.class).setCollectionAndValueUsingOppositeAndAction(movementsTransferItemCollectionItem.getDestination()));		
	}
	
	@Override
	protected void computeChanges(MovementsTransferItemCollectionItem movementsTransferItemCollectionItem, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementsTransferItemCollectionItem, loggingMessageBuilder);
		if(movementsTransferItemCollectionItem.getSource() == null){
			movementsTransferItemCollectionItem.setSource(inject(MovementBusiness.class).instanciateOne()
					.setCollection(movementsTransferItemCollectionItem.getSourceMovementCollection()).setValueSettableFromAbsolute(Boolean.TRUE)
					.setValueAbsolute(movementsTransferItemCollectionItem.getValue()).setActionFromIncrementation(Boolean.FALSE));			
		}
		
		if(movementsTransferItemCollectionItem.getDestination() == null){
			movementsTransferItemCollectionItem.setDestination(inject(MovementBusiness.class).instanciateOne()
					.setCollection(movementsTransferItemCollectionItem.getDestinationMovementCollection()).setValueSettableFromAbsolute(Boolean.TRUE)
					.setValueAbsolute(movementsTransferItemCollectionItem.getValue()).setActionFromIncrementation(Boolean.TRUE));
		}
		
		if(movementsTransferItemCollectionItem.getSource() != null && movementsTransferItemCollectionItem.getDestination()!=null) {
			throw__(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldObject(movementsTransferItemCollectionItem.getDestination())
					.setFieldName(Movement.FIELD_VALUE).setValue2((movementsTransferItemCollectionItem.getSource().getValue() == null ? movementsTransferItemCollectionItem.getValue()
							:  movementsTransferItemCollectionItem.getSource().getValue()).negate()).setEqual(Boolean.FALSE));	
		}
		
	}
	
}
