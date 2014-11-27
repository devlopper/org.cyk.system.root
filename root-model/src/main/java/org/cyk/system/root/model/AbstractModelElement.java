package org.cyk.system.root.model;

import java.io.Serializable;

/*lombok*/

/*mapping - jpa*/

public abstract class AbstractModelElement implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public abstract String getUiString();
 
}