package org.cyk.system.root.business.impl.validation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Setter;

import org.cyk.system.root.business.api.BusinessException;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.ArithmeticOperator;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class ExceptionUtils extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -5111826049865487656L;
	private static ExceptionUtils INSTANCE;
    
    public static ExceptionUtils getInstance() {
        return INSTANCE;
    }
    
    @Inject @Setter protected LanguageBusiness languageBusiness;
    @Inject @Setter protected NumberBusiness numberBusiness;
    @Inject @Setter protected TimeBusiness timeBusiness;
   
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

    /* Specialized short cuts */
    
    public void resourceNotFound(){
        exception("","exception.resource.notfound");
    }
    
    public void comparisonBetween(BigDecimal value,Interval interval,String valueName){
    	exception(!RootBusinessLayer.getInstance().getIntervalBusiness().contains(interval, value, 2) , "exception.comparison.between",new Object[]{valueName
    			,RootBusinessLayer.getInstance().getNumberBusiness().format(interval.getLow().getValue())
    			,RootBusinessLayer.getInstance().getNumberBusiness().format(interval.getHigh().getValue())});
	}
    
    public void comparison(Boolean condition,String operand1,ArithmeticOperator operator,String operand2){
    	exception(condition, "exception.comparison",new Object[]{operand1,languageBusiness.findText(operator),operand2});
	}
    public void comparison(Boolean condition,String operand1,ArithmeticOperator operator,BigDecimal operand2){
    	comparison(condition, operand1, operator, numberBusiness.format(operand2));
    }
    
    public void cannotProcessMoreThan(BigDecimal value,String processNameId,BigDecimal limit,String subjectNameId){
    	if(value==null)
    		return;
    	exception(value.compareTo(limit) >= 0 , "exception.cannotprocessmorethan",new Object[]{
    		RootBusinessLayer.getInstance().getLanguageBusiness().findText(processNameId)
			,RootBusinessLayer.getInstance().getNumberBusiness().format(limit)
    		,RootBusinessLayer.getInstance().getLanguageBusiness().findText(subjectNameId)
			});
    }
    public void cannotProcessMoreThan(Long value,String processNameId,BigDecimal limit,String subjectNameId){
    	if(value==null)
    		return;
    	cannotProcessMoreThan(new BigDecimal(value), processNameId, limit, subjectNameId);
    }
    public void cannotProcessMoreThan(Long value,String processNameId,BigDecimal limit,Class<? extends AbstractIdentifiable> identifiableClass){
    	cannotProcessMoreThan(value, processNameId, limit
    			, RootBusinessLayer.getInstance().getApplicationBusiness().findBusinessEntityInfos(identifiableClass).getUserInterface().getLabelId());
    }
    public void cannotProcessMoreThan(Long value,String processNameId,Interval interval,Class<? extends AbstractIdentifiable> identifiableClass){
    	if(interval==null || interval.getLow()==null || interval.getLow().getValue()==null)
    		return;
    	cannotProcessMoreThan(value, processNameId, interval.getHigh().getValue(), identifiableClass);
    }
    
    public void cannotCreateMoreThan(Long value,Interval interval,Class<? extends AbstractIdentifiable> identifiableClass){
    	cannotProcessMoreThan(value, "crud.create", interval, identifiableClass);
    }
    
}
