package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.userinterface.UserInterfaceCommand;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class UserInterfaceCommandDetails extends AbstractOutputDetails<UserInterfaceCommand> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText protected FieldValue component,uniformResourceLocator,script;
	
	public UserInterfaceCommandDetails(UserInterfaceCommand userInterfaceCommand) {
		super(userInterfaceCommand);
	}
	
	@Override
	public void setMaster(UserInterfaceCommand userInterfaceCommand) {
		super.setMaster(userInterfaceCommand);
		if(userInterfaceCommand!=null){			
			component = createFieldValue(userInterfaceCommand.getComponent());
			uniformResourceLocator = createFieldValue(userInterfaceCommand.getUniformResourceLocator());
			script = createFieldValue(userInterfaceCommand.getScript());
		}
	}
	
	/**/
	
	public static final String FIELD_COMPONENT = "component";
	public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
	public static final String FIELD_SCRIPT = "script";
}