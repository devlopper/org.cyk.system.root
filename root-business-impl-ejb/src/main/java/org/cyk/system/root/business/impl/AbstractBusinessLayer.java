package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractLayer;

public abstract class AbstractBusinessLayer extends AbstractLayer<AbstractBusinessService<?>> implements BusinessLayer, Serializable {

    @Inject protected GenericBusiness genericBusiness;
    
    /* shortcut methods */
    
    @SuppressWarnings("unchecked")
    protected <T extends AbstractIdentifiable> T create(T anObject){
        return (T) genericBusiness.create(anObject);
    }

}
