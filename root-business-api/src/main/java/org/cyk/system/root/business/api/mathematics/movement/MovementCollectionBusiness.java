package org.cyk.system.root.business.api.mathematics.movement;

import java.math.BigDecimal;
import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;

public interface MovementCollectionBusiness extends AbstractCollectionBusiness<MovementCollection,Movement> {

	Collection<MovementCollection> findByTypeByJoin(MovementCollectionType type,AbstractIdentifiable join);
	
	/**
	 * add sum of movement values and initial value of movement collection
	 * @param movementCollection
	 * @return
	 */
	BigDecimal computeValue(MovementCollection movementCollection);
	
	//void computeItems();
}
