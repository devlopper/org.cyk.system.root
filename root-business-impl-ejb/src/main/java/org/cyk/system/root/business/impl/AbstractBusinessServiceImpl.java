package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.generator.StringGeneratorBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.generator.StringGenerator;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.ThrowableHelper;

public abstract class AbstractBusinessServiceImpl extends AbstractBean implements BusinessService, Serializable {
	private static final long serialVersionUID = 6437552355933877400L;
	
	@Inject protected TimeBusiness timeBusiness;
	@Inject protected NumberBusiness numberBusiness;
	
	protected ExceptionUtils exceptionUtils(){
	    return ExceptionUtils.getInstance();
	}
	
	protected <IDENTIFIABLE extends AbstractIdentifiable> TypedBusiness<IDENTIFIABLE> injectBusinessInterface(Class<IDENTIFIABLE> aClass){
		return inject(BusinessInterfaceLocator.class).injectTyped(aClass);
	}
	
	protected Date universalTimeCoordinated(){
		return timeBusiness.findUniversalTimeCoordinated();
	}

	protected String generateStringValue(String generatorIdentifier,Object input){
		return inject(StringGeneratorBusiness.class).generate(generatorIdentifier, input);
	}
	
	@Deprecated
	protected String generateIdentifier(AbstractIdentifiable identifiable,String runtimeGeneratorIdentifier,StringGenerator databaseGenerator){
		return inject(StringGeneratorBusiness.class).generateIdentifier(identifiable, runtimeGeneratorIdentifier, databaseGenerator);
	}
	
	protected void setCallArgumentsCurrentExecutionStep(BusinessServiceCallArguments<?> callArguments,Object object){
		if(callArguments!=null && callArguments.getExecutionProgress()!=null){
			callArguments.getExecutionProgress().setCurrentExecutionStep(RootBusinessLayer.getInstance().getFormatterBusiness().format(object));
		}
	}
	
	protected void addCallArgumentsWorkDoneByStep(BusinessServiceCallArguments<?> callArguments){
		if(callArguments!=null && callArguments.getExecutionProgress()!=null){
			callArguments.getExecutionProgress().addWorkDoneByStep(1);
		}
	}
	
	protected void clearCallArgumentsExecution(BusinessServiceCallArguments<?> callArguments){
		if(callArguments!=null && callArguments.getExecutionProgress()!=null){
			callArguments.getExecutionProgress().clear();
		}
	}
	
	protected <T extends AbstractIdentifiable> T read(Class<T> aClass,String code){
		return inject(GenericDao.class).read(aClass, code);
	}
	
	@Deprecated
	protected void setFieldValuesIfBlank(AbstractIdentifiable source,AbstractIdentifiable destination){
		if(StringUtils.isBlank(destination.getCode()))
			destination.setCode(source.getCode());
		if(StringUtils.isBlank(destination.getName()))
			destination.setCode(source.getName());
		if(StringUtils.isBlank(destination.getAbbreviation()))
			destination.setCode(source.getAbbreviation());
	}
	
	protected void throwIfDoesNotBelongsToIdentifiablePeriod(Object instance){
		ThrowableHelper.getInstance().throwIfIdentifiablePeriodIsNull(instance);
		ThrowableHelper.getInstance().throwIfDoesNotBelongsToIdentifiablePeriod(instance, AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE);
	}
	
	protected <T> T instanciateOne(Class<T> aClass){
		return ClassHelper.getInstance().instanciateOne(aClass);
	}
	
	/**/
	
}
