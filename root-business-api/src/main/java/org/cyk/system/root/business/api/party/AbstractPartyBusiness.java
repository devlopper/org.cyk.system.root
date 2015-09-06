package org.cyk.system.root.business.api.party;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartySearchCriteria;

public interface AbstractPartyBusiness<PARTY extends Party,SEARCH_CRITERIA extends PartySearchCriteria> extends TypedBusiness<PARTY> {

	Collection<PARTY> findByCriteria(SEARCH_CRITERIA criteria);
	
	Long countByCriteria(SEARCH_CRITERIA criteria);
}
