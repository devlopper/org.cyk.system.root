package org.cyk.system.root.persistence.api.party;

import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractPartyDao<PARTY extends Party> extends TypedDao<PARTY> {

	PARTY readByEmail(String email);
	
}
