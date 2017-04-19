package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractModelElement;

@Getter @Setter @Embeddable @NoArgsConstructor @AllArgsConstructor
public class PersonRelationshipExtremity extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne @JoinColumn(name=COLUMN_PERSON) @NotNull private Person person;
	@ManyToOne @JoinColumn(name=COLUMN_ROLE) @NotNull private PersonRelationshipTypeRole role;
	
	@Override
	public String getUiString() {
		return person.getUiString()+"/"+role.getUiString();
	}

	/**/
	
	public static final String FIELD_PERSON = "person";
	public static final String FIELD_ROLE = "role";
	
	public static final String COLUMN_PERSON = FIELD_PERSON;
	public static final String COLUMN_ROLE = FIELD_ROLE;
}
