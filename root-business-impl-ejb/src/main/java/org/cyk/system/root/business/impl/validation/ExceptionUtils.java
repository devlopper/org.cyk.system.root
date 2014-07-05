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

    private static ExceptionUtils INSTANCE;
    
    public static ExceptionUtils getInstance() {
        //if(INSTANCE==null)
        //    INSTANCE = new ExceptionUtils();
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
    
    public void exception(Boolean condition,String messageId,Object[] parameters){
        if(Boolean.TRUE.equals(condition))
            throw new BusinessException(languageBusiness.findText(messageId,parameters));
    }
    
    public void exception(Boolean condition,String messageId){
        exception(condition, messageId, null);
    }
    
    public void exception(String messageId){
        exception(Boolean.TRUE, messageId);
    }

    public void resourceNotFound(){
        exception("exception.resource.notfound");
    }
    
}
