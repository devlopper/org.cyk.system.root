package org.cyk.system.root.business.impl.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierDao;

public class MovementCollectionIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<MovementCollectionIdentifiableGlobalIdentifier, MovementCollectionIdentifiableGlobalIdentifierDao,MovementCollectionIdentifiableGlobalIdentifier.SearchCriteria> implements MovementCollectionIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public MovementCollectionIdentifiableGlobalIdentifierBusinessImpl(MovementCollectionIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}

	@Override
	public Collection<MovementCollectionIdentifiableGlobalIdentifier> findByMovementCollection(MovementCollection movementCollection) {
		return dao.readByMovementCollection(movementCollection);
	}
}
