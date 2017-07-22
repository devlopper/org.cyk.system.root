package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.geography.ElectronicMailBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.api.geography.ElectronicMailDao;
import org.cyk.utility.common.generator.RandomDataProvider;

public class ElectronicMailBusinessImpl extends AbstractContactBusinessImpl<ElectronicMail, ElectronicMailDao> implements ElectronicMailBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ElectronicMailBusinessImpl(ElectronicMailDao dao) {
		super(dao); 
	}
	
	@Override
	protected Object[] getPropertyValueTokens(Contact contact, String name) {
		if(ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_CODE,GlobalIdentifier.FIELD_NAME}, name))
			return new Object[]{((ElectronicMail)contact).getAddress()};
		return super.getPropertyValueTokens(contact, name);
	}
	
	@Override
	public Contact instanciateOneRandomly() {
		return instanciateOne((ContactCollection)null,RandomDataProvider.getInstance().randomWord(RandomDataProvider.WORD_EMAIL, 5, 10));
	}

	@Override
	public ElectronicMail instanciateOne(ContactCollection collection, String address) {
		ElectronicMail electronicMail = new ElectronicMail();
		electronicMail.setAddress(address);
		return electronicMail;
	}

	@Override
	public List<ElectronicMail> instanciateMany(ContactCollection collection, List<String[]> values) {
		List<ElectronicMail> list = new ArrayList<>();
		for(String[] line : values)
			list.add(instanciateOne(collection,line[0]));
		return list;
	}

	@Override
	public List<ElectronicMail> instanciateMany(ContactCollection collection, String[] addresses) {
		List<ElectronicMail> list = new ArrayList<>();
		if(addresses!=null)
			for(String address : addresses)
				list.add(instanciateOne(collection,address));
		return list;
	}
	
	@Override
	public Collection<String> findAddresses(Collection<ElectronicMail> electronicMails) {
		Collection<String> addresses = new ArrayList<>();
		for(ElectronicMail electronicMail : electronicMails)
			addresses.add(electronicMail.getAddress());
		return addresses;
	}
	
	@Override
	public void setAddress(Party party, String value) {
		if(party.getContactCollection()==null)
			party.setContactCollection(new ContactCollection());
		party.getContactCollection().setElectronicMail(value);
	}
	
	@Override @Deprecated
	public void setAddress(Person person, String personRelationshipTypeCode, String value) {//TODO must be moved to person business
		/*PersonRelationshipType personRelationshipType = inject(PersonRelationshipTypeDao.class).read(personRelationshipTypeCode);
		Collection<PersonRelationship> personRelationships = inject(PersonRelationshipDao.class).readByPerson2ByType(person, personRelationshipType);
		if(personRelationships.isEmpty()){
			inject(PersonBusiness.class).addRelationship(person, personRelationshipTypeCode);
		}
		PersonRelationship personRelationship = inject(PersonRelationshipBusiness.class).findOneByType(person.getRelationships(), personRelationshipType);
		Person existing = inject(PersonDao.class).readByEmail(value);
		if(existing==null){
			Person parent = inject(PersonRelationshipBusiness.class).findOneByType(person.getRelationships(), personRelationshipType).getPerson1();
			setAddress(parent, value);	
		}else{
			personRelationship.setPerson1(existing);
		}
		*/	
		
	}
	
	@Override
	public String findAddress(Party party) {
		if(party.getContactCollection()==null)
			return null;
		Collection<ElectronicMail> electronicMails = inject(ContactDao.class).readByCollectionByClass(party.getContactCollection(), ElectronicMail.class);
		exceptionUtils().exception(electronicMails.size()>1, "toomuch.electronicmail.found");
		return electronicMails.isEmpty() ? null : electronicMails.iterator().next().getAddress();
	}

	@Override @Deprecated
	public String findAddress(Person person, String personRelationshipTypeCode) {
		/*
		PersonRelationshipType personRelationshipType = inject(PersonRelationshipTypeDao.class).read(personRelationshipTypeCode);
		Collection<PersonRelationship> personRelationships = inject(PersonRelationshipDao.class).readByPerson2ByType(person, personRelationshipType);
		if(personRelationships.isEmpty())
			return null;
		Person parent = inject(PersonRelationshipBusiness.class).findOneByType(person.getRelationships(), personRelationshipType).getPerson1();
		return findAddress(parent);
		*/
		return null;
	}
	
	@Override @Deprecated
	public Collection<String> findAddresses(Person person, Collection<String> personRelationshipTypeCodes) {
		/*
		Collection<PersonRelationshipType> personRelationshipTypes = inject(PersonRelationshipTypeDao.class).read(personRelationshipTypeCodes);
		Collection<PersonRelationship> personRelationships = inject(PersonRelationshipDao.class).readByPerson2ByTypes(Arrays.asList(person), personRelationshipTypes);
		Collection<String> addresses = new ArrayList<>();
		Collection<ContactCollection> contactCollections = new ArrayList<>();
		for(PersonRelationship personRelationship : personRelationships)
			contactCollections.add(personRelationship.getPerson1().getContactCollection());
		for(ElectronicMail electronicMail : inject(ContactDao.class).readByCollectionsByClass(contactCollections, ElectronicMail.class))
			addresses.add(electronicMail.getAddress());
		return addresses;
		*/
		return null;
	}
	
	@Override
	protected Contact __instanciateOne__(String[] values,InstanciateOneListener<Contact> listener) {
		listener.getInstance().getGlobalIdentifierCreateIfNull();
		set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE);
		set(listener.getSetListener(), ElectronicMail.FIELD_ADDRESS);
		return listener.getInstance();
	}
		
}
