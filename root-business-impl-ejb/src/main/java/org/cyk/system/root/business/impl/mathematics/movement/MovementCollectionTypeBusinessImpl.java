package org.cyk.system.root.business.impl.mathematics.movement;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementActionBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;
import org.cyk.system.root.persistence.api.mathematics.IntervalDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementActionDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionTypeDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.helper.FieldHelper;
 
public class MovementCollectionTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<MovementCollectionType,MovementCollectionTypeDao> implements MovementCollectionTypeBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MovementCollectionTypeBusinessImpl(MovementCollectionTypeDao dao) {
        super(dao);
    } 
	
	@Override
	protected MovementCollectionType __instanciateOne__(ObjectFieldValues objectFieldValues) {
		MovementCollectionType movementCollectionType = super.__instanciateOne__(objectFieldValues);
		movementCollectionType.setInterval(inject(IntervalDao.class).read(RootConstant.Code.Interval.MOVEMENT_COLLECTION_VALUE));
		movementCollectionType.setIncrementAction(inject(MovementActionDao.class).read(RootConstant.Code.MovementAction.INCREMENT));
		movementCollectionType.setDecrementAction(inject(MovementActionDao.class).read(RootConstant.Code.MovementAction.DECREMENT));
		return movementCollectionType;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovementCollectionType instanciateOne(String code,String name,String incrementActionName,String decrementActionName) {
		MovementCollectionType movementCollectionType = instanciateOne(code);
		movementCollectionType.setInterval(inject(IntervalBusiness.class).instanciateOne(code, "0"));
		movementCollectionType.setIncrementAction(inject(MovementActionBusiness.class)
				.instanciateOne(code+Constant.CHARACTER_UNDESCORE+RootConstant.Code.generateFromString(incrementActionName), incrementActionName));
		movementCollectionType.setDecrementAction(inject(MovementActionBusiness.class)
				.instanciateOne(code+Constant.CHARACTER_UNDESCORE+RootConstant.Code.generateFromString(decrementActionName), decrementActionName));
		movementCollectionType.getDecrementAction().getInterval().getHigh().setValue(new BigDecimal("-0.001"));
		movementCollectionType.getDecrementAction().getInterval().getLow().setValue(null);
		movementCollectionType.getDecrementAction().getInterval().getLow().setExcluded(Boolean.TRUE);
		return movementCollectionType;
	}
	
	@Override
	protected void beforeCrud(MovementCollectionType movementCollectionType, Crud crud) {
		super.beforeCrud(movementCollectionType, crud);
		if(Crud.isCreateOrUpdate(crud)){
			createIfNotIdentified(movementCollectionType.getInterval());
			createIfNotIdentified(movementCollectionType.getIncrementAction());
			createIfNotIdentified(movementCollectionType.getDecrementAction());
		}
	}
	
	/**/
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<MovementCollectionType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(MovementCollectionType.class);
			addParameterArrayElementString(2, MovementCollectionType.FIELD_INTERVAL);
			addParameterArrayElementString(3, MovementCollectionType.FIELD_INCREMENT_ACTION);
			addParameterArrayElementString(4, MovementCollectionType.FIELD_DECREMENT_ACTION);
			addParameterArrayElementString(5, MovementCollectionType.FIELD_MOVEMENT_PARENTABLE);
			addParameterArrayElementString(6, MovementCollectionType.FIELD_SUPPORT_DOCUMENT_IDENTIFIER);
			addParameterArrayElementString(7, MovementCollectionType.FIELD_DOCUMENT_IDENTIFIER_COUNT_INTERVAL);
			addParameterArrayElementString(8, MovementCollectionType.FIELD_IDENTIFIABLE_PERIOD_COLLECTION_TYPE);
			addParameterArrayElementString(9, FieldHelper.getInstance().buildPath(MovementCollectionType.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_DEFAULTED));
			
			addParameterArrayElementString(11, MovementCollectionType.FIELD_AUTOMATICALLY_JOIN_IDENTIFIABLE_PERIOD_COLLECTION);
			addParameterArrayElementString(12, MovementCollectionType.FIELD_VALUE_IS_AGGREGATED);
		}
		
	}
	
	@Getter @Setter
	public static class Details extends AbstractDataTreeTypeBusinessImpl.Details<MovementCollectionType> implements Serializable {

		private static final long serialVersionUID = -4747519269632371426L;

		public Details(MovementCollectionType movementCollectionType) {
			super(movementCollectionType);
		}
		
	}

}
