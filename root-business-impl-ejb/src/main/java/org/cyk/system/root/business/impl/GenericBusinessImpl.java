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
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.PersistenceService;

@Stateless 
//@Remote 
//@Business
public class GenericBusinessImpl extends AbstractBusinessService<AbstractIdentifiable> implements GenericBusiness,Serializable {
	
	private static final long serialVersionUID = -1042342183332719272L;
 
	@Inject private BusinessLocator businessLocator;
	
	@Override
	protected PersistenceService<AbstractIdentifiable, Long> getPersistenceService() {
	    return genericDao;
	}
	
	@Override
	public AbstractIdentifiable create(AbstractIdentifiable anIdentifiable) {
	    //TODO logic has to be merged : Find associated typed business bean and do the job 
	    /*
	    TypedBusiness<AbstractIdentifiable> typedBusiness = typedBusinessBean(anIdentifiable);
        if(typedBusiness==null){
            if(anIdentifiable instanceof DataTreeType)
                typedBusiness = dataTreeTypeBusiness;
            else if(anIdentifiable instanceof AbstractDataTree){
                AbstractDataTree<DataTreeType> dataTree =(AbstractDataTree<DataTreeType>) anIdentifiable;
                //return typedBusinessBean(dataTree).create(dataTree);
                return typedBusinessBean((Class<AbstractIdentifiable>) anIdentifiable.getClass())
        }
	    
	    
	    if(typedBusiness==null){
            validationPolicy.validateCreate(anIdentifiable);
            return dao.create(anIdentifiable);
        }else
            return typedBusiness.create(anIdentifiable);
	    */
	    
	    /*
	    if(anIdentifiable instanceof DataTreeType){
            return dataTreeTypeBusiness.create((DataTreeType) anIdentifiable);
	    }else if(anIdentifiable instanceof AbstractDataTree){
	        AbstractDataTree<DataTreeType> dataTree =(AbstractDataTree<DataTreeType>) anIdentifiable;
            //return typedBusinessBean(dataTree).create(dataTree);
	        return BusinessLocator.getInstance().locate((Class<AbstractIdentifiable>) anIdentifiable.getClass()).create(dataTree);
	    }else if(anIdentifiable instanceof Event)
	        return eventBusiness.create((Event) anIdentifiable);
	    else{
	        TypedBusiness<AbstractIdentifiable> typedBusiness = BusinessLocator.getInstance().locate((Class<AbstractIdentifiable>) anIdentifiable.getClass());
	        if(typedBusiness==null){
	            validationPolicy.validateCreate(anIdentifiable);
	            return genericDao.create(anIdentifiable);
	        }else
	            return typedBusiness.create(anIdentifiable);
	    }
	    */
	    
	    TypedBusiness<AbstractIdentifiable> businessBean = businessLocator.locate(anIdentifiable);
	    if(businessBean==null){
	        validationPolicy.validateCreate(anIdentifiable);
	        return genericDao.create(anIdentifiable);
        }else
            return businessBean.create(anIdentifiable);   
	}
	
	@Override
	public void create(Collection<AbstractIdentifiable> identifiables) {
		for(AbstractIdentifiable identifiable : identifiables)
			create(identifiable);
	}

	@Override
	public AbstractIdentifiable update(AbstractIdentifiable anObject) {
	    TypedBusiness<AbstractIdentifiable> businessBean = businessLocator.locate(anObject);
        if(businessBean==null){
            validationPolicy.validateUpdate(anObject);
            return genericDao.update(anObject);
        }else
            return businessBean.update(anObject);   
	}
	
	@Override
	public void update(Collection<AbstractIdentifiable> identifiables) {
		for(AbstractIdentifiable identifiable : identifiables)
			update(identifiable);
	}

	@Override
	public AbstractIdentifiable delete(AbstractIdentifiable anObject) {
	    TypedBusiness<AbstractIdentifiable> businessBean = businessLocator.locate(anObject);
        if(businessBean==null){
            validationPolicy.validateDelete(anObject);
            return genericDao.delete(anObject);
        }else
            return businessBean.delete(anObject);   
	}
	
	@Override
	public void delete(Collection<AbstractIdentifiable> identifiables) {
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
	    TypedBusiness<T> businessBean = (TypedBusiness<T>) BusinessLocator.getInstance().locate((Class<AbstractIdentifiable>) aClass);
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

}
