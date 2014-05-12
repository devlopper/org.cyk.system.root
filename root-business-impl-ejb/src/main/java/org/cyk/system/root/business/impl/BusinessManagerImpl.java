package org.cyk.system.root.business.impl;

import java.util.Collection;
import java.util.HashSet;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.persistence.api.PersistenceManager;
import org.cyk.utility.common.annotation.Model.CrudStrategy;
import org.cyk.utility.common.cdi.AbstractStartupBean;

@Singleton
public class BusinessManagerImpl extends AbstractStartupBean implements BusinessManager {

    private Collection<BusinessEntityInfos> entitiesInfos;
    
    @Inject private PersistenceManager persistenceManager;
    @Inject private LanguageBusiness languageBusiness;
    
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
            if(infos.getCrudStrategy().equals(crudStrategy))
                l.add(infos);
        }
        return l;
    }

}
