package org.cyk.system.root.persistence.api.value;

import org.cyk.system.root.model.value.ValueCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface ValueCollectionIdentifiableGlobalIdentifierDao extends JoinGlobalIdentifierDao<ValueCollectionIdentifiableGlobalIdentifier,ValueCollectionIdentifiableGlobalIdentifier.SearchCriteria> {
	
}
