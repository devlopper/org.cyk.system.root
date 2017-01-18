package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.ContactBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.persistence.api.geography.ContactDao;

public class ContactBusinessImpl extends AbstractCollectionItemBusinessImpl<Contact, ContactDao,ContactCollection> implements ContactBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public ContactBusinessImpl(ContactDao dao) {
		super(dao); 
	}
	
	@Override
	protected Class<ContactCollection> getCollectionClass() {
		return ContactCollection.class;
	}

	@Override
	public Collection<Contact> findByCollections(Collection<ContactCollection> collections) {
		return dao.readByCollections(collections);
	}

	@Override
	public Long countByCollections(Collection<ContactCollection> collections) {
		return dao.countByCollections(collections);
	}

	@Override
	public Collection<Contact> findByCollectionsByClasses(Collection<ContactCollection> collections,Collection<Class<? extends Contact>> classes) {
		return dao.readByCollectionsByClasses(collections, classes);
	}

	@Override
	public Long countByCollectionsByClasses(Collection<ContactCollection> collections,Collection<Class<? extends Contact>> classes) {
		return dao.countByCollectionsByClasses(collections, classes);
	}

	@Override
	public Collection<Contact> findByCollection(ContactCollection collection) {
		return dao.readByCollection(collection);
	}

	@Override
	public Long countByCollection(ContactCollection collection) {
		return dao.countByCollection(collection);
	}

	@Override
	public Collection<Contact> findByCollectionByClasses(ContactCollection collection,Collection<Class<? extends Contact>> classes) {
		return dao.readByCollectionByClasses(collection, classes);
	}

	@Override
	public Long countByCollectionByClasses(ContactCollection collection,Collection<Class<? extends Contact>> classes) {
		return dao.countByCollectionByClasses(collection, classes);
	}

	@Override
	public <T extends Contact> Collection<T> findByCollectionByClass(ContactCollection contactCollection, Class<T> aClass) {
		return dao.readByCollectionByClass(contactCollection, aClass);
	}

	@Override
	public <T extends Contact> Long countByCollectionByClass(ContactCollection contactCollection, Class<T> aClass) {
		return dao.countByCollectionByClass(contactCollection, aClass);
	}

	@Override
	public <T extends Contact> Collection<T> findByCollectionsByClass(Collection<ContactCollection> contactCollections, Class<T> aClass) {
		return dao.readByCollectionsByClass(contactCollections, aClass);
	}

	@Override
	public <T extends Contact> Long countByCollectionsByClass(Collection<ContactCollection> contactCollections, Class<T> aClass) {
		return dao.countByCollectionsByClass(contactCollections, aClass);
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
