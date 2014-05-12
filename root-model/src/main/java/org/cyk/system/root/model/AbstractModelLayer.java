package org.cyk.system.root.model;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractLayer;

public abstract class AbstractModelLayer extends AbstractLayer<Identifiable<?>> implements Serializable {
    
    /*
    protected Set<Class<?>> parametersClasses = new HashSet<>();
    
    @Override
    protected void initialisation() {
       super.initialisation();
       for(Bean<?> bean : layerBeans(Model.class)){
           Model modelAnnotation = bean.getBeanClass().getAnnotation(Model.class);
           if(CrudProcessingStrategy.PARAMETER.equals(modelAnnotation.processingStrategy()))
               parametersClasses.add(bean.getBeanClass());
       }
    }
    */
    
}
