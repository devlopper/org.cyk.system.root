package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;

public abstract class AbstractPartyDetails<PARTY extends AbstractIdentifiable> extends AbstractOutputDetails<PARTY> implements Serializable {

	private static final long serialVersionUID = 1165482775425753790L;

	public AbstractPartyDetails(PARTY party) {
		super(party);
		
	}

	/**/
	
	protected abstract Party getParty();
	
}
