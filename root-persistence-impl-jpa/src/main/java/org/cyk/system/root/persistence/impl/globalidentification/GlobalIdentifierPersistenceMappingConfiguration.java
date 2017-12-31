package org.cyk.system.root.persistence.impl.globalidentification;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Temporal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.MethodHelper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
public class GlobalIdentifierPersistenceMappingConfiguration implements Serializable {

	private static final long serialVersionUID = -2217622778504647280L;

	public static final Map<Class<?>, GlobalIdentifierPersistenceMappingConfiguration> MAP = new HashMap<>();
	
	private Collection<Property> properties;
	
	private Long maximumNumberOfDuplicateAllowed=0l;
	
	public GlobalIdentifierPersistenceMappingConfiguration addProperties(Property...properties){
		if(properties!=null && properties.length>0){
			if(this.properties == null)
				this.properties = new ArrayList<>();
			this.properties.addAll(Arrays.asList(properties));
			//System.out.println("GlobalIdentifierPersistenceMappingConfiguration.addProperties() : "+this);
		}
		return this;
	}
	
	public Property instanciateProperty(String name){
		Property property = new Property();
		property.setName(name);
		addProperties(property);
		return property;
	}
	
	public Property getPropertyByName(String name,Boolean createIfNull){
		if(CollectionHelper.getInstance().isNotEmpty(properties))
			for(Property property : properties)
				if(property.getName().equals(name))
					return property;
		Property property = null;
		if(Boolean.TRUE.equals(createIfNull)){
			property = new Property();
			property.setName(name);
			addProperties(property);
		}
		return property;
	}
	
	public Collection<Property> getUniqueProperties(){
		Collection<Property> uniques = new ArrayList<>();
		if(properties!=null)
			for(Property property : properties)
				if(property.getColumn().unique())
					uniques.add(property);
		return uniques;
	}
	
	public GlobalIdentifierPersistenceMappingConfiguration setMaximumNumberOfDuplicateAllowed(Long maximumNumberOfDuplicateAllowed){
		this.maximumNumberOfDuplicateAllowed = maximumNumberOfDuplicateAllowed;
		return this;
	}
	
	/**/
	
	public static void register(Class<?> aClass,GlobalIdentifierPersistenceMappingConfiguration configuration){
		if(MAP.containsKey(aClass))
			System.out.println("Previous configuration of "+aClass+" will be replaced by "+configuration);
		MAP.put(aClass, configuration);
	}
	
	public static GlobalIdentifierPersistenceMappingConfiguration get(Class<?> aClass,Boolean createIfNull){
		GlobalIdentifierPersistenceMappingConfiguration configuration = MAP.get(aClass);
		if(Boolean.TRUE.equals(createIfNull) && configuration == null){
			configuration = new GlobalIdentifierPersistenceMappingConfiguration();
			register(aClass, configuration);
		}
		return configuration;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
	/**/
	
	public static <T extends Annotation> void setProperty(Class<T> annotationClass,Class<?> aClass,String fieldName,T annotation){
		Property property = get(aClass, Boolean.TRUE).getPropertyByName(fieldName, Boolean.TRUE);
		MethodHelper.getInstance().callSet(property, annotationClass.getSimpleName().toLowerCase(), annotationClass, annotation);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T getProperty(Class<T> annotationClass,Class<?> aClass,String fieldName){
		Property property = get(aClass, Boolean.TRUE).getPropertyByName(fieldName, Boolean.TRUE);
		return (T) FieldHelper.getInstance().read(property, annotationClass.getSimpleName().toLowerCase());
	}
	
	public static void setColumn(Class<?> aClass,String fieldName,Column column){
		get(aClass, Boolean.TRUE).getPropertyByName(fieldName, Boolean.TRUE).setColumn(column);
	}
	
	public static Column getColumn(Class<?> aClass,String fieldName){
		return get(aClass, Boolean.TRUE).getPropertyByName(fieldName, Boolean.TRUE).getColumn();
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(of="name") @NoArgsConstructor
	public static class Property implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String name;
		private Column column;
		private Temporal temporal;
		
		public Property(String name, Column column) {
			super();
			this.name = name;
			this.column = column;
		}
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
		}

	}
	
	
}