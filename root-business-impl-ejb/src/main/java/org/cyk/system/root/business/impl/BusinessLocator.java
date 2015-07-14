package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class BusinessLocator extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -6705685474869857841L;
	private static BusinessLocator INSTANCE;
    @Getter private final Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> typedBusinessBeanMap = new HashMap<>(); 
    
    @Inject private DataTreeTypeBusiness dataTreeTypeBusiness;
    
    @Override
    protected void initialisation() {
        INSTANCE = this;
        super.initialisation();
    }
    
    public void register(Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> beansMap){
        typedBusinessBeanMap.putAll(beansMap);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public TypedBusiness<AbstractIdentifiable> locate(Class<? extends AbstractIdentifiable> beanClass){
        TypedBusiness<AbstractIdentifiable> businessBean = typedBusinessBeanMap.get(beanClass);
        if(businessBean==null){
            if(DataTreeType.class.isAssignableFrom(beanClass)){
                return (AbstractEnumerationBusiness)dataTreeTypeBusiness;
            }else if(AbstractDataTree.class.isAssignableFrom(beanClass)){
                ;
            }
        }
        
        return businessBean;
    }
    
    @SuppressWarnings("unchecked")
    public TypedBusiness<AbstractIdentifiable> locate(AbstractIdentifiable bean){
        return locate((Class<AbstractIdentifiable>) bean.getClass());
    }
    
    public static BusinessLocator getInstance() {
        return INSTANCE;
    }
    
}
