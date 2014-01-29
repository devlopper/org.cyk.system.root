package org.cyk.system.root.dao.impl;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractModel;

@Getter @Setter
public class Person extends AbstractModel implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -7061794989292809428L;
	
	private String matricule,name,lastName;
	
}
