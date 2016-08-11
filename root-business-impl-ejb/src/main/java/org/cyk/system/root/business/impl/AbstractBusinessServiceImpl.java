package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.generator.StringGenerator;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractBusinessServiceImpl extends AbstractBean implements BusinessService, Serializable {

	private static final long serialVersionUID = 6437552355933877400L;
	
	@Inject protected TimeBusiness timeBusiness;
	@Inject protected NumberBusiness numberBusiness;
	
	protected ExceptionUtils exceptionUtils(){
	    return ExceptionUtils.getInstance();
	}
	
	protected Date universalTimeCoordinated(){
		return timeBusiness.findUniversalTimeCoordinated();
	}

	protected String generateStringValue(String generatorIdentifier,Object input){
		return RootBusinessLayer.getInstance().getStringGeneratorBusiness().generate(generatorIdentifier, input);
	}
	
	protected String generateIdentifier(AbstractIdentifiable identifiable,String runtimeGeneratorIdentifier,StringGenerator databaseGenerator){
		return RootBusinessLayer.getInstance().getStringGeneratorBusiness().generateIdentifier(identifiable, runtimeGeneratorIdentifier, databaseGenerator);
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
	
	
	/**/
	
}
