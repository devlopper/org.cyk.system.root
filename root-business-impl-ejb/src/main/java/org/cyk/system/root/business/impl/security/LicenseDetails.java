package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.security.License;

public class LicenseDetails extends AbstractOutputDetails<License> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	//@Input @InputText private String expirable,expirationDate,expired,activated;
	
	public LicenseDetails(License license) {
		super(license);
		
	}
	
	@Override
	public void setMaster(License license) {
		super.setMaster(license);
		if(license!=null){
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