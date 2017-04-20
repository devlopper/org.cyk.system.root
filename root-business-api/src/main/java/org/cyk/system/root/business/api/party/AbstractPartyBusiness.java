package org.cyk.system.root.business.api.party;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;

public interface AbstractPartyBusiness<PARTY extends Party> extends TypedBusiness<PARTY> {

	Collection<ContactCollection> getContactCollections(Collection<PARTY> parties);
	
	/**/

}
