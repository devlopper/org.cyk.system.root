package org.cyk.system.root.model.userinterface;

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
public class UserInterfaceMenuNodeType extends AbstractDataTreeType implements Serializable  {

	private static final long serialVersionUID = -6838401709866343401L;

	public UserInterfaceMenuNodeType(UserInterfaceMenuNodeType parent, String code,String label) {
		super(parent, code,label);
	}

	
	
}
