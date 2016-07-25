package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.RootRandomDataProvider;
import org.cyk.system.root.model.party.person.Person;

public class PersonBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Inject private PersonBusiness personBusiness;
    private static final String CODE = "1"; 
    
    
    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
    	
    	Person person = RootRandomDataProvider.getInstance().person();
        person.setCode(CODE);
        personBusiness.create(person);
        assertThat("Person created", person.getIdentifier()!=null);
    	
        person = personBusiness.findByGlobalIdentifierCode(CODE);
        assertThat("Person found by global identifier code", person!=null);
        
    	/*for(int i=0;i<20;i++){
    		Person person = RootRandomDataProvider.getInstance().person();
    		//debug(person.getExtendedInformations());
        	//debug(person.getJobInformations());
    		create(person);
    	}
    	
    	DataReadConfiguration dataReadConfig = new DataReadConfiguration();
    	dataReadConfig.setMaximumResultCount(3l);
    	assertEquals("Count", 3, personBusiness.findAll(dataReadConfig).size());
    	
    	dataReadConfig = new DataReadConfiguration();
    	dataReadConfig.setMaximumResultCount(4l);
    	assertEquals("Count", 4, personBusiness.findAll(dataReadConfig).size());
    	
    	dataReadConfig = new DataReadConfiguration();
    	assertEquals("Count", 21, personBusiness.findAll(dataReadConfig).size());
    	
    	
    	Person person = RootRandomDataProvider.getInstance().oneFromDatabase(Person.class);
    	person = personBusiness.load(person.getIdentifier());
    	//debug(person.getExtendedInformations());
    	//debug(person.getJobInformations());
    	*/
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
