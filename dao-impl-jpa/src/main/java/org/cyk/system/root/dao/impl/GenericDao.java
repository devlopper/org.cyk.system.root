package org.cyk.system.root.dao.impl;

import org.cyk.system.root.dao.api.GenericIdentifiableQuery;
import org.cyk.system.root.model.AbstractIdentifiable;

public class GenericDao extends AbstractQueryable<AbstractIdentifiable> implements GenericIdentifiableQuery {

	private static final long serialVersionUID = 5597848028969504927L;
	
	/*-------------------------------------------------------------------------------------------*/
	
	@Override
	public AbstractIdentifiable create(Class<? extends AbstractIdentifiable> aClass,AbstractIdentifiable object) {
		entityManager.persist(object);
		return object;
	}
	
	@Override
	public AbstractIdentifiable read(Class<? extends AbstractIdentifiable> aClass,Long identifier) {
		return entityManager.find(aClass, identifier);
	}

	@Override
	public AbstractIdentifiable update(Class<? extends AbstractIdentifiable> aClass,AbstractIdentifiable object) {
		return entityManager.merge(object);
	}

	@Override
	public AbstractIdentifiable delete(Class<? extends AbstractIdentifiable> aClass,AbstractIdentifiable object) {
		entityManager.remove(entityManager.merge(object));
		return object;
	}
	
	/*--------------------------------------------------------------------------------------------*/
	
	@SuppressWarnings("unchecked")
	@Override
	public GenericIdentifiableQuery use(Class<? extends AbstractIdentifiable> aClass) {
		clazz = (Class<AbstractIdentifiable>) aClass;
		return this;
	}
	
}
