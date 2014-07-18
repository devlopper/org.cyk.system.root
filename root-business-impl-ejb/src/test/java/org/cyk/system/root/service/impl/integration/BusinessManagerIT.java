package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class BusinessManagerIT extends AbstractBusinessIT {
	
	@Inject private BusinessManager businessManager;
 
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
        //System.out.println(businessManager.findEntitiesInfos());
        //System.out.println(businessManager.findEntitiesInfos(CrudStrategy.ENUMERATION));
        //Assert.assertEquals(2, businessManager.findEntitiesInfos().size());
        businessManager.createData();
       
    }

    /**/
    
    


}
