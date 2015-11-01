package org.cyk.system.root.business.impl.validation;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.RootValueValidator;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;


/**
 * Ensemble de methodes automatiquement appele par le conteneur pour la validation de contraintes.<br/>
 * La signature doit avoir le format suivant : isValidX ou X est le nom de l'attribut ou de la contrainte. ex : isValidDateNaissance , 
 * is ValidMajorite , isValidDistance  , etc.
 * 
 * @author Komenan Y .Christian
 *
 * @param <OBJECT>
 */
public abstract class AbstractValidator<OBJECT> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	@Inject protected RootValueValidator valueValidator;
	@Inject protected LanguageBusiness languageBusiness;
	
	protected Map<Field, AbstractValidator<Object>> fieldValidatorMap = new HashMap<>();
	protected Class<OBJECT> objectClass;
	protected Class<AbstractValidator<OBJECT>> validatorClass;
	//the object to validate
	@Getter protected OBJECT object;
	
	protected List<Class<?>> groups = new LinkedList<>();
	
	// the processor
	protected Validator validator;

	// the results
	@Getter protected Set<String> messages = new LinkedHashSet<>();
	@Getter @Setter private Boolean autoClearMessages=Boolean.TRUE;

	public AbstractValidator(Class<OBJECT> objectClass) {
		super();
		constructor(objectClass);
	}
	
	@SuppressWarnings("unchecked")
	public AbstractValidator() {
		super();
		constructor((Class<OBJECT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}
	
	@SuppressWarnings("unchecked")
	private void constructor(Class<OBJECT> objectClass) {
		this.objectClass = objectClass;
		validatorClass = (Class<AbstractValidator<OBJECT>>) this.getClass();
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		//groups.add(Client.class);
	}
	
	/*
	public AbstractValidator<OBJECT> init(OBJECT object){
		// initialize fields 
		this.object=object;
		
		return this;
	}*/
	
	public AbstractValidator<OBJECT> validate(OBJECT object){
		//logTrace("Validating object {}", object);
	    this.object=object;
		if(Boolean.TRUE.equals(autoClearMessages))
			messages = new LinkedHashSet<>();
		/* processing */
		process(objectClass, object);
		process(validatorClass, this);
		processMappedFields();
		if(!Boolean.TRUE.equals(isSuccess()))
		    ExceptionUtils.getInstance().exception(this);
		return this;
	}
	
	private <T> void process(Class<T> aClass,T aObject){
		/* bean validation */
		Set<ConstraintViolation<T>> constraintViolationsModel = validator.validate(aObject,groups==null?null:groups.toArray(new Class<?>[]{}));
		
		/* collect messages */
		if(!constraintViolationsModel.isEmpty())
        	for(ConstraintViolation<T> violation : constraintViolationsModel){
        		logTrace("Constraint Violation : {}.{} : {} ",aObject.getClass().getName(),violation.getPropertyPath(),violation.getMessage());
        		messages.add(formatMessage(violation));
        	}
	}
	
	protected void processMappedFields(){
		for(Entry<Field, AbstractValidator<Object>> entry : fieldValidatorMap.entrySet()){
			AbstractValidator<Object> fieldValidator = entry.getValue();
			try {
				fieldValidator.validate(commonUtils.readField(object, entry.getKey(), Boolean.FALSE));
			} catch (Exception e) {
				messages.addAll(fieldValidator.getMessages());
			}
		}
	}
	
	protected String formatMessage(ConstraintViolation<?> constraintViolation){
		//return constraintViolation.getPropertyPath()+" "+constraintViolation.getMessage();
		Field field = commonUtils.getFieldFromClass(object.getClass(), constraintViolation.getPropertyPath().toString());
		//Formating should be moved to ValidationMessageInterpolator. But how to get field ???
		return (field==null?"":(languageBusiness.findFieldLabelText(field)+" "))+constraintViolation.getMessage();
		//return constraintViolation.getMessage();
	}
	
	public Boolean isSuccess(){
		return messages==null || messages.isEmpty();
	}
	
	public String getMessagesAsString(){
		return messages==null?"":StringUtils.join(messages, "\r\n");
	}
	
	/**/
	
	protected void registerFieldValidatorMap(String name,AbstractValidator<?> validator){
		@SuppressWarnings("unchecked")
		AbstractValidator<Object> v = (AbstractValidator<Object>) validator;
		fieldValidatorMap.put(commonUtils.getFieldFromClass(objectClass, name), v);
	}
}
