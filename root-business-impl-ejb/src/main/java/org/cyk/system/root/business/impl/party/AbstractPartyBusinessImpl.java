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
import org.cyk.system.root.model.generator.ValueGenerator;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartySearchCriteria;
import org.cyk.system.root.persistence.api.party.AbstractPartyDao;

public abstract class AbstractPartyBusinessImpl<PARTY extends Party,DAO extends AbstractPartyDao<PARTY,SEARCH_CRITERIA>,SEARCH_CRITERIA extends PartySearchCriteria> extends AbstractTypedBusinessService<PARTY, DAO> implements AbstractPartyBusiness<PARTY,SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject protected ContactCollectionBusiness contactCollectionBusiness;
	
	public AbstractPartyBusinessImpl(DAO dao) {
		super(dao); 
	}
	
	@Override
    public PARTY create(PARTY party) {
		party.setCode(generateStringValue(ValueGenerator.PARTY_CODE_IDENTIFIER, party));//TODO handle duplicate by using lock write
		party.setCreationDate(universalTimeCoordinated());
		if(party.getContactCollection()!=null)
			contactCollectionBusiness.create(party.getContactCollection());
        super.create(party);
        return party;
    }
	
	@Override
    public PARTY update(PARTY party) {
		if(party.getContactCollection()!=null)
			contactCollectionBusiness.update(party.getContactCollection());
        return super.update(party);
    }
	
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Collection<PARTY> findByCriteria(SEARCH_CRITERIA criteria) {
    	if(StringUtils.isBlank(criteria.getNameSearchCriteria().getValue()))
    		return findAll(criteria.getReadConfig());
    	return dao.readByCriteria(criteria);
    }
    
    @Override  @TransactionAttribute(TransactionAttributeType.NEVER)
    public Long countByCriteria(SEARCH_CRITERIA criteria) {
    	if(StringUtils.isBlank(criteria.getNameSearchCriteria().getValue()))
    		return countAll();
    	return dao.countByCriteria(criteria);
    }
    
    /**/
        
    protected void __load__(PARTY party) {
    	contactCollectionBusiness.load(party.getContactCollection());
    }
}
