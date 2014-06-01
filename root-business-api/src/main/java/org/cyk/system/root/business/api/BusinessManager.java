package org.cyk.system.root.business.api;

import java.util.Collection;

import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

public interface BusinessManager {

    /**
     * Create application initial data. 
     */
    void createData();
    
    Collection<BusinessEntityInfos> findEntitiesInfos();
    
    Collection<BusinessEntityInfos> findEntitiesInfos(CrudStrategy crudStrategy);
    
    Collection<BusinessLayer> findBusinessLayers();
}
