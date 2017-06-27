package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuRenderType;

public class UserInterfaceMenuRenderTypeDetails extends AbstractEnumerationDetails<UserInterfaceMenuRenderType> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
		
	public UserInterfaceMenuRenderTypeDetails(UserInterfaceMenuRenderType userInterfaceMenuRenderType) {
		super(userInterfaceMenuRenderType);
	}
}