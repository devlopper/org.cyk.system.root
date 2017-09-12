package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.geography.ContactBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.persistence.api.geography.ContactDao;

public class ContactBusinessImpl extends AbstractCollectionItemBusinessImpl<Contact, ContactDao,ContactCollection> implements ContactBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public ContactBusinessImpl(ContactDao dao) {
		super(dao); 
	}
	
	@Override
	public Contact create(Contact contact) {
		return (Contact) inject(GenericBusiness.class).create(contact);
	}
	
	@Override
	public Contact update(Contact contact) {
		return (Contact) inject(GenericBusiness.class).update(contact);
	}
	
	@Override
	public Contact delete(Contact contact) {
		return (Contact) inject(GenericBusiness.class).delete(contact);
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
	public Collection<Contact> findByCollection(ContactCollection collection) {
		return dao.readByCollection(collection);
	}

	@Override
	public Long countByCollection(ContactCollection collection) {
		return dao.countByCollection(collection);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Contact> findByCollectionsByClasses(Collection<ContactCollection> collections,Collection<Class<? extends Contact>> classes) {
		Collection<Contact> contacts = dao.readByCollectionsByClasses(collections, classes);
		Collection<Class<Contact>> pClasses = new ArrayList<>();
		for(Class<? extends Contact> aClass : classes)
			pClasses.add((Class<Contact>) aClass);
		setCollection(collections, contacts, pClasses);	
		return contacts;
	}

	@Override
	public Long countByCollectionsByClasses(Collection<ContactCollection> collections,Collection<Class<? extends Contact>> classes) {
		return dao.countByCollectionsByClasses(collections, classes);
	}
	
	@Override
	public Collection<Contact> findByCollectionByClasses(ContactCollection collection,Collection<Class<? extends Contact>> classes) {
		return findByCollectionsByClasses(Arrays.asList(collection), classes);
		//return dao.readByCollectionByClasses(collection, classes);
	}

	@Override
	public Long countByCollectionByClasses(ContactCollection collection,Collection<Class<? extends Contact>> classes) {
		return dao.countByCollectionByClasses(collection, classes);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Contact> Collection<T> findByCollectionByClass(ContactCollection collection, Class<T> aClass) {
		Collection<Class<? extends Contact>> classes = new ArrayList<>();
		classes.add(aClass);
		return (Collection<T>) findByCollectionsByClasses(Arrays.asList(collection), classes);
		//return dao.readByCollectionByClass(contactCollection, aClass);
	}

	@Override
	public <T extends Contact> Long countByCollectionByClass(ContactCollection contactCollection, Class<T> aClass) {
		return dao.countByCollectionByClass(contactCollection, aClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Contact> Collection<T> findByCollectionsByClass(Collection<ContactCollection> collections, Class<T> aClass) {
		Collection<Class<? extends Contact>> classes = new ArrayList<>();
		classes.add(aClass);
		return (Collection<T>) findByCollectionsByClasses(collections, classes);
		/*
		Collection<T> contacts = dao.readByCollectionsByClass(contactCollections, aClass);
		setCollection(contactCollections, contacts, Arrays.asList(aClass));	
		return contacts;
		*/
	}

	@Override
	public <T extends Contact> Long countByCollectionsByClass(Collection<ContactCollection> contactCollections, Class<T> aClass) {
		return dao.countByCollectionsByClass(contactCollections, aClass);
	}
	
	private <T extends Contact> void setCollection(Collection<ContactCollection> contactCollections,Collection<? extends Contact> contacts,Collection<Class<T>> classes){
		for(ContactCollection contactCollection : contactCollections){
			for(Class<?> aClass : classes){
				if(ElectronicMail.class.equals(aClass)){
					contactCollection.setElectronicMails(new ArrayList<ElectronicMail>());
					for(Contact contact : contacts)
						if(contact.getCollection().equals(contactCollection) && contact.getClass().equals(aClass))
							contactCollection.getElectronicMails().add((ElectronicMail) contact);
				}
			}
		}
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
