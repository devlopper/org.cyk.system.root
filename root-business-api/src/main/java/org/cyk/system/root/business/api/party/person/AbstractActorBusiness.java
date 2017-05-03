package org.cyk.system.root.business.api.party.person;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness.CompletePersonInstanciationOfOneFromValuesArguments;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;

public interface AbstractActorBusiness<ACTOR extends AbstractActor,SEARCH_CRITERIA extends AbstractActor.AbstractSearchCriteria<ACTOR>> extends TypedBusiness<ACTOR> {

	ACTOR findByPerson(Person person);
	
	ACTOR instanciateOne(AbstractActor actor);
	
	ACTOR instanciateOne(String code,String[] names);
	
	Collection<ACTOR> instanciateMany(String[] codes);
	/*
	Collection<ACTOR> findByCriteria(SEARCH_CRITERIA criteria);
	
	Long countByCriteria(SEARCH_CRITERIA criteria);
	*/
	//void completeInstanciationOfOneFromValues(ACTOR actor,CompleteActorInstanciationOfOneFromValuesArguments<ACTOR> arguments);
	//void completeInstanciationOfManyFromValues(List<ACTOR> actors,CompleteActorInstanciationOfManyFromValuesArguments<ACTOR> arguments);
	//List<ACTOR> completeInstanciationOfManyFromValues(CompleteActorInstanciationOfManyFromValuesArguments<ACTOR> arguments);
	
	/**/
	
	@Getter @Setter
	public static class CompleteActorInstanciationOfOneFromValuesArguments<ACTOR extends AbstractActor> extends AbstractCompleteInstanciationOfOneFromValuesArguments<ACTOR> implements Serializable{

		private static final long serialVersionUID = 6568108456054174796L;
		
		private CompletePersonInstanciationOfOneFromValuesArguments personInstanciationOfOneFromValuesArguments = new CompletePersonInstanciationOfOneFromValuesArguments();
		@Deprecated protected Integer registrationCodeIndex,registrationDateIndex;
		protected Integer personCodeColumnIndex;
		
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
