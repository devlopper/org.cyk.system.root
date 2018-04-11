package org.cyk.system.root.persistence.api.mathematics.movement;

import java.math.BigDecimal;

import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public interface MovementDao extends AbstractCollectionItemDao<Movement,MovementCollection> {

	BigDecimal sumValueWhereExistencePeriodFromDateIsLessThan(Movement movement);
	BigDecimal sumValueByCollection(MovementCollection movementCollection);

}
