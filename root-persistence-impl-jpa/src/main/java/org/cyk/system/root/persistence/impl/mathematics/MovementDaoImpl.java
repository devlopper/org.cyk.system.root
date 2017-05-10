package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.utility.common.computation.ArithmeticOperator;

public class MovementDaoImpl extends AbstractCollectionItemDaoImpl<Movement,MovementCollection> implements MovementDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		String dateFieldName = commonUtils.attributePath(Movement.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE);
		registerNamedQuery(readPrevious, _select()
				.where(Movement.FIELD_COLLECTION)
				.and(dateFieldName,Period.FIELD_FROM_DATE,ArithmeticOperator.LT)
				.orderBy(dateFieldName, Boolean.FALSE));
	}
	
	@Override
	protected QueryWrapper<Movement> getPreviousQueryWrapper(Movement movement) {
		QueryWrapper<Movement> queryWrapper = super.getPreviousQueryWrapper(movement);
		queryWrapper.parameter(Movement.FIELD_COLLECTION, movement.getCollection());
		return queryWrapper;
	}
}
 