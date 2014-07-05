package org.cyk.system.root.business.impl.validation;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.Function;

@Singleton
public class ValidationPolicyImpl extends AbstractBean implements ValidationPolicy, Serializable {

    @Inject protected GenericDao genericDao;
    protected LanguageBusiness languageBusiness = LanguageBusinessImpl.getInstance(); 
   
    @Override 
    public void validateCreate(Identifiable<?> anIdentifiable) {
        anIdentifiable.setIdentifier(null);
        checkValueSetConstraints(anIdentifiable);
        checkUniqueConstraints(anIdentifiable);
        applyValidatorObject(anIdentifiable);
    }
    
    @Override
    public void validateRead(Identifiable<?> anIdentifiable) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void validateUpdate(Identifiable<?> anIdentifiable) {
        checkUniqueConstraints(anIdentifiable);
    }

    @Override
    public void validateDelete(Identifiable<?> anIdentifiable) {
        // TODO Auto-generated method stub
        
    }
    
    /**/
    
    protected void applyValidatorObject(Identifiable<?> anIdentifiable){
        @SuppressWarnings("unchecked")
        AbstractValidator<Identifiable<?>> validator = (AbstractValidator<Identifiable<?>>) AbstractValidator.validatorOf(anIdentifiable.getClass());
        if(validator==null)
            DefaultValidator.getInstance().validate(anIdentifiable);
        else
            validator.validate(anIdentifiable);
    }
    
    protected void checkValueSetConstraints(Identifiable<?> anIdentifiable){
        if(anIdentifiable instanceof AbstractIdentifiable){
            AbstractIdentifiable identifiable = (AbstractIdentifiable) anIdentifiable;
            //TODO better to look for all field with system in groups
            exceptionUtils().exception(identifiable.getIdentifier()!=null,"exception.value.set.system",new Object[]{"identifier"});
        }
    }
    
    protected void checkUniqueConstraints(Identifiable<?> anIdentifiable){
        if(anIdentifiable instanceof AbstractEnumeration) {
            AbstractEnumeration enumeration = (AbstractEnumeration) anIdentifiable;
            //TODO look for field with @UniqueConstraint
            //Code
            if(enumeration.getIdentifier()==null){
                exceptionUtils().exception(genericDao.use(enumeration.getClass()).select(Function.COUNT).where("code", enumeration.getCode()).oneLong()>0,
                        "exception.value.duplicate",new Object[]{"Code",enumeration.getCode()});
            }else{
                AbstractEnumeration inDB = (AbstractEnumeration) genericDao.use(enumeration.getClass()).read(enumeration.getIdentifier());
                if(!inDB.getCode().equals(enumeration.getCode()))
                    //Code has changed
                    exceptionUtils().exception(genericDao.use(enumeration.getClass()).select(Function.COUNT).where("code", enumeration.getCode()).oneLong()>0,
                        "exception.value.duplicate",new Object[]{"Code",enumeration.getCode()});
            }
            
            
        }
    }
    
    @Override
    public void validateField(Field field,Object value,Object...crossValues) {
        FieldValidatorMethod method = AbstractValidator.validatorOfField(field);
        if(method==null)
            return;
        //System.out.println("ValidationPolicyImpl.validateField() : "+field);
        //System.out.println("Validation Method : "+method);
        method.execute(new Object[]{value});
    }
    
    /**/
    
    protected ExceptionUtils exceptionUtils(){
        return ExceptionUtils.getInstance();
    }
    
}
