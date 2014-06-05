package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.AbstractGenericBusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.PersistenceService;

@Stateless
public class GenericBusinessServiceImpl extends AbstractBusinessService<AbstractIdentifiable> implements GenericBusiness,Serializable {
	
	private static final long serialVersionUID = -1042342183332719272L;

	@Inject private GenericDao dao;
	
	@Override
	protected PersistenceService<AbstractIdentifiable, Long> getPersistenceService() {
	    return dao;
	}
	
	@Override
	public AbstractIdentifiable create(AbstractIdentifiable anObject) {
		return dao.create(anObject);
	}

	@Override
	public AbstractIdentifiable update(AbstractIdentifiable anObject) {
		return dao.update(anObject);
	}

	@Override
	public AbstractIdentifiable delete(AbstractIdentifiable anObject) {
		return dao.delete(anObject);
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public AbstractGenericBusinessService<AbstractIdentifiable, Long> use(Class<? extends AbstractIdentifiable> aClass) {
		dao.use(aClass);
		return this;
	}
	
	@Override
	public AbstractIdentifiable save(AbstractIdentifiable identifiable) {
	    if(identifiable.getIdentifier()==null)
	        return dao.create(identifiable);
	    else
	        return dao.update(identifiable);
	}
	
	@Override
	public AbstractIdentifiable refresh(AbstractIdentifiable identifiable) {
	    if(identifiable.getIdentifier()==null)
	        return identifiable;
	    return dao.refresh(identifiable);
	}

}
