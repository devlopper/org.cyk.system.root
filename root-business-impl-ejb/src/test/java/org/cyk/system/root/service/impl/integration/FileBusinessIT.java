package org.cyk.system.root.service.impl.integration;

import org.cyk.system.root.business.impl.RootBusinessLayer;

public class FileBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

	@Override
	protected void businesses() {
		assertEquals("image/jpeg", RootBusinessLayer.getInstance().getFileBusiness().findMime("jpg"));
	}
   
    
    
}
