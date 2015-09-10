package org.cyk.system.root.persistence.api.party;

import org.cyk.system.root.model.party.AbstractPartyExtendedInformations;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractPartyExtendedInformationsDao<INFORMATIONS extends AbstractPartyExtendedInformations<PARTY>,PARTY extends Party> extends TypedDao<INFORMATIONS> {

	INFORMATIONS readByParty(PARTY party);
	
}
