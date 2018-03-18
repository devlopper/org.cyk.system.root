package org.cyk.system.root.persistence.api.party;

import java.util.Collection;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartyBusinessRole;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface PartyIdentifiableGlobalIdentifierDao extends JoinGlobalIdentifierDao<PartyIdentifiableGlobalIdentifier,PartyIdentifiableGlobalIdentifier.SearchCriteria> {

	PartyIdentifiableGlobalIdentifier readByPartyByIdentifiableGlobalIdentifierByRole(Party party,GlobalIdentifier globalIdentifier,PartyBusinessRole role);
	
	Collection<PartyIdentifiableGlobalIdentifier> readByParty(Party party);
}
