package org.cyk.system.root.business.impl.integration;

import java.math.BigDecimal;

import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.junit.Test;

public class MovementBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    private String movementUnlimitedIdentifier="MU",movementLimitedIdentifier="ML",movementOnlyUnlimitedIdentifier="MOL"
    		,movementUpdatesUnlimitedIdentifier="MUPL",movementLimitedIdentifier_Low_100_8_High_2_10_Total_0_150="ML10082100150";
    private Movement movement;
    
    @Override
    protected void populate() {
    	super.populate();
    	MovementCollection  movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementUnlimitedIdentifier, "IN", "OUT");
    	movementCollection.getInterval().getLow().setValue(null);
    	create(movementCollection);
    	
    	movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementOnlyUnlimitedIdentifier, "IN", "OUT");
    	movementCollection.getInterval().getLow().setValue(null);
    	create(movementCollection);
    	movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementUpdatesUnlimitedIdentifier, "IN", "OUT");
    	movementCollection.getInterval().getLow().setValue(null);
    	create(movementCollection);
    	
    	movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementLimitedIdentifier, "IN", "OUT");
    	movementCollection.getInterval().setHigh(new IntervalExtremity(new BigDecimal("100")));
    	create(movementCollection);
    	
    	movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementLimitedIdentifier_Low_100_8_High_2_10_Total_0_150, "IN", "OUT");
    	movementCollection.getInterval().setHigh(new IntervalExtremity(new BigDecimal("150")));
    	movementCollection.getDecrementAction().getInterval().getLow().setValue(new BigDecimal("-100"));
    	movementCollection.getDecrementAction().getInterval().getHigh().setValue(new BigDecimal("-8"));
    	movementCollection.getIncrementAction().getInterval().getLow().setValue(new BigDecimal("2"));
    	movementCollection.getIncrementAction().getInterval().getHigh().setValue(new BigDecimal("10"));
    	create(movementCollection);
    }
    
    @Override
    protected void businesses() {
    	//rootBusinessTestHelper.createMovement(movementUnlimitedIdentifier, "100000", "100000");
    	//rootBusinessTestHelper.createMovement(movementUnlimitedIdentifier, "100000", "200000");
    	//rootBusinessTestHelper.createMovement(movementUnlimitedIdentifier, "100000", "300000");
    	//rootBusinessTestHelper.createMovement(movementUnlimitedIdentifier, "-80000", "220000");
    	
    	
    }
    
    @Test
    public void doMovementsAndUpdates(){
    	movement = rootBusinessTestHelper.createMovement(movementUpdatesUnlimitedIdentifier, "15", "15");
    	movement = rootBusinessTestHelper.createMovement(movementUpdatesUnlimitedIdentifier, "10", "25");
    	movement = rootBusinessTestHelper.updateMovement(movement,"6", "21");
    	movement = rootBusinessTestHelper.createMovement(movementUpdatesUnlimitedIdentifier, "1", "22");
    	movement = rootBusinessTestHelper.updateMovement(movement, "10", "31");
    	movement = rootBusinessTestHelper.updateMovement(movement, "15", "36");
    	movement = rootBusinessTestHelper.createMovement(movementUpdatesUnlimitedIdentifier, "4", "40");
    	movement = rootBusinessTestHelper.updateMovement(movement, "-36", "0");
    	movement = rootBusinessTestHelper.updateMovement(movement, "1", "37");
    	movement = rootBusinessTestHelper.deleteMovement(movement, "1", "36");
    }
    
    @Test
    public void doMovementsOnly(){
    	rootBusinessTestHelper.createMovement(movementOnlyUnlimitedIdentifier, "1", "1");
    	rootBusinessTestHelper.createMovement(movementOnlyUnlimitedIdentifier, "1", "2");
    	rootBusinessTestHelper.createMovement(movementOnlyUnlimitedIdentifier, "1", "3");
    	rootBusinessTestHelper.createMovement(movementOnlyUnlimitedIdentifier, "10", "13");
    	rootBusinessTestHelper.createMovement(movementOnlyUnlimitedIdentifier, "13", "26");
    	rootBusinessTestHelper.createMovement(movementOnlyUnlimitedIdentifier, "-20", "6");
    	rootBusinessTestHelper.createMovement(movementOnlyUnlimitedIdentifier, "100", "106");
    	rootBusinessTestHelper.createMovement(movementOnlyUnlimitedIdentifier, "-200", "-94");
    }
    
    /* Exceptions */
    
    /*@Test
    public void incrementValueMustNotBeLessThanIntervalLow(){
    	rootBusinessTestHelper.incrementValueMustNotBeLessThanIntervalLow(movementLimitedIdentifier);
    }*/
    
    @Test
    public void incrementValueMustNotBeGreaterThanIntervalHigh(){
    	rootBusinessTestHelper.incrementValueMustNotBeGreaterThanIntervalHigh(movementLimitedIdentifier);
    }
    @Test
    public void decrementValueMustNotBeLessThanIntervalLow(){
    	rootBusinessTestHelper.decrementValueMustNotBeLessThanIntervalLow(movementLimitedIdentifier);
    }
    /*
    @Test
    public void decrementValueMustNotBeGreaterThanIntervalHigh(){
    	rootBusinessTestHelper.decrementValueMustNotBeGreaterThanIntervalHigh(movementLimitedIdentifier);
    }*/
    
    @Test
    public void collectionValueMustNotBeLessThanIntervalLow(){
    	rootBusinessTestHelper.collectionValueMustNotBeLessThanIntervalLow(movementLimitedIdentifier);
    }
    
    @Test
    public void collectionValueMustNotBeGreaterThanIntervalHigh(){
    	rootBusinessTestHelper.collectionValueMustNotBeGreaterThanIntervalHigh(movementLimitedIdentifier);
    }
    
}
