package org.cyk.system.root.persistence.api.party;

import java.util.Collection;

import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartySearchCriteria;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractPartyDao<PARTY extends Party,SEARCH_CRITERIA extends PartySearchCriteria> extends TypedDao<PARTY> {

	Collection<PARTY> readByCriteria(SEARCH_CRITERIA criteria);
    
	Long countByCriteria(SEARCH_CRITERIA criteria);
	
	PARTY readByEmail(String email);
}
