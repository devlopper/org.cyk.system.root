package org.cyk.system.root.model.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.validation.Client;

@Getter @Setter @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public class UniformResourceLocator extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -4633680454658548588L;

	@Input @InputText @Column(nullable=false/*,unique=true*/) @NotNull(groups=Client.class) private String address;
	//private Boolean parametersRequired = Boolean.TRUE;
	
	@Transient private Collection<UniformResourceLocatorParameter> parameters = new ArrayList<>();

	public UniformResourceLocator() {
		super();
	}

	public UniformResourceLocator(String address) {
		super(address,address,null,null);
		this.address = address;
	}
	
	public void addParameter(String name,String value){
		parameters.add(new UniformResourceLocatorParameter(this, name, value));
	}
	public void addParameter(String name){
		addParameter(name, null);
	}
	
	@Override
	public String toString() {
		return address+(parameters==null || parameters.isEmpty() ? Constant.EMPTY_STRING : Constant.CHARACTER_QUESTION_MARK
				+(StringUtils.join(parameters,Constant.CHARACTER_AMPERSTAMP)));
	}
	
	/**/
	
	public static final String FIELD_PATH = "path";
	public static final String FIELD_PARAMETERS = "parameters";
	
	/**/
	
	public static class Builder extends AbstractBuilder<UniformResourceLocator> implements Serializable {

		private static final long serialVersionUID = -4888461643390793029L;

		public Builder() {
			super(UniformResourceLocator.class);
		}
		
		public static Builder instanciateOne(){
			Builder builder = new Builder();
			builder.instanciate();
			return builder;
		}
		
		public Builder setAddress(String address){
			instance.setAddress(address);
			return this;
		}
		
		public Builder addParameter(String name,Object value){
			instance.getParameters().add(UniformResourceLocatorParameter.Builder.create(name, value));
			return this;
		}
		
		public Builder addParameters(Object...parameters){
			if(parameters!=null)
				for(int i=0;i<parameters.length-1;i+=2)
					addParameter((String)parameters[i], parameters[i+1]);
			return this;
		}
		
		public Builder addClassParameter(Class<? extends AbstractIdentifiable> aClass){
			addParameters(UniformResourceLocatorParameter.Builder.createClass(aClass));
			return this;
		}
		
		public Builder addIdentifiableParameter(AbstractIdentifiable identifiable){ 
			addClassParameter(identifiable.getClass());
			addParameters(UniformResourceLocatorParameter.Builder.createIdentifiable(identifiable));
			return this;
		}
		
		public Builder addIdentifiableParameter(Long identifier){
			addParameters(UniformResourceLocatorParameter.Builder.createIdentifiable(identifier));
			return this;
		}
		
		public Builder addIdentifiableParameter(){
			addParameters(UniformResourceLocatorParameter.Builder.createIdentifiable());
			return this;
		}
		
		public Builder addAnyInstanceOf(Class<? extends AbstractIdentifiable> aClass){
			addClassParameter(aClass).addIdentifiableParameter();
			return this;
		}
		
		/**/
		
		public static UniformResourceLocator create(String address,Object...parameters){
			return instanciateOne().setAddress(address).addParameters(parameters).build();
		}
		
		
		/**/
		
		public UniformResourceLocator build(){
			return instance;
		}
		
		/**/
		
		public static interface Listener{
			
			Collection<Listener> COLLECTION = new ArrayList<>();
			
			public static class Adapter extends BeanAdapter implements Listener,Serializable{
				private static final long serialVersionUID = -1259531075221759261L;
				
				/**/
				
				public static class Default extends Adapter implements Serializable{
					private static final long serialVersionUID = -5558988592648009882L;
					
				}

				
			}
			
			
		}
		
	}

}
