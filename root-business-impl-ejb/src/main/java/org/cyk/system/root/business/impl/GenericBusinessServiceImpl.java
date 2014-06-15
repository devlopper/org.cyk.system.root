package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.AbstractGenericBusinessService;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.PersistenceService;

@Stateless
public class GenericBusinessServiceImpl extends AbstractBusinessService<AbstractIdentifiable> implements GenericBusiness,Serializable {
	
	private static final long serialVersionUID = -1042342183332719272L;

	// TODO to be removed if circular dependency resolved
	@Inject private ValidationPolicy validationPolicy;
	@Inject private DataTreeTypeBusiness dataTreeTypeBusiness;
	
	@Inject private GenericDao dao;
	
	@Override
	protected PersistenceService<AbstractIdentifiable, Long> getPersistenceService() {
	    return dao;
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public AbstractIdentifiable create(AbstractIdentifiable anIdentifiable) {
	    validationPolicy.validateCreate(anIdentifiable);
	    if(anIdentifiable instanceof DataTreeType)
            return dataTreeTypeBusiness.create((DataTreeType) anIdentifiable);
	    else if(anIdentifiable instanceof AbstractDataTree){
	        AbstractDataTree<DataTreeType> dataTree =(AbstractDataTree<DataTreeType>) anIdentifiable;
            return dataTreeBusinessBean(dataTree).create(dataTree);
	    }else
            return dao.create(anIdentifiable);
	}

	@Override
	public AbstractIdentifiable update(AbstractIdentifiable anObject) {
	    validationPolicy.validateUpdate(anObject);
		return dao.update(anObject);
	}

	@Override
	public AbstractIdentifiable delete(AbstractIdentifiable anObject) {
	    validationPolicy.validateDelete(anObject);
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
	        return create(identifiable);
	    else 
	        return update(identifiable);
	}
	
	@Override
	public AbstractIdentifiable refresh(AbstractIdentifiable identifiable) {
	    if(identifiable.getIdentifier()==null)
	        return identifiable;
	    return dao.refresh(identifiable);
	}

}
