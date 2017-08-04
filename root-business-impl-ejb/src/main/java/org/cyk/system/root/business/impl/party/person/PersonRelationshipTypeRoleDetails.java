package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class PersonRelationshipTypeRoleDetails extends AbstractOutputDetails<PersonRelationshipTypeRole> implements Serializable {
	
	private static final long serialVersionUID = -1498269103849317057L;
	
	@Input @InputText private FieldValue personRelationshipType,role;
	
	public PersonRelationshipTypeRoleDetails(PersonRelationshipTypeRole personRelationshipTypeRole) {
		super(personRelationshipTypeRole);

	}
	
	@Override
	public void setMaster(PersonRelationshipTypeRole personRelationshipTypeRole) {
		super.setMaster(personRelationshipTypeRole);
		if(personRelationshipTypeRole==null){
			
		}else{
			personRelationshipType = new FieldValue(personRelationshipTypeRole.getPersonRelationshipType());
			role = new FieldValue(personRelationshipTypeRole.getRole());
			
		}
	}
	
	public static final String FIELD_PERSON_RELATIONSHIP_TYPE = "personRelationshipType";
	public static final String FIELD_ROLE = "role";
	
}