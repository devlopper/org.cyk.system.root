package org.cyk.system.root.persistence.api.mathematics.movement;

import java.util.Collection;

import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface MovementCollectionIdentifiableGlobalIdentifierDao extends JoinGlobalIdentifierDao<MovementCollectionIdentifiableGlobalIdentifier,MovementCollectionIdentifiableGlobalIdentifier.SearchCriteria> {
	
	Collection<MovementCollectionIdentifiableGlobalIdentifier> readByMovementCollection(MovementCollection movementCollection);
	
}
