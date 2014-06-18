package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeBusiness;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventType;
import org.cyk.system.root.model.event.Period;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Deployment(initialisationType=InitialisationType.EAGER)
public class RootBusinessLayer extends AbstractBusinessLayer implements Serializable {
 
    @Inject private LocalityBusiness localityBusiness;
    
    @Override
    public void createInitialData() {
        geography();
        language();
    }
    
    private void geography(){
        LocalityType continent=new LocalityType(null, "CONTINENT", "Continent");
        create(continent);
        LocalityType country=new LocalityType(continent, "COUNTRY", "Country");
        create(country);
        LocalityType city=new LocalityType(country, "CITY", "City");
        create(city);
        
        Locality afrique;
        
        create(afrique = new Locality(null, continent, "Afrique"));
        create(new Locality(null, continent, "Amerique"));
        create(new Locality(null, continent, "Europe"));
        
        create(new Locality(afrique, continent, "Cote d'Ivoire"));
        create(new Locality(afrique, continent, "Benin"));
        
        create(new PhoneNumberType("FIXE", "Fixe"));
        create(new PhoneNumberType("MOBILE", "Mobile"));
        
        EventType eventType1=create(new EventType("RDV", "Rendez vous", null)),
                eventType2=create(new EventType("REU", "Reunion", null));
        
        Date date = new Date();
        create(new Event(eventType1,"Voir le docteur","Conseils et suivi",new Period(date, DateUtils.addMinutes(date, 15))));
        date = DateUtils.addMonths(new Date(), -1);
        create(new Event(eventType2,"Cours maths","Discussion",new Period(date,DateUtils.addHours(date, 1) )));
        date = DateUtils.addMonths(new Date(), 1);
        create(new Event(eventType1,"Bilan AG","Explications",new Period(date, DateUtils.addHours(date, 2))));
    }
    
    private void language(){
         
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void registerDataTreeBusinessBean(Map<Class<AbstractDataTree<DataTreeType>>, AbstractDataTreeBusiness<AbstractDataTree<DataTreeType>, DataTreeType>> beansMap) {
        beansMap.put((Class)Locality.class, (AbstractDataTreeBusiness)localityBusiness);
        
    }

}
