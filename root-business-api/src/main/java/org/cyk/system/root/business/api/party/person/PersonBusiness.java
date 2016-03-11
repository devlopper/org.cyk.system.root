package org.cyk.system.root.business.api.party.person;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.business.api.party.AbstractPartyBusiness;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;

public interface PersonBusiness extends AbstractPartyBusiness<Person,PersonSearchCriteria> {

	String findNames(Person person,FindNamesOptions options);
	String findNames(Person person);

	/**/
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class FindNamesOptions implements Serializable{
		private static final long serialVersionUID = 5198052741650985781L;
		
		private Boolean useTitle = Boolean.FALSE;
		
	}

}
