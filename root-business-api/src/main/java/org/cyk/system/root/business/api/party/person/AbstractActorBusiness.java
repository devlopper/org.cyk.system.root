package org.cyk.system.root.business.api.party.person;

import java.io.Serializable;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness.CompletePersonInstanciationOfOneFromValuesArguments;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;

import lombok.Getter;
import lombok.Setter;

public interface AbstractActorBusiness<ACTOR extends AbstractActor> extends TypedBusiness<ACTOR> {

	ACTOR findByPerson(Person person);
	
	ACTOR findByRegistrationCode(String registrationCode);
	
	//void completeInstanciationOfOneFromValues(ACTOR actor,CompleteActorInstanciationOfOneFromValuesArguments<ACTOR> arguments);
	//void completeInstanciationOfManyFromValues(List<ACTOR> actors,CompleteActorInstanciationOfManyFromValuesArguments<ACTOR> arguments);
	//List<ACTOR> completeInstanciationOfManyFromValues(CompleteActorInstanciationOfManyFromValuesArguments<ACTOR> arguments);
	
	/**/
	
	@Getter @Setter
	public static class CompleteActorInstanciationOfOneFromValuesArguments<ACTOR extends AbstractActor> extends AbstractCompleteInstanciationOfOneFromValuesArguments<ACTOR> implements Serializable{

		private static final long serialVersionUID = 6568108456054174796L;
		
		private CompletePersonInstanciationOfOneFromValuesArguments personInstanciationOfOneFromValuesArguments = new CompletePersonInstanciationOfOneFromValuesArguments();
		protected Integer registrationCodeIndex,registrationDateIndex;
		
		public void setValues(String[] values){
			this.values = values;
			personInstanciationOfOneFromValuesArguments.setValues(values);
		}
	}
	
	@Getter @Setter
	public static class CompleteActorInstanciationOfManyFromValuesArguments<ACTOR extends AbstractActor> extends AbstractCompleteInstanciationOfManyFromValuesArguments<ACTOR> implements Serializable{

		private static final long serialVersionUID = 6568108456054174796L;
		
		private CompleteActorInstanciationOfOneFromValuesArguments<ACTOR> instanciationOfOneFromValuesArguments = new CompleteActorInstanciationOfOneFromValuesArguments<>();
		
	}
}
