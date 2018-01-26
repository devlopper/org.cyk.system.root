package org.cyk.system.root.persistence.api.party;

import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface PartyIdentifiableGlobalIdentifierDao extends JoinGlobalIdentifierDao<PartyIdentifiableGlobalIdentifier,PartyIdentifiableGlobalIdentifier.SearchCriteria> {
	
}
