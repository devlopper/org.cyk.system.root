package org.cyk.system.root.model.userinterface;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @FieldOverride(name=MenuItem.FIELD_TYPE,type=MenuItemType.class)
@ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.ENUMERATION)
public class MenuItem extends AbstractDataTree<MenuItemType> implements Serializable  {

	private static final long serialVersionUID = -6128937819261060725L;

	@ManyToOne private UniformResourceLocator uniformResourceLocator;
	
	public MenuItem(MenuItem parent, MenuItemType type, String code,String name) {
		super(parent, type, code);
		setName(name);
	}
	
	public MenuItem(MenuItem parent, MenuItemType type, String code) {
		super(parent,type,code);
	}
}
