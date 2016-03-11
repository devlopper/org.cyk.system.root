package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.party.person.AbstractActorBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.generator.ValueGenerator;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.person.AbstractActorDao;

public abstract class AbstractActorBusinessImpl<ACTOR extends AbstractActor,DAO extends AbstractActorDao<ACTOR>> extends AbstractTypedBusinessService<ACTOR, DAO> implements AbstractActorBusiness<ACTOR>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractActorBusinessImpl(DAO dao) {
		super(dao); 
	}
	
	@Override
	public ACTOR create(ACTOR anActor) {
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
	
	@Override
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

	
	
	
}
