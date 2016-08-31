package org.cyk.system.root.business.impl.integration;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class GenericBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 7023768959389316273L;

	@Deployment
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	}
		 
	@Override
    protected void populate() {
	   
    }
	
    @Override
    protected void businesses() {
        
    }

    /**/
    
    


}
