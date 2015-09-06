package org.cyk.system.root.business.api.security;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.security.License;

public interface LicenseBusiness extends TypedBusiness<License> {
    
	void expire(License license);
	
	void expand(License license);
    
}
