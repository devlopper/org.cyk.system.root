package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.TypedBusiness.SetListener;
import org.cyk.system.root.business.api.generator.StringGeneratorBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.generator.StringGenerator;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.cdi.AbstractBean;

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
	
	@SuppressWarnings("unchecked")
	protected void set(Object instance,Field field,Class<?> fieldType,Integer index,String[] values,LogMessage.Builder logMessageBuilder) {
		if(index >= values.length || StringUtils.isBlank(values[index])){
			if(index >= values.length)
				addLogMessageBuilderParameters(logMessageBuilder,"Cannot access "+field.getName()+" at index "+index,"*");
			else if(StringUtils.isBlank(values[index]))
				addLogMessageBuilderParameters(logMessageBuilder,"Blank value of "+field.getName()+" at index "+index,"*");
		}else{
			Object value = null;
			if(fieldType.isAnnotationPresent(Entity.class)){
				value = read((Class<AbstractIdentifiable>)fieldType, values[index]);
				if(value==null)
					addLogMessageBuilderParameters(logMessageBuilder,"no "+fieldType.getSimpleName()+" found for ",values[index]);	
			}else
				value = commonUtils.convertString(values[index], fieldType);
			
			//if(BigDecimal.class.equals(fieldType))
			//	System.out.println(values[index]+" => "+value);
			
			/*else if(Date.class.equals(fieldType))
				value = inject(TimeBusiness.class).parse(values[index]);
			else if(String.class.equals(fieldType))
				value = values[index];
			else if(BigDecimal.class.equals(fieldType))
				value = new BigDecimal(values[index]);
			else if(Long.class.equals(fieldType))
				value = new Long(values[index]);
			else if(Byte.class.equals(fieldType))
				value = new Byte(values[index]);
			else if(Integer.class.equals(fieldType))
				value = new Integer(values[index]);
			else if(Boolean.class.equals(fieldType))
				value = new Boolean(values[index]);
			else if(fieldType.isEnum()){
				for(Object object : fieldType.getEnumConstants()){
					if(object.toString().equals(values[index])){
						value = object;
						break;
					}
				}
				if(value==null)
					addLogMessageBuilderParameters(logMessageBuilder,"no enum constant found for ",values[index]);	
			}else{
				addLogMessageBuilderParameters(logMessageBuilder,fieldType+" fo field named "+field.getName()+" not handled","*");
				return;
			}
			*/
			commonUtils.writeField(field, instance, value);
			addLogMessageBuilderParameters(logMessageBuilder,"set field "+field.getName()+" to",value);	
		}
	}
	
	protected void set(SetListener listener,String...fieldNames) {
		String fieldName = StringUtils.join(fieldNames,Constant.CHARACTER_DOT.toString());
		addLogMessageBuilderParameters(listener.getLogMessageBuilder(), "set",fieldName);
		Object instance = StringUtils.contains(fieldName, Constant.CHARACTER_DOT.toString()) ? commonUtils.readProperty(listener.getInstance(),
				StringUtils.substringBeforeLast(fieldName, Constant.CHARACTER_DOT.toString())) 
				: listener.getInstance();
		addLogMessageBuilderParameters(listener.getLogMessageBuilder(), "instance",instance);
		Field field = commonUtils.getFieldFromClass(instance.getClass(),  StringUtils.contains(fieldName, Constant.CHARACTER_DOT.toString()) ? 
				StringUtils.substringAfterLast(fieldName, Constant.CHARACTER_DOT.toString()) : fieldName);
		if(/*instance==null || */field==null){
			/*if(instance==null)
				addLogMessageBuilderParameters(listener.getLogMessageBuilder(),"Cannot access instance "+fieldName+" in class "+listener.getInstance().getClass(),"*");
			else */if(field==null)
				addLogMessageBuilderParameters(listener.getLogMessageBuilder(),"Cannot access field "+fieldName+" in class "+instance.getClass(),"*");	
		}else
			set(instance, field,listener.getFieldType() == null ? listener.getFieldType(instance.getClass(), field) : listener.getFieldType(), listener.getIndex()
					, listener.getValues(), listener.getLogMessageBuilder());
		listener.setIndex(listener.getIndex()+listener.getIndexIncrement());
		listener.setFieldType(null);
		listener.setNullValue(null);
	}
	
	protected void setFieldValuesIfBlank(AbstractIdentifiable source,AbstractIdentifiable destination){
		if(StringUtils.isBlank(destination.getCode()))
			destination.setCode(source.getCode());
		if(StringUtils.isBlank(destination.getName()))
			destination.setCode(source.getName());
		if(StringUtils.isBlank(destination.getAbbreviation()))
			destination.setCode(source.getAbbreviation());
	}
	
	/**/
	
}
