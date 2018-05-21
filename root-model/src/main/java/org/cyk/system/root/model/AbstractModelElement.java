package org.cyk.system.root.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/*lombok*/

/*mapping - jpa*/

@Getter @Setter
public abstract class AbstractModelElement extends org.cyk.utility.common.model.identifiable.Common implements Serializable{
	private static final long serialVersionUID = 1L;
	
}