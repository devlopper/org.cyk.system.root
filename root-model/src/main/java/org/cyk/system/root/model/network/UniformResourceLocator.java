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
import lombok.experimental.Accessors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.CommonBusinessAction;
import org.cyk.system.root.model.IdentifiableRuntimeCollection;
import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
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
	
	@Transient private IdentifiableRuntimeCollection<UniformResourceLocatorParameter> parameters = new IdentifiableRuntimeCollection<>();

	public UniformResourceLocator() {
		super();
		getParameters().setSynchonizationEnabled(Boolean.TRUE);
	}

	public UniformResourceLocator(String address) {
		super(address,address,null,null);
		this.address = address;
	}
	
	public void addParameter(String name,String value){
		getParameters().addOne(new UniformResourceLocatorParameter(this, name, value));
	}
	
	public void addParameter(String name){
		addParameter(name, null);
	}
	
	public void addParameters(Collection<UniformResourceLocatorParameter> parameters){
		this.getParameters().addMany(parameters);
	}
	
	@Override
	public String toString() {
		return address+(parameters==null || getParameters().getCollection().isEmpty() ? Constant.EMPTY_STRING : Constant.CHARACTER_QUESTION_MARK
				+(StringUtils.join(parameters,Constant.CHARACTER_AMPERSTAMP)));
	}
	
	/**/
	
	public static final String FIELD_ADDRESS = "address";
	public static final String FIELD_PARAMETERS = "parameters";
	
	/**/
	
	public static final String PRIVATE_FOLDER="private";
	public static final String DYNAMIC_FOLDER="__dynamic__";
	public static final String CRUD_FOLDER="crud";
	public static final String SELECT_FOLDER="select";
	public static final String PROCESS_FOLDER="process";
	public static final String PROCESSED_FILE_EXTENSION = "jsf";
	public static final String FILE_LIST = "list";
	public static final String FILE_EDIT = "edit";
	public static final String FILE_CONSULT = "consult";
	public static final String FILE_CREATE_MANY = "createMany";
	
	public static final String DYNAMIC_CRUD_ONE = "/private/__dynamic__/crud/crudone.jsf";
	public static final String DYNAMIC_CRUD_MANY = "/private/__dynamic__/crud/crudmany.jsf";
	public static final String DYNAMIC_SELECT_ONE = "/private/__dynamic__/select/selectone.jsf";
	public static final String DYNAMIC_SELECT_MANY = "/private/__dynamic__/select/selectmany.jsf";
	public static final String DYNAMIC_EXPORT_FILE_JASPER = "/private/file/export/_cyk_report_/_dynamicbuilder_/_jasper_/";
	public static final String EXPORT_FILE_JASPER = "/private/file/export/_cyk_report_/_business_/_jasper_/";
	public static final String EXPORT_FILE = "/private/file/export/report.jsf";
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Builder extends AbstractBuilder<UniformResourceLocator> implements Serializable {

		private static final long serialVersionUID = -4888461643390793029L;

		private String address;
		
		public Builder() {
			super(UniformResourceLocator.class);
		}
		
		public static Builder instanciateOne(){
			Builder builder = new Builder();
			builder.instanciate();
			return builder;
		}
				
		public Builder addParameter(String name,Object value){
			instance.getParameters().addOne(UniformResourceLocatorParameter.Builder.create(name, value));
			return this;
		}
		
		public Builder addParameters(Object...parameters){
			if(parameters!=null)
				for(int i=0;i<parameters.length-1;i+=2)
					addParameter((String)parameters[i], parameters[i+1]);
			return this;
		}
		
		public Builder addParameters(Collection<UniformResourceLocatorParameter> parameters){
			if(parameters!=null)
				instance.addParameters(parameters);
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
		
		public UniformResourceLocator build(){
			if(instance==null)
				instanciate();
			instance.setAddress(address);
			return instance;
		}
		
		/**/
		
		public static UniformResourceLocator create(String address,Object...parameters){
			return instanciateOne().setAddress(address).addParameters(parameters).build();
		}
		
		public static UniformResourceLocator create(final CommonBusinessAction commonBusinessAction,final Object object){
			if(object instanceof AbstractIdentifiable){
				return instanciateOne().setAddress(ListenerUtils.getInstance().getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
					@Override
					public String execute(Listener listener) {
						return listener.getAddress(commonBusinessAction, object);
					}
				})).addParameters(ListenerUtils.getInstance().getCollection(Listener.COLLECTION,new ListenerUtils.CollectionMethod<Listener,UniformResourceLocatorParameter>() {
					@Override
					public Collection<UniformResourceLocatorParameter> execute(Listener listener) {
						return listener.getParameters(commonBusinessAction, object);
					}
				})).build();
			}
			return null;
		}
		
		/**/
		
		public static interface Listener{
			
			Collection<Listener> COLLECTION = new ArrayList<>();
			
			String getAddress(CommonBusinessAction commonBusinessAction,Object object);
			Collection<UniformResourceLocatorParameter> getParameters(CommonBusinessAction commonBusinessAction,Object object);
			
			public static class Adapter extends BeanAdapter implements Listener,Serializable{
				private static final long serialVersionUID = -1259531075221759261L;
				
				@Override
				public String getAddress(CommonBusinessAction commonBusinessAction, Object object) {
					return null;
				}
				
				@Override
				public Collection<UniformResourceLocatorParameter> getParameters(CommonBusinessAction commonBusinessAction, Object object) {
					return null;
				}
				
				/**/
				
				public static class Default extends Adapter implements Serializable{
					private static final long serialVersionUID = -5558988592648009882L;
					
					
					
				}

				
			}
			
			
		}
		
	}

}
