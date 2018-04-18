package org.cyk.system.root.business.api.mathematics.movement;

import java.util.Collection;

import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;

public interface MovementCollectionIdentifiableGlobalIdentifierBusiness extends JoinGlobalIdentifierBusiness<MovementCollectionIdentifiableGlobalIdentifier,MovementCollectionIdentifiableGlobalIdentifier.SearchCriteria> {

	Collection<MovementCollectionIdentifiableGlobalIdentifier> findByMovementCollection(MovementCollection movementCollection);
	
}
