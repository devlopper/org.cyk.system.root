package org.cyk.system.root.persistence.api.party;

import java.util.Collection;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface PartyIdentifiableGlobalIdentifierDao extends JoinGlobalIdentifierDao<PartyIdentifiableGlobalIdentifier,PartyIdentifiableGlobalIdentifier.SearchCriteria> {

	PartyIdentifiableGlobalIdentifier readByPartyByIdentifiableGlobalIdentifierByRole(Party party,GlobalIdentifier globalIdentifier,BusinessRole role);
	Collection<PartyIdentifiableGlobalIdentifier> readByIdentifiableGlobalIdentifierByRole(GlobalIdentifier globalIdentifier,BusinessRole role);
	
	Collection<PartyIdentifiableGlobalIdentifier> readByParty(Party party);
	Collection<PartyIdentifiableGlobalIdentifier> readByPartyByBusinessRole(Party party,BusinessRole role);
}
