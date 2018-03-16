package org.cyk.system.root.persistence.impl.party;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartyBusinessRole;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.party.PartyIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.globalidentification.AbstractJoinGlobalIdentifierDaoImpl;

public class PartyIdentifiableGlobalIdentifierDaoImpl extends AbstractJoinGlobalIdentifierDaoImpl<PartyIdentifiableGlobalIdentifier,PartyIdentifiableGlobalIdentifier.SearchCriteria> implements PartyIdentifiableGlobalIdentifierDao,Serializable {
	private static final long serialVersionUID = 6306356272165070761L;

	private String readByPartyByIdentifiableGlobalIdentifierByRole,readByParty;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByPartyByIdentifiableGlobalIdentifierByRole, _select().where(PartyIdentifiableGlobalIdentifier.FIELD_PARTY)
				.and(PartyIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER).and(PartyIdentifiableGlobalIdentifier.FIELD_ROLE));
		registerNamedQuery(readByParty, _select().where(PartyIdentifiableGlobalIdentifier.FIELD_PARTY));
	}
	
	@Override
	public PartyIdentifiableGlobalIdentifier readByPartyByIdentifiableGlobalIdentifierByRole(Party party,GlobalIdentifier globalIdentifier,PartyBusinessRole role) {
		return namedQuery(readByPartyByIdentifiableGlobalIdentifierByRole).parameter(PartyIdentifiableGlobalIdentifier.FIELD_PARTY, party)
				.parameter(PartyIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER, globalIdentifier)
				.parameter(PartyIdentifiableGlobalIdentifier.FIELD_ROLE, role).ignoreThrowable(NoResultException.class).resultOne();
	}

	@Override
	public Collection<PartyIdentifiableGlobalIdentifier> readByParty(Party party) {
		return namedQuery(readByParty).parameter(PartyIdentifiableGlobalIdentifier.FIELD_PARTY, party).resultMany();
	}
}
 