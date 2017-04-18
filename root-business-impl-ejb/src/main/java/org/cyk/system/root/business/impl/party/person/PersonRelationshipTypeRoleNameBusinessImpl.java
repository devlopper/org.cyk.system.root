package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeRoleNameBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRoleName;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeRoleNameDao;

public class PersonRelationshipTypeRoleNameBusinessImpl extends AbstractEnumerationBusinessImpl<PersonRelationshipTypeRoleName, PersonRelationshipTypeRoleNameDao> implements PersonRelationshipTypeRoleNameBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public PersonRelationshipTypeRoleNameBusinessImpl(PersonRelationshipTypeRoleNameDao dao) {
		super(dao); 
	}   
	
}
