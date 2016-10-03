package org.cyk.system.root.business.impl.integration;

import java.util.LinkedHashSet;
import java.util.Set;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeBusiness;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.junit.Test;

public class PersonBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    private static final String CODE = "1"; 
    
    @Override
    protected void businesses() {
    	
    	Person person = create(inject(PersonBusiness.class).instanciateOneRandomly(CODE));
        assertThat("Person created", person.getIdentifier()!=null);
    	
        person = inject(PersonBusiness.class).find(CODE);
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

    @Test
    public void relationship(){
    	String p1="p1",p2="p2";
    	Set<String> codes = new LinkedHashSet<>();
    	codes.add(p1);
    	codes.add(p2);
    	inject(PersonBusiness.class).create(inject(PersonBusiness.class).instanciateManyRandomly(codes));
    	
    	PersonRelationship personRelationship1;
    	create( personRelationship1 = new PersonRelationship(inject(PersonBusiness.class).find(p1),inject(PersonRelationshipTypeBusiness.class).find(PersonRelationshipType.FAMILY_FATHER)
    			,inject(PersonBusiness.class).find(p1)));
    	
    	System.out.println(personRelationship1);
    	
    	inject(PersonRelationshipBusiness.class).delete(personRelationship1);
    }

}
