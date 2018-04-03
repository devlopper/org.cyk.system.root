package org.cyk.system.root.persistence.api.information;

import org.cyk.system.root.model.information.InformationStateIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface InformationStateIdentifiableGlobalIdentifierDao extends JoinGlobalIdentifierDao<InformationStateIdentifiableGlobalIdentifier,InformationStateIdentifiableGlobalIdentifier.SearchCriteria> {
	
}
