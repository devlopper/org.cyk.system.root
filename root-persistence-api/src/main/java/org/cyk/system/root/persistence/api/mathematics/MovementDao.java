package org.cyk.system.root.persistence.api.mathematics;

import java.math.BigDecimal;

import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public interface MovementDao extends AbstractCollectionItemDao<Movement,MovementCollection> {

	BigDecimal sumValueWhereExistencePeriodFromDateIsLessThan(Movement movement);

}
