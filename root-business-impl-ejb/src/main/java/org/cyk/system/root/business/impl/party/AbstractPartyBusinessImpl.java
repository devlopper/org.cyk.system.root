package org.cyk.system.root.business.impl.party;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.party.AbstractPartyBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.party.AbstractPartyDao;
import org.cyk.utility.common.file.ExcelSheetReader;

public abstract class AbstractPartyBusinessImpl<PARTY extends Party,DAO extends AbstractPartyDao<PARTY>> extends AbstractTypedBusinessService<PARTY, DAO> implements AbstractPartyBusiness<PARTY>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractPartyBusinessImpl(DAO dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<ContactCollection> getContactCollections(Collection<PARTY> parties) {
		Collection<ContactCollection> collection = new ArrayList<>();
		for(PARTY party : parties)
			collection.add(party.getContactCollection());
		return collection;
	}

	@Override
    public PARTY create(PARTY party) {
		if(party.getContactCollection()!=null){
			if(StringUtils.isEmpty(party.getContactCollection().getName()))
				party.getContactCollection().setName(party.getCode());
			inject(ContactCollectionBusiness.class).create(party.getContactCollection());
		}
		super.create(party);
        return party;
    }
	
	@Override
    public PARTY update(PARTY party) {
		if(party.getContactCollection()!=null)
			inject(ContactCollectionBusiness.class).save(party.getContactCollection());
        return super.update(party);
    }
	
    @Override
	public PARTY delete(PARTY party) {
    	if(party.getContactCollection()!=null)
    		inject(ContactCollectionBusiness.class).delete(party.getContactCollection());
    	party.setContactCollection(null);
		return super.delete(party);
	}

    @Override
	public <SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> Collection<PARTY> findBySearchCriteria(SEARCH_CRITERIA searchCriteria) {
		if(StringUtils.isBlank(((AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet)searchCriteria).getName().getValue())){
    		return findAll(searchCriteria.getReadConfig());
    	}
    	prepareFindByCriteria(searchCriteria);
    	return dao.readBySearchCriteria(searchCriteria);
	}

	@Override
	public <SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> Long countBySearchCriteria(SEARCH_CRITERIA searchCriteria) {
		if(StringUtils.isBlank(((AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet)searchCriteria).getName().getValue()))
    		return countAll();
    	prepareFindByCriteria(searchCriteria);
    	return dao.countBySearchCriteria(searchCriteria);
	}
    
    /**/
        
	protected void __load__(PARTY party) {
    	if(party.getContactCollection()!=null)
    		inject(ContactCollectionBusiness.class).load(party.getContactCollection());
    }

	@Override @Deprecated
	public void completeInstanciationOfOneFromValues(PARTY party,AbstractCompleteInstanciationOfOneFromValuesArguments<PARTY> completeInstanciationOfOneFromValuesArguments) {
		CompletePartyInstanciationOfOneFromValuesArguments<PARTY> arguments = (CompletePartyInstanciationOfOneFromValuesArguments<PARTY>) completeInstanciationOfOneFromValuesArguments;
		//completeInstanciationOfOneFromValuesBeforeProcessing(party,arguments.getValues(),arguments.getListener());
		
		if(arguments.getCodeIndex()!=null)
			party.setCode(arguments.getValues()[arguments.getCodeIndex()]);
		
		if(arguments.getNameIndex()!=null)
			party.setName(arguments.getValues()[arguments.getNameIndex()]);
		
		if(arguments.getBirthDateIndex()!=null && StringUtils.isNotBlank(arguments.getValues()[arguments.getBirthDateIndex()]))
			party.setBirthDate(timeBusiness.parse(arguments.getValues()[arguments.getBirthDateIndex()]));
		
		if(arguments.getCreationDateIndex()!=null)
			party.setBirthDate(timeBusiness.parse(arguments.getValues()[arguments.getCreationDateIndex()]));
		
		//completeInstanciationOfOneFromValuesAfterProcessing(party,arguments.getValues(),arguments.getListener());
		
	}

	@Override @Deprecated
	public void completeInstanciationOfManyFromValues(List<PARTY> parties,AbstractCompleteInstanciationOfManyFromValuesArguments<PARTY> completeInstanciationOfManyFromValuesArguments) {
		CompletePartyInstanciationOfManyFromValuesArguments<PARTY> arguments = (CompletePartyInstanciationOfManyFromValuesArguments<PARTY>) completeInstanciationOfManyFromValuesArguments;
		List<String[]> values =  ExcelSheetReader.Adapter.getValues(arguments.getValues());
		//completeInstanciationOfManyFromValuesBeforeProcessing(parties,values,arguments.getListener());
		for(int index = 0; index < arguments.getValues().size(); index++ ){
			arguments.getInstanciationOfOneFromValuesArguments().setValues(arguments.getValues().get(index).getValues());
			completeInstanciationOfOneFromValues(parties.get(index), arguments.getInstanciationOfOneFromValuesArguments());
		}
		//completeInstanciationOfManyFromValuesAfterProcessing(parties,values,arguments.getListener());
	}
	
}
