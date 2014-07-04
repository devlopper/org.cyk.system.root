package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.party.AbstractPartyBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.party.AbstractPartyDao;

public abstract class AbstractPartyBusinessImpl<PARTY extends Party,DAO extends AbstractPartyDao<PARTY>> extends AbstractTypedBusinessService<PARTY, DAO> implements AbstractPartyBusiness<PARTY>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject protected ContactCollectionBusiness contactCollectionBusiness;

	@Inject
	public AbstractPartyBusinessImpl(DAO dao) {
		super(dao); 
	}
	
	@Override
    public PARTY create(PARTY party) {
	    contactCollectionBusiness.create(party.getContactCollection());
        super.create(party);
        return party;
    }
	
    @Override
    public PARTY load(Long identifier) {
        PARTY party = super.load(identifier);
        contactCollectionBusiness.load(party.getContactCollection());
        return party;
    }
}
