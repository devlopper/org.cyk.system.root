package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.interceptor.InvocationContext;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.event.ExecutedMethodBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.event.ExecutedMethod;
import org.cyk.system.root.model.event.ExecutedMethodInputOutput;
import org.cyk.system.root.persistence.api.event.ExecutedMethodDao;


public class ExecutedMethodBusinessImpl extends AbstractTypedBusinessService<ExecutedMethod, ExecutedMethodDao> implements ExecutedMethodBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ExecutedMethodBusinessImpl(ExecutedMethodDao dao) {
		super(dao); 
	}
	
	@Override
	protected Collection<? extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<?>> getListeners() {
		return Listener.COLLECTION;
	}

	@Override
	protected ExecutedMethod __instanciateOne__(String[] values,org.cyk.system.root.business.api.TypedBusiness.InstanciateOneListener<ExecutedMethod> listener) {
		super.__instanciateOne__(values, listener);
		listener.getInstance().getGlobalIdentifierCreateIfNull();
		set(listener.getSetListener(), ExecutedMethod.FIELD_CLASS_NAME);
		set(listener.getSetListener(), ExecutedMethod.FIELD_METHOD_NAME);
		set(listener.getSetListener(), ExecutedMethod.FIELD_INPUT,ExecutedMethodInputOutput.FIELD_COUNT);
		set(listener.getSetListener(), ExecutedMethod.FIELD_INPUT,ExecutedMethodInputOutput.FIELD_TEXT);
		set(listener.getSetListener(), ExecutedMethod.FIELD_INPUT,ExecutedMethodInputOutput.FIELD_COMPRESSED);
		set(listener.getSetListener(), ExecutedMethod.FIELD_OUTPUT,ExecutedMethodInputOutput.FIELD_COUNT);
		set(listener.getSetListener(), ExecutedMethod.FIELD_OUTPUT,ExecutedMethodInputOutput.FIELD_TEXT);
		set(listener.getSetListener(), ExecutedMethod.FIELD_OUTPUT,ExecutedMethodInputOutput.FIELD_COMPRESSED);
		set(listener.getSetListener(), ExecutedMethod.FIELD_THROWABLE);
		
		Integer index = listener.getSetListener().getIndex();
		listener.getInstance().getExistencePeriod().setFromDate(new Date(Long.parseLong(values[index++])));
		listener.getInstance().getExistencePeriod().setToDate(new Date(Long.parseLong(values[index++])));
		listener.getInstance().getExistencePeriod().getNumberOfMillisecond().set(Long.parseLong(values[index++]));
		
		
		return listener.getInstance();
	}
	
	private Boolean compressable(String string){
		return string.length() > 255;
	}

	@Override
	public ExecutedMethod instanciateOne(String className,String methodName,Object[] inputs,String inputsAsString,Object[] outputs
			,String outputAsString,String throwable,Long startTime,Long endTime,Long numberOfMillisecond) {
		return instanciateOne(new String[]{className,methodName,String.valueOf(inputs.length),compressable(inputsAsString) ? commonUtils.compressString(inputsAsString) 
				: inputsAsString,compressable(inputsAsString).toString(),outputs == null ? null : String.valueOf(outputs.length)
				,compressable(outputAsString) ? commonUtils.compressString(outputAsString):outputAsString,compressable(outputAsString).toString(),throwable
						,String.valueOf(startTime),String.valueOf(endTime),String.valueOf(numberOfMillisecond)});
	}
	
	@Override
	public ExecutedMethod instanciateOne(InvocationContext invocationContext,String inputAsString,Object output,String outputAsString,String throwable,Long startTime
			,Long endTime,Long numberOfMillisecond) {
		String className = StringUtils.replace(invocationContext.getTarget().getClass().getName(), "$Proxy$_$$_WeldSubclass", "");
		return instanciateOne(className, invocationContext.getMethod().getName(), invocationContext.getParameters()
				,inputAsString, invocationContext.getMethod().getReturnType().equals(Void.TYPE) ? null : new Object[]{output},outputAsString
						,throwable==null?null:throwable.toString(), startTime, endTime, numberOfMillisecond);
	}
	
	/**/
	
	public static interface Listener extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<ExecutedMethod> {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		/**/
		
		public static class Adapter extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.Adapter.Default<ExecutedMethod> implements Listener,Serializable{
			private static final long serialVersionUID = 1L;
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable{
				private static final long serialVersionUID = 1L;
				
				/**/
			
				
				public static class EnterpriseResourcePlanning extends Listener.Adapter.Default implements Serializable{
					private static final long serialVersionUID = 1L;
					
					/**/					
					
				}
			}
		}
	}

}
