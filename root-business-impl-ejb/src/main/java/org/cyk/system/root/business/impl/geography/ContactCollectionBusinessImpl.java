package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.persistence.api.geography.ContactCollectionDao;
import org.cyk.system.root.persistence.api.geography.ContactDao;

@Stateless
public class ContactCollectionBusinessImpl extends AbstractTypedBusinessService<ContactCollection, ContactCollectionDao> implements ContactCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private ContactDao contactDao;

	@Inject
	public ContactCollectionBusinessImpl(ContactCollectionDao dao) {
		super(dao); 
	}   
	
	@Override
    public void load(ContactCollection aCollection) {
        aCollection.setPhoneNumbers(contactDao.readAll(PhoneNumber.class));
        aCollection.setLocations(contactDao.readAll(Location.class));
    }

    @Override
    public ContactCollection create(ContactCollection collection) {
        super.create(collection);
        configure(collection.getPhoneNumbers(), collection);
        configure(collection.getLocations(), collection);
        return collection;
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
	
}
