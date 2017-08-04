package org.cyk.system.root.business.api.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OpticalDecoderOptions extends OpticalEncoderDecoderOptions implements Serializable {

	private static final long serialVersionUID = 2151716694164578593L;

	/**
	 * Spend more time to try to find a barcode; optimize for accuracy, not speed. Doesn't matter what it maps to
	 */
	private Boolean tryHarder = Boolean.TRUE,pureBarCode=Boolean.FALSE;
	private Set<Class<?>> exceptionClassesToIgnore = new HashSet<>();
	private Map<Class<?>, Object> valueToReturnOnClassIgnored = new HashMap<>();
	private Boolean fullScan = Boolean.TRUE;
	
	private ScannerOptions scannerOptions = new ScannerOptions();
	
	@Getter @Setter
	public class ScannerOptions implements Serializable{

		private static final long serialVersionUID = 5297402382896190437L;
		
		private Integer xStart=0,yStart=0,xStep=5,yStep=5,xIncrement=100,yIncrement=100;
		
	}
}
