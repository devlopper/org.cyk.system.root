package org.cyk.system.root.model.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.BeanAdapter;

@Getter @Setter @NoArgsConstructor @Entity
public class UniformResourceLocatorParameter extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 5164454356106670454L;

	@ManyToOne @NotNull private UniformResourceLocator uniformResourceLocator;
	
	private String value;
	
	public UniformResourceLocatorParameter(UniformResourceLocator uniformResourceLocator, String name, String value) {
		super();
		this.uniformResourceLocator = uniformResourceLocator;
		this.value = value;
		setName(name);
	}
	
	@Override
	public String toString() {
		return getName()+"="+value;
	}
	
	/**/

	public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_VALUE = "value";
	
	/**/
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Builder extends AbstractBuilder<UniformResourceLocatorParameter> implements Serializable {

		private static final long serialVersionUID = -4888461643390793029L;

		private UniformResourceLocator uniformResourceLocator;
		private Object name,value;
		
		public Builder() {
			super(UniformResourceLocatorParameter.class);
		}
		
		public static Builder instanciateOne(){
			Builder builder = new Builder();
			builder.instanciate();
			return builder;
		}
		
		public Builder set(UniformResourceLocator uniformResourceLocator,Object name,Object value){
			setUniformResourceLocator(uniformResourceLocator);
			setNameValue(name, value);
			return this;
		}
		
		public Builder setNameValue(Object name,Object value){
			setName(name);
			setValue(value);
			return this;
		}
		
		/*public Builder setValue(final Object value){
			instance.setValue(ListenerUtils.getInstance().getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
				@Override
				public String execute(Listener listener) {
					return listener.getValueAsString(value);
				}
			}));
			return this;
		}*/
		
		/**/
		
		public static UniformResourceLocatorParameter create(String name,Object value){
			return instanciateOne().setName(name).setValue(value).build();
		}
		
		public static UniformResourceLocatorParameter createClass(Class<? extends AbstractIdentifiable> aClass){
			return instanciateOne().setName(CLASS).setValue(aClass).build();
		}
		
		public static UniformResourceLocatorParameter createIdentifiable(AbstractIdentifiable identifiable){
			return instanciateOne().setName(IDENTIFIABLE).setValue(identifiable).build();
		}
		
		public static UniformResourceLocatorParameter createIdentifiable(Long identifier){
			return instanciateOne().setName(IDENTIFIABLE).setValue(identifier).build();
		}
		
		public static UniformResourceLocatorParameter createIdentifiable(){
			return instanciateOne().setName(IDENTIFIABLE).setValue(Constant.EMPTY_STRING).build();
		}
		
		/**/
		
		public UniformResourceLocatorParameter build(){
			if(instance == null)
				instanciate();
			if(name!=null)
				instance.setName(name.toString());
			if(value!=null)
				instance.setValue(value.toString());
			if(uniformResourceLocator!=null)
				instance.setUniformResourceLocator(uniformResourceLocator);
			return instance;
		}
		
		/**/
		
		public static interface Listener{
			
			Collection<Listener> COLLECTION = new ArrayList<>();
			
			String getValueAsString(Object object);
			@Deprecated String getIdentifiableParameter();
			@Deprecated String getClassParameter();
			
			public static class Adapter extends BeanAdapter implements Listener,Serializable{
				private static final long serialVersionUID = -1259531075221759261L;
				
				@Override
				public String getValueAsString(Object object) {
					return null;
				}
				
				@Override
				public String getIdentifiableParameter() {
					return null;
				}

				@Override
				public String getClassParameter() {
					return null;
				}
				
				/**/
				
				public static class Default extends Adapter implements Serializable{
					private static final long serialVersionUID = -5558988592648009882L;
					
					@Override
					public String getValueAsString(Object object) {
						return object.toString();
					}
				}	
			}
			
		}
		
	}

	/**/
	
	public static final String ACTION_IDENTIFIER = "actid";
	public static final String GLOBAL_IDENTIFIER = "globalid";
	public static final String GLOBAL_IDENTIFIER_OWNER_CLASS = GLOBAL_IDENTIFIER+"ownerclass";
	public static final String USER_ACCOUNT = "ridp";
	public static final String FILE_IDENTIFIER = "fileidentifier";
	public static final String REPORT_IDENTIFIER = "ridp";
	public static final String FORM_MODEL = "formmodel";
	public static final String FORM_MODEL_ACTOR = "afm";
	public static final String CLASS = "clazz";
	public static final String IDENTIFIABLE = "identifiable";//TODO to be changed to IDENTIFIER
	public static final String WINDOW="windowParam";
	public static final String FILE_EXTENSION="fileExtensionParam";
	public static final String PRINT="print";
	
	public static final String PDF="pdf";
	public static final String XLS="xls";
	public static final String DETAILS="details";
	public static final String CRUD="crud";
	public static final String CRUD_CREATE="create";
	public static final String CRUD_READ="read";
	public static final String CRUD_UPDATE="update";
	public static final String CRUD_DELETE="delete";
	
	public static final String WINDOW_MODE = "windowmode";
	public static final String WINDOW_MODE_DIALOG = "windowmodedialog";
	public static final String WINDOW_MODE_NORMAL = "windowmodenormal";
	public static final String PREVIOUS_URL = "previousurl";
	public static final String TAB_ID = "tabid";
	public static final String FORM_IDENTIFIER = "formid";
	public static final String TITLE = "title";
	public static final String ATTACHMENT = "attachment";
	
	public static final String URL = "url";
	public static final String VIEW_IDENTIFIER = "outcome";
	public static final String VIEW_IDENTIFIER_REPORT = "report";
	
	public static final String DATA_COLLECTION_RENDERING_MODE = "datacollectionrenderingmode";
	public static final String DATA_COLLECTION_RENDERING_MODE_TABLE = "table";
	public static final String DATA_COLLECTION_RENDERING_MODE_HIERARCHY = "hierarchy";
	
	public static final String LAZY = "lazy";
	public static final String LAZY_TRUE = "true";
	public static final String LAZY_FALSE = "false";
	
	public static final String FILTER = "filter";
}
