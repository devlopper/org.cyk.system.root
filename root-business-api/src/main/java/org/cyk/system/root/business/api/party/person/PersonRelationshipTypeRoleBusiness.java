package org.cyk.system.root.business.api.party.person;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;

public interface PersonRelationshipTypeRoleBusiness extends TypedBusiness<PersonRelationshipTypeRole> {

	PersonRelationshipTypeRole instanciateOne(String typeCode,String roleCode);
	
}
