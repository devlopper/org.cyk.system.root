package org.cyk.system.root.business.api.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class OpticalEncoderDecoderOptions implements Serializable {

	private static final long serialVersionUID = 2151716694164578593L;

	public static final BarcodeFormat DEFAULT_FORMAT = BarcodeFormat.QR;
	public static final String DEFAULT_CHARACTER_SET = "ISO-8859-1";
	
	protected BarcodeFormat format = DEFAULT_FORMAT;
	protected String characterSet = DEFAULT_CHARACTER_SET;
	
	/**/
	
}
