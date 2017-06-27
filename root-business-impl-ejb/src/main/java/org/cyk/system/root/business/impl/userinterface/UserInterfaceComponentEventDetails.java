package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.userinterface.UserInterfaceComponentEvent;

public class UserInterfaceComponentEventDetails extends AbstractOutputDetails<UserInterfaceComponentEvent> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	public UserInterfaceComponentEventDetails(UserInterfaceComponentEvent userInterfaceComponentEvent) {
		super(userInterfaceComponentEvent);
		
	}
	
	@Override
	public void setMaster(UserInterfaceComponentEvent userInterfaceComponentEvent) {
		super.setMaster(userInterfaceComponentEvent);
		if(userInterfaceComponentEvent!=null){
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