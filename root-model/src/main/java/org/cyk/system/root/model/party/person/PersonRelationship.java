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

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public class PersonRelationship extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2742833783679362737L;

	@ManyToOne @NotNull protected Person person1;
	@ManyToOne @NotNull protected PersonRelationshipType type;
	@ManyToOne @NotNull protected Person person2;
	
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

	
}
