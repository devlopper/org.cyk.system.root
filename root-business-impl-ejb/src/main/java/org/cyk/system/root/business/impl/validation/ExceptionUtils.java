package org.cyk.system.root.business.impl.validation;

import java.io.Serializable;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.cyk.system.root.business.api.BusinessException;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class ExceptionUtils extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -5111826049865487656L;
	private static ExceptionUtils INSTANCE;
    
    public static ExceptionUtils getInstance() {
        return INSTANCE;
    }
    
    @Inject protected LanguageBusiness languageBusiness;    
   
    @Override
    protected void initialisation() {
        INSTANCE = this;
        super.initialisation();
    }
    
    public void exception(Set<String> messages){
        throw new BusinessException(messages);
    }
    
    public void exception(AbstractValidator<?> validator){
        throw new BusinessException(validator.getMessages());
    }
    
    public void exception(Boolean condition,String identifier,String messageId,Object[] parameters){
        if(Boolean.TRUE.equals(condition)){
        	String message = languageBusiness.findText(messageId,parameters);
            BusinessException exception = new BusinessException(message);
            exception.setIdentifier(identifier);
            throw exception;
        }
    }
    public void exception(Boolean condition,String messageId,Object[] parameters){
    	exception(condition,null,messageId,parameters);
    }
    
    public void exception(Boolean condition,String identifier,String messageId){
        exception(condition, identifier,messageId, null);
    }
    public void exception(Boolean condition,String messageId){
    	exception(condition,null,messageId);
    }
    
    public void exception(String identifier,String messageId){
        exception(Boolean.TRUE,identifier, messageId);
    }
    public void exception(String messageId){
    	exception("",messageId);
    }
    
    public void exception(Exception exception){
        exception("","exception.internal");
    }

    public void resourceNotFound(){
        exception("","exception.resource.notfound");
    }
    
}
