package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MovementCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.mathematics.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionIdentifiableGlobalIdentifierDao;

public class MovementCollectionIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<MovementCollectionIdentifiableGlobalIdentifier, MovementCollectionIdentifiableGlobalIdentifierDao,MovementCollectionIdentifiableGlobalIdentifier.SearchCriteria> implements MovementCollectionIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public MovementCollectionIdentifiableGlobalIdentifierBusinessImpl(MovementCollectionIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
	
}
