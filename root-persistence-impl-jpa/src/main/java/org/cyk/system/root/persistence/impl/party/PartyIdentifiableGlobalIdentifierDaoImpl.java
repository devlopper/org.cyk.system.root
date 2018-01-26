package org.cyk.system.root.persistence.impl.party;

import java.io.Serializable;

import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.party.PartyIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.globalidentification.AbstractJoinGlobalIdentifierDaoImpl;

public class PartyIdentifiableGlobalIdentifierDaoImpl extends AbstractJoinGlobalIdentifierDaoImpl<PartyIdentifiableGlobalIdentifier,PartyIdentifiableGlobalIdentifier.SearchCriteria> implements PartyIdentifiableGlobalIdentifierDao,Serializable {
	private static final long serialVersionUID = 6306356272165070761L;

}
 