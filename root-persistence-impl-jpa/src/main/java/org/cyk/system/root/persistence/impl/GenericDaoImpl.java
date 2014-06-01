package org.cyk.system.root.persistence.impl;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.GenericDao;

public class GenericDaoImpl extends AbstractPersistenceService<AbstractIdentifiable> implements GenericDao {

	private static final long serialVersionUID = 5597848028969504927L;
	
	/*-------------------------------------------------------------------------------------------*/
	
	
	
	/*--------------------------------------------------------------------------------------------*/
	
	@SuppressWarnings("unchecked")
	@Override
	public GenericDao use(Class<? extends AbstractIdentifiable> aClass) {
		clazz = (Class<AbstractIdentifiable>) aClass;
		return this;
	}
	
}
