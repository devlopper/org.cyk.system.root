package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {PersonRelationship.COLUMN_PERSON1,PersonRelationship.COLUMN_ROLE_1, PersonRelationship.COLUMN_PERSON2,PersonRelationship.COLUMN_ROLE_2})})
public class PersonRelationship extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2742833783679362737L;

	@Embedded @AssociationOverrides(value={
			@AssociationOverride(name=PersonRelationshipExtremity.FIELD_PERSON,joinColumns={@JoinColumn(name=COLUMN_PERSON1)})
			,@AssociationOverride(name=PersonRelationshipExtremity.FIELD_ROLE,joinColumns={@JoinColumn(name=COLUMN_ROLE_1)})
	}) 
	private PersonRelationshipExtremity extremity1 = new PersonRelationshipExtremity();
	
	@Embedded @AssociationOverrides(value={
			@AssociationOverride(name=PersonRelationshipExtremity.FIELD_PERSON,joinColumns={@JoinColumn(name=COLUMN_PERSON2)})
			,@AssociationOverride(name=PersonRelationshipExtremity.FIELD_ROLE,joinColumns={@JoinColumn(name=COLUMN_ROLE_2)})
	}) 
	private PersonRelationshipExtremity extremity2 = new PersonRelationshipExtremity();
	
	public PersonRelationshipExtremity getExtremity1(){
		if(extremity1==null)
			extremity1 = new PersonRelationshipExtremity();
		return extremity1;
	}
	
	public PersonRelationshipExtremity getExtremity2(){
		if(extremity2==null)
			extremity2 = new PersonRelationshipExtremity();
		return extremity2;
	}
	
	@Override
	public String toString() {
		return getUiString();
	}
	
	@Override
	public String getUiString() {
		return extremity1.getUiString()+" / "+extremity2.getUiString();
	}

	public static final String FIELD_EXTREMITY_1 = "extremity1";
	public static final String FIELD_EXTREMITY_2 = "extremity2";
	
	public static final String COLUMN_PERSON1 = PersonRelationshipExtremity.FIELD_PERSON+"1";
	public static final String COLUMN_ROLE_1 = PersonRelationshipExtremity.FIELD_ROLE+"1";
	
	public static final String COLUMN_PERSON2 = PersonRelationshipExtremity.FIELD_PERSON+"2";
	public static final String COLUMN_ROLE_2 = PersonRelationshipExtremity.FIELD_ROLE+"2";
}
