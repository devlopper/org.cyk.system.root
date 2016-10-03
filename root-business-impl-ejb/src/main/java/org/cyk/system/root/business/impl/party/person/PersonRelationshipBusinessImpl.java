package org.cyk.system.root.business.impl.party.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;

@Stateless
public class PersonRelationshipBusinessImpl extends AbstractTypedBusinessService<PersonRelationship,PersonRelationshipDao> implements PersonRelationshipBusiness {
 
	private static final long serialVersionUID = 2801588592108008404L;

	@Inject
    public PersonRelationshipBusinessImpl(PersonRelationshipDao dao) {
        super(dao);
    } 
	
	/*@Override
	public PersonRelationship delete(PersonRelationship personRelationship) {
		personRelationship.setPerson1(null);
		personRelationship.setType(null);
		personRelationship.setPerson2(null);
		return super.delete(personRelationship);
	}*/
}
