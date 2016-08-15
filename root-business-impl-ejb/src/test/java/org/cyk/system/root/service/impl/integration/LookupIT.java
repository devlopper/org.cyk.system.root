package org.cyk.system.root.service.impl.integration;

import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.impl.BusinessLocator;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;

public class LookupIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

	@Override
	protected void businesses() {
		System.out.println(BusinessLocator.getInstance().locate(Locality.class));
		System.out.println(BusinessLocator.getInstance().locate(LocalityType.class));
		
		EventBusiness eventBusiness = RootBusinessLayer.getInstance().inject(EventBusiness.class);
		System.out.println("LookupIT.businesses() : "+eventBusiness);
		assertThat("EventBusiness injected", eventBusiness!=null);
	}
   
    
    
}
