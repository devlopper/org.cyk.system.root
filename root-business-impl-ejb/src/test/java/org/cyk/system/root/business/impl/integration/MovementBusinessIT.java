package org.cyk.system.root.business.impl.integration;

import java.math.BigDecimal;

import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.utility.common.helper.RandomHelper;
import org.junit.Test;

public class MovementBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    private String movementUnlimitedIdentifier="MU",movementLimitedIdentifier="ML",movementOnlyUnlimitedIdentifier="MOL"
    		,movementUpdatesUnlimitedIdentifier="MUPL",movementLimitedIdentifier_Low_100_8_High_2_10_Total_0_150="ML10082100150";
    
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
    	movementCollection.setValue(new BigDecimal("7"));
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
    public void findPrevious(){
    	TestCase testCase = instanciateTestCase();
    	Movement movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "15",null);
    	movement.setBirthDate(date(2000, 5, 2));
    	movement = testCase.create(movement);
    	String code001 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code001, "15","15", "15");
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class,code001);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 0);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "10",null);
    	movement.setBirthDate(date(2000, 5, 1));
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code002, "10","25","25");
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code002 , code001);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 1);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "10",null);
    	movement.setBirthDate(date(2000, 5, 3));
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code003, "10","35", "35");
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code002 ,code001, code003);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 1);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code003, 2);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUnlimitedIdentifier, "10",null);
    	movement.setBirthDate(date(2000, 4, 1));
    	movement = testCase.create(movement);
    	String code001A = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code001A, "10","10", "10");
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code001A);
    	
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code002 ,code001, code003);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 1);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code003, 2);
    	
    	testCase.clean();
    }
    
    @Test
    public void doMovementsAndUpdates(){
    	TestCase testCase = instanciateTestCase();
    	Movement movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "15",null));
    	String code001 = movement.getCode();
    	testCase.read(Movement.class, code001);
    	rootBusinessTestHelper.assertMovement(code001, "15","15", "15");
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "10",null));
    	String code002 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code002, "10","25", "25");
    	
    	movement = inject(MovementDao.class).read(code002);
    	movement.setValue(new BigDecimal("6"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code002, "6","21", "21");
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "1",null));
    	String code003 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code003, "1","22", "22");
    	
    	movement = inject(MovementDao.class).read(code003);
    	movement.setValue(new BigDecimal("10"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code003, "10","31", "31");
    	
    	movement = inject(MovementDao.class).read(code003);
    	movement.setValue(new BigDecimal("15"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code003, "15","36", "36");
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "4",null));
    	String code004 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code004, "4","40", "40");
    	
    	movement = inject(MovementDao.class).read(code004);
    	movement.setValue(new BigDecimal("-36"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code004, "-36","0", "0");
    	
    	movement = inject(MovementDao.class).read(code004);
    	movement.setValue(new BigDecimal("1"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code004, "1", "37","37");
    	
    	movement = inject(MovementDao.class).read(code004);
    	testCase.delete(movement);
    	rootBusinessTestHelper.assertMovementCollection(movementUpdatesUnlimitedIdentifier, "36");
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "-6",null));
    	String code005 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code005, "-6","30", "30");
    	
    	testCase.clean();
    }
    
   @Test
   public void computeChanges(){
	   MovementCollection movementCollection = inject(MovementCollectionBusiness.class).find(movementLimitedIdentifier_Low_100_8_High_2_10_Total_0_150);
    	Movement movement = new Movement();
    	inject(MovementBusiness.class).computeChanges(movement);
    	rootBusinessTestHelper.assertComputedChanges(movement, null, null);
    	
    	movement.setCollection(movementCollection);
    	inject(MovementBusiness.class).computeChanges(movement);
    	rootBusinessTestHelper.assertComputedChanges(movement, "7", null);
    	
    	movement.setCollection(null);
    	inject(MovementBusiness.class).computeChanges(movement);
    	rootBusinessTestHelper.assertComputedChanges(movement, null, null);
    	
    	movement.setCollection(movementCollection);
    	movement.setAction(movementCollection.getIncrementAction());
    	inject(MovementBusiness.class).computeChanges(movement);
    	rootBusinessTestHelper.assertComputedChanges(movement, "7", null);
    	
    	movement.setValue(new BigDecimal("5"));
    	inject(MovementBusiness.class).computeChanges(movement);
    	rootBusinessTestHelper.assertComputedChanges(movement, "7", "12");
    	
    	movement.setValue(null);
    	inject(MovementBusiness.class).computeChanges(movement);
    	rootBusinessTestHelper.assertComputedChanges(movement, "7", null);
    	
    	movement.setValue(new BigDecimal("5"));
    	movement.setAction(null);
    	inject(MovementBusiness.class).computeChanges(movement);
    	rootBusinessTestHelper.assertComputedChanges(movement, "7", null);
    	
    	movement.setCollection(null);
    	movement.setAction(movementCollection.getIncrementAction());
    	inject(MovementBusiness.class).computeChanges(movement);
    	rootBusinessTestHelper.assertComputedChanges(movement, null, null);
    	
    	movement.setCollection(movementCollection);
    	movement.setValue(new BigDecimal("10"));
    	movement.setAction(movementCollection.getIncrementAction());
    	inject(MovementBusiness.class).computeChanges(movement);
    	rootBusinessTestHelper.assertComputedChanges(movement, "7", "17");
    	
    	movement.setAction(movementCollection.getDecrementAction());
    	inject(MovementBusiness.class).computeChanges(movement);
    	rootBusinessTestHelper.assertComputedChanges(movement, "7", "-3");
   }
    
    //@Test
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
    
    //@Test
    public void incrementValueMustNotBeGreaterThanIntervalHigh(){
    	rootBusinessTestHelper.incrementValueMustNotBeGreaterThanIntervalHigh(movementLimitedIdentifier);
    }
    
    //@Test
    public void decrementValueMustNotBeLessThanIntervalLow(){
    	rootBusinessTestHelper.decrementValueMustNotBeLessThanIntervalLow(movementLimitedIdentifier);
    }
    
    /*
    @Test
    public void decrementValueMustNotBeGreaterThanIntervalHigh(){
    	rootBusinessTestHelper.decrementValueMustNotBeGreaterThanIntervalHigh(movementLimitedIdentifier);
    }*/
    
    //@Test
    public void collectionValueMustNotBeLessThanIntervalLow(){
    	rootBusinessTestHelper.collectionValueMustNotBeLessThanIntervalLow(movementLimitedIdentifier);
    }
    
    //@Test
    public void collectionValueMustNotBeGreaterThanIntervalHigh(){
    	rootBusinessTestHelper.collectionValueMustNotBeGreaterThanIntervalHigh(movementLimitedIdentifier);
    }
    
    /**/
    
   
}
