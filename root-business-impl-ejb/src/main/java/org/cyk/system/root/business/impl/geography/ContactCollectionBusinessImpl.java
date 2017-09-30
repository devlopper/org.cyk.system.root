package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.ContactBusiness;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.geography.ElectronicMailAddressBusiness;
import org.cyk.system.root.business.api.geography.LocationBusiness;
import org.cyk.system.root.business.api.geography.PhoneNumberBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.persistence.api.geography.ContactCollectionDao;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.StringHelper;

public class ContactCollectionBusinessImpl extends AbstractCollectionBusinessImpl<ContactCollection,Contact, ContactCollectionDao,ContactDao,ContactBusiness> implements ContactCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ContactCollectionBusinessImpl(ContactCollectionDao dao) {
		super(dao); 
	}   
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public ContactCollection instanciateOneRandomly() {
		ContactCollection collection = new ContactCollection();
		collection.add((ElectronicMailAddress) inject(ElectronicMailAddressBusiness.class).instanciateOneRandomly());
		collection.add((Location) inject(LocationBusiness.class).instanciateOneRandomly());
		collection.add((PhoneNumber) inject(PhoneNumberBusiness.class).instanciateOneRandomly());
		//collection.addPostalBox((PostalBox) inject(PostalBoxBusiness.class).instanciateOneRandomly());
		//collection.addWebsite((Website) inject(WebsiteBusiness.class).instanciateOneRandomly());
		return collection;
	}
	
	@Override
	public ContactCollection instanciateOne(String[] phoneNumbers, String[] electronicMails, String[] postalBoxes,String[] websites) {
		ContactCollection contactCollection = instanciateOne();
		contactCollection.addPhoneNumbers(inject(PhoneNumberBusiness.class).instanciateMany(contactCollection, phoneNumbers));
		contactCollection.addElectronicMailAddresses(inject(ElectronicMailAddressBusiness.class).instanciateMany(contactCollection, electronicMails));
		//contactCollection.setPostalBoxes(inject(Postalb.class).instanciateMany(contactCollection, phoneNumbers));
		//contactCollection.setWebsites(inject(PhoneNumberBusiness.class).instanciateMany(contactCollection, phoneNumbers));
		return contactCollection;
	}
	
	@Override
	public String getElectronicMailAddress(ContactCollection collection) {
		Collection<ElectronicMailAddress> electronicMailAddresses = collection.getItems().filter(ElectronicMailAddress.class);
		if(CollectionHelper.getInstance().isNotEmpty(electronicMailAddresses)){
			if(CollectionHelper.getInstance().getSize(electronicMailAddresses)==1)
				return electronicMailAddresses.iterator().next().getAddress();
			else
				exceptionUtils().exception(electronicMailAddresses.size() > 1, "toomuchelectronicmailsfound");	
		}
		return null;
	}
	
	@Override
	protected Boolean isNullItem(Contact contact) {
		if(contact instanceof ElectronicMailAddress)
			return StringHelper.getInstance().isBlank(((ElectronicMailAddress)contact).getAddress());
		if(contact instanceof PhoneNumber)
			return StringHelper.getInstance().isBlank(((PhoneNumber)contact).getNumber());
		return Boolean.FALSE;
	}
	
}
