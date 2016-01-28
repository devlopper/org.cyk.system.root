package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.security.Role;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class RoleDetails extends AbstractEnumerationDetails<Role> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String uniformResourceLocators;
	
	public RoleDetails(Role role) {
		super(role);
		uniformResourceLocators = StringUtils.join(rootBusinessLayer.getRoleUniformResourceLocatorBusiness().findByRole(role)
				,ContentType.TEXT.getNewLineMarker());
	}
}