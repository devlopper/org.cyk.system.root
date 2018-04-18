package org.cyk.system.root.business.impl.party;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.PartyBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.party.PartyDao;

public class PartyBusinessImpl extends AbstractPartyBusinessImpl<Party, PartyDao> implements PartyBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;
	 
	@Inject
	public PartyBusinessImpl(PartyDao dao) {
		super(dao); 
	}

	@Override
	public Collection<Party> findByIdentifiableByBusinessRole(AbstractIdentifiable identifiable,BusinessRole businessRole) {
		return dao.readByIdentifiableByBusinessRole(identifiable, businessRole);
	}

	@Override
	public Collection<Party> findByIdentifiableByBusinessRoleCode(AbstractIdentifiable identifiable,String businessRoleCode) {
		return dao.readByIdentifiableByBusinessRoleCode(identifiable, businessRoleCode);
	}

	@Override
	public Party findFirstByIdentifiableByBusinessRole(AbstractIdentifiable identifiable, BusinessRole businessRole) {
		return dao.readFirstByIdentifiableByBusinessRole(identifiable, businessRole);
	}

	@Override
	public Party findFirstByIdentifiableByBusinessRoleCode(AbstractIdentifiable identifiable, String businessRoleCode) {
		return dao.readFirstByIdentifiableByBusinessRoleCode(identifiable, businessRoleCode);
	}  
	
}
 