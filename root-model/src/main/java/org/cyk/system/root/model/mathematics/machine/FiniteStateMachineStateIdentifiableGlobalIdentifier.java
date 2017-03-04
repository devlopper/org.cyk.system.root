package org.cyk.system.root.model.mathematics.machine;

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
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A join between a finite state machine state and an identifiable
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @Entity  @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class FiniteStateMachineStateIdentifiableGlobalIdentifier extends AbstractJoinGlobalIdentifier implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	@ManyToOne @NotNull private FiniteStateMachineState finiteStateMachineState;
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;
		
		public SearchCriteria addGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers){
			return (SearchCriteria) super.addGlobalIdentifiers(globalIdentifiers);
		}
		
		public SearchCriteria addGlobalIdentifier(GlobalIdentifier globalIdentifier){
			return (SearchCriteria) super.addGlobalIdentifier(globalIdentifier);
		}

		@Override
		public void set(String value) {}

		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			
		}
		
	}
	
	@Getter @Setter
	public static class IdentifiablesSearchCriteria<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected Collection<FiniteStateMachineStateIdentifiableGlobalIdentifier> finiteStateMachineStateIdentifiableGlobalIdentifiers = new ArrayList<>();
		protected Class<IDENTIFIABLE> identifiableClass;
		protected SearchCriteria finiteStateMachineStateIdentifiableGlobalIdentifier;
		
		public IdentifiablesSearchCriteria(Class<IDENTIFIABLE> identifiableClass,SearchCriteria finiteStateMachineStateIdentifiableGlobalIdentifier){
			this.identifiableClass = identifiableClass;
			this.finiteStateMachineStateIdentifiableGlobalIdentifier = finiteStateMachineStateIdentifiableGlobalIdentifier;
		}
		
		public IdentifiablesSearchCriteria<IDENTIFIABLE> addFiniteStateMachineStateIdentifiableGlobalIdentifiers(Collection<FiniteStateMachineStateIdentifiableGlobalIdentifier> finiteStateMachineStateIdentifiableGlobalIdentifiers){
			this.finiteStateMachineStateIdentifiableGlobalIdentifiers.addAll(finiteStateMachineStateIdentifiableGlobalIdentifiers);
			return this;
		}
		
		public IdentifiablesSearchCriteria<IDENTIFIABLE> addFiniteStateMachineStateIdentifiableGlobalIdentifier(FiniteStateMachineStateIdentifiableGlobalIdentifier finiteStateMachineStateIdentifiableGlobalIdentifier){
			return addFiniteStateMachineStateIdentifiableGlobalIdentifiers(Arrays.asList(finiteStateMachineStateIdentifiableGlobalIdentifier));
		}
		
		@Override
		public void set(String value) {
			finiteStateMachineStateIdentifiableGlobalIdentifier.set(value);
		}

		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			
		}
		
	}
	
	public static void define(Class<? extends AbstractIdentifiable> aClass){
		define(FiniteStateMachineStateIdentifiableGlobalIdentifier.class, aClass);
	}
	public static Boolean isUserDefinedClass(Class<?> aClass){
		return isUserDefinedClass(FiniteStateMachineStateIdentifiableGlobalIdentifier.class,aClass);
	}
	public static Boolean isUserDefinedObject(Object object){
		return isUserDefinedObject(FiniteStateMachineStateIdentifiableGlobalIdentifier.class,object);
	}
	
}