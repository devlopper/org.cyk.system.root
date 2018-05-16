package org.cyk.system.root.model.globalidentification;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A join between an identifiable and a global identifier
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @MappedSuperclass  @NoArgsConstructor @Accessors(chain=true)
public class AbstractJoinGlobalIdentifier extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	private static final Map<Class<? extends AbstractJoinGlobalIdentifier> , Collection<Class<? extends AbstractIdentifiable>>> USER_DEFINED_JOINABLE_CLASSES
		= new HashMap<>();
	private static final Map<Class<? extends AbstractJoinGlobalIdentifier> , String> MASTER_FIELD_NAME_MAP = new HashMap<>();
	
	@ManyToOne @JoinColumn(name=COLUMN_IDENTIFIABLE_GLOBAL_IDENTIFIER) @NotNull protected GlobalIdentifier identifiableGlobalIdentifier;
	//protected Boolean onDeleteCascadeToJoin;
	
	public AbstractJoinGlobalIdentifier(AbstractIdentifiable identifiable){
		identifiableGlobalIdentifier = identifiable.getGlobalIdentifier();
	}
	
	public String getMasterFieldName(){
		return getMasterFieldName(getClass());
	}
	
	public Field getMasterField(){
		return FieldHelper.getInstance().get(getClass(), getMasterFieldName());
	}
	
	public AbstractJoinGlobalIdentifier setMaster(AbstractIdentifiable identifiable){
		FieldHelper.getInstance().set(this, identifiable, getMasterField());
		return this;
	}
	
	public AbstractJoinGlobalIdentifier setMasterFromCode(String code){
		Field masterField = getMasterField();
		FieldHelper.getInstance().set(this, getFromCode(FieldHelper.getInstance().getType(getClass(), masterField), code), masterField);
		return this;
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> AbstractJoinGlobalIdentifier setIdentifiableGlobalIdentifierFromCode(Class<IDENTIFIABLE> aClass,String code){
		AbstractIdentifiable identifiable = getFromCode(aClass, code);
		if(identifiable!=null)
			identifiableGlobalIdentifier = identifiable.getGlobalIdentifier();
		return this;
	}
	
	/**/
	
	public static void setMasterFieldName(Class<? extends AbstractJoinGlobalIdentifier> aClass,String fieldName){
		MASTER_FIELD_NAME_MAP.put(aClass, fieldName);
	}
	
	public static String getMasterFieldName(Class<? extends AbstractJoinGlobalIdentifier> aClass){
		String fieldName = MASTER_FIELD_NAME_MAP.get(aClass);
		if(StringHelper.getInstance().isBlank(fieldName)){
			fieldName = StringHelper.getInstance().normalizeToVariableName(StringUtils.substringBefore(aClass.getSimpleName(),"IdentifiableGlobalIdentifier"));
		}
		return fieldName;
	}
	
	@Getter @Setter
	public static abstract class AbstractSearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected Collection<GlobalIdentifier> globalIdentifiers = new ArrayList<>();
		
		public AbstractSearchCriteria addGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers){
			this.globalIdentifiers.addAll(globalIdentifiers);
			return this;
		}
		
		public AbstractSearchCriteria addGlobalIdentifier(GlobalIdentifier globalIdentifier){
			return addGlobalIdentifiers(Arrays.asList(globalIdentifier));
		}
		
		public AbstractSearchCriteria addIdentifiablesGlobalIdentifiers(Collection<? extends AbstractIdentifiable> identifiables){
			for(AbstractIdentifiable identifiable : identifiables)
				addIdentifiableGlobalIdentifier(identifiable);
			return this;
		}
		
		public AbstractSearchCriteria addIdentifiableGlobalIdentifier(AbstractIdentifiable identifiable){
			return addGlobalIdentifiers(Arrays.asList(identifiable.getGlobalIdentifier()));
		}
		
		/**/
		
		@Override
		public void set(String value) {
			
		}
		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			
		}
		
		@Override
		public String toString() {
			return "Identifiables : "+globalIdentifiers.toString();
		}
		
	}
	
	/**/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void define(Class<? extends AbstractJoinGlobalIdentifier> moduleClass,Class<? extends AbstractIdentifiable> aClass){
		Collection<Class<? extends AbstractIdentifiable>> collection = USER_DEFINED_JOINABLE_CLASSES.get(moduleClass);
		if(collection==null)
			USER_DEFINED_JOINABLE_CLASSES.put(moduleClass,collection = new HashSet());
		collection.add(aClass);
	}
	
	protected static Boolean isUserDefinedClass(Class<?> moduleClass,Class<?> aClass){
		Collection<Class<? extends AbstractIdentifiable>> collection = USER_DEFINED_JOINABLE_CLASSES.get(moduleClass);
		return collection!=null && collection.contains(aClass);
	}
	protected static Boolean isUserDefinedObject(Class<?> moduleClass,Object object){
		return object!=null && Boolean.TRUE.equals(isUserDefinedClass(moduleClass,object.getClass()));
	}
	
	/**/
	
	@Getter @Setter
	public static class Filter<T extends AbstractJoinGlobalIdentifier> extends AbstractIdentifiable.Filter<T> implements Serializable{
		private static final long serialVersionUID = 1L;
    	/*
		protected Collection<GlobalIdentifier> globalIdentifiers;
		
		@Override
		public org.cyk.utility.common.helper.FilterHelper.Filter<T> addMaster(Object master) {
			super.addMaster(master);
			addMasterGlobalIdentifierByIdentifiable((AbstractIdentifiable) master);
			return this;
		}
		
		public Filter<T> addMasterGlobalIdentifiers(Collection<GlobalIdentifier> masterGlobalIdentifiers){
			if(CollectionHelper.getInstance().isNotEmpty(masterGlobalIdentifiers)){
				if(this.globalIdentifiers==null)
					this.globalIdentifiers = new ArrayList<>();
				for(GlobalIdentifier index : masterGlobalIdentifiers)
					if(index!=null)
						this.globalIdentifiers.add(index);	
			}
			return this;
		}
		
		public Filter<T> addMasterGlobalIdentifier(GlobalIdentifier masterGlobalIdentifier){
			if(masterGlobalIdentifier!=null)
				addMasterGlobalIdentifiers(Arrays.asList(masterGlobalIdentifier));
			return this;
		}
		
		public Filter<T> addMasterGlobalIdentifiersByIdentifiables(Collection<Object> identifiables){
			return addMasterGlobalIdentifiers(MethodHelper.getInstance().callGet(identifiables, GlobalIdentifier.class, AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER));
		}
		
		public Filter<T> addMasterGlobalIdentifierByIdentifiable(AbstractIdentifiable identifiable){
			if(identifiable != null){
				Collection<Object> collection = new ArrayList<>();
				collection.add(identifiable);
				return addMasterGlobalIdentifiersByIdentifiables(collection);
			}
			return this;
		}
		*/

    }
	
	/**/
	
	public static final String FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER = "identifiableGlobalIdentifier";
	
	public static final String COLUMN_IDENTIFIABLE_GLOBAL_IDENTIFIER = FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER;
}