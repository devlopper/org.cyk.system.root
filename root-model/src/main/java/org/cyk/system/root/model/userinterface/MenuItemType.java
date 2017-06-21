package org.cyk.system.root.model.userinterface;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity 
public class MenuItemType extends AbstractDataTreeType implements Serializable  {

	private static final long serialVersionUID = -6838401709866343401L;

	public MenuItemType(MenuItemType parent, String code,String label) {
		super(parent, code,label);
	}

	
	
}
