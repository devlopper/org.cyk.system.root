package org.cyk.system.root.model.party.person;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @FieldOverride(name=AbstractDataTree.FIELD_TYPE,type=PersonRelationshipTypeGroup.class)
@ModelBean(crudStrategy=CrudStrategy.ENUMERATION,genderType=GenderType.MALE)
public class PersonRelationshipType extends AbstractDataTree<PersonRelationshipTypeGroup> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public PersonRelationshipType(PersonRelationshipType parent,PersonRelationshipTypeGroup group,String code,String name) {
		super(parent, group,code);
	}
	
	
}
