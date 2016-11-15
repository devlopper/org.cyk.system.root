package org.cyk.system.root.business.impl.validation;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.impl.globalidentification.GlobalIdentifierPersistenceMappingConfiguration;
import org.cyk.system.root.persistence.impl.globalidentification.GlobalIdentifierPersistenceMappingConfiguration.Property;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.Function;

@Singleton
public class ValidationPolicyImpl extends AbstractBean implements ValidationPolicy, Serializable {

	private static final long serialVersionUID = -2342883118003657110L;
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
        AbstractValidator<Identifiable<?>> validator = (AbstractValidator<Identifiable<?>>) ValidatorMap.getInstance().validatorOf(anIdentifiable.getClass());
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
    	AbstractIdentifiable identifiable = (AbstractIdentifiable) anIdentifiable;
    	GlobalIdentifierPersistenceMappingConfiguration configuration = GlobalIdentifierPersistenceMappingConfiguration.get(anIdentifiable.getClass(), Boolean.FALSE);
    	if(configuration == null)
    		;
    	else{
    		for(Property property : configuration.getUniqueProperties())
    			checkUniqueConstraints(identifiable, property.getName());
    	}
    }
    
    protected void checkUniqueConstraints(AbstractIdentifiable identifiable,String fieldName){
        Object fieldValue = commonUtils.readProperty(identifiable, fieldName);
        String fieldLabelId = LanguageBusinessImpl.FIELD_MARKER_START+(StringUtils.contains(fieldName, Constant.CHARACTER_DOT.toString()) 
        		? StringUtils.substringAfterLast(fieldName, Constant.CHARACTER_DOT.toString()) : fieldName);
    	logTrace("Check field unique constraint for an instance of {} , field={} , value={}",identifiable.getClass().getSimpleName(),fieldName,fieldValue);
    	
        if(identifiable.getIdentifier()==null){
        	Long countInDB = inject(GenericDao.class).use(identifiable.getClass()).select(Function.COUNT).where(null,fieldName,"uniqueValue", fieldValue,ArithmeticOperator.EQ).oneLong();
        	logTrace("Check for Create. Count existing = {}",countInDB);
        	exceptionUtils().exception(countInDB>0,"exception.value.duplicate",new Object[]{inject(LanguageBusiness.class).findText(fieldLabelId),fieldValue});
        }else{
        	AbstractIdentifiable inDB = genericDao.use(identifiable.getClass()).read(identifiable.getIdentifier());
            if(!commonUtils.readProperty(inDB, fieldName).equals(fieldValue))
                //field value has changed
                exceptionUtils().exception(genericDao.use(identifiable.getClass()).select(Function.COUNT).where(null,fieldName,"uniqueValue", fieldValue,ArithmeticOperator.EQ).oneLong()>0,
                    "exception.value.duplicate",new Object[]{inject(LanguageBusiness.class).findText(fieldLabelId),fieldValue});
        }
    }
    
    @Override
    public void validateField(Field field,Object value,Object...crossValues) {
        FieldValidatorMethod method = ValidatorMap.getInstance().validatorOfField(field);
        if(method==null)
            return;
        logTrace("Validating field {} with method {}", field,method);
        method.execute(new Object[]{value});
    }
    
    /**/
    
    protected ExceptionUtils exceptionUtils(){
        return ExceptionUtils.getInstance();
    }
    
}
