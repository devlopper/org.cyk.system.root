package org.cyk.system.root.model.information;

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
 * A join between an information state and an identifiable
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @Entity  @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {InformationStateIdentifiableGlobalIdentifier.FIELD_INFORMATION_STATE
		,InformationStateIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER})})
public class InformationStateIdentifiableGlobalIdentifier extends AbstractJoinGlobalIdentifier implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	@ManyToOne @JoinColumn(name=COLUMN_INFORMATION_STATE) @NotNull private InformationState informationState;
	
	/**/
	
	public static final String FIELD_INFORMATION_STATE = "informationState";
	
	public static final String COLUMN_INFORMATION_STATE = FIELD_INFORMATION_STATE;
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;
		
		public SearchCriteria addGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers){
			return (SearchCriteria) super.addGlobalIdentifiers(globalIdentifiers);
		}
		public SearchCriteria addGlobalIdentifier(GlobalIdentifier globalIdentifier){
			return (SearchCriteria) super.addGlobalIdentifier(globalIdentifier);
		}
		
	}
	
	public static void define(Class<? extends AbstractIdentifiable> aClass){
		define(InformationStateIdentifiableGlobalIdentifier.class, aClass);
	}
	public static Boolean isUserDefinedClass(Class<?> aClass){
		return isUserDefinedClass(InformationStateIdentifiableGlobalIdentifier.class,aClass);
	}
	public static Boolean isUserDefinedObject(Object object){
		return isUserDefinedObject(InformationStateIdentifiableGlobalIdentifier.class,object);
	}
	
}