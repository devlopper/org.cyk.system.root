package org.cyk.system.root.business.impl.network;

import java.io.Serializable;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.utility.common.cdi.AbstractBean;

public class UniformResourceLocatorBuilder extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -4888461643390793029L;

	public static String CLASS_PARAMETER_NAME;
	public static String IDENTIFIABLE_PARAMETER_NAME;
	
	private UniformResourceLocator uniformResourceLocator;
	
	public UniformResourceLocatorBuilder newUniformResourceLocator(){
		uniformResourceLocator = new UniformResourceLocator();
		return this;
	}
	
	public UniformResourceLocatorBuilder setAddress(String address){
		uniformResourceLocator.setAddress(address);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public UniformResourceLocatorBuilder addParameter(String name,Object value){
		if(value instanceof Class<?>){
			if(AbstractIdentifiable.class.isAssignableFrom((Class<?>) value))
				value = RootBusinessLayer.getInstance().getApplicationBusiness().findBusinessEntityInfos((Class<? extends AbstractIdentifiable>) value).getIdentifier();
			else
				value = value.toString();
		}else if(value instanceof String)
			value =(String) value;
		else
			value = value==null?null:value.toString();
		uniformResourceLocator.addParameter(name, value==null?null:value.toString());
		return this;
	}
	
	public UniformResourceLocatorBuilder addParameters(Object...parameters){
		if(parameters!=null)
			for(int i=0;i<parameters.length-1;i+=2)
				addParameter((String)parameters[i], parameters[i+1]);
		return this;
	}
	
	public UniformResourceLocatorBuilder addClassParameter(Class<? extends AbstractIdentifiable> aClass){
		addParameters(CLASS_PARAMETER_NAME, aClass);
		return this;
	}
	
	public UniformResourceLocatorBuilder addIdentifiableParameter(AbstractIdentifiable identifiable){
		addParameters(IDENTIFIABLE_PARAMETER_NAME, identifiable.getIdentifier().toString());
		return this;
	}
	
	public UniformResourceLocatorBuilder addIdentifiableParameter(Long identifier){
		addParameters(IDENTIFIABLE_PARAMETER_NAME, identifier==null?null:identifier.toString());
		return this;
	}
	
	public UniformResourceLocatorBuilder addIdentifiableParameter(){
		addParameter(IDENTIFIABLE_PARAMETER_NAME, null);
		return this;
	}
	
	public UniformResourceLocatorBuilder addAnyInstanceOf(Class<? extends AbstractIdentifiable> aClass){
		addClassParameter(aClass).addIdentifiableParameter();
		return this;
	}
	
	public UniformResourceLocatorBuilder addIdentifiable(AbstractIdentifiable identifiable){
		addParameters(CLASS_PARAMETER_NAME, identifiable.getClass(),IDENTIFIABLE_PARAMETER_NAME, identifiable.getIdentifier().toString());
		return this;
	}
	
	public UniformResourceLocator build(){
		return uniformResourceLocator;
	}
	
}
