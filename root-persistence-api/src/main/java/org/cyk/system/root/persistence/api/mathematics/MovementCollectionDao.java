package org.cyk.system.root.persistence.api.mathematics;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.mathematics.MovementCollectionType;
import org.cyk.system.root.persistence.api.AbstractCollectionDao;

public interface MovementCollectionDao extends AbstractCollectionDao<MovementCollection,Movement> {

	Collection<MovementCollection> readByTypeByJoin(MovementCollectionType type,AbstractIdentifiable join);
	
}
