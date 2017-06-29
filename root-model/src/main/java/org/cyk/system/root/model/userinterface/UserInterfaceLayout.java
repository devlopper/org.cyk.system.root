package org.cyk.system.root.model.userinterface;

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

@Getter @Setter @NoArgsConstructor @Entity @FieldOverride(name=UserInterfaceLayout.FIELD_TYPE,type=UserInterfaceLayoutType.class)
@ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.ENUMERATION)
public class UserInterfaceLayout extends AbstractDataTree<UserInterfaceLayoutType> implements Serializable  {

	private static final long serialVersionUID = -6128937819261060725L;

	public UserInterfaceLayout(UserInterfaceLayout parent, UserInterfaceLayoutType type, String code,String name) {
		super(parent, type, code);
		setName(name);
	}
	
	public UserInterfaceLayout(UserInterfaceLayout parent, UserInterfaceLayoutType type, String code) {
		super(parent,type,code);
	}
	
}
