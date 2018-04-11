package org.cyk.system.root.persistence.api.mathematics.movement;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;
import org.cyk.system.root.persistence.api.AbstractCollectionDao;

public interface MovementCollectionDao extends AbstractCollectionDao<MovementCollection,Movement> {

	Collection<MovementCollection> readByTypeByJoin(MovementCollectionType type,AbstractIdentifiable join);
	
}
