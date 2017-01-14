package org.cyk.system.root.business.impl;

import javax.interceptor.InvocationContext;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.event.ExecutedMethodBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.Constant;

public class LogInterceptorAdapter extends org.cyk.utility.common.cdi.annotation.Interceptor.Adapter.Default{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void processAfter(InvocationContext invocationContext,String input, Object result,String output,Throwable throwable,Long startTime,Long endTime, Long numberOfMillisecond) {
		super.processAfter(invocationContext,input, result,output,throwable,startTime,endTime, numberOfMillisecond);
		if(!StringUtils.startsWithAny(invocationContext.getMethod().getName(), "instanciate","find","get","set")){
			inject(GenericBusiness.class).create(inject(ExecutedMethodBusiness.class)
					.instanciateOne(invocationContext,input,result,output,throwable==null?null:throwable.toString(),startTime, endTime, numberOfMillisecond));
			}
	}
	
	private String getObjectsAsString(Object[] objects) {
		if(objects.length==1 && objects[0] instanceof AbstractIdentifiable){
			AbstractIdentifiable identifiable = (AbstractIdentifiable) objects[0];
			return (identifiable.getClass().getSimpleName()+Constant.CHARACTER_SPACE)
					+(StringUtils.isBlank(identifiable.getCode()) ? identifiable.getIdentifier() : identifiable.getCode());
		}
		return super.getInputAsString(objects);
	}
	
	@Override
	public String getInputAsString(Object[] objects) {
		return getObjectsAsString(objects);
	}
	
	@Override
	public String getOutputAsString(Object[] objects) {
		return getObjectsAsString(objects);
	}
}
