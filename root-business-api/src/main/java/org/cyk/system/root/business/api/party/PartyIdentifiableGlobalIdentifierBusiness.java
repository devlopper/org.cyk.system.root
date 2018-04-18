package org.cyk.system.root.business.api.party;

import java.util.Collection;

import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;

public interface PartyIdentifiableGlobalIdentifierBusiness extends JoinGlobalIdentifierBusiness<PartyIdentifiableGlobalIdentifier,PartyIdentifiableGlobalIdentifier.SearchCriteria> {
	
	Collection<PartyIdentifiableGlobalIdentifier> findByPartyByBusinessRole(Party party,BusinessRole role);
	Collection<PartyIdentifiableGlobalIdentifier> findByIdentifiableGlobalIdentifierByBusinessRole(AbstractIdentifiable identifiable,BusinessRole businessRole);
	Collection<PartyIdentifiableGlobalIdentifier> findByIdentifiableGlobalIdentifierByBusinessRoleCode(AbstractIdentifiable identifiable,String businessRoleCode);
	void deleteByPartyByBusinessRoleByIdentifiable(Party party, BusinessRole businessRole,AbstractIdentifiable identifiable);
	void deleteByPartyFieldNameByBusinessRoleCodeByIdentifiable(String partyFieldName, String businessRoleCode,AbstractIdentifiable identifiable);
}
