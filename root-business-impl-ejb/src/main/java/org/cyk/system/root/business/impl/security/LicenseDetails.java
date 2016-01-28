package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.security.License;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class LicenseDetails extends AbstractOutputDetails<License> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String expirable,expirationDate,expired;
	//@Input @InputFile(extensions=@FileExtensions(groups=FileExtensionGroup.RICH_TEXT)) private File file;
	
	public LicenseDetails(License license) {
		super(license);
		expirable = formatResponse(license.getExpirable());
		if(Boolean.TRUE.equals(license.getExpirable())){
			expirationDate = formatDateTime(license.getPeriod().getToDate());
		}else{
			expirationDate = rootBusinessLayer.getLanguageBusiness().findText("never");
		}
		expired = formatResponse(license.getExpired());
	}
}