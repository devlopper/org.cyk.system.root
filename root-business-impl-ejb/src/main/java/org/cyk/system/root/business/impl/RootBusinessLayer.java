package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Deployment(initialisationType=InitialisationType.EAGER)
public class RootBusinessLayer extends AbstractBusinessLayer implements Serializable {

    @Inject private LocalityBusiness localityBusiness;  
    @Inject private LocalityTypeBusiness localityTypeBusiness;
    
    @Override
    public void createInitialData() {
        geography();
        language();
    }
    
    private void geography(){
        LocalityType continent=new LocalityType(null, "CONTINENT", "Continent");
        localityTypeBusiness.create(continent);
        LocalityType country=new LocalityType(continent, "COUNTRY", "Country");
        localityTypeBusiness.create(country);
        LocalityType city=new LocalityType(country, "CITY", "City");
        localityTypeBusiness.create(city);
        
        Locality afrique;
        
        localityBusiness.create(afrique = new Locality(null, continent, "Afrique"));
        localityBusiness.create(new Locality(null, continent, "Amerique"));
        localityBusiness.create(new Locality(null, continent, "Europe"));
        
        localityBusiness.create(new Locality(afrique, continent, "Cote d'Ivoire"));
        localityBusiness.create(new Locality(afrique, continent, "Benin"));
        
        create(new PhoneNumberType("FIXE", "Fixe"));
        create(new PhoneNumberType("MOBILE", "Mobile"));
    }
    
    private void language(){
        
    }

}
