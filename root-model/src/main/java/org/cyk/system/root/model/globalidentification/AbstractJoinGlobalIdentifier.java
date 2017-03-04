package org.cyk.system.root.model.globalidentification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A join between an identifiable and a global identifier
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @MappedSuperclass  @NoArgsConstructor 
public class AbstractJoinGlobalIdentifier extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	private static final Map<Class<? extends AbstractJoinGlobalIdentifier> , Collection<Class<? extends AbstractIdentifiable>>> USER_DEFINED_JOINABLE_CLASSES
		= new HashMap<>();
	
	@ManyToOne @NotNull
	protected GlobalIdentifier identifiableGlobalIdentifier;
	
	public AbstractJoinGlobalIdentifier(AbstractIdentifiable identifiable){
		identifiableGlobalIdentifier = identifiable.getGlobalIdentifier();
	}
	
	/**/
	
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
	
	public static final String FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER = "identifiableGlobalIdentifier";
}