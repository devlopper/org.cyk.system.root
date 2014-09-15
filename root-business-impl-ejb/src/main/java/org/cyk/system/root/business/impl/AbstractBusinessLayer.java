package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.business.impl.validation.FieldValidatorMethod;
import org.cyk.system.root.business.impl.validation.ValidatorMap;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractLayer;

public abstract class AbstractBusinessLayer extends AbstractLayer<AbstractBusinessService<?>> implements BusinessLayer, Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -4484371129296972868L;
	@Inject protected GenericBusiness genericBusiness;
    @Inject protected BusinessLocator businessLocator;
    protected ValidatorMap validatorMap = ValidatorMap.getInstance();
    
    @Override
    protected void initialisation() {
        super.initialisation();
        registerTypedBusinessBean(businessLocator.getTypedBusinessBeanMap());
    }
    
    /* shortcut methods */
    
    @SuppressWarnings("unchecked")
    protected <T extends AbstractIdentifiable> T create(T anObject){
        return (T) genericBusiness.create(anObject);
    }
    
    protected <T> void registerValidator(Class<T> clazz,AbstractValidator<T> validator){
        validatorMap.registerValidator(clazz, validator);
    }
    
    protected void registerFieldValidator(Field field,FieldValidatorMethod method){
        validatorMap.registerFieldValidator(field, method);
    }
    
}
