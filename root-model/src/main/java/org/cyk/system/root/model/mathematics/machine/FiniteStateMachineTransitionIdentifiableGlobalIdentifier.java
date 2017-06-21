package org.cyk.system.root.model.mathematics.machine;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.search.StringSearchCriteria;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {FiniteStateMachineTransitionIdentifiableGlobalIdentifier.FIELD_TRANSITION
		,FiniteStateMachineTransitionIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER})})
public class FiniteStateMachineTransitionIdentifiableGlobalIdentifier extends AbstractJoinGlobalIdentifier implements Serializable {

	private static final long serialVersionUID = 2576023570217657424L;

	@ManyToOne @NotNull private FiniteStateMachineTransition transition;
	
	/**/
	
	public static final String FIELD_TRANSITION = "transition";
	
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
		public void set(String value) {
			
		}

		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			
		}
		
	}

}
