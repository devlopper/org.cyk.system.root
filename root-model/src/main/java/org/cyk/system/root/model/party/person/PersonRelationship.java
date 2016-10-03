package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class PersonRelationship extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2742833783679362737L;

	@ManyToOne @NotNull private Person person1;
	@ManyToOne @NotNull private PersonRelationshipType type;
	@ManyToOne @NotNull private Person person2;
	
	public PersonRelationship(Person person1, PersonRelationshipType type,
			Person person2) {
		super();
		this.person1 = person1;
		this.type = type;
		this.person2 = person2;
	}
	
	@Override
	public String toString() {
		return getUiString();
	}
	
	@Override
	public String getUiString() {
		return person1+" "+type.getCode()+" of "+person2;
	}

	public static final String FIELD_PERSON1 = "person1";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_PERSON2 = "person2";
	
}
