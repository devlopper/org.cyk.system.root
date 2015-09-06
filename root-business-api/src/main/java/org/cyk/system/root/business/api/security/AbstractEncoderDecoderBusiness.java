package org.cyk.system.root.business.api.security;

import org.cyk.system.root.business.api.BusinessService;


public interface AbstractEncoderDecoderBusiness<SOURCE,DESTINATION,OPTIONS> extends BusinessService {

	DESTINATION execute(SOURCE data,OPTIONS options);

	DESTINATION execute(SOURCE data);
	
}

