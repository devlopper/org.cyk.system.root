package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.geography.ContactBusiness;
import org.cyk.system.root.business.api.geography.CountryBusiness;
import org.cyk.system.root.business.api.geography.ElectronicMailBusiness;
import org.cyk.system.root.business.api.geography.LocationBusiness;
import org.cyk.system.root.business.api.geography.PhoneNumberBusiness;

public class GeographyBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 8691254326402622637L;

    @Override
    protected void businesses() {
    	System.out.println(inject(CountryBusiness.class).findAll().size());
    	
    	System.out.println(inject(ContactBusiness.class).findAll());
    	System.out.println(inject(ElectronicMailBusiness.class).findAll());
    	System.out.println(inject(PhoneNumberBusiness.class).findAll());
    	System.out.println(inject(LocationBusiness.class).findAll());
    	
    	System.out.println(inject(LocationBusiness.class).instanciateOneRandomly());
    }

}
