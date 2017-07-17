package org.cyk.system.root.business.impl.validation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessException;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.StringHelper.CaseType;
import org.cyk.utility.common.helper.ThrowableHelper;

import lombok.Setter;

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
        	String message = new StringHelper.ToStringMapping.Adapter.Default(messageId).setCaseType(CaseType.FU).addParameters(parameters).execute();
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
    
    @SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> void exists(T identifiable){
    	if(identifiable==null)
    		return;
    	duplicates((Class<T>)identifiable.getClass(), Arrays.asList(identifiable),1l);
    }
    
    public void exception(Exception exception){
        exception(Boolean.TRUE,"exception.internal",new Object[]{exception.toString()});
    }
    
    public <T extends AbstractIdentifiable> void duplicates(Class<T> aClass,Collection<T> identifiables,Long maximum){
    	if(identifiables==null || maximum==null || identifiables.isEmpty())
    		return;
    	Set<String> codes = new LinkedHashSet<>();
    	for(T identifiable : identifiables)
    		codes.add(identifiable.getCode());
    	logTrace("Duplicates : {} , maximum : {}", identifiables,maximum);
    	exception(identifiables.size() > maximum ,"exception.record.duplicate",new Object[]{inject(LanguageBusiness.class).findClassLabelText(aClass)
    			,StringUtils.join(codes,Constant.CHARACTER_COMA.toString())});
    }

    /* Specialized short cuts */
    
    public void resourceNotFound(){
        exception("","exception.resource.notfound");
    }
    
    public void comparisonBetween(BigDecimal value,Interval interval,String valueName){
    	if(interval==null)
    		return;
    	exception(!inject(IntervalBusiness.class).contains(interval, value, 2) , "exception.comparison.between",new Object[]{valueName
    		,inject(NumberBusiness.class).format(interval.getLow().getValue())
    		,inject(NumberBusiness.class).format(interval.getHigh().getValue())});
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
    			inject(LanguageBusiness.class).findText(processNameId)
			,inject(NumberBusiness.class).format(limit)
    		,inject(LanguageBusiness.class).findText(subjectNameId)
			});
    }
    public void cannotProcessMoreThan(Long value,String processNameId,BigDecimal limit,String subjectNameId){
    	if(value==null)
    		return;
    	cannotProcessMoreThan(new BigDecimal(value), processNameId, limit, subjectNameId);
    }
    public void cannotProcessMoreThan(Long value,String processNameId,BigDecimal limit,Class<? extends AbstractIdentifiable> identifiableClass){
    	cannotProcessMoreThan(value, processNameId, limit
    			, inject(ApplicationBusiness.class).findBusinessEntityInfos(identifiableClass).getUserInterface().getLabelId());
    }
    public void cannotProcessMoreThan(Long value,String processNameId,Interval interval,Class<? extends AbstractIdentifiable> identifiableClass){
    	if(interval==null || interval.getLow()==null || interval.getLow().getValue()==null)
    		return;
    	cannotProcessMoreThan(value, processNameId, interval.getHigh().getValue(), identifiableClass);
    }
    
    public void cannotCreateMoreThan(Long value,Interval interval,Class<? extends AbstractIdentifiable> identifiableClass){
    	cannotProcessMoreThan(value, "crud.create", interval, identifiableClass);
    }
    
    /**/
    
    public String getMessage(Throwable throwable){
    	String message = null;
    	Throwable cause = ThrowableHelper.getInstance().getInstanceOf(throwable, PersistenceException.class);
    	if(cause == null){
    		
    	}else{
    		List<String> tokens = DatabaseManagementSystemMessageProvider.Adapter.DEFAULT.getTokens(cause);	
    		message = languageBusiness.findText(tokens.remove(0),tokens.toArray());	
    	}
    	return message;
    }
    
}
