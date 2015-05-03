package org.cyk.system.root.business.api.security;


public interface AbstractEncoderDecoderBusiness<SOURCE,DESTINATION,OPTIONS> {

	DESTINATION execute(SOURCE data,OPTIONS options);

	DESTINATION execute(SOURCE data);
	
}

