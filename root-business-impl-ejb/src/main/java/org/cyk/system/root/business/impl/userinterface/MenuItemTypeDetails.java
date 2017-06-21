package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.business.impl.geography.AbstractDataTreeTypeDetails;
import org.cyk.system.root.model.userinterface.MenuItemType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuItemTypeDetails extends AbstractDataTreeTypeDetails<MenuItemType> implements Serializable {

	private static final long serialVersionUID = -4747519269632371426L;

	public MenuItemTypeDetails(MenuItemType dataTreeType) {
		super(dataTreeType);
	}
	
}
