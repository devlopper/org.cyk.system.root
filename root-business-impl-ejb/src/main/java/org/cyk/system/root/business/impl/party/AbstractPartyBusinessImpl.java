package org.cyk.system.root.business.impl.party;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

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
	
	@Override
	public PARTY instanciateOneRandomly() {
		PARTY party = super.instanciateOne();
		party.setContactCollection(inject(ContactCollectionBusiness.class).instanciateOneRandomly());
		return party;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<ContactCollection> getContactCollections(Collection<PARTY> parties) {
		Collection<ContactCollection> collection = new ArrayList<>();
		for(PARTY party : parties)
			collection.add(party.getContactCollection());
		return collection;
	}
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(PARTY identifiable) {
		return Arrays.asList(Party.FIELD_CONTACT_COLLECTION);
	}

    /**/
        
	@Override
	protected void deleteFileIdentifiableGlobalIdentifier(PARTY identifiable) {
		
	}
	
	@Override
	protected void deleteMovementCollectionIdentifiableGlobalIdentifier(PARTY identifiable) {
		
	}
	
	

}
