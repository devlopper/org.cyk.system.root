package org.cyk.system.root.model.information;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.ENUMERATION)
public class IdentifiableCollectionType extends AbstractDataTreeType implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public IdentifiableCollectionType(IdentifiableCollectionType parent, String code,String label) {
		super(parent, code,label);
	}
	
}
