package org.cyk.system.root.persistence.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuItem;
import org.cyk.system.root.persistence.api.userinterface.UserInterfaceMenuItemDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;

public class UserInterfaceMenuItemDaoImpl extends AbstractCollectionItemDaoImpl<UserInterfaceMenuItem,UserInterfaceMenu> implements UserInterfaceMenuItemDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

}
 