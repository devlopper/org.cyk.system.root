package org.cyk.system.root.persistence.api.party;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.model.party.Party;

public interface PartyDao extends AbstractPartyDao<Party> {

	Collection<Party> readByIdentifiablesByBusinessRole(Collection<? extends AbstractIdentifiable> identifiables,BusinessRole businessRole);
	Collection<Party> readByIdentifiablesByBusinessRoleCode(Collection<? extends AbstractIdentifiable> identifiables,String businessRoleCode);
	
	Collection<Party> readByIdentifiableByBusinessRole(AbstractIdentifiable identifiable,BusinessRole businessRole);
	Collection<Party> readByIdentifiableByBusinessRoleCode(AbstractIdentifiable identifiable,String businessRoleCode);
	
	Long countByIdentifiablesByBusinessRole(Collection<? extends AbstractIdentifiable> identifiables,BusinessRole businessRole);
	Long countByIdentifiablesByBusinessRoleCode(Collection<? extends AbstractIdentifiable> identifiables,String businessRoleCode);
	
	Long countByIdentifiableByBusinessRole(AbstractIdentifiable identifiable,BusinessRole businessRole);
	Long countByIdentifiableByBusinessRoleCode(AbstractIdentifiable identifiable,String businessRoleCode);
	
	Party readFirstByIdentifiableByBusinessRole(AbstractIdentifiable identifiable,BusinessRole businessRole);
	Party readFirstByIdentifiableByBusinessRoleCode(AbstractIdentifiable identifiable,String businessRoleCode);

}
