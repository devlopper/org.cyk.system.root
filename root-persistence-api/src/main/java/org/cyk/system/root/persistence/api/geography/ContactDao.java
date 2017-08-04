package org.cyk.system.root.persistence.api.geography;

import java.util.Collection;

import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public interface ContactDao extends AbstractCollectionItemDao<Contact,ContactCollection> {

    //Collection<Contact> readByClasses(Collection<Class<? extends Contact>> classes);
    //Long countByClasses(Collection<Class<? extends Contact>> classes);
    
    Collection<Contact> readByCollections(Collection<ContactCollection> collections);
    Long countByCollections(Collection<ContactCollection> collections);
    
    Collection<Contact> readByCollectionsByClasses(Collection<ContactCollection> collections,Collection<Class<? extends Contact>> classes);
    Long countByCollectionsByClasses(Collection<ContactCollection> collections,Collection<Class<? extends Contact>> classes);
    
    /* Short Cuts*/
    
    //<T extends Contact> Collection<T> readByClass(Class<T> aClass);
    //<T extends Contact> Long countByClass(Class<T> aClass);
    
    Collection<Contact> readByCollection(ContactCollection collection);
    Long countByCollection(ContactCollection collection);
    
    Collection<Contact> readByCollectionByClasses(ContactCollection collection,Collection<Class<? extends Contact>> classes);
    Long countByCollectionByClasses(ContactCollection collection,Collection<Class<? extends Contact>> classes);
    
    <T extends Contact> Collection<T> readByCollectionByClass(ContactCollection contactCollection,Class<T> aClass);
    <T extends Contact> Long countByCollectionByClass(ContactCollection contactCollection,Class<T> aClass);
    
    <T extends Contact> Collection<T> readByCollectionsByClass(Collection<ContactCollection> contactCollection,Class<T> aClass);
    <T extends Contact> Long countByCollectionsByClass(Collection<ContactCollection> contactCollection,Class<T> aClass);
    
}
