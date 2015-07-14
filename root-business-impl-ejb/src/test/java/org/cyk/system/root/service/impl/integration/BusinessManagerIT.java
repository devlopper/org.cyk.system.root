package org.cyk.system.root.service.impl.integration;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class BusinessManagerIT extends AbstractBusinessIT {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1957148189138455114L;
	//@Inject private BusinessManager businessManager;
 
	@Deployment
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	}
		
	@Override
    protected void populate() {}
	
    @Override
    protected void create() {}

    @Override
    protected void read() {}
    
    @Override
    protected void update() {}
    
    @Override
    protected void delete() {}
	
    @Override
    protected void businesses() {
        
    }

    @Override
    protected void finds() {
        
       
    }

    /**/
    
    


}
