package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.generator.StringGeneratorBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.generator.StringGenerator;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;

import lombok.Getter;
import lombok.Setter;

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
	
	protected void addLogMessageBuilderParameter(LogMessage.Builder logMessageBuilder,Object...parameters){
		if(logMessageBuilder==null)
			return;
		logMessageBuilder.addParameters(parameters);
	}
	
	protected <T extends AbstractIdentifiable> T read(Class<T> aClass,String code){
		return inject(GenericDao.class).read(aClass, code);
	}
	
	@SuppressWarnings("unchecked")
	protected void set(Object object,Field field,Class<?> fieldType,Integer index,String[] values,LogMessage.Builder logMessageBuilder) {
		if(index >= values.length || StringUtils.isBlank(values[index])){
			if(index >= values.length)
				addLogMessageBuilderParameter(logMessageBuilder,"Cannot access "+field.getName()+" at index "+index,"*");
			else if(StringUtils.isBlank(values[index]))
				addLogMessageBuilderParameter(logMessageBuilder,"Blank value of "+field.getName()+" at index "+index,"*");
		}else{
			Object value = null;
			if(fieldType.isAnnotationPresent(Entity.class))
				value = read((Class<AbstractIdentifiable>)fieldType, values[index]);
			else if(Date.class.equals(fieldType))
				value = inject(TimeBusiness.class).parse(values[index]);
			else if(String.class.equals(fieldType))
				value = values[index];
			else if(BigDecimal.class.equals(fieldType))
				value = new BigDecimal(values[index]);
			else if(Long.class.equals(fieldType))
				value = new Long(values[index]);
			else{
				addLogMessageBuilderParameter(logMessageBuilder,fieldType+" fo field named "+field.getName()+" not handled","*");
				return;
			}
			commonUtils.writeField(field, object, value);
				
		}
	}
	
	protected void set(Object object,String fieldName,SetListener listener) {
		Field field = commonUtils.getFieldFromClass(object.getClass(), fieldName);
		if(field==null)
			addLogMessageBuilderParameter(listener.getLogMessageBuilder(),"Cannot access "+fieldName+" in class "+object.getClass(),"*");
		else
			set(object, field,listener.getFieldType() == null ? listener.getFieldType(object.getClass(), field) : listener.getFieldType(), listener.getIndex()
					, listener.getValues(), listener.getLogMessageBuilder());
		listener.setIndex(listener.getIndex()+listener.getIndexIncrement());
	
	}
	
	public static interface SetListener extends Serializable {
		
		public Integer getIndex();
		public void setIndex(Integer index);
		public Integer getIndexIncrement();
		public SetListener setIndexIncrement(Integer indexIncrement);
		public String[] getValues();
		public Class<?> getFieldType();
		public SetListener setFieldType(Class<?> fieldType);
		public Class<?> getFieldType(Class<?> aClass,Field field);
		public LogMessage.Builder getLogMessageBuilder();
		
		@Getter @Setter
		public static class Adapter extends BeanAdapter implements SetListener,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected Integer index,indexIncrement=1;
			protected String[] values;
			protected LogMessage.Builder logMessageBuilder;
			protected Class<?> fieldType;
			
			public SetListener setIndexIncrement(Integer indexIncrement){
				this.indexIncrement = indexIncrement;
				return this;
			}
			
			public SetListener setFieldType(Class<?> fieldType){
				this.fieldType = fieldType;
				return this;
			}
			
			@Override
			public Class<?> getFieldType(Class<?> aClass,Field field) {
				return null;
			}
			
			public static class Default extends SetListener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(String[] values,Integer index,LogMessage.Builder logMessageBuilder) {
					this.values = values;
					this.index = index;
					this.logMessageBuilder = logMessageBuilder;
				}
				
				@Override
				public Class<?> getFieldType(Class<?> aClass,Field field) {
					return commonUtils.getFieldType(aClass, field);
				}
			}
			
		}
		
	}
	
	
	/**/
	
}
