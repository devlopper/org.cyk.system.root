package org.cyk.system.root.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/*lombok*/

/*mapping - jpa*/

public abstract class AbstractModelElement implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final int COEFFICIENT_PRECISION = 5;
	public static final int PERCENT_PRECISION = 5;
	public static final int FLOAT_SCALE = 2;
	public static final int PERCENT_SCALE = 4;
	public static final BigDecimal LOWEST_NON_ZERO_POSITIVE_VALUE = new BigDecimal("0."+StringUtils.repeat('0', 10)+"1");
	
	public abstract String getUiString();
 
}