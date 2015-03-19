package org.cyk.system.root.persistence.impl.geography;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class ContactDaoImpl extends AbstractTypedDao<Contact> implements ContactDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByClass,readByClassByCollections;
	
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByClass, "SELECT contact FROM Contact contact WHERE TYPE(contact) = :aClass");
        registerNamedQuery(readByClassByCollections, "SELECT contact FROM Contact contact WHERE TYPE(contact) = :aClass AND contact.collection.identifier IN :identifiers");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Contact> Collection<T> readAll(Class<T> aClass) {
        return (Collection<T>) namedQuery(readByClass).parameter("aClass", aClass).resultMany();
    }

	@Override
	public <T extends Contact> Collection<T> readAllByCollection(Class<T> aClass, ContactCollection collection) {
		return readAllByCollections(aClass, Arrays.asList(collection));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Contact> Collection<T> readAllByCollections(Class<T> aClass, Collection<ContactCollection> contactCollections) {
		return (Collection<T>) namedQuery(readByClassByCollections).parameter("aClass", aClass).parameterIdentifiers(contactCollections).resultMany();
	}
	
   
 
}
 