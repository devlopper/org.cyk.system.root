package org.cyk.system.root.persistence.api.security;

import org.cyk.system.root.model.security.RoleIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface RoleIdentifiableGlobalIdentifierDao extends JoinGlobalIdentifierDao<RoleIdentifiableGlobalIdentifier,RoleIdentifiableGlobalIdentifier.SearchCriteria> {
	
}
