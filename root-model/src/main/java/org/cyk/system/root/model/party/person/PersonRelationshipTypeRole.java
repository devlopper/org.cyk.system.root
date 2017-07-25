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

@Getter @Setter @NoArgsConstructor @Entity
@ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
@Table(uniqueConstraints={@UniqueConstraint(columnNames={PersonRelationshipTypeRole.COLUMN_PERSON_RELATIONSHIP_TYPE,PersonRelationshipTypeRole.COLUMN_ROLE})})
public class PersonRelationshipTypeRole extends AbstractIdentifiable implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @JoinColumn(name=COLUMN_PERSON_RELATIONSHIP_TYPE) @NotNull private PersonRelationshipType personRelationshipType;
	
	@ManyToOne @JoinColumn(name=COLUMN_ROLE) @NotNull private PersonRelationshipTypeRoleName role;
	
	/**/
	
	@Override
	public String toString() {
		return /*personRelationshipType.toString()+Constant.CHARACTER_SLASH+*/role.toString();
	}
	
	@Override
	public String getUiString() {
		return /*personRelationshipType.getUiString()+Constant.CHARACTER_SLASH+*/role.getUiString();
	}
	
	public static final String FIELD_PERSON_RELATIONSHIP_TYPE = "personRelationshipType";
	public static final String FIELD_ROLE = "role";
	
	public static final String COLUMN_PERSON_RELATIONSHIP_TYPE = FIELD_PERSON_RELATIONSHIP_TYPE;
	public static final String COLUMN_ROLE = FIELD_ROLE;
}
