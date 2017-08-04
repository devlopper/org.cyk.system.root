package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.security.Role;

public class RoleDetails extends AbstractEnumerationDetails<Role> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
	
	public RoleDetails(Role role) {
		super(role);
		
	}
}