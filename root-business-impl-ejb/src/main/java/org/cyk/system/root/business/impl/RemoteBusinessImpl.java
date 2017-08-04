package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.cyk.system.root.business.api.RemoteBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;

@Stateless @Remote
public class RemoteBusinessImpl extends AbstractBusinessServiceImpl implements RemoteBusiness ,Serializable {

	private static final long serialVersionUID = -4219622996262337807L;

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public <T extends AbstractIdentifiable> Collection<T> find(Class<T> aClass) {
		return inject(PersistenceInterfaceLocator.class).injectTyped(aClass).readAll();
	}

	@Override
	public <T extends AbstractIdentifiable> void create(Class<T> aClass,Collection<T> collection) {
		inject(BusinessInterfaceLocator.class).injectTyped(aClass).create(collection);
	}

	@Override
	public <T extends AbstractIdentifiable> void update(Class<T> aClass,Collection<T> collection) {
		inject(BusinessInterfaceLocator.class).injectTyped(aClass).update(collection);
	}

	@Override
	public <T extends AbstractIdentifiable> void delete(Class<T> aClass,Collection<T> collection) {
		inject(BusinessInterfaceLocator.class).injectTyped(aClass).delete(collection);
	}

	@Override
	public <T extends AbstractIdentifiable> void save(Class<T> aClass,Collection<T> collection) {
		inject(BusinessInterfaceLocator.class).injectTyped(aClass).save(collection);
	}

	

}
