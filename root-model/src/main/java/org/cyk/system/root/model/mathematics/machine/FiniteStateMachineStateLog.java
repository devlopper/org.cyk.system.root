package org.cyk.system.root.model.mathematics.machine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractLog;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @Entity @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class FiniteStateMachineStateLog extends AbstractLog implements Serializable {

	private static final long serialVersionUID = 2576023570217657424L;

	@ManyToOne @NotNull private FiniteStateMachineState state;
	
	@ManyToOne @NotNull private GlobalIdentifier identifiableGlobalIdentifier;
	
	/**/
	
	public static final String FIELD_STATE = "state";

	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected Collection<FiniteStateMachineState> finiteStateMachineStates = new ArrayList<>();
		protected String timeDivisionTypeCode;
		
		public SearchCriteria(){
			
		}
		
		public SearchCriteria addFiniteStateMachineStates(Collection<FiniteStateMachineState> finiteStateMachineStates){
			this.finiteStateMachineStates.addAll(finiteStateMachineStates);
			return this;
		}
		public SearchCriteria addFiniteStateMachineState(FiniteStateMachineState finiteStateMachineState){
			return addFiniteStateMachineStates(Arrays.asList(finiteStateMachineState));
		}
	}
	
	@Getter @Setter
	public static class IdentifiablesSearchCriteria<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected Collection<FiniteStateMachineStateLog> finiteStateMachineStateLogs = new ArrayList<>();
		protected Class<IDENTIFIABLE> identifiableClass;
		protected SearchCriteria finiteStateMachineStateLog;
		
		public IdentifiablesSearchCriteria(Class<IDENTIFIABLE> identifiableClass,SearchCriteria finiteStateMachineStateLog){
			this.identifiableClass = identifiableClass;
			this.finiteStateMachineStateLog = finiteStateMachineStateLog;
		}
		
		public IdentifiablesSearchCriteria<IDENTIFIABLE> addFiniteStateMachineStateLogs(Collection<FiniteStateMachineStateLog> finiteStateMachineStateLogs){
			this.finiteStateMachineStateLogs.addAll(finiteStateMachineStateLogs);
			return this;
		}
		public IdentifiablesSearchCriteria<IDENTIFIABLE> addFiniteStateMachineStateLog(FiniteStateMachineStateLog finiteStateMachineStateLog){
			return addFiniteStateMachineStateLogs(Arrays.asList(finiteStateMachineStateLog));
		}
		
	}
}
