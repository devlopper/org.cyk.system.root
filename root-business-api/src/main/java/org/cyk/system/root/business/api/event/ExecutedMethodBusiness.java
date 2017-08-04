package org.cyk.system.root.business.api.event;

import javax.interceptor.InvocationContext;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.event.ExecutedMethod;

public interface ExecutedMethodBusiness extends TypedBusiness<ExecutedMethod> {
    
	ExecutedMethod instanciateOne(String className,String methodName,Object[] inputs,String inputsAsString,Object[] outputs,String outputAsString,String throwable
			,Long startTime,Long endTime,Long numberOfMillisecond);
	
	ExecutedMethod instanciateOne(InvocationContext invocationContext,String inputAsString,Object output,String outputAsString,String throwable,Long startTime
			,Long endTime,Long numberOfMillisecond);
}
