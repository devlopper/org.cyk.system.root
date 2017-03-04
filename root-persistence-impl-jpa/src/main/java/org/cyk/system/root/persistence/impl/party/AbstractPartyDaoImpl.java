package org.cyk.system.root.persistence.impl.party;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.Party.PartySearchCriteria;
import org.cyk.system.root.persistence.api.party.AbstractPartyDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractPartyDaoImpl<PARTY extends Party,SEARCH_CRITERIA extends PartySearchCriteria> extends AbstractTypedDao<PARTY> implements AbstractPartyDao<PARTY>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	protected String readByEmail;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByEmail, "SELECT party FROM "+clazz.getSimpleName()+" party WHERE EXISTS("
				+ " SELECT email FROM ElectronicMail email WHERE email.address = :"+ElectronicMail.FIELD_ADDRESS+" AND email.collection = party.contactCollection"
				+ ")");
	}
	
	@Override
	public PARTY readByEmail(String email) {
		return namedQuery(readByEmail).parameter(ElectronicMail.FIELD_ADDRESS, email).ignoreThrowable(NoResultException.class).resultOne();
	}
	
	
	
}
 