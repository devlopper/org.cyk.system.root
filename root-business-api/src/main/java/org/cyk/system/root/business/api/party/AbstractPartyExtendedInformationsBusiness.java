package org.cyk.system.root.business.api.party;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.party.AbstractPartyExtendedInformations;
import org.cyk.system.root.model.party.Party;

public interface AbstractPartyExtendedInformationsBusiness<INFORMATIONS extends AbstractPartyExtendedInformations<PARTY>,PARTY extends Party> extends TypedBusiness<INFORMATIONS> {

}
