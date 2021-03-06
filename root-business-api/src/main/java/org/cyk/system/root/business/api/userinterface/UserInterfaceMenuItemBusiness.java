package org.cyk.system.root.business.api.userinterface;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuItem;

public interface UserInterfaceMenuItemBusiness extends AbstractCollectionItemBusiness<UserInterfaceMenuItem,UserInterfaceMenu> {

	Collection<UserInterfaceMenuItem> merge(Collection<Collection<UserInterfaceMenuItem>> userInterfaceMenuItemCollections);
	
}
