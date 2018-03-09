package org.cyk.system.root.business.impl.validation;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessThrowable;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.system.root.persistence.impl.globalidentification.GlobalIdentifierPersistenceMappingConfiguration;
import org.cyk.system.root.persistence.impl.globalidentification.GlobalIdentifierPersistenceMappingConfiguration.Property;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.ConditionHelper.Condition;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.ValidationHelper;

@Singleton
public class ValidationPolicyImpl extends AbstractBean implements ValidationPolicy, Serializable {

	private static final long serialVersionUID = -2342883118003657110L;
	@Inject protected GenericDao genericDao;
    protected LanguageBusiness languageBusiness = LanguageBusinessImpl.getInstance(); 
    
    @Override 
    public void validateCreate(Identifiable<?> anIdentifiable) {
    	//ValidationHelper.getInstance().validate(anIdentifiable, Constant.Action.CREATE, Boolean.TRUE);
    	
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
    	applyValidatorObject(anIdentifiable);
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
            //DefaultValidator.getInstance().validate(anIdentifiable);
        	//inject(DefaultValidator.class).validate(anIdentifiable);
        	new ValidationHelper.Validate.Adapter.Default(anIdentifiable).setIsThrowMessages(Boolean.TRUE).setThrowableClass(BusinessThrowable.class).execute();
        else
            validator.validate(anIdentifiable);
    }
    
    protected void checkValueSetConstraints(Identifiable<?> anIdentifiable){
        if(anIdentifiable instanceof AbstractIdentifiable){
            AbstractIdentifiable identifiable = (AbstractIdentifiable) anIdentifiable;
            //TODO better to look for all field with system in groups
            exceptionUtils().exception(identifiable.getIdentifier()!=null,"exception.value.set.system",new Object[]{"identifier"});
            
            for(FieldHelper.Field index : FieldHelper.Field.get(anIdentifiable.getClass())){
            	if(index.getConstraints().getIsNullable() != null && Boolean.FALSE.equals(index.getConstraints().getIsNullable()))
            		throw__(ConditionHelper.Condition.getBuilderNull(identifiable,index.getName()));	
            	
        	}
        }
    }
    
    @SuppressWarnings("unchecked")
	protected void checkUniqueConstraints(Identifiable<?> anIdentifiable){
    	AbstractIdentifiable identifiable = (AbstractIdentifiable) anIdentifiable;
    	GlobalIdentifierPersistenceMappingConfiguration configuration = GlobalIdentifierPersistenceMappingConfiguration.get(anIdentifiable.getClass(), Boolean.FALSE);
    	if(configuration == null)
    		;
    	else{
    		for(Property property : configuration.getUniqueProperties())
    			checkUniqueConstraints(identifiable, property.getName());
    	}
    	
    	for(FieldHelper.Field index : FieldHelper.Field.get(anIdentifiable.getClass())){
    		if(Boolean.TRUE.equals(index.getConstraints().getIsUnique()))
    			checkUniqueConstraints(identifiable, index.getName());
    	}
    	
    	TypedDao<AbstractIdentifiable> business = inject(PersistenceInterfaceLocator.class).injectTypedByObject(identifiable);
    	if(anIdentifiable.getIdentifier()==null){
    		if(business==null)
    			;
    		else
    			exceptionUtils().duplicates((Class<AbstractIdentifiable>)identifiable.getClass(), business.readDuplicates(identifiable)
    				, configuration==null?0l:configuration.getMaximumNumberOfDuplicateAllowed());
    	}else{
    		//TODO what about update
    	}
    }
    
    protected void checkUniqueConstraints(AbstractIdentifiable identifiable,String fieldName){
        LoggingHelper.Message.Builder loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
    	Object fieldValue = commonUtils.readProperty(identifiable, fieldName);
        String fieldLabelId = LanguageBusinessImpl.FIELD_MARKER_START+(StringUtils.contains(fieldName, Constant.CHARACTER_DOT.toString()) 
        		? StringUtils.substringAfterLast(fieldName, Constant.CHARACTER_DOT.toString()) : fieldName);
    	
        loggingMessageBuilder.addNamedParameters("Check field unique constraint for an instance",identifiable.getClass().getSimpleName(),"field",fieldName,"value",fieldValue);
        
        if(identifiable.getIdentifier()==null){
        	Long countInDB = inject(GenericDao.class).use(identifiable.getClass()).select(Function.COUNT).where(null,fieldName,"uniqueValue", fieldValue,ArithmeticOperator.EQ).oneLong();
        	loggingMessageBuilder.addNamedParameters("Check for Create. Count existing",countInDB);
        	throw__(new Condition.Builder.Comparison.Count.Adapter.Default().setFieldObject(identifiable).setFieldName(fieldName).setValue1(countInDB).setValue2(1)
        			.setGreater(Boolean.TRUE).setEqual(Boolean.TRUE));
        	
        }else{
        	AbstractIdentifiable inDB = genericDao.use(identifiable.getClass()).read(identifiable.getIdentifier());
            Object oldValue = commonUtils.readProperty(inDB, fieldName);
            //TODO more check are required. 1 both null 2 1st null second not null 3 first not null second null
        	if(oldValue!=null && fieldValue!=null && !oldValue.equals(fieldValue))
                //field value has changed
        		//System.out.println("ValidationPolicyImpl.checkUniqueConstraints() "+oldValue+" : "+fieldValue);
                exceptionUtils().exception(genericDao.use(identifiable.getClass()).select(Function.COUNT).where(null,fieldName,"uniqueValue", fieldValue,ArithmeticOperator.EQ).oneLong()>0,
                    "exception.value.duplicate",new Object[]{inject(LanguageBusiness.class).findText(fieldLabelId),fieldValue});
        }
        logTrace(loggingMessageBuilder);
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
