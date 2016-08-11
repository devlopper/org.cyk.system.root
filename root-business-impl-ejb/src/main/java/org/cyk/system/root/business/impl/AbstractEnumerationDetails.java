package org.cyk.system.root.business.impl;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter
public abstract class AbstractEnumerationDetails<ENUMERATION extends AbstractEnumeration> extends AbstractOutputDetails<ENUMERATION> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
	
	public AbstractEnumerationDetails(ENUMERATION enumeration) {
		super(enumeration);
	}
	
}