package org.cyk.system.root.business.impl.party.person;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;

public class PersonRelationshipBusinessImpl extends AbstractTypedBusinessService<PersonRelationship,PersonRelationshipDao> implements PersonRelationshipBusiness {
 
	private static final long serialVersionUID = 2801588592108008404L;

	@Inject
    public PersonRelationshipBusinessImpl(PersonRelationshipDao dao) {
        super(dao);
    }
	
	@Override
	protected void beforeCreate(PersonRelationship personRelationship) {
		super.beforeCreate(personRelationship);
		for(Person person : new Person[]{personRelationship.getPerson1(),personRelationship.getPerson2()}){
			Person existing = null;
			if(person.getContactCollection()!=null && person.getContactCollection().getElectronicMails()!=null && !person.getContactCollection().getElectronicMails().isEmpty())
				existing = inject(PersonDao.class).readByEmail(person.getContactCollection().getElectronicMails().iterator().next().getAddress());
			if(existing==null)
				createIfNotIdentified(person);
			else if(personRelationship.getPerson1() == person)
				personRelationship.setPerson1(existing);
			else if(personRelationship.getPerson2() == person)
				personRelationship.setPerson2(existing);
		}
		exceptionUtils().exception(personRelationship.getPerson1().equals(personRelationship.getPerson2()), "person1.cannotbe.person2");
	}
	
	@Override
	public PersonRelationship create(PersonRelationship personRelationship) {
		return super.create(personRelationship);
	}

	@Override
	public Collection<PersonRelationship> findByPerson(Person person) {
		return dao.readByPerson(person);
	}

	@Override
	public Collection<PersonRelationship> findByType(PersonRelationshipType type) {
		return dao.readByType(type);
	}

	@Override
	public Collection<PersonRelationship> findByPersonByType(Person person, PersonRelationshipType type) {
		return dao.readByPersonByType(person, type);
	} 
	
	@Override
	public Collection<PersonRelationship> findByPerson1ByType(Person person, PersonRelationshipType type) {
		return dao.readByPerson1ByType(person, type);
	} 
	
	@Override
	public Collection<PersonRelationship> findByPerson2ByType(Person person, PersonRelationshipType type) {
		return dao.readByPerson2ByType(person, type);
	}
	
	@Override
	public Collection<PersonRelationship> findByPerson2ByTypes(Collection<Person> persons,Collection<PersonRelationshipType> types) {
		return dao.readByPerson2ByTypes(persons, types);
	}

	@Override
	public Collection<PersonRelationship> findByType(Collection<PersonRelationship> personRelationships,PersonRelationshipType type) {
		Collection<PersonRelationship> collection = new ArrayList<>();
		for(PersonRelationship personRelationship : personRelationships)
			if(personRelationship.getType().equals(type))
				collection.add(personRelationship);
		return collection;
	}

	@Override
	public PersonRelationship findOneByType(Collection<PersonRelationship> personRelationships,PersonRelationshipType type) {
		Collection<PersonRelationship> collection = findByType(personRelationships, type);
		exceptionUtils().exception(collection.size()>1, "toomuch.PersonRelationship.found");
		return collection.isEmpty() ? null : collection.iterator().next();
	}

	
	@Override
	public PersonRelationship instanciateOne(String person1Code,String personRelationshipTypeCode, String person2Code) {
		PersonRelationship personRelationship = instanciateOne();
		personRelationship.setPerson1(read(Person.class, person1Code));
		personRelationship.setType(read(PersonRelationshipType.class, personRelationshipTypeCode));
		personRelationship.setPerson2(read(Person.class, person2Code));
		return personRelationship;
	} 
}
