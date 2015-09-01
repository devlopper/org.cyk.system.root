package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.AbstractActorBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.generator.ValueGenerator;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.person.AbstractActorDao;

public abstract class AbstractActorBusinessImpl<ACTOR extends AbstractActor,DAO extends AbstractActorDao<ACTOR>> extends AbstractTypedBusinessService<ACTOR, DAO> implements AbstractActorBusiness<ACTOR>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject protected PersonBusiness personBusiness;
	
	public AbstractActorBusinessImpl(DAO dao) {
		super(dao); 
	}
	
	@Override
	public ACTOR create(ACTOR anActor) {
		personBusiness.create(anActor.getPerson());
		anActor.getRegistration().setDate(universalTimeCoordinated()); 
		anActor.getRegistration().setCode(generateStringValue(ValueGenerator.ACTOR_REGISTRATION_CODE_IDENTIFIER, anActor));
		return super.create(anActor);
	}
	
	@Override
	public ACTOR update(ACTOR anActor) {
		personBusiness.update(anActor.getPerson());
		return super.update(anActor);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public ACTOR findByPerson(Person person) {
		return dao.readByPerson(person);
	}
	
	@Override
	public ACTOR findByRegistrationCode(String registrationCode) {
		return dao.readByRegistrationCode(registrationCode);
	}
	
	protected void __load__(ACTOR actor){
		personBusiness.load(actor.getPerson());
	}

	
	
	
}
