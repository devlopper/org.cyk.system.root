package org.cyk.system.root.business.impl.integration;

import org.junit.Test;

public class PerformanceIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void installApplication() {
    	super.installApplication();
    }
    
    @Override
    protected void businesses() {

    }
    
    @Test
    public void createPerson1(){
    	createPersons(1);
    }
    
    //@Test
    public void createPerson10(){
    	createPersons(10);
    }
    
    //@Test
    public void createPerson100(){
    	createPersons(100);
    }
    
    //@Test
    public void createPerson250(){
    	createPersons(250);
    }
    
    //@Test
    public void createPerson500(){
    	createPersons(500);
    }
    
    //@Test
    public void createPerson1000(){
    	createPersons(1000);
    }
    
    //@Test
    public void createPerson5000(){
    	createPersons(5000);
    }
    
    //@Test
    public void createPerson10000(){
    	createPersons(10000);
    }
    
    private void createPersons(final Integer numberOfPerson){
    	/*Execution executionUsingOneCall = CommonUtils.getInstance().execute("Create "+numberOfPerson+" persons using one business call", new Runnable() {
			@Override
			public void run() {
				Collection<Person> persons = inject(PersonBusiness.class).instanciateManyRandomly(numberOfPerson);
		    	create(persons);
			}
		});
    	
    	Execution executionUsingManyCall = CommonUtils.getInstance().execute("Create "+numberOfPerson+" persons using many business call", new Runnable() {
			@Override
			public void run() {
				Collection<Person> persons = inject(PersonBusiness.class).instanciateManyRandomly(numberOfPerson);
				for(Person person : persons)
		    		create(person);
			}
		});
    	
    	System.out.println(executionUsingOneCall+" / "+executionUsingManyCall +" / Ratio = " 
    			+( (executionUsingOneCall.getDuration() - executionUsingManyCall.getDuration())) / 100 );
    	*/
    }
    
}
