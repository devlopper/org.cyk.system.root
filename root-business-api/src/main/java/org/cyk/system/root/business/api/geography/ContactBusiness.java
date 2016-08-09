package org.cyk.system.root.business.api.geography;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;

public interface ContactBusiness extends TypedBusiness<Contact> {
    
	Collection<Contact> findByCollections(Collection<ContactCollection> collections);
    Long countByCollections(Collection<ContactCollection> collections);
    
    Collection<Contact> findByCollectionsByClasses(Collection<ContactCollection> collections,Collection<Class<? extends Contact>> classes);
    Long countByCollectionsByClasses(Collection<ContactCollection> collections,Collection<Class<? extends Contact>> classes);
    
    /* Short Cuts*/
    
    //<T extends Contact> Collection<T> findByClass(Class<T> aClass);
    //<T extends Contact> Long countByClass(Class<T> aClass);
    
    Collection<Contact> findByCollection(ContactCollection collection);
    Long countByCollection(ContactCollection collection);
    
    Collection<Contact> findByCollectionByClasses(ContactCollection collection,Collection<Class<? extends Contact>> classes);
    Long countByCollectionByClasses(ContactCollection collection,Collection<Class<? extends Contact>> classes);
    
    <T extends Contact> Collection<T> findByCollectionByClass(ContactCollection contactCollection,Class<T> aClass);
    <T extends Contact> Long countByCollectionByClass(ContactCollection contactCollection,Class<T> aClass);
    
    <T extends Contact> Collection<T> findByCollectionsByClass(Collection<ContactCollection> contactCollection,Class<T> aClass);
    <T extends Contact> Long countByCollectionsByClass(Collection<ContactCollection> contactCollection,Class<T> aClass);
    
}
