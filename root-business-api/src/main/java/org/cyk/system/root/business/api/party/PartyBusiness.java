package org.cyk.system.root.business.api.party;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.model.party.Party;

public interface PartyBusiness extends AbstractPartyBusiness<Party> {

	Collection<Party> findByIdentifiableByBusinessRole(AbstractIdentifiable identifiable,BusinessRole businessRole);
	Collection<Party> findByIdentifiableByBusinessRoleCode(AbstractIdentifiable identifiable,String businessRoleCode);
	
	Collection<Party> findByIdentifiablesByBusinessRoleCode(Collection<? extends AbstractIdentifiable> identifiables,String businessRoleCode);
	
	Party findFirstByIdentifiableByBusinessRole(AbstractIdentifiable identifiable,BusinessRole businessRole);
	Party findFirstByIdentifiableByBusinessRoleCode(AbstractIdentifiable identifiable,String businessRoleCode);
	
}
