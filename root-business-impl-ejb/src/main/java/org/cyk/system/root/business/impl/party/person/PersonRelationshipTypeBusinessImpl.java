package org.cyk.system.root.business.impl.party.person;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeBusinessImpl;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeDao;

public class PersonRelationshipTypeBusinessImpl extends AbstractDataTreeBusinessImpl<PersonRelationshipType,PersonRelationshipTypeDao,PersonRelationshipTypeGroup> implements PersonRelationshipTypeBusiness {
 
	private static final long serialVersionUID = 2801588592108008404L;

	@Inject
    public PersonRelationshipTypeBusinessImpl(PersonRelationshipTypeDao dao) {
        super(dao);
    } 
	
	/*@Override
	protected PersonRelationshipType __instanciateOne__(String[] values,InstanciateOneListener<PersonRelationshipType> listener) {
		PersonRelationshipType personRelationshipType = super.__instanciateOne__(values, listener);
		System.out.println(commonUtils.convertToString(values, "|"));
		debug(personRelationshipType);
		return personRelationshipType;
	}*/
}
