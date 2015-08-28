package org.cyk.system.root.service.impl.integration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.service.impl.data.LocationTestEntity;
import org.cyk.system.root.service.impl.data.PersonTestEntity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class SequenceIdentifierBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 7023768959389316273L;

	@Deployment
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	}
		 
	private List<PersonTestEntity> personTestEntities = new ArrayList<>();
	private List<LocationTestEntity> locationTestEntities = new ArrayList<>();
	
	@Override
    protected void populate() {
	   
    }
	
    @Override
    protected void create() {
    	
        
    }

    @Override
    protected void read() {
        
    }
    
    @Override
    protected void update() {
        
    }
    
    @Override
    protected void delete() {
       
    }
		
    @Override
    protected void finds() {
    	installApplication();
    	
    	for(int i = 0;i<10;i++){
    		personTestEntities.add((PersonTestEntity) genericBusiness.create(new PersonTestEntity("M0"+i,"Tata","pion")));
    		locationTestEntities.add((LocationTestEntity) genericBusiness.create(new LocationTestEntity(RandomStringUtils.randomAlphabetic(6))));
    	}
    	
    	Long i = 1l;
    	//System.out.println(genericBusiness.use(PersonTestEntity.class).find().all());
    	//System.out.println(genericBusiness.use(LocationTestEntity.class).find().all());
    	//System.out.println("SequenceIdentifierBusinessIT.finds()");
    	for(AbstractIdentifiable identifiable : genericBusiness.use(PersonTestEntity.class).find().all()){
    		System.out.println(identifiable+" , "+i);
    		assertThat("Identifier", identifiable.getIdentifier().equals(i++));
    	}
    	
    	i = 1l;
    	for(AbstractIdentifiable identifiable : genericBusiness.use(LocationTestEntity.class).find().all()){
    		assertThat("Identifier", identifiable.getIdentifier().equals(i++));
    	}
    }

    @Override
    protected void businesses() {
        
    }

    /**/
    
    


}
