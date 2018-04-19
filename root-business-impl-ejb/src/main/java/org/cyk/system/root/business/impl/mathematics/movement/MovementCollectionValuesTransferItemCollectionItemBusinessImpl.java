package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.mathematics.movement.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionValuesTransferItemCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollectionItem;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionValuesTransferItemCollectionItemDao;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.LoggingHelper;

public class MovementCollectionValuesTransferItemCollectionItemBusinessImpl extends AbstractCollectionItemBusinessImpl<MovementCollectionValuesTransferItemCollectionItem, MovementCollectionValuesTransferItemCollectionItemDao,MovementCollectionValuesTransferItemCollection> implements MovementCollectionValuesTransferItemCollectionItemBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public MovementCollectionValuesTransferItemCollectionItemBusinessImpl(MovementCollectionValuesTransferItemCollectionItemDao dao) {
		super(dao); 
	}
	
	@Override
	public MovementCollectionValuesTransferItemCollectionItem instanciateOne() {
		return super.instanciateOne().setSource(instanciateOne(Movement.class).setValueSettableFromAbsolute(Boolean.TRUE).setActionFromIncrementation(Boolean.FALSE))
				.setDestination(instanciateOne(Movement.class).setValueSettableFromAbsolute(Boolean.TRUE).setActionFromIncrementation(Boolean.TRUE));
	}
	
	@Override
	protected void beforeCrud(MovementCollectionValuesTransferItemCollectionItem movementsTransferItemCollectionItem, Crud crud) {
		super.beforeCrud(movementsTransferItemCollectionItem, crud);
		if(Crud.isCreateOrUpdate(crud)){
			if(inject(MovementBusiness.class).isNotIdentified(movementsTransferItemCollectionItem.getSource()))
				inject(MovementBusiness.class).create(movementsTransferItemCollectionItem.getSource());
			if(inject(MovementBusiness.class).isNotIdentified(movementsTransferItemCollectionItem.getDestination()))
				inject(MovementBusiness.class).create(movementsTransferItemCollectionItem.getDestination());	
		}
	}
	
	@Override
	protected void afterDelete(MovementCollectionValuesTransferItemCollectionItem movementsTransferItemCollectionItem) {
		super.afterDelete(movementsTransferItemCollectionItem);
		inject(MovementBusiness.class).create(instanciateOne(Movement.class).setCollectionAndValueUsingOppositeAndAction(movementsTransferItemCollectionItem.getSource()));
		inject(MovementBusiness.class).create(instanciateOne(Movement.class).setCollectionAndValueUsingOppositeAndAction(movementsTransferItemCollectionItem.getDestination()));		
	}
	
	@Override
	protected void computeChanges(MovementCollectionValuesTransferItemCollectionItem movementsTransferItemCollectionItem, LoggingHelper.Message.Builder loggingMessageBuilder) {
		super.computeChanges(movementsTransferItemCollectionItem, loggingMessageBuilder);
		
		if(movementsTransferItemCollectionItem.getSource() != null){
			if(Boolean.TRUE.equals(movementsTransferItemCollectionItem.getSource().getValueSettableFromAbsolute())){
				if(movementsTransferItemCollectionItem.getSource().getValueAbsolute() == null && movementsTransferItemCollectionItem.getDestination()!=null)
					movementsTransferItemCollectionItem.getSource().setValueAbsolute(movementsTransferItemCollectionItem.getDestination().getValueAbsolute());
			}else {
				if(movementsTransferItemCollectionItem.getSource().getValue() == null && movementsTransferItemCollectionItem.getDestination().getValue()!=null)
					movementsTransferItemCollectionItem.getSource().setValue(movementsTransferItemCollectionItem.getDestination().getValue().abs());
			}
			inject(MovementBusiness.class).computeChanges(movementsTransferItemCollectionItem.getSource());
		}
		
		if(movementsTransferItemCollectionItem.getDestination() != null){
			if(Boolean.TRUE.equals(movementsTransferItemCollectionItem.getDestination().getValueSettableFromAbsolute())){
				if(movementsTransferItemCollectionItem.getDestination().getValueAbsolute() == null && movementsTransferItemCollectionItem.getSource()!=null)
					movementsTransferItemCollectionItem.getDestination().setValueAbsolute(movementsTransferItemCollectionItem.getSource().getValueAbsolute());
			}else {
				if(movementsTransferItemCollectionItem.getDestination().getValue() == null && movementsTransferItemCollectionItem.getSource().getValue()!=null)
					movementsTransferItemCollectionItem.getDestination().setValue(movementsTransferItemCollectionItem.getSource().getValue().abs());
			}
			inject(MovementBusiness.class).computeChanges(movementsTransferItemCollectionItem.getDestination());
		}
		
		if(movementsTransferItemCollectionItem.getSource() != null && movementsTransferItemCollectionItem.getDestination()!=null) {
			System.out
					.println("MovementCollectionValuesTransferItemCollectionItemBusinessImpl.computeChanges()");
			System.out.println(movementsTransferItemCollectionItem.getSource().getValue() == null);
			System.out.println(movementsTransferItemCollectionItem.getValue());
			System.out.println(movementsTransferItemCollectionItem.getSource().getValue());
			throw__(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldObject(movementsTransferItemCollectionItem.getDestination())
					.setFieldName(Movement.FIELD_VALUE).setValue2((movementsTransferItemCollectionItem.getSource().getValue() == null ? movementsTransferItemCollectionItem.getValue()
							:  movementsTransferItemCollectionItem.getSource().getValue()).negate()).setEqual(Boolean.FALSE));	
		}
		
	}
	
}
