package org.cyk.system.root.business.impl.party;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
		if(StringUtils.isBlank(party.getCode()))
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
    	if(party.getContactCollection()!=null)
    		contactCollectionBusiness.load(party.getContactCollection());
    }

	@Override
	public void completeInstanciationOfOneFromValues(PARTY party,AbstractCompleteInstanciationOfOneFromValuesArguments<PARTY> completeInstanciationOfOneFromValuesArguments) {
		CompletePartyInstanciationOfOneFromValuesArguments<PARTY> arguments = (CompletePartyInstanciationOfOneFromValuesArguments<PARTY>) completeInstanciationOfOneFromValuesArguments;
		completeInstanciationOfOneFromValuesBeforeProcessing(party,arguments.getValues(),arguments.getListener());
		
		if(arguments.getCodeIndex()!=null)
			party.setCode(arguments.getValues()[arguments.getCodeIndex()]);
		
		if(arguments.getNameIndex()!=null)
			party.setName(arguments.getValues()[arguments.getNameIndex()]);
		
		if(arguments.getBirthDateIndex()!=null && StringUtils.isNotBlank(arguments.getValues()[arguments.getBirthDateIndex()]))
			party.setBirthDate(timeBusiness.parse(arguments.getValues()[arguments.getBirthDateIndex()]));
		
		if(arguments.getCreationDateIndex()!=null)
			party.setCreationDate(timeBusiness.parse(arguments.getValues()[arguments.getCreationDateIndex()]));
		
		completeInstanciationOfOneFromValuesProcessed(party,arguments.getValues(),arguments.getListener());
		
	}

	@Override
	public void completeInstanciationOfManyFromValues(List<PARTY> parties,AbstractCompleteInstanciationOfManyFromValuesArguments<PARTY> completeInstanciationOfManyFromValuesArguments) {
		CompletePartyInstanciationOfManyFromValuesArguments<PARTY> arguments = (CompletePartyInstanciationOfManyFromValuesArguments<PARTY>) completeInstanciationOfManyFromValuesArguments;
		completeInstanciationOfManyFromValuesBeforeProcessing(parties,arguments.getValues(),arguments.getListener());
		for(int index = 0; index < arguments.getValues().size(); index++ ){
			arguments.getInstanciationOfOneFromValuesArguments().setValues(arguments.getValues().get(index));
			completeInstanciationOfOneFromValues(parties.get(index), arguments.getInstanciationOfOneFromValuesArguments());
		}
		completeInstanciationOfManyFromValuesAfterProcessing(parties,arguments.getValues(),arguments.getListener());
	}

	

	
	
	
}
