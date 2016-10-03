package org.cyk.system.root.business.impl.party.person;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;

@Stateless
public class PersonRelationshipBusinessImpl extends AbstractTypedBusinessService<PersonRelationship,PersonRelationshipDao> implements PersonRelationshipBusiness {
 
	private static final long serialVersionUID = 2801588592108008404L;

	@Inject
    public PersonRelationshipBusinessImpl(PersonRelationshipDao dao) {
        super(dao);
    }

	@Override
	public Collection<PersonRelationship> findByPerson(Person person) {
		return dao.readByPerson(person);
	}

	@Override
	public Collection<PersonRelationship> findByType(PersonRelationshipType type) {
		return dao.readByType(type);
	} 
	
}
