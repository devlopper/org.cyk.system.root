package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @NoArgsConstructor @Entity @FieldOverride(name=AbstractDataTree.FIELD_TYPE,type=PersonRelationshipTypeGroup.class)
@ModelBean(crudStrategy=CrudStrategy.ENUMERATION,genderType=GenderType.MALE)
public class PersonRelationshipType extends AbstractDataTree<PersonRelationshipTypeGroup> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public static final String FAMILY_FATHER = PersonRelationshipTypeGroup.FAMILY+Constant.CHARACTER_UNDESCORE+"FATHER";
	public static final String FAMILY_MOTHER = PersonRelationshipTypeGroup.FAMILY+Constant.CHARACTER_UNDESCORE+"MOTHER";
	
	public static final String SOCIETY_TO_CONTACT_IN_EMERGENCY_CASE = PersonRelationshipTypeGroup.SOCIETY+Constant.CHARACTER_UNDESCORE+"EMERGENCY";
	public static final String SOCIETY_DOCTOR = PersonRelationshipTypeGroup.SOCIETY+Constant.CHARACTER_UNDESCORE+"DOCTOR";
	
	public PersonRelationshipType(PersonRelationshipType parent,PersonRelationshipTypeGroup group,String code,String name) {
		super(parent, group,code);
	}
	
	
}
