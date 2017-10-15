package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.geography.ElectronicMailAddressBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.api.geography.ElectronicMailAddressDao;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.helper.RandomHelper;

import lombok.Getter;
import lombok.Setter;

public class ElectronicMailAddressBusinessImpl extends AbstractContactBusinessImpl<ElectronicMailAddress, ElectronicMailAddressDao> implements ElectronicMailAddressBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ElectronicMailAddressBusinessImpl(ElectronicMailAddressDao dao) {
		super(dao); 
	}
	
	@Override
	protected Object[] getPropertyValueTokens(Contact contact, String name) {
		if(ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_CODE,GlobalIdentifier.FIELD_NAME}, name))
			return new Object[]{((ElectronicMailAddress)contact).getAddress()};
		return super.getPropertyValueTokens(contact, name);
	}
	
	@Override
	public Contact instanciateOneRandomly() {
		return instanciateOne((ContactCollection)null,RandomHelper.getInstance().getElectronicMailAddress());
	}

	@Override
	public ElectronicMailAddress instanciateOne(ContactCollection collection, String address) {
		ElectronicMailAddress electronicMailAddress = new ElectronicMailAddress();
		electronicMailAddress.setAddress(address);
		if(collection!=null)
			collection.add(electronicMailAddress);
		return electronicMailAddress;
	}

	@Override
	public List<ElectronicMailAddress> instanciateMany(ContactCollection collection, List<String[]> values) {
		List<ElectronicMailAddress> list = new ArrayList<>();
		for(String[] line : values)
			list.add(instanciateOne(collection,line[0]));
		return list;
	}

	@Override
	public List<ElectronicMailAddress> instanciateMany(ContactCollection collection, String[] addresses) {
		List<ElectronicMailAddress> list = new ArrayList<>();
		if(addresses!=null)
			for(String address : addresses)
				list.add(instanciateOne(collection,address));
		return list;
	}
	
	@Override
	public Collection<String> findAddresses(Collection<ElectronicMailAddress> electronicMailAddresses) {
		Collection<String> addresses = new ArrayList<>();
		for(ElectronicMailAddress electronicMailAddress : electronicMailAddresses)
			addresses.add(electronicMailAddress.getAddress());
		return addresses;
	}
	
	@Override
	public void setAddress(Party party, String value) {
		if(party.getContactCollection()==null)
			party.setContactCollection(new ContactCollection());
		party.getContactCollection().add(new ElectronicMailAddress(value));
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
		Collection<ElectronicMailAddress> electronicMailAddresses = inject(ContactDao.class).readByCollectionByClass(party.getContactCollection(), ElectronicMailAddress.class);
		exceptionUtils().exception(electronicMailAddresses.size()>1, "toomuch.electronicmail.found");
		return electronicMailAddresses.isEmpty() ? null : electronicMailAddresses.iterator().next().getAddress();
	}

	@Override @Deprecated
	public String findAddress(Person person, String personRelationshipTypeCode) {
		return null;
	}
	
	@Override @Deprecated
	public Collection<String> findAddresses(Person person, Collection<String> personRelationshipTypeCodes) {
		return null;
	}
	
	@Override
	protected Contact __instanciateOne__(String[] values,InstanciateOneListener<Contact> listener) {
		listener.getInstance().getGlobalIdentifierCreateIfNull();
		set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE);
		set(listener.getSetListener(), ElectronicMailAddress.FIELD_ADDRESS);
		return listener.getInstance();
	}
	
	public static class BuilderOneDimensionArray extends org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<ElectronicMailAddress> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(ElectronicMailAddress.class);
			addFieldCode().addParameterArrayElementString(ElectronicMailAddress.FIELD_ADDRESS);
		}
		
	}
	
	@Getter @Setter
	public static class Details extends AbstractContactBusinessImpl.Details<ElectronicMailAddress> implements Serializable {

		private static final long serialVersionUID = 4444472169870625893L;

		@Input @InputText protected String address;
		
		public Details(ElectronicMailAddress electronicMailAddress) {
			super(electronicMailAddress);
		}
		
		@Override
		public void setMaster(ElectronicMailAddress electronicMailAddress) {
			super.setMaster(electronicMailAddress);
			if(electronicMailAddress!=null){
				address = electronicMailAddress.getAddress();
			}
		}
		
		public static final String FIELD_ADDRESS = "address";

	}

		
}
