package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class PersonRelationshipDetails extends AbstractOutputDetails<PersonRelationship> implements Serializable {
	
	private static final long serialVersionUID = -1498269103849317057L;
	
	@Input @InputText private FieldValue type;
	@Input @InputText private FieldValue person1;
	@Input @InputText private FieldValue person2;
	
	public PersonRelationshipDetails(PersonRelationship personRelationship) {
		super(personRelationship);
		person1 = new FieldValue(personRelationship.getPerson1());
		type = new FieldValue(personRelationship.getType());
		person2 = new FieldValue(personRelationship.getPerson2());
		
	}
	
	public static final String FIELD_PERSON1 = "person1";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_PERSON2 = "person2";
}