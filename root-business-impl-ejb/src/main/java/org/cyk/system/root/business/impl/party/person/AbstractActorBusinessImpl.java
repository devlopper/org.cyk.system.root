package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.party.person.AbstractActorBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.BusinessServiceProvider;
import org.cyk.system.root.business.impl.BusinessServiceProvider.Service;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.person.AbstractActorDao;
import org.cyk.utility.common.computation.DataReadConfiguration;

public abstract class AbstractActorBusinessImpl<ACTOR extends AbstractActor,DAO extends AbstractActorDao<ACTOR,SEARCH_CRITERIA>,SEARCH_CRITERIA extends AbstractActor.AbstractSearchCriteria<ACTOR>> extends AbstractTypedBusinessService<ACTOR, DAO> implements AbstractActorBusiness<ACTOR,SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractActorBusinessImpl(DAO dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ACTOR instanciateOne(AbstractActor actor) {
		ACTOR myActor = instanciateOne();
		myActor.setPerson(actor.getPerson());
		return myActor;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ACTOR instanciateOne() {
		ACTOR actor = super.instanciateOne();
		actor.setPerson(inject(PersonBusiness.class).instanciateOne());
		return actor;
	}
	
	@Override
	public ACTOR instanciateOne(String code, String[] names) {
		ACTOR actor = super.instanciateOne();
		actor.setCode(code);
		actor.setName(commonUtils.getValueAt(names, 0));
		actor.getPerson().setLastnames(commonUtils.getValueAt(names, 1));
		return actor;
	}
	

	@Override
	public Collection<ACTOR> instanciateMany(String[] codes) {
		Collection<ACTOR> actors = new ArrayList<>();
		for(String code : codes)
			actors.add(instanciateOne(code, null));
		return actors;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public ACTOR instanciateOneRandomly() {
		ACTOR actor = super.instanciateOne();
		actor.setPerson(inject(PersonBusiness.class).instanciateOneRandomly());
		actor.setImage(actor.getPerson().getImage());
		actor.setCode(actor.getPerson().getCode());
		actor.setName(actor.getPerson().getName());
		return actor;
	}

	@Override
	public ACTOR create(ACTOR anActor) {
		if(anActor.getPerson().getIdentifier()==null){
			if(StringUtils.isBlank(anActor.getPerson().getCode()))
				anActor.getPerson().setCode(anActor.getCode());
			if(StringUtils.isBlank(anActor.getPerson().getName()))
				anActor.getPerson().setName(anActor.getName());
			if(anActor.getPerson().getImage()==null)
				anActor.getPerson().setImage(anActor.getImage());
			anActor.getPerson().getGlobalIdentifierCreateIfNull().setCreatedBy(anActor.getGlobalIdentifierCreateIfNull().getCreatedBy());
			inject(PersonBusiness.class).create(anActor.getPerson());
		}
		return super.create(anActor);
	}
	
	@Override
	public ACTOR update(ACTOR anActor) {
		anActor.getPerson().setName(anActor.getName());//TODO i think it is better to align those names because there are same concept . is it ?
		inject(PersonBusiness.class).update(anActor.getPerson());
		return super.update(anActor);
	}
	
	@Override
	public ACTOR delete(ACTOR actor) {
		inject(PersonBusiness.class).delete(actor.getPerson());
		actor.setPerson(null);
		return super.delete(actor);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public ACTOR findByPerson(Person person) {
		return dao.readByPerson(person);
	}
	
	protected void __load__(ACTOR actor){
		inject(PersonBusiness.class).load(actor.getPerson());
	}

	@Override
	public void completeInstanciationOfOne(ACTOR actor) {
		super.completeInstanciationOfOne(actor);
		inject(PersonBusiness.class).completeInstanciationOfOne(actor.getPerson());
	}

	@Override
	public void completeInstanciationOfOneFromValues(ACTOR actor,AbstractCompleteInstanciationOfOneFromValuesArguments<ACTOR> completeInstanciationOfManyFromValuesArguments) {
		CompleteActorInstanciationOfOneFromValuesArguments<ACTOR> arguments = (CompleteActorInstanciationOfOneFromValuesArguments<ACTOR>) completeInstanciationOfManyFromValuesArguments;
		completeInstanciationOfOneFromValuesBeforeProcessing(actor, arguments.getValues(),arguments.getListener());
		
		if(actor.getPerson()==null)
			actor.setPerson(new Person());
		inject(PersonBusiness.class).completeInstanciationOfOneFromValues(actor.getPerson(), arguments.getPersonInstanciationOfOneFromValuesArguments());
		
		if(arguments.getRegistrationCodeIndex()!=null)
			actor.getGlobalIdentifierCreateIfNull().setCode(arguments.getValues()[arguments.getRegistrationCodeIndex()]);
		
		if(arguments.getRegistrationDateIndex()!=null)
			actor.setBirthDate(timeBusiness.parse(arguments.getValues()[arguments.getRegistrationDateIndex()]));
		
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
		prepareFindByCriteria(criteria);
    	return dao.readByCriteria(criteria);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByCriteria(SEARCH_CRITERIA criteria) {
		if(StringUtils.isBlank(criteria.getPerson().getName().getValue()))
    		return countAll();
		prepareFindByCriteria(criteria);
    	return dao.countByCriteria(criteria);
	}

	/**/
	
	public static abstract class BusinessServiceProviderIdentifiable<ACTOR extends AbstractActor,SEARCH_CRITERIA extends AbstractActor.AbstractSearchCriteria<ACTOR>> extends BusinessServiceProvider.Identifiable.Adapter.Default<ACTOR> implements Serializable {

		private static final long serialVersionUID = -3282900979154003071L;

		public BusinessServiceProviderIdentifiable(Class<ACTOR> clazz) {
			super(clazz);
		}

		@SuppressWarnings("unchecked")
		public Collection<ACTOR> find(DataReadConfiguration configuration) {
			SEARCH_CRITERIA criteria = createSearchCriteria(Service.FIND,configuration);
			criteria.getReadConfig().set(configuration);
			return ((AbstractActorBusiness<ACTOR, SEARCH_CRITERIA>)getBusiness()).findByCriteria(criteria);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Long count(DataReadConfiguration configuration) {
			SEARCH_CRITERIA criteria = createSearchCriteria(Service.COUNT,configuration);
			return ((AbstractActorBusiness<ACTOR, SEARCH_CRITERIA>)getBusiness()).countByCriteria(criteria);
		}
		
		protected abstract SEARCH_CRITERIA createSearchCriteria(Service service,DataReadConfiguration configuration);
		
		
	}
	
/**/
	
	public static interface Listener<ACTOR extends AbstractActor> extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<ACTOR>{
		
		/**/

		public static class Adapter<ACTOR extends AbstractActor> extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.Adapter<ACTOR> implements Listener<ACTOR>, Serializable {
			private static final long serialVersionUID = -1625238619828187690L;
			
		}
		
	}
	
	
}
