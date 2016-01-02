package org.cyk.system.root.service.impl.integration;

import org.cyk.system.root.model.mathematics.MovementCollection;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class MovementBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    private String movementUnlimitedIdentifier="CASHIER001",movementLimitedIdentifier="CASHIER002";
    
    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    @Override
    protected void populate() {
    	super.populate();
    	MovementCollection  movementCollection = new MovementCollection();
    	rootTestHelper.set(movementCollection, movementUnlimitedIdentifier,"0", null, null);
    	create(movementCollection);
    	
    	movementCollection = new MovementCollection();
    	rootTestHelper.set(movementCollection, movementLimitedIdentifier, "0","0", "1000000");
    	create(movementCollection);
    	
    }
    
    @Override
    protected void businesses() {
    	rootTestHelper.doMovement(movementUnlimitedIdentifier, "100000", "100000");
    	rootTestHelper.doMovement(movementUnlimitedIdentifier, "100000", "200000");
    	rootTestHelper.doMovement(movementUnlimitedIdentifier, "100000", "300000");
    	rootTestHelper.doMovement(movementUnlimitedIdentifier, "80000", "220000");
    }
    
    /* Exceptions */
    
    @Test(expected=Exception.class)
    public void valueGreaterThanHigh(){
    	rootTestHelper.valueGreaterThanHigh(movementLimitedIdentifier, "3000000");
    }
    
    @Test(expected=Exception.class)
    public void valueLowerThanLow(){
    	rootTestHelper.valueLowerThanLow(movementLimitedIdentifier, "100");
    }
    
   
    

}
