package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.security.Software;

public class SoftwareDetails extends AbstractEnumerationDetails<Software> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
		
	public SoftwareDetails(Software software) {
		super(software);
	}
}