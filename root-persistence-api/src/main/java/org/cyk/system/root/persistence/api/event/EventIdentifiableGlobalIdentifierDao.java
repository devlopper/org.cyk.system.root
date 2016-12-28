package org.cyk.system.root.persistence.api.event;

import org.cyk.system.root.model.event.EventIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface EventIdentifiableGlobalIdentifierDao extends JoinGlobalIdentifierDao<EventIdentifiableGlobalIdentifier,EventIdentifiableGlobalIdentifier.SearchCriteria> {
	
}
