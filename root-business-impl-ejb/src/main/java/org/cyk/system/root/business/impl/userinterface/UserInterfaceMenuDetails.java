package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractCollectionDetails;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class UserInterfaceMenuDetails extends AbstractCollectionDetails.Extends<UserInterfaceMenu> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue type,renderType;
	
	public UserInterfaceMenuDetails(UserInterfaceMenu userInterfaceMenu) {
		super(userInterfaceMenu);
	}
	
	@Override
	public void setMaster(UserInterfaceMenu userInterfaceMenu) {
		super.setMaster(userInterfaceMenu);
		if(userInterfaceMenu!=null){
			type = createFieldValue(userInterfaceMenu.getType());
			renderType = createFieldValue(userInterfaceMenu.getRenderType());
		}
	}
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_RENDER_TYPE = "renderType";

}