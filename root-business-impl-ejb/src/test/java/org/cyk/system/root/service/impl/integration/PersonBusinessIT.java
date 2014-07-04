package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.party.PersonBusiness;
import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.system.root.business.impl.party.PersonValidator;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.party.Person;
import org.cyk.utility.common.CommonUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class PersonBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        for(Class<?> c : CommonUtils.getInstance().getPackageClasses("org.cyk.system.root", Object.class))
            System.out.println(c);
        return createRootDeployment(); 
    } 
    
    @Inject private PersonBusiness personBusiness;
    @Inject private DataTreeTypeBusiness dataTreeTypeBusiness;
    @Inject private LocalityBusiness localityBusiness;
    @Inject private PersonValidator personValidator;
    
    private Locality locality;
    @Override
    protected void populate() {
        LocalityType t = (LocalityType) dataTreeTypeBusiness.create(new LocalityType(null, "LT1", "Pays"));
        locality = new Locality(null, t, "L1");
        locality.setName("Name");
        localityBusiness.create(locality);     
    }
   
    @Override
    protected void _execute_() {
        super._execute_();
        Person person = new Person();
        person.setName(null);
        person.setBirthLocation(new Location(null,locality,"Pres de la pharmacie"));
        try{personValidator.validate(person);}
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(personValidator.getMessages());
        //personBusiness.create(person);
    }

    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
        
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
