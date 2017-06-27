package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.userinterface.UserInterfaceComponent;

public class UserInterfaceComponentDetails extends AbstractOutputDetails<UserInterfaceComponent> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	public UserInterfaceComponentDetails(UserInterfaceComponent userInterfaceComponent) {
		super(userInterfaceComponent);
		
	}
	
	@Override
	public void setMaster(UserInterfaceComponent userInterfaceComponent) {
		super.setMaster(userInterfaceComponent);
		if(userInterfaceComponent!=null){
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