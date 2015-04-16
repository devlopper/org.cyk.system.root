package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.geography.LocalityType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class GeographyBusinessIT extends AbstractBusinessIT {
	   
	/**
	 * 
	 */
	private static final long serialVersionUID = 8691254326402622637L;
	@Inject private LocalityBusiness localityBusiness;  
	@Inject private LocalityTypeBusiness localityTypeBusiness;
	
	@Deployment 
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	}
		 
	@Override
    protected void populate() {
	   
    }
	
    @Override
    protected void create() {
        /*LocalityType t1 = new LocalityType(null, "C", "Continent");
        genericBusiness.create(t1);
        Assert.assertNotNull(t1.getIdentifier());
        Locality l = new Locality(null, t1, "A");
        genericBusiness.create(l);
        Assert.assertNotNull(l.getIdentifier());*/
    }

    @Override
    protected void read() {
        //Assert.assertNotNull(localityBusiness.find("A"));
    }
    
    @Override
    protected void update() {
        /*Locality l =localityBusiness.find("A");
        l.setName("Dabou");
        Assert.assertEquals("Dabou", localityBusiness.find("A").getName());*/
    }
    
    @Override
    protected void delete() {
       /*genericBusiness.delete(localityBusiness.find("A"));
       Assert.assertNull(localityBusiness.find("A"));*/
    }
		
    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
    	applicationBusiness.install(RootBusinessLayer.fakeInstallation());
    	Assert.assertNotNull(localityTypeBusiness.find(LocalityType.COUNTRY));
    	System.out.println(localityBusiness.findByType(RootBusinessLayer.getInstance().getCountryLocalityType()));
    }

    /**/
    
    


}
