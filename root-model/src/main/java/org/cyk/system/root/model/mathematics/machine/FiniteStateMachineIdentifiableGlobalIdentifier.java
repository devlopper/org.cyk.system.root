package org.cyk.system.root.model.mathematics.machine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
 * A join between a finite state machine and an identifiable
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @Entity  @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {FiniteStateMachineIdentifiableGlobalIdentifier.FIELD_FINITE_STATE_MACHINE
		,FiniteStateMachineIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER})})
public class FiniteStateMachineIdentifiableGlobalIdentifier extends AbstractJoinGlobalIdentifier implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	@ManyToOne @JoinColumn(name=COLUMN_FINITE_STATE_MACHINE) @NotNull private FiniteStateMachine finiteStateMachine;
	
	/**/
	
	public static final String FIELD_FINITE_STATE_MACHINE = "finiteStateMachine";
	
	public static final String COLUMN_FINITE_STATE_MACHINE = FIELD_FINITE_STATE_MACHINE;
	
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

		protected Collection<FiniteStateMachineIdentifiableGlobalIdentifier> finiteStateMachineIdentifiableGlobalIdentifiers = new ArrayList<>();
		protected Class<IDENTIFIABLE> identifiableClass;
		protected SearchCriteria finiteStateMachineIdentifiableGlobalIdentifier;
		
		public IdentifiablesSearchCriteria(Class<IDENTIFIABLE> identifiableClass,SearchCriteria finiteStateMachineIdentifiableGlobalIdentifier){
			this.identifiableClass = identifiableClass;
			this.finiteStateMachineIdentifiableGlobalIdentifier = finiteStateMachineIdentifiableGlobalIdentifier;
		}
		
		public IdentifiablesSearchCriteria<IDENTIFIABLE> addFiniteStateMachineIdentifiableGlobalIdentifiers(Collection<FiniteStateMachineIdentifiableGlobalIdentifier> finiteStateMachineIdentifiableGlobalIdentifiers){
			this.finiteStateMachineIdentifiableGlobalIdentifiers.addAll(finiteStateMachineIdentifiableGlobalIdentifiers);
			return this;
		}
		
		public IdentifiablesSearchCriteria<IDENTIFIABLE> addFiniteStateMachineIdentifiableGlobalIdentifier(FiniteStateMachineIdentifiableGlobalIdentifier finiteStateMachineIdentifiableGlobalIdentifier){
			return addFiniteStateMachineIdentifiableGlobalIdentifiers(Arrays.asList(finiteStateMachineIdentifiableGlobalIdentifier));
		}
		
		@Override
		public void set(String value) {
			finiteStateMachineIdentifiableGlobalIdentifier.set(value);
		}

		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			
		}
		
	}
	
	public static void define(Class<? extends AbstractIdentifiable> aClass){
		define(FiniteStateMachineIdentifiableGlobalIdentifier.class, aClass);
	}
	public static Boolean isUserDefinedClass(Class<?> aClass){
		return isUserDefinedClass(FiniteStateMachineIdentifiableGlobalIdentifier.class,aClass);
	}
	public static Boolean isUserDefinedObject(Object object){
		return isUserDefinedObject(FiniteStateMachineIdentifiableGlobalIdentifier.class,object);
	}
	
}