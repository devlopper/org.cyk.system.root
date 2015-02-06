package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;

import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.datasource.DataSource;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.datasource.JdbcDataSource;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.business.impl.validation.FieldValidatorMethod;
import org.cyk.system.root.business.impl.validation.ValidatorMap;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractLayer;

public abstract class AbstractBusinessLayer extends AbstractLayer<AbstractBusinessService<?>> implements BusinessLayer, Serializable {
    
	private static final long serialVersionUID = -4484371129296972868L;
	
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected GenericBusiness genericBusiness;
    @Inject protected BusinessLocator businessLocator;
    @Inject protected BusinessManager businessManager;
    @Inject protected LanguageBusiness languageBusiness;
    
    protected ValidatorMap validatorMap = ValidatorMap.getInstance();
    
    @Override
    protected void initialisation() {
        super.initialisation();
        id = this.getClass().getName();
        registerTypedBusinessBean(businessLocator.getTypedBusinessBeanMap());
    }
    
	@Override
	public void registerTypedBusinessBean(Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> arg0) {
		
	}
    
    /* shortcut methods */
    
    protected void registerResourceBundle(String id, ClassLoader aClassLoader) {
		languageBusiness.registerResourceBundle(id, aClassLoader);
	}

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
    
    protected DataSource createShiroDataSource(){
    	return new JdbcDataSource();
    }
    
    
    
}
