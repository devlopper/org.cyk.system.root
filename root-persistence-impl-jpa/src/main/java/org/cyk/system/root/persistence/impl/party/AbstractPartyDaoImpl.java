package org.cyk.system.root.persistence.impl.party;

import java.io.Serializable;

import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.party.AbstractPartyDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractPartyDaoImpl<PARTY extends Party> extends AbstractTypedDao<PARTY> implements AbstractPartyDao<PARTY>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	


}
 