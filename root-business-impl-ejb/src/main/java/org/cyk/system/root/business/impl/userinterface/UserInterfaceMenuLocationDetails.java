package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuLocation;

public class UserInterfaceMenuLocationDetails extends AbstractEnumerationDetails<UserInterfaceMenuLocation> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
		
	public UserInterfaceMenuLocationDetails(UserInterfaceMenuLocation userInterfaceMenuLocation) {
		super(userInterfaceMenuLocation);
	}
}