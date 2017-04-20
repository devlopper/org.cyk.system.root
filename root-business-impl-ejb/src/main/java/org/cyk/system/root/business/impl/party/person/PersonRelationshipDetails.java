package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;

@Getter @Setter
public class PersonRelationshipDetails extends AbstractOutputDetails<PersonRelationship> implements Serializable {
	
	private static final long serialVersionUID = -1498269103849317057L;
	
	@IncludeInputs private PersonRelationshipExtremityDetails extremity1 = new PersonRelationshipExtremityDetails();
	@IncludeInputs private PersonRelationshipExtremityDetails extremity2 = new PersonRelationshipExtremityDetails();
	
	public PersonRelationshipDetails(PersonRelationship personRelationship) {
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
		}
	}
	
	public static final String FIELD_EXTREMITY1 = "extremity1";
	public static final String FIELD_EXTREMITY2 = "extremity2";
}