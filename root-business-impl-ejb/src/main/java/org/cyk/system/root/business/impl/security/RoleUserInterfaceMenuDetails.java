package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.security.RoleUserInterfaceMenu;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class RoleUserInterfaceMenuDetails extends AbstractOutputDetails<RoleUserInterfaceMenu> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue role,userInterfaceMenu;
	
	public RoleUserInterfaceMenuDetails(RoleUserInterfaceMenu roleUserInterfaceMenu) {
		super(roleUserInterfaceMenu);
	}
	
	public void setMaster(RoleUserInterfaceMenu roleUserInterfaceMenu) {
		if(roleUserInterfaceMenu!=null){
			role = new FieldValue(roleUserInterfaceMenu.getRole());
			userInterfaceMenu = new FieldValue(roleUserInterfaceMenu.getUserInterfaceMenu());
		}
	}
	
	public static final String FIELD_ROLE = "role";
	public static final String FIELD_USER_INTERFACE_MENU = "userInterfaceMenu";
}