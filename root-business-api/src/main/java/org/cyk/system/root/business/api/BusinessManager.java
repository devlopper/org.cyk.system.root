package org.cyk.system.root.business.api;

import java.util.Collection;

import org.cyk.utility.common.annotation.Model.CrudStrategy;

public interface BusinessManager {

    /**
     * Create application initial data. 
     */
    void createData();
    
    Collection<BusinessEntityInfos> findEntitiesInfos();
    
    Collection<BusinessEntityInfos> findEntitiesInfos(CrudStrategy crudStrategy);
    
}
