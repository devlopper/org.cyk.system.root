package org.cyk.system.root.model.time;

import java.io.Serializable;
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
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A join between a period and an identifiable
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @Entity  @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {IdentifiablePeriodIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_PERIOD
		,IdentifiablePeriodIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER})})
public class IdentifiablePeriodIdentifiableGlobalIdentifier extends AbstractJoinGlobalIdentifier implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	@ManyToOne @JoinColumn(name=COLUMN_IDENTIFIABLE_PERIOD) @NotNull private IdentifiablePeriod identifiablePeriod;
	
	/**/
	
	public IdentifiablePeriodIdentifiableGlobalIdentifier(IdentifiablePeriod identifiablePeriod,AbstractIdentifiable identifiable){
		this.identifiablePeriod = identifiablePeriod;
		identifiableGlobalIdentifier = identifiable.getGlobalIdentifier();
	}
	
	public static final String FIELD_IDENTIFIABLE_PERIOD = "identifiablePeriod";
	
	public static final String COLUMN_IDENTIFIABLE_PERIOD = FIELD_IDENTIFIABLE_PERIOD;
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;
		
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
	}
	
	public static void define(Class<? extends AbstractIdentifiable> aClass){
		define(IdentifiablePeriodIdentifiableGlobalIdentifier.class, aClass);
	}
	public static Boolean isUserDefinedClass(Class<?> aClass){
		return isUserDefinedClass(IdentifiablePeriodIdentifiableGlobalIdentifier.class,aClass);
	}
	public static Boolean isUserDefinedObject(Object object){
		return isUserDefinedObject(IdentifiablePeriodIdentifiableGlobalIdentifier.class,object);
	}
	
}