package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.utility.common.cdi.AbstractLayer;

public abstract class AbstractBusinessLayer extends AbstractLayer<AbstractBusinessService<?>> implements BusinessLayer, Serializable {

    //FIXME can be merge in a single MAP??? Why not???
    private static final Map<Class<AbstractDataTree<DataTreeType>>, AbstractDataTreeBusiness<AbstractDataTree<DataTreeType>, DataTreeType>> 
        DATA_TREE_BUSINESS_BEAN_MAP = new HashMap<>();
        
    private static final Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> 
        TYPED_BUSINESS_BEAN_MAP = new HashMap<>();    
    
    @Inject protected GenericBusiness genericBusiness;
    
    @Override
    protected void initialisation() {
        super.initialisation();
        registerDataTreeBusinessBean(DATA_TREE_BUSINESS_BEAN_MAP);
        registerTypedBusinessBean(TYPED_BUSINESS_BEAN_MAP);
    }
    
    /* shortcut methods */
    
    @SuppressWarnings("unchecked")
    protected <T extends AbstractIdentifiable> T create(T anObject){
        return (T) genericBusiness.create(anObject);
    }
    
    public static AbstractDataTreeBusiness<AbstractDataTree<DataTreeType>, DataTreeType> findDataTreeBusinessBean(Class<AbstractDataTree<DataTreeType>> beanClass){
        AbstractDataTreeBusiness<AbstractDataTree<DataTreeType>, DataTreeType> businessBean = DATA_TREE_BUSINESS_BEAN_MAP.get(beanClass);
        if(businessBean==null)
            throw new RuntimeException("No "+AbstractDataTree.class.getSimpleName()+" Business has been registered for <"+beanClass+">");
        return businessBean;
    }
    
    @SuppressWarnings("unchecked")
    public static AbstractDataTreeBusiness<AbstractDataTree<DataTreeType>, DataTreeType> findDataTreeBusinessBean(AbstractDataTree<DataTreeType> bean){
        return findDataTreeBusinessBean((Class<AbstractDataTree<DataTreeType>>) bean.getClass());
    }
    
    public static TypedBusiness<AbstractIdentifiable> findTypedBusinessBean(Class<AbstractIdentifiable> beanClass){
        TypedBusiness<AbstractIdentifiable> businessBean = TYPED_BUSINESS_BEAN_MAP.get(beanClass);
        return businessBean;
    }

}
