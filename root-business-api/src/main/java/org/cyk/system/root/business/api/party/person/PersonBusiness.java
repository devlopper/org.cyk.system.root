package org.cyk.system.root.business.api.party.person;

import java.io.Serializable;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.business.api.party.AbstractPartyBusiness;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.Constant;

public interface PersonBusiness extends AbstractPartyBusiness<Person> {

	Collection<Person> get(Collection<? extends AbstractActor> actors);
	
	String findNames(Person person,FindNamesArguments arguments);
	String findNames(Person person);

	String findInitials(Person person,FindInitialsArguments arguments);
	String findInitials(Person person);
	
	Person instanciateOne(String code,String[] names);
	/*
	PersonRelationship addRelationship(Person person,String relationshipTypeCode);
	void addRelationships(Person person,Collection<String> relationshipTypeCodes);
	*/
	Collection<Person> findByPersonByRelationshipTypeRole(String personCode,String personRelationshipTypeRoleCode);
	
	//Collection<Person> findByPersonRelationshipPerson2ByPersonRelationshipTypes(Collection<Person> persons,Collection<PersonRelationshipType> personRelationshipTypes);
	
	void setRelatedIdentifiables(Person identifiable,Boolean image,Boolean signature,String...relatedIdentifiableFieldNames);
	
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
	
}
