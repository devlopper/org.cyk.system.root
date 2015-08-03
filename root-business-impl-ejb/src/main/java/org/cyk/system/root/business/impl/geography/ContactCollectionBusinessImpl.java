package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.system.root.persistence.api.geography.ContactCollectionDao;
import org.cyk.system.root.persistence.api.geography.ContactDao;

public class ContactCollectionBusinessImpl extends AbstractTypedBusinessService<ContactCollection, ContactCollectionDao> implements ContactCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private ContactDao contactDao;

	@Inject
	public ContactCollectionBusinessImpl(ContactCollectionDao dao) {
		super(dao); 
	}   
	
	protected void __load__(ContactCollection aCollection) {
        aCollection.setPhoneNumbers(contactDao.readByCollectionByClass(aCollection,PhoneNumber.class));
        aCollection.setLocations(contactDao.readByCollectionByClass(aCollection,Location.class));
        aCollection.setElectronicMails(contactDao.readByCollectionByClass(aCollection,ElectronicMail.class));
        aCollection.setPostalBoxs(contactDao.readByCollectionByClass(aCollection,PostalBox.class));
    }

    @Override
    public ContactCollection create(ContactCollection collection) {
        super.create(collection);
        configure(collection.getPhoneNumbers(), collection);
        configure(collection.getLocations(), collection);
        configure(collection.getElectronicMails(), collection);
        configure(collection.getPostalBoxs(), collection);
        return collection;
    }
    
    @Override
    public ContactCollection delete(ContactCollection collection) {
    	delete(collection.getPhoneNumbers(), collection);
    	delete(collection.getLocations(), collection);
    	delete(collection.getElectronicMails(), collection);
    	delete(collection.getPostalBoxs(), collection);
    	return super.delete(collection);
    }
    
    @Override
    public ContactCollection update(ContactCollection collection) {
    	update(contactDao.readByCollectionByClass(collection,PhoneNumber.class), collection.getPhoneNumbers(), collection);
    	update(contactDao.readByCollectionByClass(collection,Location.class), collection.getLocations(), collection);
    	update(contactDao.readByCollectionByClass(collection,ElectronicMail.class), collection.getElectronicMails(), collection);
    	update(contactDao.readByCollectionByClass(collection,PostalBox.class), collection.getPostalBoxs(), collection);
    	return super.update(collection);
    }
     
    private void configure(Collection<? extends Contact> contacts,ContactCollection collection){
        if(contacts==null)
            return;
        byte order = 0;
        for(Contact contact : contacts){
            contact.setCollection(collection);
            contact.setOrderIndex(order++);
            contactDao.create(contact);
        }
    }
    
    private void update(Collection<? extends Contact> databaseContacts,Collection<? extends Contact> inputContacts,ContactCollection collection){
        if(inputContacts==null)
            return;
        for(Contact inputContact : inputContacts){
        	inputContact.setCollection(collection);
        	if(inputContact.getIdentifier()==null){
        		contactDao.create(inputContact);
        	}else{
        		contactDao.update(inputContact);
        	}
        }
        
        Collection<Contact> deletes = new ArrayList<>();
        
        for(Contact databaseContact : databaseContacts){
        	if(!inputContacts.contains(databaseContact))
        		deletes.add(databaseContact);	
        }
        
        for(Contact contact : deletes)
        	contactDao.delete(contact);
        
    }
    
    private void delete(Collection<? extends Contact> contacts,ContactCollection collection){
        if(contacts==null)
            return;
        for(Contact contact : contacts)
            contactDao.delete(contact);
    }
	
}
