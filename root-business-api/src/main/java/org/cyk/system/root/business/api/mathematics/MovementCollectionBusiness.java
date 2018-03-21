package org.cyk.system.root.business.api.mathematics;

import java.math.BigDecimal;
import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.mathematics.MovementCollectionType;

public interface MovementCollectionBusiness extends AbstractCollectionBusiness<MovementCollection,Movement> {

	MovementCollection instanciateOne(String typeCode,BigDecimal value,AbstractIdentifiable join);
	
	@Deprecated
	BigDecimal computeValue(MovementCollection movementCollection,MovementAction movementAction,BigDecimal increment);
	
	Collection<MovementCollection> findByTypeByJoin(MovementCollectionType type,AbstractIdentifiable join);
	
	/**
	 * add sum of movement values and initial value of movement collection
	 * @param movementCollection
	 * @return
	 */
	BigDecimal computeValue(MovementCollection movementCollection);
}
