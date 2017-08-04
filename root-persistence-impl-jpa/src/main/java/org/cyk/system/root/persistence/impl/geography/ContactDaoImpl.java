package org.cyk.system.root.persistence.impl.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.persistence.api.geography.ContactDao;

public class ContactDaoImpl extends AbstractContactDaoImpl<Contact> implements ContactDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByCollections,countByCollections,readByCollectionsByClasses,countByCollectionsByClasses;
	
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByCollections, _select().whereIdentifierIn(Contact.FIELD_COLLECTION).orderBy("identifier", Boolean.TRUE));
        registerNamedQuery(readByCollectionsByClasses, _select().whereIdentifierIn(Contact.FIELD_COLLECTION).and().whereClassIn().orderBy("identifier", Boolean.TRUE));
        getConfiguration().setReadByClasses(Boolean.TRUE).setReadByNotClasses(Boolean.TRUE);
    }
    
	private Collection<Class<?>> classes(Collection<Class<? extends Contact>> classes){
		Collection<Class<?>> lclasses = new ArrayList<>();
		for(Class<?> cls : classes)
			lclasses.add(cls);
		return lclasses;
	}
	
	@Override
	public Collection<Contact> readByCollections(Collection<ContactCollection> collections) {
		return namedQuery(readByCollections).parameterIdentifiers(collections).resultMany();
	}

	@Override
	public Long countByCollections(Collection<ContactCollection> collections) {
		return countNamedQuery(countByCollections).parameterIdentifiers(collections).resultOne();
	}

	@Override
	public Collection<Contact> readByCollectionsByClasses(Collection<ContactCollection> collections,Collection<Class<? extends Contact>> classes) {
		return namedQuery(readByCollectionsByClasses).parameterIdentifiers(collections).parameterClasses(classes(classes)).resultMany();
	}

	@Override
	public Long countByCollectionsByClasses(Collection<ContactCollection> collections, Collection<Class<? extends Contact>> classes) {
		return countNamedQuery(countByCollectionsByClasses).parameterIdentifiers(collections).parameterClasses(classes(classes)).resultOne();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Contact> Collection<T> readByCollectionsByClass(Collection<ContactCollection> collections,Class<T> aClass) {
		Collection<Class<? extends Contact>> lclasses = new ArrayList<>();
		lclasses.add(aClass);
		return (Collection<T>) readByCollectionsByClasses(collections, lclasses);
	}

	@Override
	public <T extends Contact> Long countByCollectionsByClass(Collection<ContactCollection> collections,Class<T> aClass) {
		Collection<Class<? extends Contact>> lclasses = new ArrayList<>();
		lclasses.add(aClass);
		return countByCollectionsByClasses(collections, lclasses);
	}
	
	@Override
	public <T extends Contact> Collection<T> readByCollectionByClass(ContactCollection collection,Class<T> aClass) {
		return readByCollectionsByClass(Arrays.asList(collection), aClass);
	}

	@Override
	public <T extends Contact> Long countByCollectionByClass(ContactCollection collection, Class<T> aClass) {
		return countByCollectionsByClass(Arrays.asList(collection), aClass);
	}

	@Override
	public Collection<Contact> readByCollectionByClasses(ContactCollection collection,Collection<Class<? extends Contact>> classes) {
		return readByCollectionsByClasses(Arrays.asList(collection), classes);
	}

	@Override
	public Long countByCollectionByClasses(ContactCollection collection, Collection<Class<? extends Contact>> classes) {
		return countByCollectionsByClasses(Arrays.asList(collection), classes);
	}

	@Override
	public Collection<Contact> readByCollection(ContactCollection collection) {
		return readByCollections(Arrays.asList(collection));
	}

	@Override
	public Long countByCollection(ContactCollection collection) {
		return countByCollections(Arrays.asList(collection));
	}

	@Override
	public Collection<Contact> readByValue(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByValue(String value) {
		// TODO Auto-generated method stub
		return null;
	}

}
 