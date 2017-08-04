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
	 
	 @Override
	public <T extends AbstractIdentifiable> T read(Class<T> aClass, String code) {
		return inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(code);
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
		throwNotYetImplemented();
		return null;
	}

	@Override
	public Long countByGlobalIdentifierSearchCriteria(SearchCriteria globalIdentifierSearchCriteria) {
		throwNotYetImplemented();
		return null;
	}

	@Override
	public AbstractIdentifiable readFirstWhereExistencePeriodFromDateIsLessThan(AbstractIdentifiable identifiable) {
		throwNotYetImplemented();
		return null;
	}

	@Override
	public Collection<AbstractIdentifiable> readWhereExistencePeriodFromDateIsLessThan(AbstractIdentifiable identifiable) {
		throwNotYetImplemented();
		return null;
	}

	@Override
	public AbstractIdentifiable read(String globalIdentifierCode) {
		throwNotYetImplemented();
		return null;
	}

	@Override
	public Collection<AbstractIdentifiable> read(Collection<String> globalIdentifierCodes) {
		throwNotYetImplemented();
		return null;
	}

	@Override
	public Long countWhereExistencePeriodFromDateIsLessThan(AbstractIdentifiable identifiable) {
		throwNotYetImplemented();
		return null;
	}

	@Override
	public Collection<AbstractIdentifiable> readWhereExistencePeriodFromDateIsGreaterThan(AbstractIdentifiable identifiable) {
		throwNotYetImplemented();
		return null;
	}

	@Override
	public Long countWhereExistencePeriodFromDateIsGreaterThan(AbstractIdentifiable identifiable) {
		throwNotYetImplemented();
		return null;
	}

   
	
}
