package org.cyk.system.root.service.impl.integration;

import org.cyk.system.root.service.impl.data.ValidationTestEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class ValidationBusinessIT extends AbstractBusinessIT {
	   
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
        genericBusiness.create(new ValidationTestEntity());
       
    }

    @Override protected void read() {}
    @Override protected void update() {}
    @Override protected void delete() {}	
    @Override protected void finds() {}

    @Override protected void businesses() {}

}
