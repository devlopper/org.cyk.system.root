package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractCollectionItemDetails;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuItem;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserInterfaceMenuItemDetails extends AbstractCollectionItemDetails.AbstractDefault<UserInterfaceMenuItem,UserInterfaceMenu> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue menuNode;
	
	public UserInterfaceMenuItemDetails(UserInterfaceMenuItem userInterfaceMenuItem) {
		super(userInterfaceMenuItem);
	}
	
	@Override
	public void setMaster(UserInterfaceMenuItem userInterfaceMenuItem) {
		super.setMaster(userInterfaceMenuItem);
		if(userInterfaceMenuItem!=null){
			menuNode = createFieldValue(userInterfaceMenuItem.getMenuNode());
		}
	}
	
	public static final String FIELD_MENU_NODE = "menuNode";
	
}