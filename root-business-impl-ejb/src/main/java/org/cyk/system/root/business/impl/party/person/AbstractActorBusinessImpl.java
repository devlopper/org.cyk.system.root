package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.party.person.AbstractActorBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.generator.ValueGenerator;
import org.cyk.system.root.model.party.AbstractActorSearchCriteria;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.person.AbstractActorDao;

public abstract class AbstractActorBusinessImpl<ACTOR extends AbstractActor,DAO extends AbstractActorDao<ACTOR,SEARCH_CRITERIA>,SEARCH_CRITERIA extends AbstractActorSearchCriteria<ACTOR>> extends AbstractTypedBusinessService<ACTOR, DAO> implements AbstractActorBusiness<ACTOR,SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractActorBusinessImpl(DAO dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ACTOR instanciateOne(AbstractActor actor) {
		ACTOR myActor = instanciateOne();
		myActor.setPerson(actor.getPerson());
		myActor.getRegistration().setCode(actor.getRegistration().getCode());
		return myActor;
	}

	@Override
	public ACTOR create(ACTOR anActor) {
		if(anActor.getPerson().getIdentifier()==null)
			RootBusinessLayer.getInstance().getPersonBusiness().create(anActor.getPerson());
		anActor.getRegistration().setDate(universalTimeCoordinated()); 
		if(StringUtils.isBlank(anActor.getRegistration().getCode()))
			anActor.getRegistration().setCode(generateStringValue(ValueGenerator.ACTOR_REGISTRATION_CODE_IDENTIFIER, anActor));
		return super.create(anActor);
	}
	
	@Override
	public ACTOR update(ACTOR anActor) {
		RootBusinessLayer.getInstance().getPersonBusiness().update(anActor.getPerson());
		return super.update(anActor);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public ACTOR findByPerson(Person person) {
		return dao.readByPerson(person);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public ACTOR findByRegistrationCode(String registrationCode) {
		return dao.readByRegistrationCode(registrationCode);
	}
	
	protected void __load__(ACTOR actor){
		RootBusinessLayer.getInstance().getPersonBusiness().load(actor.getPerson());
	}

	@Override
	public void completeInstanciationOfOne(ACTOR actor) {
		super.completeInstanciationOfOne(actor);
		RootBusinessLayer.getInstance().getPersonBusiness().completeInstanciationOfOne(actor.getPerson());
	}

	@Override
	public void completeInstanciationOfOneFromValues(ACTOR actor,AbstractCompleteInstanciationOfOneFromValuesArguments<ACTOR> completeInstanciationOfManyFromValuesArguments) {
		CompleteActorInstanciationOfOneFromValuesArguments<ACTOR> arguments = (CompleteActorInstanciationOfOneFromValuesArguments<ACTOR>) completeInstanciationOfManyFromValuesArguments;
		completeInstanciationOfOneFromValuesBeforeProcessing(actor, arguments.getValues(),arguments.getListener());
		
		if(actor.getPerson()==null)
			actor.setPerson(new Person());
		RootBusinessLayer.getInstance().getPersonBusiness().completeInstanciationOfOneFromValues(actor.getPerson(), arguments.getPersonInstanciationOfOneFromValuesArguments());
		
		if(arguments.getRegistrationCodeIndex()!=null)
			actor.getRegistration().setCode(arguments.getValues()[arguments.getRegistrationCodeIndex()]);
		
		if(arguments.getRegistrationDateIndex()!=null)
			actor.getRegistration().setDate(timeBusiness.parse(arguments.getValues()[arguments.getRegistrationDateIndex()]));
		
		completeInstanciationOfOne(actor);
		
		completeInstanciationOfOneFromValuesAfterProcessing(actor, arguments.getValues(),arguments.getListener());
	} 

	@Override
	public void completeInstanciationOfManyFromValues(List<ACTOR> actors,AbstractCompleteInstanciationOfManyFromValuesArguments<ACTOR> completeInstanciationOfManyFromValuesArguments) {
		CompleteActorInstanciationOfManyFromValuesArguments<ACTOR> arguments = (CompleteActorInstanciationOfManyFromValuesArguments<ACTOR>) completeInstanciationOfManyFromValuesArguments;
		completeInstanciationOfManyFromValuesBeforeProcessing(actors,arguments.getValues(),arguments.getListener());
		for(int index = 0; index < arguments.getValues().size(); index++ ){
			arguments.getInstanciationOfOneFromValuesArguments().setValues(arguments.getValues().get(index));
			completeInstanciationOfOneFromValues(actors.get(index), arguments.getInstanciationOfOneFromValuesArguments());
		}
		completeInstanciationOfManyFromValuesAfterProcessing(actors,arguments.getValues(),arguments.getListener());
	}
/*
	@Override
	public List<ACTOR> completeInstanciationOfManyFromValues(AbstractCompleteInstanciationOfManyFromValuesArguments<ACTOR> arguments) {
		List<ACTOR> actors = new ArrayList<>();
		for(int index = 0; index < arguments.getValues().size(); index++ ){
			ACTOR actor = newInstance(getClazz());
			actors.add(actor);
		}
		completeInstanciationOfManyFromValues(actors,arguments);
		return actors;
	}*/

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<ACTOR> findByCriteria(SEARCH_CRITERIA criteria) {
		if(StringUtils.isBlank(criteria.getPerson().getName().getValue())){
    		return findAll(criteria.getReadConfig());
    	}
    	return dao.readByCriteria(criteria);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByCriteria(SEARCH_CRITERIA criteria) {
		if(StringUtils.isBlank(criteria.getPerson().getName().getValue()))
    		return countAll();
    	return dao.countByCriteria(criteria);
	}

	
	
	
}
