package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import org.cyk.system.root.business.api.party.AbstractPartyExtendedInformationsBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.party.AbstractPartyExtendedInformations;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.party.AbstractPartyExtendedInformationsDao;

public abstract class AbstractPartyExtendedInformationsBusinessImpl<INFORMATIONS extends AbstractPartyExtendedInformations<PARTY>,DAO extends AbstractPartyExtendedInformationsDao<INFORMATIONS,PARTY>,PARTY extends Party> extends AbstractTypedBusinessService<INFORMATIONS, DAO> implements AbstractPartyExtendedInformationsBusiness<INFORMATIONS,PARTY>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractPartyExtendedInformationsBusinessImpl(DAO dao) {
		super(dao); 
	}
	
	@Override
	public INFORMATIONS findByParty(PARTY party) {
		return dao.readByParty(party);
	}
	
}
