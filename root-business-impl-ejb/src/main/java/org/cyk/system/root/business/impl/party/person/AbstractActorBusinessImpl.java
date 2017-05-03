package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.party.person.AbstractActorBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.party.person.AbstractActorDao;

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

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ACTOR instanciateOneRandomly() {
		ACTOR actor = super.instanciateOne();
		actor.setPerson(inject(PersonBusiness.class).instanciateOneRandomly());
		actor.setImage(actor.getPerson().getImage());
		actor.setCode(actor.getPerson().getCode());
		actor.setName(actor.getPerson().getName());
		return actor;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ACTOR instanciateOneRandomly(String code) {
		ACTOR actor = super.instanciateOneRandomly(code);
		actor.getPerson().setCode(code);
		return actor;
	}

	@Override
	protected void beforeCreate(ACTOR anActor) {
		super.beforeCreate(anActor);
		//move code to super and use a variable to do distinguish
		if(anActor.getPerson().getIdentifier()==null){
			if(StringUtils.isBlank(anActor.getPerson().getCode()))
				anActor.getPerson().setCode(anActor.getCode());
			if(StringUtils.isBlank(anActor.getPerson().getName()))
				anActor.getPerson().setName(anActor.getName());
			if(anActor.getPerson().getImage()==null)
				anActor.getPerson().setImage(anActor.getImage());
			anActor.getPerson().getGlobalIdentifierCreateIfNull().setCreatedBy(anActor.getGlobalIdentifierCreateIfNull().getCreatedBy());
			inject(PersonBusiness.class).create(anActor.getPerson());
		}else{
			if(StringUtils.isEmpty(anActor.getCode()))
				anActor.setCode(anActor.getPerson().getCode());
			if(StringUtils.isEmpty(anActor.getName()))
				anActor.setName(anActor.getPerson().getName());
			if(anActor.getImage() == null)
				anActor.setImage(anActor.getPerson().getImage());
		}
		/*
		if(StringUtils.isBlank(anActor.getCode()))
			anActor.setCode(anActor.getPerson().getCode());
		if(StringUtils.isBlank(anActor.getName()))
			anActor.setName(anActor.getPerson().getName());
		if(anActor.getImage() == null)
			anActor.setImage(anActor.getPerson().getImage());
		*/
	}

	@Override
	protected void beforeUpdate(ACTOR anActor) {
		super.beforeUpdate(anActor);
		anActor.getPerson().setName(anActor.getName());//TODO i think it is better to align those names because there are same concept . is it ?
		inject(PersonBusiness.class).update(anActor.getPerson());
	}
	
	@Override
	protected void beforeDelete(ACTOR actor) {
		super.beforeDelete(actor);
		inject(PersonBusiness.class).delete(actor.getPerson());
		actor.setPerson(null);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ACTOR findByPerson(Person person) {
		return dao.readByPerson(person);
	}
	
	protected void __load__(ACTOR actor){
		inject(PersonBusiness.class).load(actor.getPerson());
	}

	@Override
	public <SEARCH_CRITERIA_2 extends AbstractFieldValueSearchCriteriaSet> Collection<ACTOR> findBySearchCriteria(SEARCH_CRITERIA_2 searchCriteria) {
		if(StringUtils.isBlank(((AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet)searchCriteria).getName().getValue())){
    		return findAll(searchCriteria.getReadConfig());
    	}
    	prepareFindByCriteria(searchCriteria);
    	return dao.readBySearchCriteria(searchCriteria);
	}

	@Override
	public <SEARCH_CRITERIA_2 extends AbstractFieldValueSearchCriteriaSet> Long countBySearchCriteria(SEARCH_CRITERIA_2 searchCriteria) {
		if(StringUtils.isBlank(((AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet)searchCriteria).getName().getValue()))
    		return countAll();
    	prepareFindByCriteria(searchCriteria);
    	return dao.countBySearchCriteria(searchCriteria);
	}

	@Override
	protected AbstractFieldValueSearchCriteriaSet createSearchCriteriaInstance() {
		return new AbstractActor.AbstractSearchCriteria.Default();
	}



	public static interface Listener<ACTOR extends AbstractActor> extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<ACTOR>{
		
		/**/

		public static class Adapter<ACTOR extends AbstractActor> extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.Adapter<ACTOR> implements Listener<ACTOR>, Serializable {
			private static final long serialVersionUID = -1625238619828187690L;
		
			public static class Default<ACTOR extends AbstractActor> extends Listener.Adapter<ACTOR> implements Serializable {

				private static final long serialVersionUID = 2428586349249113529L;
				
				@Override
				public void afterInstanciateOne(UserAccount userAccount,ACTOR actor) {
					super.afterInstanciateOne(userAccount, actor);
					actor.getPerson().setExtendedInformations(new PersonExtendedInformations(actor.getPerson()));
				}
				
					
			}
			
		}
		
	}
	
	
}
