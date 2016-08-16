package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.time.Period;

public class BusinessInterfaceLocatorIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Inject private BusinessInterfaceLocator businessInterfaceLocator;
    
    @Override
    protected void populate() {
    	
    }
    
	@Override
	protected void businesses() {
		assertThat("Locality Business Interface found", businessInterfaceLocator.locate(Locality.class)!=null);
		assertThat("Locality Business Interface created", businessInterfaceLocator.injectLocated(Locality.class)!=null);
		
		assertThat("Period Business Interface not found", businessInterfaceLocator.locate(Period.class)==null);
		assertThat("Period Business Interface not created", businessInterfaceLocator.injectLocated(Period.class)==null);
	}
   
    
    
}
