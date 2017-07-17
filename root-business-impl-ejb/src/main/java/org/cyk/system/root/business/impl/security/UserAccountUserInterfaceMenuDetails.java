package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.security.UserAccountUserInterfaceMenu;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class UserAccountUserInterfaceMenuDetails extends AbstractOutputDetails<UserAccountUserInterfaceMenu> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue userAccount,userInterfaceMenu;
	
	public UserAccountUserInterfaceMenuDetails(UserAccountUserInterfaceMenu userAccountUserInterfaceMenu) {
		super(userAccountUserInterfaceMenu);
	}
	
	public void setMaster(UserAccountUserInterfaceMenu userAccountUserInterfaceMenu) {
		if(userAccountUserInterfaceMenu!=null){
			userAccount = createFieldValue(userAccountUserInterfaceMenu.getUserAccount());
			userInterfaceMenu = createFieldValue(userAccountUserInterfaceMenu.getUserInterfaceMenu());
		}
	}
	
	public static final String FIELD_USER_ACCOUNT = "userAccount";
	public static final String FIELD_USER_INTERFACE_MENU = "userInterfaceMenu";
}