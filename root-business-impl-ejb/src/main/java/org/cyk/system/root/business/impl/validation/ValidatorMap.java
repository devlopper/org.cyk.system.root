package org.cyk.system.root.business.impl.validation;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import lombok.Getter;
import lombok.extern.java.Log;

import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

@Log
@Getter @Singleton @Deployment(initialisationType=InitialisationType.EAGER,order=-2)
public class ValidatorMap extends AbstractBean implements Serializable {
    
    private static ValidatorMap INSTANCE;
    
    private final Map<Class<Object>, AbstractValidator<Object>> classMap = new HashMap<>();   
    private final Map<Field, FieldValidatorMethod> fieldMap = new HashMap<>();   
    
    @Override
    protected void initialisation() {
        INSTANCE = this;
        super.initialisation();
    }
    
    @SuppressWarnings("unchecked")
    public <T> AbstractValidator<T> validatorOf(Class<T> objectClass){
        return (AbstractValidator<T>) classMap.get(objectClass);
    }
    
    @SuppressWarnings("unchecked")
    public <T> void registerValidator(Class<T> clazz,AbstractValidator<T> validator){
        classMap.put((Class<Object>) clazz, (AbstractValidator<Object>) validator);
        log.info(clazz.getName()+" validated by "+validator.getClass().getName());
    }
    
    public void registerFieldValidator(Field field,FieldValidatorMethod method){
        fieldMap.put(field, method);
        log.info(field+" validated by "+method);
    }
    
    public FieldValidatorMethod validatorOfField(Field field){
        return fieldMap.get(field);
    }
    
    public static ValidatorMap getInstance() {
        //if(INSTANCE==null)
         //   INSTANCE = new ValidatorMap();
        return INSTANCE;
    }

}
