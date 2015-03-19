package org.cyk.system.root.persistence.api.geography;

import java.util.Collection;

import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.persistence.api.TypedDao;

public interface ContactDao extends TypedDao<Contact> {

    <T extends Contact> Collection<T> readAll(Class<T> aClass);
    <T extends Contact> Collection<T> readAllByCollection(Class<T> aClass,ContactCollection contactCollection);
    <T extends Contact> Collection<T> readAllByCollections(Class<T> aClass,Collection<ContactCollection> contactCollections);
    
}
