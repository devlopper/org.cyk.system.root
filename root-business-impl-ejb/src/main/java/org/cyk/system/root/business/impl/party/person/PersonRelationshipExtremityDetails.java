package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractModelElementOutputDetails;
import org.cyk.system.root.model.party.person.PersonRelationshipExtremity;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter @NoArgsConstructor
public class PersonRelationshipExtremityDetails extends AbstractModelElementOutputDetails<PersonRelationshipExtremity> implements Serializable {

	private static final long serialVersionUID = 1307822857551633645L;

	@Input @InputText private FieldValue person,role;
	
	public PersonRelationshipExtremityDetails(PersonRelationshipExtremity personRelationshipExtremity) {
		super(personRelationshipExtremity);
	}
	
	@Override
	public void setMaster(PersonRelationshipExtremity personRelationshipExtremity) {
		super.setMaster(personRelationshipExtremity);
		if(personRelationshipExtremity==null){
			
		}else{
			person = new FieldValue(personRelationshipExtremity.getPerson());
			role = new FieldValue(personRelationshipExtremity.getRole());
		}
	}
	
	public static final String FIELD_PERSON = "person";
	public static final String FIELD_ROLE = "role";
	
}
