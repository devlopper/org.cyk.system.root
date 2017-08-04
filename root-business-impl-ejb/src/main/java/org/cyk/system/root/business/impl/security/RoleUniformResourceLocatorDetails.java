package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class RoleUniformResourceLocatorDetails extends AbstractOutputDetails<RoleUniformResourceLocator> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue role,uniformResourceLocator;
	
	public RoleUniformResourceLocatorDetails(RoleUniformResourceLocator roleUniformResourceLocator) {
		super(roleUniformResourceLocator);
	}
	
	public void setMaster(RoleUniformResourceLocator roleUniformResourceLocator) {
		if(roleUniformResourceLocator!=null){
			role = new FieldValue(roleUniformResourceLocator.getRole());
			uniformResourceLocator = new FieldValue(roleUniformResourceLocator.getUniformResourceLocator());
		}
	}
	
	public static final String FIELD_ROLE = "role";
	public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
}