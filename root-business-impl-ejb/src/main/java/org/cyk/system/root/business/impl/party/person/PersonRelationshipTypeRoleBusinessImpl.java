package org.cyk.system.root.business.impl.party.person;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeRoleBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeRoleDao;

public class PersonRelationshipTypeRoleBusinessImpl extends AbstractTypedBusinessService<PersonRelationshipTypeRole,PersonRelationshipTypeRoleDao> implements PersonRelationshipTypeRoleBusiness {
 
	private static final long serialVersionUID = 2801588592108008404L;

	@Inject
    public PersonRelationshipTypeRoleBusinessImpl(PersonRelationshipTypeRoleDao dao) {
        super(dao);
    }
	
	@Override
	protected PersonRelationshipTypeRole __instanciateOne__(String[] values,InstanciateOneListener<PersonRelationshipTypeRole> listener) {
		super.__instanciateOne__(values, listener);
		set(listener.getSetListener(), PersonRelationshipTypeRole.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE);
		set(listener.getSetListener(), PersonRelationshipTypeRole.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_NAME);
		set(listener.getSetListener(), PersonRelationshipTypeRole.FIELD_PERSON_RELATIONSHIP_TYPE);
		set(listener.getSetListener(), PersonRelationshipTypeRole.FIELD_ROLE);
		return listener.getInstance();
	}
	
}
