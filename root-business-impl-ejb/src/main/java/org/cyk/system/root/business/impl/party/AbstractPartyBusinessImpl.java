package org.cyk.system.root.business.impl.party;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.party.AbstractPartyBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.party.AbstractPartyDao;

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
	protected void beforeCreate(PARTY party) {
		super.beforeCreate(party);
		if(party.getContactCollection()!=null){
			if(StringUtils.isEmpty(party.getContactCollection().getName()))
				party.getContactCollection().setName(party.getCode());
			inject(ContactCollectionBusiness.class).create(party.getContactCollection());
		}
	}

	@Override
	protected void beforeUpdate(PARTY party) {
		super.beforeUpdate(party);
		if(party.getContactCollection()!=null)
			inject(ContactCollectionBusiness.class).save(party.getContactCollection());
	}

	@Override
	protected void beforeDelete(PARTY party) {
		super.beforeDelete(party);
		if(party.getContactCollection()!=null){
    		ContactCollection contactCollection = party.getContactCollection();
    		party.setContactCollection(null);
    		inject(ContactCollectionBusiness.class).delete(contactCollection);
		}
	}
		    
    /**/
        
	protected void __load__(PARTY party) {
    	if(party.getContactCollection()!=null)
    		inject(ContactCollectionBusiness.class).load(party.getContactCollection());
    }

}
