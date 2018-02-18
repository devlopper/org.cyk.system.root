package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.PartyBusiness;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.party.PartyDao;

public class PartyBusinessImpl extends AbstractPartyBusinessImpl<Party, PartyDao> implements PartyBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;
	 
	@Inject
	public PartyBusinessImpl(PartyDao dao) {
		super(dao); 
	}  
	
}
 