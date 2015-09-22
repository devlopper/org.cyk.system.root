package org.cyk.system.root.business.api.security;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.security.License;

public interface LicenseBusiness extends TypedBusiness<License> {
    
	Boolean getEnabled();
	void setEnabled(Boolean value);
	
	void expire(License license);
	
	void expand(License license);
    
}
