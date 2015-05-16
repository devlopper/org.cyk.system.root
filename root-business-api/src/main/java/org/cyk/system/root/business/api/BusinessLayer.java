package org.cyk.system.root.business.api;

import java.util.Map;

import org.cyk.system.root.model.AbstractIdentifiable;


public interface BusinessLayer {

   
    /**
     * Create application initial data. 
     */
    void createInitialData(Boolean runFakeTransactions);
     
    //void registerDataTreeBusinessBean(Map<Class<AbstractDataTree<DataTreeType>>, AbstractDataTreeBusiness<AbstractDataTree<DataTreeType>, DataTreeType>> beansMap);
    
    void registerTypedBusinessBean(Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> beansMap);
    
}
