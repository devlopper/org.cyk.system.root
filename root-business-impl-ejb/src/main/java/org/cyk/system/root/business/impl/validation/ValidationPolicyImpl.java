package org.cyk.system.root.business.impl.validation;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.cyk.system.root.business.api.BusinessException;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.Function;

@Singleton
public class ValidationPolicyImpl extends AbstractBean implements ValidationPolicy, Serializable {

    @Inject protected GenericDao genericDao;
    @Inject protected LanguageBusiness languageBusiness;    
   
    @Override 
    public void validateCreate(Identifiable<?> anIdentifiable) {
        checkValueSetConstraints(anIdentifiable);
        checkUniqueConstraints(anIdentifiable);
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
    
    protected void checkValueSetConstraints(Identifiable<?> anIdentifiable){
        if(anIdentifiable instanceof AbstractIdentifiable){
            AbstractIdentifiable identifiable = (AbstractIdentifiable) anIdentifiable;
            //TODO better to look for all field with system in groups
            exception(identifiable.getIdentifier()!=null,"exception.value.set.system",new Object[]{"identifier"});
        }
    }
    
    protected void checkUniqueConstraints(Identifiable<?> anIdentifiable){
        if(anIdentifiable instanceof AbstractEnumeration) {
            AbstractEnumeration enumeration = (AbstractEnumeration) anIdentifiable;
            //TODO look for field with @UniqueConstraint
            exception(genericDao.use(enumeration.getClass()).select(Function.COUNT).where("code", enumeration.getCode()).oneLong()>0,
                    "exception.value.duplicate",new Object[]{"Code",enumeration.getCode()});
        }
    }
    
    /**/
    
    protected void exception(Boolean condition,String messageId,Object[] parameters){
        if(Boolean.TRUE.equals(condition))
            throw new BusinessException(languageBusiness.findText(messageId,parameters));
    }
    
    protected void exception(Boolean condition,String messageId){
        exception(condition, messageId, null);
    }

    
    
}
