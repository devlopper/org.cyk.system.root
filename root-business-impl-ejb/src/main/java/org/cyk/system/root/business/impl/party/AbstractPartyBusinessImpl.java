package org.cyk.system.root.business.impl.party;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.party.AbstractPartyBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartySearchCriteria;
import org.cyk.system.root.persistence.api.party.AbstractPartyDao;

public abstract class AbstractPartyBusinessImpl<PARTY extends Party,DAO extends AbstractPartyDao<PARTY,SEARCH_CRITERIA>,SEARCH_CRITERIA extends PartySearchCriteria> extends AbstractTypedBusinessService<PARTY, DAO> implements AbstractPartyBusiness<PARTY,SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject protected ContactCollectionBusiness contactCollectionBusiness;

	@Inject
	public AbstractPartyBusinessImpl(DAO dao) {
		super(dao); 
	}
	
	@Override
    public PARTY create(PARTY party) {
		party.setCreationDate(universalTimeCoordinated());
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
    
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Collection<PARTY> findByCriteria(SEARCH_CRITERIA criteria) {
    	if(StringUtils.isBlank(criteria.getNameSearchCriteria().getValue()))
    		return findAll();
    	return dao.readByCriteria(criteria);
    }
    
    @Override  @TransactionAttribute(TransactionAttributeType.NEVER)
    public Long countByCriteria(SEARCH_CRITERIA criteria) {
    	if(StringUtils.isBlank(criteria.getNameSearchCriteria().getValue()))
    		return countAll();
    	return dao.countByCriteria(criteria);
    }
}
