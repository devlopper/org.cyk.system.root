package org.cyk.system.root.model.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A join between a value collection and an identifiable
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @Entity  @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class ValueCollectionIdentifiableGlobalIdentifier extends AbstractJoinGlobalIdentifier implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	@ManyToOne @NotNull private ValueCollection valueCollection;
	
	/**/
	
	public ValueCollectionIdentifiableGlobalIdentifier(ValueCollection valueCollection,AbstractIdentifiable identifiable){
		super(identifiable);
		this.valueCollection = valueCollection;
	}
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;
		
		private Collection<ValueCollection> valueCollections = new ArrayList<>();
		
		@Override
		public SearchCriteria addGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers){
			return (SearchCriteria) super.addGlobalIdentifiers(globalIdentifiers);
		}
		
		@Override
		public SearchCriteria addGlobalIdentifier(GlobalIdentifier globalIdentifier){
			return (SearchCriteria) super.addGlobalIdentifier(globalIdentifier);
		}
		
		@Override
		public SearchCriteria addIdentifiablesGlobalIdentifiers(Collection<? extends AbstractIdentifiable> identifiables) {
			return (SearchCriteria) super.addIdentifiablesGlobalIdentifiers(identifiables);
		}
		
		@Override
		public SearchCriteria addIdentifiableGlobalIdentifier(AbstractIdentifiable identifiable) {
			return (SearchCriteria) super.addIdentifiableGlobalIdentifier(identifiable);
		}

		public SearchCriteria addValueCollections(Collection<ValueCollection> valueCollections){
			this.valueCollections.addAll(valueCollections);
			return this;
		}
		public SearchCriteria addValueCollection(ValueCollection valueCollection){
			return addValueCollections(Arrays.asList(valueCollection));
		}
		
		
	}
	
	public static void define(Class<? extends AbstractIdentifiable> aClass){
		define(ValueCollectionIdentifiableGlobalIdentifier.class, aClass);
	}
	
	public static Boolean isUserDefinedClass(Class<?> aClass){
		return isUserDefinedClass(ValueCollectionIdentifiableGlobalIdentifier.class,aClass);
	}
	
	public static Boolean isUserDefinedObject(Object object){
		return isUserDefinedObject(ValueCollectionIdentifiableGlobalIdentifier.class,object);
	}
	
}