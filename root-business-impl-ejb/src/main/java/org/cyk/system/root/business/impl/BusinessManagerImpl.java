package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.persistence.api.PersistenceManager;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.cdi.AbstractStartupBean;

@Stateless @TransactionAttribute(TransactionAttributeType.NEVER)
public class BusinessManagerImpl extends AbstractStartupBean implements BusinessManager,Serializable {

    private Collection<BusinessEntityInfos> entitiesInfos;
    
    @Inject private PersistenceManager persistenceManager;
    @Inject private LanguageBusiness languageBusiness;
    
    private Collection<BusinessLayer> businessLayers = new ArrayList<>();
    
    @Override
    protected void initialisation() {
        super.initialisation();
        for(Object object : startupBeanExtension.getReferences())
            if(BusinessLayer.class.isAssignableFrom(object.getClass()))
                businessLayers.add((BusinessLayer) object);
    }
    
    @Override
    public void createData() {
        for(BusinessLayer layer : businessLayers)
            layer.createInitialData();
       
    }
    
    @Override
    public Collection<BusinessEntityInfos> findEntitiesInfos() {
        if(entitiesInfos==null){
            entitiesInfos = new HashSet<>();
            for(Class<? extends Identifiable<?>> clazz : persistenceManager.findEntities()){
                BusinessEntityInfos b = new BusinessEntityInfos(clazz, languageBusiness);
                
                entitiesInfos.add(b);
            }
        }
        return entitiesInfos;
    }

    @Override
    public Collection<BusinessEntityInfos> findEntitiesInfos(CrudStrategy crudStrategy) {
        Collection<BusinessEntityInfos> l = new HashSet<>();
        for(BusinessEntityInfos infos : findEntitiesInfos()){
            if(infos.getCrudStrategy()!=null && infos.getCrudStrategy().equals(crudStrategy))
                l.add(infos);
        }
        return l;
    }
    
    @Override
    public Collection<BusinessLayer> findBusinessLayers() {
       return businessLayers;
    }

}
