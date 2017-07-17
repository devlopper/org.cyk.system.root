package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuType;

public class UserInterfaceMenuTypeDetails extends AbstractEnumerationDetails<UserInterfaceMenuType> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
		
	public UserInterfaceMenuTypeDetails(UserInterfaceMenuType userInterfaceMenuType) {
		super(userInterfaceMenuType);
	}
}