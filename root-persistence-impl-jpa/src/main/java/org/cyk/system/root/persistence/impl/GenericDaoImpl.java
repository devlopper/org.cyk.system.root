package org.cyk.system.root.persistence.impl;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier.SearchCriteria;
import org.cyk.system.root.persistence.api.GenericDao;

public class GenericDaoImpl extends AbstractPersistenceService<AbstractIdentifiable> implements GenericDao {

	private static final long serialVersionUID = 5597848028969504927L;
	
	/*-------------------------------------------------------------------------------------------*/
	
	 @Override
	    public AbstractIdentifiable refresh(AbstractIdentifiable identifiable) {
	     entityManager.refresh(identifiable);   
	     return identifiable;
	    }
	
	/*--------------------------------------------------------------------------------------------*/
	
	@SuppressWarnings("unchecked")
	@Override
	public GenericDao use(Class<? extends AbstractIdentifiable> aClass) {
		clazz = (Class<AbstractIdentifiable>) aClass;
		return this;
	}

	@Override
	public Collection<AbstractIdentifiable> readByGlobalIdentifierSearchCriteria(SearchCriteria globalIdentifierSearchCriteria) {
		return null;
	}

	@Override
	public Long countByGlobalIdentifierSearchCriteria(SearchCriteria globalIdentifierSearchCriteria) {
		return null;
	}

   
	
}
