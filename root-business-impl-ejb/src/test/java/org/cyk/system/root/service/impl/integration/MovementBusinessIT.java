package org.cyk.system.root.service.impl.integration;

import org.cyk.system.root.model.mathematics.MovementCollection;
import org.junit.Test;

public class MovementBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    private String movementUnlimitedIdentifier="CASHIER001",movementLimitedIdentifier="CASHIER002";
    
    @Override
    protected void populate() {
    	super.populate();
    	MovementCollection  movementCollection = new MovementCollection();
    	rootTestHelper.set(movementCollection, movementUnlimitedIdentifier,"0", null, null,"Entrée","Sortie");
    	create(movementCollection);
    	
    	movementCollection = new MovementCollection();
    	rootTestHelper.set(movementCollection, movementLimitedIdentifier, "0","0", "1000000","Dépot","Retrait");
    	create(movementCollection);
    	
    }
    
    @Override
    protected void businesses() {
    	rootTestHelper.createMovement(movementUnlimitedIdentifier, "100000", "100000");
    	rootTestHelper.createMovement(movementUnlimitedIdentifier, "100000", "200000");
    	rootTestHelper.createMovement(movementUnlimitedIdentifier, "100000", "300000");
    	rootTestHelper.createMovement(movementUnlimitedIdentifier, "-80000", "220000");
    }
    
    /* Exceptions */
    
    @Test
    public void incrementValueMustNotBeLessThanIntervalLow(){
    	rootTestHelper.incrementValueMustNotBeLessThanIntervalLow(movementLimitedIdentifier);
    }
    @Test
    public void incrementValueMustNotBeGreaterThanIntervalHigh(){
    	rootTestHelper.incrementValueMustNotBeGreaterThanIntervalHigh(movementLimitedIdentifier);
    }
    @Test
    public void decrementValueMustNotBeLessThanIntervalLow(){
    	rootTestHelper.decrementValueMustNotBeLessThanIntervalLow(movementLimitedIdentifier);
    }
    @Test
    public void decrementValueMustNotBeGreaterThanIntervalHigh(){
    	rootTestHelper.decrementValueMustNotBeGreaterThanIntervalHigh(movementLimitedIdentifier);
    }
    
    @Test
    public void valueLowerThanActionMinimum(){
    	rootTestHelper.valueLowerThanActionMinimum(movementLimitedIdentifier, "0");
    }
    
    //@Test
    public void valueGreaterThanActionMaximum(){
    	rootTestHelper.valueGreaterThanActionMaximum(movementLimitedIdentifier, "0");
    }
    
    @Test
    public void valueGreaterThanHigh(){
    	rootTestHelper.valueGreaterThanHigh(movementLimitedIdentifier, "3000000");
    }
    
    @Test
    public void valueLowerThanLow(){
    	rootTestHelper.valueLowerThanLow(movementLimitedIdentifier, "100");
    }
    
}
