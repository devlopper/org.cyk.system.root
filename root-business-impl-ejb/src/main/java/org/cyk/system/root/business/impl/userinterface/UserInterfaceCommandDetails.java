package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.userinterface.UserInterfaceCommand;

public class UserInterfaceCommandDetails extends AbstractOutputDetails<UserInterfaceCommand> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	public UserInterfaceCommandDetails(UserInterfaceCommand userInterfaceCommand) {
		super(userInterfaceCommand);
		
	}
	
	@Override
	public void setMaster(UserInterfaceCommand userInterfaceCommand) {
		super.setMaster(userInterfaceCommand);
		if(userInterfaceCommand!=null){
			//activated = formatResponse(license.getActivated());
			//expirable = formatResponse(license.getExpirable());
			/*if(Boolean.TRUE.equals(license.getExpirable())){
				expirationDate = formatDateTime(license.getPeriod().getToDate());
			}else{
				expirationDate = inject(LanguageBusiness.class).findText("never");
			}*/
			//expired = formatResponse(license.getExpired());
		}
	}
	
	/**/
	
	
}