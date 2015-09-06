package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.RootRandomDataProvider;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class PersonBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment(); 
    } 
    
    @Inject private PersonBusiness personBusiness;
    
    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
    	installApplication();
    	for(int i=0;i<20;i++)
    		create(RootRandomDataProvider.getInstance().person());
    	
    	DataReadConfiguration dataReadConfig = new DataReadConfiguration();
    	dataReadConfig.setMaximumResultCount(3l);
    	assertEquals("Count", 3, personBusiness.findAll(dataReadConfig).size());
    	
    	dataReadConfig = new DataReadConfiguration();
    	dataReadConfig.setMaximumResultCount(4l);
    	assertEquals("Count", 4, personBusiness.findAll(dataReadConfig).size());
    	
    	dataReadConfig = new DataReadConfiguration();
    	assertEquals("Count", 21, personBusiness.findAll(dataReadConfig).size());
    }

    @Override
    protected void create() {
        
    }

    @Override
    protected void delete() {
        
    }

    

    @Override
    protected void read() {
        
    }

    @Override
    protected void update() {
        
    }

}
