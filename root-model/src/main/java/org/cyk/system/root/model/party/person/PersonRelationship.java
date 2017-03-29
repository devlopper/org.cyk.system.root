package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {PersonRelationship.COLUMN_PERSON1,PersonRelationship.COLUMN_TYPE, PersonRelationship.COLUMN_PERSON2})})
public class PersonRelationship extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2742833783679362737L;

	@ManyToOne @JoinColumn(name=COLUMN_PERSON1) @NotNull private Person person1;
	@ManyToOne @JoinColumn(name=COLUMN_TYPE) @NotNull private PersonRelationshipType type;
	@ManyToOne @JoinColumn(name=COLUMN_PERSON2) @NotNull private Person person2;
	
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
		return person1+" "+type.getName()+" of "+person2;
	}

	public static final String FIELD_PERSON1 = "person1";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_PERSON2 = "person2";
	
	/**/
	
	public static final String COLUMN_PERSON1 = FIELD_PERSON1;
	public static final String COLUMN_TYPE = FIELD_TYPE;
	public static final String COLUMN_PERSON2 = FIELD_PERSON2;
}
