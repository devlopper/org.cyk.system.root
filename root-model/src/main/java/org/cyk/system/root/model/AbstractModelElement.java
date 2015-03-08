package org.cyk.system.root.model;

import java.io.Serializable;

/*lombok*/

/*mapping - jpa*/

public abstract class AbstractModelElement implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final int COEFFICIENT_PRECISION = 5;
	public static final int PERCENT_PRECISION = 5;
	public static final int FLOAT_SCALE = 2;
	public static final int PERCENT_SCALE = 4;
	
	public abstract String getUiString();
 
}