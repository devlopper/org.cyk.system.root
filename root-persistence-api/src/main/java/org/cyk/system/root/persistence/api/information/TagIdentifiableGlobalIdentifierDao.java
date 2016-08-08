package org.cyk.system.root.persistence.api.information;

import org.cyk.system.root.model.information.TagIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface TagIdentifiableGlobalIdentifierDao extends JoinGlobalIdentifierDao<TagIdentifiableGlobalIdentifier,TagIdentifiableGlobalIdentifier.SearchCriteria> {
	
}
