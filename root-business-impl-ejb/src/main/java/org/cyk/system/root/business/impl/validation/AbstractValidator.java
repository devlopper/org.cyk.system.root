package org.cyk.system.root.business.impl.validation;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.validation.Client;


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
	public static final Map<Class<Object>, AbstractValidator<Object>> MAP = new HashMap<>();   
	protected static final String MESSAGE_NOT_VALID_FORMAT = "%s n'est pas valide";
	
	@Inject protected LanguageBusiness languageBusiness;	
	
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
		groups.add(Client.class);
	}
	
	/*
	public AbstractValidator<OBJECT> init(OBJECT object){
		// initialize fields 
		this.object=object;
		
		return this;
	}*/
	
	public AbstractValidator<OBJECT> validate(OBJECT object){
	    this.object=object;
		if(Boolean.TRUE.equals(autoClearMessages))
			messages = new LinkedHashSet<>();
		/* processing */
		process(objectClass, object);
		process(validatorClass, this);
		if(!Boolean.TRUE.equals(isSucces()))
		    ExceptionUtils.getInstance().exception(this);
		return this;
	}
	
	private <T> void process(Class<T> aClass,T aObject){
		/* bean validation */
		Set<ConstraintViolation<T>> constraintViolationsModel = validator.validate(aObject,groups==null?null:groups.toArray(new Class<?>[]{}));
		
		/* collect messages */
		if(!constraintViolationsModel.isEmpty())
        	for(ConstraintViolation<T> violation : constraintViolationsModel)
        		messages.add(formatMessage(violation));
	}
	
	public void manualProcess(){
		
	}
	
	protected String formatMessage(ConstraintViolation<?> constraintViolation){
		return constraintViolation.getPropertyPath()+" "+constraintViolation.getMessage();
		//return constraintViolation.getMessage();
	}
	
	public Boolean isSucces(){
		return messages==null || messages.isEmpty();
	}
	
	public String getMessagesAsString(){
		return messages==null?"":StringUtils.join(messages, "\r\n");
	}
	
	protected static String messageNotValid(String constraint){
		return String.format(MESSAGE_NOT_VALID_FORMAT, constraint);
	}
	
	/**/
	/*
	protected void exception(String messageId){
		exception(messageId, Boolean.TRUE);
	}
	
	protected void exception(String messageId,Boolean rollback){
		if(Boolean.TRUE.equals(rollback))
			throw new BusinessException(languageBusiness.findText(messageId));
		throw new BusinessExceptionNoRollBack(languageBusiness.findText(messageId));
	}
	*/
	/**/
	
	@SuppressWarnings("unchecked")
    public static <T> AbstractValidator<T> validatorOf(Class<T> objectClass){
        return (AbstractValidator<T>) MAP.get(objectClass);
    }
	
	@SuppressWarnings("unchecked")
    public static <T> void registerValidator(Class<T> clazz,AbstractValidator<T> validator){
	    MAP.put((Class<Object>) clazz, (AbstractValidator<Object>) validator);
    }
}
