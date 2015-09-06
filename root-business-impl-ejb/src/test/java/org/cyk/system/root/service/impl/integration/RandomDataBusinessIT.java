package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.RootRandomDataProvider;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class RandomDataBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
 
    @Inject private PersonBusiness personBusiness;
    @Inject private RootRandomDataProvider rootRandomDataProvider;
    
    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    } 
    
    @Override
    protected void businesses() {
    	installApplication();
    	rootRandomDataProvider.createPerson(3);
    	personBusiness.create(rootRandomDataProvider.person());
    	
    }
    
    @Override protected void finds() {}
    @Override protected void create() {}
    @Override protected void delete() {}
    @Override protected void read() {}
    @Override protected void update() {}
    
}
