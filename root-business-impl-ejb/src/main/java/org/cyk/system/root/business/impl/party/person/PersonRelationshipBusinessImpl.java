package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

public class PersonRelationshipBusinessImpl extends AbstractTypedBusinessService<PersonRelationship,PersonRelationshipDao> implements PersonRelationshipBusiness {
 
	private static final long serialVersionUID = 2801588592108008404L;

	@Inject
    public PersonRelationshipBusinessImpl(PersonRelationshipDao dao) {
        super(dao);
    }
	
	@Override
	protected Object[] getPropertyValueTokens(PersonRelationship personRelationship,String name) {
		if(ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_CODE,GlobalIdentifier.FIELD_NAME}, name))
			return new Object[]{personRelationship.getExtremity1().getPerson(),personRelationship.getExtremity1().getRole()
				,personRelationship.getExtremity2().getPerson(),personRelationship.getExtremity2().getRole()};
		return super.getPropertyValueTokens(personRelationship, name);
	}
	
	@Override
	protected void beforeCreate(PersonRelationship personRelationship) {
		super.beforeCreate(personRelationship);
		/*
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
		*/
	}

	@Override
	public Collection<PersonRelationship> findByPerson(Person person) {
		return dao.readByPerson(person);
	}
	/*
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
			if(personRelationship.getExtremity1().getRole().getPersonRelationshipType().equals(type))
				collection.add(personRelationship);
		return collection;
	}

	@Override
	public PersonRelationship findOneByType(Collection<PersonRelationship> personRelationships,PersonRelationshipType type) {
		Collection<PersonRelationship> collection = findByType(personRelationships, type);
		exceptionUtils().exception(collection.size()>1, "toomuch.PersonRelationship.found");
		return collection.isEmpty() ? null : collection.iterator().next();
	}*/

	
	@Override
	public PersonRelationship instanciateOne(String person1Code,String person1RoleCode,String person2Code,String person2RoleCode) {
		PersonRelationship personRelationship = instanciateOne();
		personRelationship.getExtremity1().setPerson(read(Person.class, person1Code));
		personRelationship.getExtremity1().setRole(read(PersonRelationshipTypeRole.class, person1RoleCode));
		
		personRelationship.getExtremity2().setPerson(read(Person.class, person2Code));
		personRelationship.getExtremity2().setRole(read(PersonRelationshipTypeRole.class, person2RoleCode));
		return personRelationship;
	}

	@Override
	public Collection<PersonRelationship> findByPersons(Collection<Person> persons) {
		return dao.readByPersons(persons);
	}

	@Override
	public Collection<PersonRelationship> findByRole(PersonRelationshipTypeRole role) {
		return dao.readByRole(role);
	}

	@Override
	public Collection<PersonRelationship> findByRoles(Collection<PersonRelationshipTypeRole> roles) {
		return dao.readByRoles(roles);
	}

	@Override
	public Collection<PersonRelationship> findByPersonByRole(Person person,PersonRelationshipTypeRole role) {
		return dao.readByPersonByRole(person, role);
	}

	@Override
	public Collection<PersonRelationship> findByPersonsByRoles(Collection<Person> persons,Collection<PersonRelationshipTypeRole> roles) {
		return dao.readByPersonsByRoles(persons, roles);
	}

	@Override
	public Collection<PersonRelationship> findByPersonsByRole(Collection<Person> persons, PersonRelationshipTypeRole role) {
		return dao.readByPersonsByRole(persons, role);
	}

	@Override
	public Collection<PersonRelationship> findByPersonByRoles(Person person,Collection<PersonRelationshipTypeRole> roles) {
		return dao.readByPersonByRoles(person, roles);
	}

	@Override
	public Collection<PersonRelationship> findOppositeByPersonByRole(Person person, PersonRelationshipTypeRole role) {
		return dao.readOppositeByPersonByRole(person, role);
	}

	@Override
	public Collection<PersonRelationship> findOppositeByPersonsByRoles(Collection<Person> persons,Collection<PersonRelationshipTypeRole> roles) {
		return dao.readOppositeByPersonsByRoles(persons, roles);
	}

	@Override
	public Collection<PersonRelationship> findOppositeByPersonsByRole(Collection<Person> persons, PersonRelationshipTypeRole role) {
		return dao.readOppositeByPersonsByRole(persons, role);
	}

	@Override
	public Collection<PersonRelationship> findOppositeByPersonByRoles(Person person, Collection<PersonRelationshipTypeRole> roles) {
		return dao.readOppositeByPersonByRoles(person, roles);
	}
	
	@Override
	public Collection<Person> getRelatedPersons(Collection<PersonRelationship> personRelationships, Person person) {
		Collection<Person> persons = new ArrayList<>();
		for(PersonRelationship personRelationship : personRelationships)
			persons.add(personRelationship.getExtremity1().getPerson().equals(person) ?personRelationship.getExtremity2().getPerson()
					: personRelationship.getExtremity1().getPerson());
		return persons;
	}
	
	/**/
	
	@Getter @Setter
	public static class Details extends AbstractTypedBusinessService.Details<PersonRelationship> implements Serializable {
		
		private static final long serialVersionUID = -1498269103849317057L;
		
		@IncludeInputs private PersonRelationshipExtremityDetails extremity1;// = new PersonRelationshipExtremityDetails();
		@IncludeInputs private PersonRelationshipExtremityDetails extremity2;// = new PersonRelationshipExtremityDetails();
		
		@Input @InputText private FieldValue person1,role1,person2,role2;
		
		public Details(PersonRelationship personRelationship) {
			super(personRelationship);

		}
		
		@Override
		public void setMaster(PersonRelationship personRelationship) {
			super.setMaster(personRelationship);
			if(personRelationship==null){
				
			}else{
				if(extremity1==null)
					extremity1 = new PersonRelationshipExtremityDetails();
				extremity1.setMaster(personRelationship.getExtremity1());
				if(extremity2==null)
					extremity2 = new PersonRelationshipExtremityDetails();
				extremity2.setMaster(personRelationship.getExtremity2());
				
				person1 = extremity1.getPerson();
				role1 = extremity1.getRole();
				
				person2 = extremity2.getPerson();
				role2 = extremity2.getRole();
			}
		}
		
		public static final String FIELD_EXTREMITY1 = "extremity1";
		public static final String FIELD_EXTREMITY2 = "extremity2";
		
		public static final String FIELD_PERSON_1 = "person1";
		public static final String FIELD_ROLE_1 = "role1";
		public static final String FIELD_PERSON_2 = "person2";
		public static final String FIELD_ROLE_2 = "role2";
	}
}
