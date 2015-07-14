package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.party.AbstractPartyBusinessImpl;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;
import org.cyk.system.root.persistence.api.party.PersonDao;

@Stateless
public class PersonBusinessImpl extends AbstractPartyBusinessImpl<Person, PersonDao,PersonSearchCriteria> implements PersonBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	//@Inject private RepeatedEventBusiness repeatedEventBusiness;
	
	@Inject
	public PersonBusinessImpl(PersonDao dao) {
		super(dao); 
	}  
	
	@Override
	public Person create(Person person) {
		super.create(person);
		//person.setBirthDateAnniversary(repeatedEventBusiness.createAnniversary(person.getBirthDate(),person.getNames()));
		return person;
	}
	
	@Override
	public Person update(Person person) {
		Person p = super.update(person);
		//repeatedEventBusiness.updateAnniversary(person.getBirthDateAnniversary(),person.getBirthDate(), person.getName());
		return p;
	}
	
}
 