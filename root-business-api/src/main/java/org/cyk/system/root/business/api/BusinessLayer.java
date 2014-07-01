package org.cyk.system.root.business.api;

import java.util.Map;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;


public interface BusinessLayer {

   
    /**
     * Create application initial data. 
     */
    void createInitialData();
    
    void registerDataTreeBusinessBean(Map<Class<AbstractDataTree<DataTreeType>>, AbstractDataTreeBusiness<AbstractDataTree<DataTreeType>, DataTreeType>> beansMap);
    
    void registerTypedBusinessBean(Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> beansMap);
    
}
