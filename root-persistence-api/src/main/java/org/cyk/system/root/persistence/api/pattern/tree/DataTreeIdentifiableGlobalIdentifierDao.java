package org.cyk.system.root.persistence.api.pattern.tree;

import org.cyk.system.root.model.pattern.tree.DataTreeIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface DataTreeIdentifiableGlobalIdentifierDao extends JoinGlobalIdentifierDao<DataTreeIdentifiableGlobalIdentifier,DataTreeIdentifiableGlobalIdentifier.SearchCriteria> {
	
}
