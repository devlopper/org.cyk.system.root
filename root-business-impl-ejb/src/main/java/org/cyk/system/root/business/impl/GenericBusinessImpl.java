package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.AbstractGenericBusinessService;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.system.root.persistence.impl.GenericDaoImpl;

@Stateless 
//@Remote 
//@Business
public class GenericBusinessImpl extends AbstractIdentifiableBusinessServiceImpl<AbstractIdentifiable> implements GenericBusiness,Serializable {
	
	private static final long serialVersionUID = -1042342183332719272L;
 
	@Inject private BusinessInterfaceLocator businessInterfaceLocator;
	
	@Inject private GenericDaoImpl genericDaoImpl;
	
	@Override
	protected PersistenceService<AbstractIdentifiable, Long> getPersistenceService() {
	    return genericDao;
	}
	
	@Override
	public AbstractIdentifiable create(AbstractIdentifiable anIdentifiable) {	    
	    TypedBusiness<AbstractIdentifiable> businessBean = businessInterfaceLocator.injectTypedByObject(anIdentifiable);
	    if(businessBean==null){
	    	inject(ValidationPolicy.class).validateCreate(anIdentifiable);
	        return genericDao.create(anIdentifiable);
        }else{
        	return businessBean.create(anIdentifiable); 
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AbstractIdentifiable create(AbstractIdentifiable identifiable, Collection<? extends AbstractIdentifiable> identifiables) {
		create(identifiable);
		create((Collection<AbstractIdentifiable>)identifiables);
		return identifiable;
	}
	
	@Override
	public void create(Collection<AbstractIdentifiable> identifiables) {
		if(identifiables==null)
			return;
		for(AbstractIdentifiable identifiable : identifiables)
			create(identifiable);
	}

	@Override
	public AbstractIdentifiable update(AbstractIdentifiable anObject) {
	    TypedBusiness<AbstractIdentifiable> businessBean = businessInterfaceLocator.injectTypedByObject(anObject);
	    if(businessBean==null){
	    	inject(ValidationPolicy.class).validateUpdate(anObject);
            return genericDao.update(anObject);
        }else
            return businessBean.update(anObject);   
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AbstractIdentifiable update(AbstractIdentifiable identifiable, Collection<? extends AbstractIdentifiable> identifiables) {
		update(identifiable);
		update((Collection<AbstractIdentifiable>)identifiables);
		return identifiable;
	}
	
	@Override
	public void update(Collection<AbstractIdentifiable> identifiables) {
		if(identifiables==null)
			return;
		for(AbstractIdentifiable identifiable : identifiables)
			update(identifiable);
	}

	@Override
	public AbstractIdentifiable delete(AbstractIdentifiable anObject) {
	    TypedBusiness<AbstractIdentifiable> businessBean = businessInterfaceLocator.injectTypedByObject(anObject);
        if(businessBean==null){
        	inject(ValidationPolicy.class).validateDelete(anObject);
            return genericDao.delete(anObject);
        }else
            return businessBean.delete(anObject);   
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AbstractIdentifiable delete(AbstractIdentifiable identifiable,Collection<? extends AbstractIdentifiable> identifiables) {
		delete(identifiable);
		delete((Collection<AbstractIdentifiable>)identifiables);
		return identifiable;
	}
	
	@Override
	public void delete(Collection<AbstractIdentifiable> identifiables) {
		if(identifiables==null)
			return;
		for(AbstractIdentifiable identifiable : identifiables)
			delete(identifiable);
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public AbstractGenericBusinessService<AbstractIdentifiable, Long> use(Class<? extends AbstractIdentifiable> aClass) {
		genericDao.use(aClass);
		return this;
	}
	
	@Override
	public AbstractIdentifiable save(AbstractIdentifiable identifiable) {
	    if(identifiable.getIdentifier()==null)
	        return create(identifiable);
	    else 
	        return update(identifiable);
	}
	
	@SuppressWarnings("unchecked")
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public <T extends AbstractIdentifiable> T load(Class<T> aClass, Long identifier) {
	    TypedBusiness<T> businessBean = (TypedBusiness<T>) BusinessInterfaceLocator.getInstance().injectTyped((Class<AbstractIdentifiable>) aClass);
	    if(businessBean==null)
	        return (T) use(aClass).find(identifier);
	    else
	        return businessBean.load(identifier);
	}
	
	@Override
	public AbstractIdentifiable refresh(AbstractIdentifiable identifiable) {
	    if(identifiable.getIdentifier()==null)
	        return identifiable;
	    return genericDao.refresh(identifiable);
	}

	@Override
	public void flushEntityManager() {
		genericDaoImpl.getEntityManager().flush();
	}
}
