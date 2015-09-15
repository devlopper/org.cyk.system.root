package org.cyk.system.root.persistence.impl.party;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.party.AbstractPartyExtendedInformations;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.party.AbstractPartyExtendedInformationsDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractPartyExtendedInformationsDaoImpl<INFORMATIONS extends AbstractPartyExtendedInformations<PARTY>,PARTY extends Party> extends AbstractTypedDao<INFORMATIONS> implements AbstractPartyExtendedInformationsDao<INFORMATIONS,PARTY>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	protected String readByParty;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByParty, "SELECT informations FROM "+clazz.getSimpleName()+" informations WHERE informations.party = :party");
	}
	
	@Override
	public INFORMATIONS readByParty(PARTY party) {
		return namedQuery(readByParty).parameter("party", party).ignoreThrowable(NoResultException.class).resultOne();
	}

}
 