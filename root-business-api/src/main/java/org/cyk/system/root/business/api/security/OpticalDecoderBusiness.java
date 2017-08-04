package org.cyk.system.root.business.api.security;

public interface OpticalDecoderBusiness extends AbstractDecoderBusiness<byte[], String,OpticalDecoderOptions> {

	String EXCEPTION_NOT_FOUND = "NOT_FOUND";
	String EXCEPTION_CHEKSUM = "CHEKSUM";
	String EXCEPTION_FORMAT = "FORMAT";
	
}
