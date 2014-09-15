package org.cyk.system.root.service.impl.integration;

import org.cyk.system.root.service.impl.data.PersonTestEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class GenericBusinessIT extends AbstractBusinessIT {
	   
	/**
	 * 
	 */
	private static final long serialVersionUID = 7023768959389316273L;

	@Deployment
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	}
		 
	@Override
    protected void populate() {
	   
    }
	
    @Override
    protected void create() {
        genericBusiness.create(new PersonTestEntity("M01","Tata","pion"));
        genericBusiness.create(new PersonTestEntity("M02","Toto","paon"));
        genericBusiness.create(new PersonTestEntity("M03","Tutu","poon"));
    }

    @Override
    protected void read() {
        
    }
    
    @Override
    protected void update() {
        
    }
    
    @Override
    protected void delete() {
       
    }
		
    @Override
    protected void finds() {
        //Assert.assertTrue("M01 Not found", genericBusiness.e);
    }

    @Override
    protected void businesses() {
        
    }

    /**/
    
    


}
