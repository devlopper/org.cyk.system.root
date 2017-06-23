package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractCollectionDetails;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;

public class UserInterfaceMenuDetails extends AbstractCollectionDetails.Extends<UserInterfaceMenu> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	public UserInterfaceMenuDetails(UserInterfaceMenu userInterfaceMenu) {
		super(userInterfaceMenu);
	}

}