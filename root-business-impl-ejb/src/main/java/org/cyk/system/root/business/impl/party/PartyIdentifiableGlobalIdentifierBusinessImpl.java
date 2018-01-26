package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.PartyIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.party.PartyIdentifiableGlobalIdentifierDao;

public class PartyIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<PartyIdentifiableGlobalIdentifier, PartyIdentifiableGlobalIdentifierDao,PartyIdentifiableGlobalIdentifier.SearchCriteria> implements PartyIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public PartyIdentifiableGlobalIdentifierBusinessImpl(PartyIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
}
