package org.cyk.system.root.business.api.party.person;

import java.io.Serializable;

import org.cyk.system.root.business.api.party.AbstractPartyBusiness;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Person.SearchCriteria;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.utility.common.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface PersonBusiness extends AbstractPartyBusiness<Person,SearchCriteria> {

	String findNames(Person person,FindNamesArguments arguments);
	String findNames(Person person);

	String findInitials(Person person,FindInitialsArguments arguments);
	String findInitials(Person person);
	
	Person instanciateOne(String code,String[] names);
	
	PersonRelationship addRelationship(Person person,String relationshipTypeCode);
	
	/**/
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class FindNamesArguments implements Serializable{
		private static final long serialVersionUID = 5198052741650985781L;
		public static Boolean FIRST_NAME_IS_FIRST=Boolean.TRUE;
		
		private Boolean useTitle = Boolean.FALSE,firstNameIsFirst=FIRST_NAME_IS_FIRST;
		
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class FindInitialsArguments implements Serializable{
		private static final long serialVersionUID = 5198052741650985781L;
		
		private FindNamesArguments findNamesArguments = new FindNamesArguments();
		private String separator = Constant.EMPTY_STRING;
		
	}
	
	@Getter @Setter
	public static class CompletePersonInstanciationOfOneFromValuesArguments extends AbstractCompleteInstanciationOfOneFromValuesArguments<Person> implements Serializable{

		private static final long serialVersionUID = 6568108456054174796L;
		
		private CompletePartyInstanciationOfOneFromValuesArguments<Person> partyInstanciationOfOneFromValuesArguments = new CompletePartyInstanciationOfOneFromValuesArguments<>();
		protected Integer lastnameIndex,sexCodeIndex,birthLocationOtherDetailsIndex,titleCodeIndex,jobFunctionCodeIndex,jobTitleCodeIndex;
		
		public void setValues(String[] values){
			this.values = values;
			partyInstanciationOfOneFromValuesArguments.setValues(values);
		}
	}
	
	@Getter @Setter
	public static class CompletePersonInstanciationOfManyFromValuesArguments extends AbstractCompleteInstanciationOfManyFromValuesArguments<Person> implements Serializable{

		private static final long serialVersionUID = 6568108456054174796L;
		
		private CompletePersonInstanciationOfOneFromValuesArguments instanciationOfOneFromValuesArguments = new CompletePersonInstanciationOfOneFromValuesArguments();
		
	}

}
