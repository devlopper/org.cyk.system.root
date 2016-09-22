package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.geography.ContactBusiness;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.geography.ElectronicMailBusiness;
import org.cyk.system.root.business.api.geography.LocationBusiness;
import org.cyk.system.root.business.api.geography.PhoneNumberBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.system.root.model.geography.Website;
import org.cyk.system.root.persistence.api.geography.ContactCollectionDao;
import org.cyk.system.root.persistence.api.geography.ContactDao;

public class ContactCollectionBusinessImpl extends AbstractCollectionBusinessImpl<ContactCollection,Contact, ContactCollectionDao,ContactDao,ContactBusiness> implements ContactCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private ContactDao contactDao;

	@Inject
	public ContactCollectionBusinessImpl(ContactCollectionDao dao) {
		super(dao); 
	}   
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public ContactCollection instanciateOneRandomly() {
		ContactCollection collection = new ContactCollection();
		collection.addElectronicMail((ElectronicMail) inject(ElectronicMailBusiness.class).instanciateOneRandomly());
		collection.addLocation((Location) inject(LocationBusiness.class).instanciateOneRandomly());
		collection.addPhoneNumber((PhoneNumber) inject(PhoneNumberBusiness.class).instanciateOneRandomly());
		//collection.addPostalBox((PostalBox) inject(PostalBoxBusiness.class).instanciateOneRandomly());
		//collection.addWebsite((Website) inject(WebsiteBusiness.class).instanciateOneRandomly());
		return collection;
	}
	
	protected void __load__(ContactCollection aCollection) {
		if(aCollection==null)
			return;
        aCollection.setPhoneNumbers(contactDao.readByCollectionByClass(aCollection,PhoneNumber.class));
        aCollection.setLocations(contactDao.readByCollectionByClass(aCollection,Location.class));
        aCollection.setElectronicMails(contactDao.readByCollectionByClass(aCollection,ElectronicMail.class));
        aCollection.setPostalBoxs(contactDao.readByCollectionByClass(aCollection,PostalBox.class));
        aCollection.setWebsites(contactDao.readByCollectionByClass(aCollection,Website.class));
    }

    @Override
    public ContactCollection create(ContactCollection collection) {
        super.create(collection);
        configure(collection.getPhoneNumbers(), collection);
        configure(collection.getLocations(), collection);
        configure(collection.getElectronicMails(), collection);
        configure(collection.getPostalBoxs(), collection);
        configure(collection.getWebsites(), collection);
        return collection;
    }
    
    @Override
    public ContactCollection delete(ContactCollection collection) {
    	delete(collection.getPhoneNumbers(), collection);
    	delete(collection.getLocations(), collection);
    	delete(collection.getElectronicMails(), collection);
    	delete(collection.getPostalBoxs(), collection);
    	delete(collection.getWebsites(), collection);
    	return super.delete(collection);
    }
    
    @Override
    public ContactCollection update(ContactCollection collection) {
    	update(contactDao.readByCollectionByClass(collection,PhoneNumber.class), collection.getPhoneNumbers(), collection);
    	update(contactDao.readByCollectionByClass(collection,Location.class), collection.getLocations(), collection);
    	update(contactDao.readByCollectionByClass(collection,ElectronicMail.class), collection.getElectronicMails(), collection);
    	update(contactDao.readByCollectionByClass(collection,PostalBox.class), collection.getPostalBoxs(), collection);
    	update(contactDao.readByCollectionByClass(collection,Website.class), collection.getWebsites(), collection);
    	return super.update(collection);
    }
     
    private void configure(Collection<? extends Contact> contacts,ContactCollection collection){
        if(contacts==null)
            return;
        long order = 0;
        for(Contact contact : contacts){
            contact.setCollection(collection);
            contact.setOrderNumber(order++);
            contactDao.create(contact);
        }
    }
    
    private void update(Collection<? extends Contact> databaseContacts,Collection<? extends Contact> inputContacts,ContactCollection collection){
        if(inputContacts==null)
            return;
        logDebug("Contacts inputed {}",StringUtils.join(inputContacts,","));
        for(Contact inputContact : inputContacts){
        	inputContact.setCollection(collection);
        	if(inputContact.getIdentifier()==null){
        		contactDao.create(inputContact);
        		logDebug("Contact inputed created {}",inputContact);
        	}else{
        		contactDao.update(inputContact);
        		logDebug("Contact inputed updated {}",inputContact);
        	}
        }
        
        Collection<Contact> deletes = new ArrayList<>();
        
        for(Contact databaseContact : databaseContacts){
        	if(!inputContacts.contains(databaseContact)){
        		deletes.add(databaseContact);
        		logDebug("Database contact will be deleted {}",databaseContact);
        	}
        }
        
        for(Contact contact : deletes){
        	contact.setCollection(null);
        	contactDao.delete(contact);
        	logDebug("Contact deleted {}",contact);
        }
        
    }
    
    private void delete(Collection<? extends Contact> contacts,ContactCollection collection){
        if(contacts==null)
            return;
        for(Contact contact : contacts){
        	contact.setCollection(null);
        	contactDao.delete(contact);
        }
    }

	@Override
	protected ContactDao getItemDao() {
		return contactDao;
	}

	@Override
	protected ContactBusiness getItemBusiness() {
		return inject(ContactBusiness.class);
	}

	@Override
	public ContactCollection instanciateOne(String[] phoneNumbers, String[] electronicMails, String[] postalBoxes,String[] websites) {
		ContactCollection contactCollection = new ContactCollection();
		contactCollection.setPhoneNumbers(inject(PhoneNumberBusiness.class).instanciateMany(contactCollection, phoneNumbers));
		contactCollection.setElectronicMails(inject(ElectronicMailBusiness.class).instanciateMany(contactCollection, electronicMails));
		//contactCollection.setPostalBoxes(inject(Postalb.class).instanciateMany(contactCollection, phoneNumbers));
		//contactCollection.setWebsites(inject(PhoneNumberBusiness.class).instanciateMany(contactCollection, phoneNumbers));
		return contactCollection;
	}
	
}
