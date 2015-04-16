package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.security.UserAccountSearchCriteria;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class SecurityBusinessIT extends AbstractBusinessIT {

	private static final long serialVersionUID = 8691254326402622637L;
	
	@Inject private UserAccountBusiness userAccountBusiness;
	
	@Deployment 
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	}
		 
	@Override
    protected void populate() {
	   
    }
	
    @Override
    protected void create() {}

    @Override
    protected void read() {}
    
    @Override
    protected void update() {}
    
    @Override
    protected void delete() {}
		
    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
    	applicationBusiness.install(RootBusinessLayer.fakeInstallation());
    	UserAccountSearchCriteria criteria = new UserAccountSearchCriteria(null);
    	//System.out.println(RootBusinessLayer.getInstance().getAdministratorRole());
    	criteria.getRoleExcluded().add(RootBusinessLayer.getInstance().getAdministratorRole());
    	
    	/*System.out.println(userAccountBusiness.findAll());
    	System.out.println(userAccountBusiness.findAllExcludeRoles(Arrays.asList(RootBusinessLayer.getInstance().getAdministratorRole())));
    	System.out.println(userAccountBusiness.findByCriteria(criteria));*/
    	Assert.assertEquals(1l, userAccountBusiness.countByCriteria(criteria).longValue());
    }

    /**/
    
    


}
