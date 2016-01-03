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
    	rootBusinessTestHelper.set(movementCollection, movementUnlimitedIdentifier,"0", null, null,"Entrée","Sortie");
    	create(movementCollection);
    	
    	movementCollection = new MovementCollection();
    	rootBusinessTestHelper.set(movementCollection, movementLimitedIdentifier, "0","0", "1000000","Dépot","Retrait");
    	create(movementCollection);
    	
    }
    
    @Override
    protected void businesses() {
    	rootBusinessTestHelper.createMovement(movementUnlimitedIdentifier, "100000", "100000");
    	rootBusinessTestHelper.createMovement(movementUnlimitedIdentifier, "100000", "200000");
    	rootBusinessTestHelper.createMovement(movementUnlimitedIdentifier, "100000", "300000");
    	rootBusinessTestHelper.createMovement(movementUnlimitedIdentifier, "-80000", "220000");
    }
    
    /* Exceptions */
    
    @Test
    public void incrementValueMustNotBeLessThanIntervalLow(){
    	rootBusinessTestHelper.incrementValueMustNotBeLessThanIntervalLow(movementLimitedIdentifier);
    }
    @Test
    public void incrementValueMustNotBeGreaterThanIntervalHigh(){
    	rootBusinessTestHelper.incrementValueMustNotBeGreaterThanIntervalHigh(movementLimitedIdentifier);
    }
    @Test
    public void decrementValueMustNotBeLessThanIntervalLow(){
    	rootBusinessTestHelper.decrementValueMustNotBeLessThanIntervalLow(movementLimitedIdentifier);
    }
    @Test
    public void decrementValueMustNotBeGreaterThanIntervalHigh(){
    	rootBusinessTestHelper.decrementValueMustNotBeGreaterThanIntervalHigh(movementLimitedIdentifier);
    }
    
    @Test
    public void collectionValueMustNotBeLessThanIntervalLow(){
    	rootBusinessTestHelper.collectionValueMustNotBeLessThanIntervalLow(movementLimitedIdentifier);
    }
    
    @Test
    public void collectionValueMustNotBeGreaterThanIntervalHigh(){
    	rootBusinessTestHelper.collectionValueMustNotBeGreaterThanIntervalHigh(movementLimitedIdentifier);
    }
    
}
