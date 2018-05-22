package org.cyk.system.root.business.impl.__test__;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.mathematics.movement.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionTypeBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.RandomHelper;
import org.cyk.utility.common.helper.TimeHelper;

public class MovementTestCases extends AbstractTestCases implements Serializable {
	private static final long serialVersionUID = 1L;

    public void crudOneMovementCollectionType(){
    	TestCase testCase = instanciateOneCase();
    	MovementCollectionType movementCollectionType = inject(MovementCollectionTypeBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	assertNotNull(movementCollectionType.getInterval());
    	assertNotNull(movementCollectionType.getIncrementAction());
    	assertNotNull(movementCollectionType.getDecrementAction());
    	testCase.create(movementCollectionType);
    	testCase.clean();
    }
        
    public void crudOneMovementCollection(){
    	TestCase testCase = instanciateOneCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	assertNotNull(movementCollection.getType());
    	assertNotNull(movementCollection.getType().getInterval());
    	movementCollection.getType().getInterval().getLow().setValue(null);
    	testCase.create(movementCollection);
    	testCase.clean();
    }
        
    public void instanciateOneMovement(){
    	TestCase testCase = instanciateOneCase();
    	String collectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode));
    	testCase.assertNotNullByBusinessIdentifier(MovementCollection.class, collectionCode);
    	
    	Movement movement = testCase.instanciateOne(Movement.class,"code").setCollectionFromCode(collectionCode);
    	assertEquals("code", movement.getCode());
    	assertNotNull(movement.getCollection());
    	testCase.clean();
    }
        
    public void instanciateOneMovementInIdentifiablePeriod(){
    	final TestCase testCase = instanciateOneCase();
    	final String collectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode).setTypeFromCode(RootConstant.Code.MovementCollectionType.CASH_REGISTER));
    	testCase.assertNotNullByBusinessIdentifier(MovementCollection.class, collectionCode);
    	
    	String identifiablePeriodCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	IdentifiablePeriodCollection identifiablePeriodCollection = testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	
    	final String identifiablePeriodCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode)
    			.setBirthDate(getDate(2000, 1, 1, 0, 0)).setDeathDate(getDate(2000, 1, 1, 23, 59)).setCollectionFromCode(identifiablePeriodCollectionCode));
    	
    	testCase.computeChanges(testCase.instanciateOne(Movement.class).setCollectionFromCode(collectionCode).__setBirthDateComputedByUser__(Boolean.TRUE)
    			.setBirthDateFromString("1/1/2000 1:0").set__identifiablePeriod__fromCode(RootConstant.Code.generate(identifiablePeriodCollection, identifiablePeriodCode)));
    	
    	testCase.clean();
    }
        
    public void crudOneMovement(){
    	TestCase testCase = instanciateOneCase();
    	String movementCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode));
    	testCase.create(testCase.instanciateOne(Movement.class).setValueFromObject(1).setCollectionFromCode(movementCollectionCode));
    	testCase.clean();
    }
        
    public void crudOneMovementWhereIdentifiablePeriodIsSetToNotClosed(){
    	TestCase testCase = instanciateOneCase();
    	
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode).setTypeFromCode(RootConstant.Code.MovementCollectionType.CASH_REGISTER));
    	
    	testCase.create(new IdentifiablePeriodCollectionIdentifiableGlobalIdentifier(testCase.read(IdentifiablePeriodCollection.class
    			, RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY)
    			, testCase.read(MovementCollection.class,movementCollectionCode)));
    	
    	String identifiablePeriodCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode)
    			.setCollectionFromCode(RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY));
    	
    	Movement movement01 = testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode).__setBirthDateComputedByUser__(Boolean.TRUE)
    			.setBirthDate(testCase.getTimeAfterNowByNumberOfMinute(2)).setActionFromIncrementation(Boolean.TRUE).setValueFromObject(1)
    			.set__identifiablePeriod__fromCode(identifiablePeriodCode);
    	testCase.computeChanges(movement01);
    	assertNotNull(movement01.get__identifiablePeriod__());
    	testCase.create(movement01);
    	    	
    	testCase.clean();
    }
        
    public void crudTwoMovementsWhereIdentifiablePeriodIsSetToNotClosed(){
    	TestCase testCase = instanciateOneCase();
    	
    	/*String movementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode).setTypeFromCode(RootConstant.Code.MovementCollectionType.CASH_REGISTER));
    	
    	testCase.create(new IdentifiablePeriodCollectionIdentifiableGlobalIdentifier(testCase.read(IdentifiablePeriodCollection.class
    			, RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY)
    			, testCase.read(MovementCollection.class,movementCollectionCode)));
    	*/
    	String identifiablePeriodCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode)
    			.setCollectionFromCode(RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY));
    	
    	Movement movement01 = testCase.instanciateOne(Movement.class).setCollectionFromCode(RootConstant.Code.MovementCollection.CASH_REGISTER).__setBirthDateComputedByUser__(Boolean.TRUE)
    			.setBirthDate(testCase.getTimeAfterNowByNumberOfMinute(2)).setActionFromIncrementation(Boolean.TRUE).setValueFromObject(1)
    			.set__identifiablePeriod__fromCode(identifiablePeriodCode);
    	testCase.computeChanges(movement01);
    	assertNotNull(movement01.get__identifiablePeriod__());
    	testCase.create(movement01);
    	
    	Movement movement02 = testCase.instanciateOne(Movement.class).setCollectionFromCode(RootConstant.Code.MovementCollection.CASH_REGISTER).__setBirthDateComputedByUser__(Boolean.TRUE)
    			.setBirthDate(testCase.getTimeAfterNowByNumberOfMinute(3)).setActionFromIncrementation(Boolean.TRUE).setValueFromObject(2)
    			.set__identifiablePeriod__fromCode(identifiablePeriodCode);
    	testCase.computeChanges(movement02);
    	assertNotNull(movement02.get__identifiablePeriod__());
    	testCase.create(movement02);
    	
    	testCase.clean();
    }
     
    public void assertComputedMovementBirthDateIsIncrementedWhenSetBySystem(){
    	TestCase testCase = instanciateOneCase();
    	Movement movement = testCase.instanciateOne(Movement.class);
    	assertNull(movement.getBirthDate());
    	inject(MovementBusiness.class).computeChanges(movement);
    	Date date1 = movement.getBirthDate();
    	assertNotNull(date1);    
    	
    	TimeHelper.getInstance().pause(1000);
    	
    	inject(MovementBusiness.class).computeChanges(movement);
    	Date date2 = movement.getBirthDate();
    	assertNotNull(date2);    	
    	
    	assertEquals(Boolean.TRUE, date1.compareTo(date2) < 0);
    	
    	TimeHelper.getInstance().pause(1000);
    	
    	inject(MovementBusiness.class).computeChanges(movement);
    	date2 = movement.getBirthDate();
    	assertNotNull(date2);    	
    	
    	assertEquals(Boolean.TRUE, date1.compareTo(date2) < 0);
    	
    	testCase.clean();
    }
        
    public void assertComputedMovementBirthDateIsNotChangedBySystemWhenSetByUser(){
    	TestCase testCase = instanciateOneCase();
    	Movement movement = testCase.instanciateOne(Movement.class);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE);
    	assertNull(movement.getBirthDate());
    	inject(MovementBusiness.class).computeChanges(movement);
    	assertNull(movement.getBirthDate());
    	
    	movement.setBirthDate(getDate(2000, 1, 1));
    	
    	Date date1 = movement.getBirthDate();
    	assertEquals(getDate(2000, 1, 1), date1);    
    	
    	inject(MovementBusiness.class).computeChanges(movement);
    	Date date2 = movement.getBirthDate();
    	assertEquals(getDate(2000, 1, 1), date2);  
    	
    	assertEquals(Boolean.TRUE, date1.compareTo(date2) == 0);
    	
    	inject(MovementBusiness.class).computeChanges(movement);
    	date2 = movement.getBirthDate();
    	assertEquals(getDate(2000, 1, 1), date2);  
    	
    	assertEquals(Boolean.TRUE, date1.compareTo(date2) == 0);
    	
    	testCase.clean();
    }
        
    public void createCreateMovementsWithDateSetBySystem(){
    	TestCase testCase = instanciateOneCase();    	
    	testCase.assertCreateMovements(getDate(2000, 1, 1, 0, 0), getDate(2000, 1, 1, 23, 59), new Object[][]{
    		{"true",null,"1","1","1",new String[][] {
    			{"0","1","1","true"}
    		}}
    		,{"true",null,"2","3","2",new String[][] {
    			{"0","1","1","true"}
    			,{"1","2","3","true"}
    		}}
    		,{"true",null,"3","6","3",new String[][] {
    			{"0","1","1","true"}
    			,{"1","2","3","true"}
    			,{"2","3","6","true"}
    		}}
    	});    	  	
    	testCase.clean();
    	testCase.deleteAll(IdentifiablePeriod.class);
    }
        
    public void createCreateMovementsWithDateSetByUserAscending(){
    	TestCase testCase = instanciateOneCase();    	
    	testCase.assertCreateMovements(getDate(2000, 1, 1, 0, 0), getDate(2000, 1, 1, 23, 59), new Object[][]{
    		{"true",getDate(2000, 1, 1, 0, 5),"1","1","1",new String[][] {
    			{"0","1","1","true"}
    		}}
    		,{"true",getDate(2000, 1, 1, 0, 6),"2","3","2",new String[][] {
    			{"0","1","1","true"}
    			,{"1","2","3","true"}
    		}}
    		,{"true",getDate(2000, 1, 1, 0, 7),"3","6","3",new String[][] {
    			{"0","1","1","true"}
    			,{"1","2","3","true"}
    			,{"2","3","6","true"}
    		}}
    	});    	  	
    	testCase.clean();
    	testCase.deleteAll(IdentifiablePeriod.class);
    }
        
    public void createCreateMovementsWithDateSetByUserDescending(){
    	TestCase testCase = instanciateOneCase();    	
    	testCase.assertCreateMovements(getDate(2000, 1, 1, 0, 0), getDate(2000, 1, 1, 23, 59), new Object[][]{
    		{"true",getDate(2000, 1, 1, 0, 7),"3","3","1",new String[][] {
    			{"0","3","3","true"}
    		}}
    		,{"true",getDate(2000, 1, 1, 0, 6),"2","5","2",new String[][] {
    			{"0","2","2","true"}
    			,{"1","3","5","true"}
    		}}
    		,{"true",getDate(2000, 1, 1, 0, 5),"1","6","3",new String[][] {
    			{"0","1","1","true"}
    			,{"1","2","3","true"}
    			,{"2","3","6","true"}
    		}}
    	});    	  	  	
    	testCase.clean();
    	testCase.deleteAll(IdentifiablePeriod.class);
    }
        
    public void createCreateMovementsWithDateFirstSpecifiedAncestorSecondNotSpecifiedNewest(){
    	TestCase testCase = instanciateOneCase();    	
    	testCase.assertCreateMovements(getDate(2000, 1, 1, 0, 0), getDate(2000, 1, 1, 23, 59), new Object[][]{
    		{"true",getDate(2000, 1, 1, 0, 5),"1","1","1",new String[][] {
    			{"0","1","1","true"}
    		}}
    		,{"true",null,"2","3","2",new String[][] {
    			{"0","1","1","true"}
    			,{"1","2","3","true"}
    		}}
    	});    	  	
    	testCase.clean();
    	testCase.deleteAll(IdentifiablePeriod.class);
    }
        
    public void createCreateMovementsWithDateFirstSpecifiedNewestSecondNotSpecifiedAncestor(){
    	TestCase testCase = instanciateOneCase();    	
    	testCase.assertCreateMovements(getDate(2000, 1, 1, 0, 0), getDate(2000, 1, 1, 23, 59), new Object[][]{
    		{"true",new Date(System.currentTimeMillis()+1000*60*5),"1","1","1",new String[][] {
    			{"0","1","1","true"}
    		}}
    		,{"true",null,"2","3","2",new String[][] {
    			{"0","2","2","true"}
    			,{"1","1","3","true"}
    		}}
    	});    	  	
    	testCase.clean();
    	testCase.deleteAll(IdentifiablePeriod.class);
    }
        
    public void createCreateMovementsWithDateFirstNotSpecifiedAncestorSecondSpecifiedNewest(){
    	TestCase testCase = instanciateOneCase();    	
    	testCase.assertCreateMovements(getDate(2000, 1, 1, 0, 0), getDate(2000, 1, 1, 23, 59), new Object[][]{
    		{"true",null,"1","1","1",new String[][] {
    			{"0","1","1","true"}
    		}}
    		,{"true",new Date(System.currentTimeMillis()+1000*60*5),"2","3","2",new String[][] {
    			{"0","1","1","true"}
    			,{"1","2","3","true"}
    		}}
    	});    	  	
    	testCase.clean();
    	testCase.deleteAll(IdentifiablePeriod.class);
    }
        
    public void createCreateMovementsWithDateFirstNotSpecifiedNewestSecondSpecifiedAncestor(){
    	TestCase testCase = instanciateOneCase();    	
    	testCase.assertCreateMovements(getDate(2000, 1, 1, 0, 0), getDate(2000, 1, 1, 23, 59), new Object[][]{
    		{"true",null,"1","1","1",new String[][] {
    			{"0","1","1","true"}
    		}}
    		,{"true",getDate(2000, 1, 1, 0, 6),"2","3","2",new String[][] {
    			{"0","2","2","true"}
    			,{"1","1","3","true"}
    		}}
    	});    	  	
    	testCase.clean();
    	testCase.deleteAll(IdentifiablePeriod.class);
    }
        
    public void crudOneMovementWithSpecifiedDate(){
    	TestCase testCase = instanciateOneCase();
    	String movementCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode));
    	
    	String identifiablePeriodCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	
    	IdentifiablePeriod identifiablePeriod = testCase.instanciateOne(IdentifiablePeriod.class).setCollectionFromCode(identifiablePeriodCollectionCode);
		identifiablePeriod.setCode(testCase.getRandomHelper().getAlphabetic(5));
		identifiablePeriod.setBirthDate(getDate(2000, 1, 1, 0, 0));
		identifiablePeriod.setDeathDate(getDate(2000, 1, 1, 23, 59));
		testCase.create(identifiablePeriod);
		
    	Movement movement = testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode);
    	movement.setValue(BigDecimal.ONE).set__identifiablePeriod__(identifiablePeriod).setBirthDate(getDate(2000, 1, 1, 0, 5));
    	testCase.create(movement);
    	testCase.assertMovement(movement.getCode(), "1", "1");
    	testCase.clean();
    }
        
    public void crudOneMovementWithoutSpecifiedDate(){
    	TestCase testCase = instanciateOneCase();
    	String movementCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode).setValue(BigDecimal.ZERO));
    	
    	String identifiablePeriodCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	
    	IdentifiablePeriod identifiablePeriod = testCase.instanciateOne(IdentifiablePeriod.class);
		identifiablePeriod.setCode(testCase.getRandomHelper().getAlphabetic(5));
		identifiablePeriod.setBirthDate(getDate(2000, 1, 1, 0, 0));
		identifiablePeriod.setDeathDate(getDate(2000, 1, 1, 23, 59)).setCollectionFromCode(identifiablePeriodCollectionCode);
		testCase.create(identifiablePeriod);
		
    	Movement movement = testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode);
    	movement.setValue(BigDecimal.ONE).set__identifiablePeriod__(identifiablePeriod);
    	testCase.create(movement);
    	movement = testCase.read(Movement.class, movement.getCode());
    	assertNotNull(movement.getBirthDate());
    	testCase.assertMovement(movement.getCode(), "1", "1");
    	testCase.clean();
    }
        
    public void crudOneMovementCollectionAndMovements(){
    	TestCase testCase = instanciateOneCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	Movement movement = inject(MovementBusiness.class).instanciateOne(movementCollection);
    	movement.setCode(RandomHelper.getInstance().getAlphabetic(3));
    	movement.setValue(new BigDecimal("15"));
    	movement.setAction(movementCollection.getType().getIncrementAction());
    	movementCollection.getItems().setSynchonizationEnabled(Boolean.TRUE);
    	testCase.create(movementCollection);
    	assertEquals(1, inject(MovementDao.class).readByCollection(movementCollection).size());
    	
    	movementCollection = testCase.read(MovementCollection.class, movementCollection.getCode());
    	movementCollection.getItems().addMany(inject(MovementDao.class).readByCollection(movementCollection));
    	movementCollection.getItems().setSynchonizationEnabled(Boolean.TRUE);
    	testCase.update(movementCollection);
    	assertEquals(1, inject(MovementDao.class).readByCollection(movementCollection).size());
    	
    	testCase.clean();
    }
        
    public void useValueAbsolute(){
    	TestCase testCase = instanciateOneCase();
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementCollectionCode);
    	movementCollection.setValue(new BigDecimal("0"));
    	testCase.create(movementCollection);
    	
    	Movement movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), null,Boolean.TRUE);
    	movement.setValueAbsolute(new BigDecimal("15"));
    	movement.setValueSettableFromAbsolute(Boolean.TRUE);
    	testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "15", "1").assertMovement(code001, "15","15",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), null,Boolean.TRUE);
    	movement.setValueAbsolute(new BigDecimal("10"));
    	movement.setValueSettableFromAbsolute(Boolean.TRUE);
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "25", "2").assertMovement(code002, "10","25",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), null,Boolean.FALSE);
    	movement.setValueAbsolute(new BigDecimal("7"));
    	movement.setValueSettableFromAbsolute(Boolean.TRUE);
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "18", "3").assertMovement(code003, "-7","18",Boolean.FALSE);
    	
    	testCase.clean();
    }
        
    public void useNullAction(){
    	TestCase testCase = instanciateOneCase();
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementCollectionCode);
    	movementCollection.setValue(new BigDecimal("0"));
    	testCase.create(movementCollection);
    	
    	Movement movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "15",null);
    	testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.read(Movement.class, code001);
    	testCase.assertMovementCollection(movementCollectionCode, "15", "1").assertMovement(code001, "15","15",null);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "10",null);
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "25", "2").assertMovement(code002, "10","25",null);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "-7",null);
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "18", "3").assertMovement(code003, "-7","18",null);
    	
    	testCase.clean();
    }
        
    public void addSequenceAscending(){
    	TestCase testCase = instanciateOneCase();
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementCollectionCode);
    	movementCollection.setValue(new BigDecimal("0"));
    	testCase.create(movementCollection);
    	
    	Movement movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "15",Boolean.TRUE);
    	testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.read(Movement.class, code001);
    	testCase.assertMovementCollection(movementCollectionCode, "15", "1")
			.assertMovement(code001, "15","15",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "10",Boolean.TRUE);
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "25", "2")
			.assertMovement(code002, "10","25",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "7",Boolean.TRUE);
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "32", "3")
			.assertMovement(code003, "7","32",Boolean.TRUE);
    	
    	testCase.clean();
    }
        
    public void addSequenceDescending(){
    	TestCase testCase = instanciateOneCase();
    	MovementCollection movementCollection = testCase.instanciateOneWithRandomIdentifier(MovementCollection.class);
    	String movementCollectionCode = movementCollection.getCode();
    	testCase.create(movementCollection);
    	
    	String code001 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(Movement.class,code001).__set__(movementCollectionCode,Boolean.TRUE,15,Boolean.TRUE,"30/1/2000"));
    	testCase.assertMovementCollection(movementCollectionCode, "15", "1").assertMovements(movementCollectionCode
    			, new String[]{"0","15","15","true"}
    	);
    	
    	testCase.create(testCase.instanciateOne(Movement.class).__set__(movementCollectionCode,Boolean.TRUE,10,Boolean.TRUE,"29/1/2000"));
    	testCase.assertMovementCollection(movementCollectionCode, "25", "2").assertMovements(movementCollectionCode
    			, new String[]{"0","10","10","true"}
    			, new String[]{"1","15","25","true"}
    	);
    	
    	testCase.create(testCase.instanciateOne(Movement.class).__set__(movementCollectionCode,Boolean.TRUE,7,Boolean.TRUE,"28/1/2000"));
    	testCase.assertMovementCollection(movementCollectionCode, "32", "3").assertMovements(movementCollectionCode
    			, new String[]{"0","7","7","true"}
    			, new String[]{"1","10","17","true"}
    			, new String[]{"2","15","32","true"}
    	);
    	
    	String code004 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(Movement.class,code004).__set__(movementCollectionCode,Boolean.FALSE,-7,Boolean.TRUE,"27/1/2000"));
    	testCase.assertMovementCollection(movementCollectionCode, "25", "4").assertMovements(movementCollectionCode
    			, new String[]{"0","-7","-7","false"}
    			, new String[]{"1","7","0","true"}
    			, new String[]{"2","10","10","true"}
    			, new String[]{"3","15","25","true"}
    	);
    	
    	testCase.create(testCase.instanciateOne(Movement.class).__set__(movementCollectionCode,Boolean.TRUE,20,Boolean.TRUE,"26/1/2000"));
    	testCase.assertMovementCollection(movementCollectionCode, "45", "5").assertMovements(movementCollectionCode
    			, new String[]{"0","20","20","true"}
    			, new String[]{"1","-7","13","false"}
    			, new String[]{"2","7","20","true"}
    			, new String[]{"3","10","30","true"}
    			, new String[]{"4","15","45","true"}
    	);
    	
    	String code007 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(Movement.class,code007).__set__(movementCollectionCode,Boolean.TRUE,8,Boolean.TRUE,"24/1/2000"));
    	testCase.assertMovementCollection(movementCollectionCode, "53", "6").assertMovements(movementCollectionCode
    			, new String[]{"0","8","8","true"}
    			, new String[]{"1","20","28","true"}
    			, new String[]{"2","-7","21","false"}
    			, new String[]{"3","7","28","true"}
    			, new String[]{"4","10","38","true"}
    			, new String[]{"5","15","53","true"}
    	);
    	
    	testCase.create(testCase.instanciateOne(Movement.class).__set__(movementCollectionCode,Boolean.FALSE,-3,Boolean.TRUE,"25/1/2000"));
    	testCase.assertMovementCollection(movementCollectionCode, "50", "7").assertMovements(movementCollectionCode
    			, new String[]{"0","8","8","true"}
    			, new String[]{"1","-3","5","false"}
    			, new String[]{"2","20","25","true"}
    			, new String[]{"3","-7","18","false"}
    			, new String[]{"4","7","25","true"}
    			, new String[]{"5","10","35","true"}
    			, new String[]{"6","15","50","true"}
    	);
    	
    	testCase.update(testCase.read(Movement.class, RootConstant.Code.generate(movementCollection, code001)).setValueFromObject(16));
    	testCase.assertMovementCollection(movementCollectionCode, "51", "7").assertMovements(movementCollectionCode
    			, new String[]{"0","8","8","true"}
    			, new String[]{"1","-3","5","false"}
    			, new String[]{"2","20","25","true"}
    			, new String[]{"3","-7","18","false"}
    			, new String[]{"4","7","25","true"}
    			, new String[]{"5","10","35","true"}
    			, new String[]{"6","16","51","true"}
    	);
    	
    	testCase.update(testCase.read(Movement.class, RootConstant.Code.generate(movementCollection, code007)).setValueFromObject(17));
    	testCase.assertMovementCollection(movementCollectionCode, "60", "7").assertMovements(movementCollectionCode
    			, new String[]{"0","17","17","true"}
    			, new String[]{"1","-3","14","false"}
    			, new String[]{"2","20","34","true"}
    			, new String[]{"3","-7","27","false"}
    			, new String[]{"4","7","34","true"}
    			, new String[]{"5","10","44","true"}
    			, new String[]{"6","16","60","true"}
    	);
    	
    	testCase.update(testCase.read(Movement.class, RootConstant.Code.generate(movementCollection, code004)).setValueFromObject(-13));
    	testCase.assertMovementCollection(movementCollectionCode, "54", "7").assertMovements(movementCollectionCode
    			, new String[]{"0","17","17","true"}
    			, new String[]{"1","-3","14","false"}
    			, new String[]{"2","20","34","true"}
    			, new String[]{"3","-13","21","false"}
    			, new String[]{"4","7","28","true"}
    			, new String[]{"5","10","38","true"}
    			, new String[]{"6","16","54","true"}
    	);
    	
    	testCase.deleteByCode(Movement.class, RootConstant.Code.generate(movementCollection, code007));
    	testCase.assertMovementCollection(movementCollectionCode, "37", "6").assertMovements(movementCollectionCode
    			, new String[]{"0","-3","-3","false"}
    			, new String[]{"1","20","17","true"}
    			, new String[]{"2","-13","4","false"}
    			, new String[]{"3","7","11","true"}
    			, new String[]{"4","10","21","true"}
    			, new String[]{"5","16","37","true"}
    	);
    	
    	testCase.deleteByCode(Movement.class, RootConstant.Code.generate(movementCollection, code001));
    	testCase.assertMovementCollection(movementCollectionCode, "21", "5").assertMovements(movementCollectionCode
    			, new String[]{"0","-3","-3","false"}
    			, new String[]{"1","20","17","true"}
    			, new String[]{"2","-13","4","false"}
    			, new String[]{"3","7","11","true"}
    			, new String[]{"4","10","21","true"}
    	);
    	
    	testCase.deleteByCode(Movement.class, RootConstant.Code.generate(movementCollection, code004));
    	testCase.assertMovementCollection(movementCollectionCode, "34", "4").assertMovements(movementCollectionCode
    			, new String[]{"0","-3","-3","false"}
    			, new String[]{"1","20","17","true"}
    			, new String[]{"2","7","24","true"}
    			, new String[]{"3","10","34","true"}
    	);
    	
    	testCase.clean();
    }
        
    public void findPrevious(){
    	TestCase testCase = instanciateOneCase();
    	String movementUpdatesUnlimitedIdentifier = testCase.getRandomAlphabetic();
    	MovementCollection movementUpdatesUnlimited = inject(MovementCollectionBusiness.class).instanciateOne(movementUpdatesUnlimitedIdentifier);
    	movementUpdatesUnlimited.setValue(new BigDecimal("0"));
    	testCase.create(movementUpdatesUnlimited);
    	
    	String movementUnlimitedIdentifier = testCase.getRandomAlphabetic();
    	MovementCollection movementUnlimited = inject(MovementCollectionBusiness.class).instanciateOne(movementUnlimitedIdentifier);
    	movementUnlimited.setValue(new BigDecimal("0"));
    	testCase.create(movementUnlimited);
    	
    	Movement movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "15",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(getDate(2000, 5, 2));
    	movement = testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.assertMovement(code001, "15","15",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class,code001);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 0);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(getDate(2000, 5, 3));
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,movement, 1);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(getDate(2000, 5, 1));
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	testCase.assertMovement(code002, "10","10",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code002 , code001);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 1);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(getDate(2000, 5, 3));
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	testCase.assertMovement(code003, "10","35",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code002 ,code001, code003);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 1);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code003, 2);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(getDate(2000, 4, 1));
    	movement = testCase.create(movement);
    	String code001A = movement.getCode();
    	testCase.assertMovement(code001A, "10","10",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code001A);
    	
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code002 ,code001, code003);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 1);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code003, 2);
    	
    	testCase.clean();
    }
    
    public void doMovementsAndUpdates(){
    	TestCase testCase = instanciateOneCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	movementCollection.setValue(new BigDecimal("0"));
    	testCase.create(movementCollection);
    	
    	Movement movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "15",Boolean.TRUE));
    	String code001 = movement.getCode();
    	testCase.read(Movement.class, code001);
    	testCase.assertMovement(code001, "15","15",Boolean.TRUE);
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "10",Boolean.TRUE));
    	String code002 = movement.getCode();
    	testCase.assertMovement(code002, "10","25",Boolean.TRUE);
    	
    	movement = inject(MovementDao.class).read(code002);
    	movement.setValue(new BigDecimal("6"));
    	testCase.update(movement);
    	testCase.assertMovement(code002, "6","21",Boolean.TRUE);
    	
  
    	movement = inject(MovementDao.class).read(code001);
    	movement.setValue(new BigDecimal("10"));
    	testCase.update(movement);
    	testCase.assertMovement(code001, "10","10",Boolean.TRUE);
    	
    	movement = inject(MovementDao.class).read(code001);
    	movement.setValue(new BigDecimal("15"));
    	testCase.update(movement);
    	testCase.assertMovement(code001, "15","15",Boolean.TRUE);
    	
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "1",Boolean.TRUE));
    	String code003 = movement.getCode();
    	testCase.assertMovement(code003, "1","22",Boolean.TRUE);
    	
    	movement = inject(MovementDao.class).read(code003);
    	movement.setValue(new BigDecimal("10"));
    	testCase.update(movement);
    	testCase.assertMovement(code003, "10","31",Boolean.TRUE);
    	
    	movement = inject(MovementDao.class).read(code003);
    	movement.setValue(new BigDecimal("15"));
    	testCase.update(movement);
    	testCase.assertMovement(code003, "15","36",Boolean.TRUE);
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "4",Boolean.TRUE));
    	String code004 = movement.getCode();
    	testCase.assertMovement(code004, "4","40",Boolean.TRUE);
    	
    	movement = inject(MovementDao.class).read(code004);
    	movement.setAction(movement.getCollection().getType().getDecrementAction());
    	movement.setValue(new BigDecimal("-36"));
    	testCase.update(movement);
    	testCase.assertMovement(code004, "-36","0",Boolean.FALSE);
    	
    	movement = inject(MovementDao.class).read(code004);
    	movement.setAction(movement.getCollection().getType().getIncrementAction());
    	movement.setValue(new BigDecimal("1"));
    	testCase.update(movement);
    	testCase.assertMovement(code004, "1", "37",Boolean.TRUE);
    	
    	movement = inject(MovementDao.class).read(code004);
    	testCase.delete(movement);
    	testCase.assertMovementCollection(movementCollection.getCode(), "36","3");
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "-6",Boolean.FALSE));
    	String code005 = movement.getCode();
    	testCase.assertMovement(code005, "-6","30",Boolean.FALSE);
    	
    	testCase.clean();
    }
    
    public void filter(){
    	TestCase testCase = instanciateOneCase();
    	MovementCollection movementCollection01 = inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	testCase.create(movementCollection01);
    	
    	MovementCollection movementCollection02 = inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	testCase.create(movementCollection02);
    	
    	MovementCollection movementCollection03 = inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	testCase.create(movementCollection03);
    	
    	testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection01.getCode(), "15",Boolean.TRUE)
    			.setCode("MyCode001"));
    	testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection01.getCode(), "-15",Boolean.FALSE)
    			.setCode("MyCodeABC"));
    	testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection01.getCode(), "15",Boolean.TRUE)
    			.setCode("MyCodeAB001"));
    	
    	testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection03.getCode(), "-15",Boolean.FALSE)
    			.setCode("MyCode001"));
    	testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection03.getCode(), "15",Boolean.TRUE)
    			.setCode("MyCode002"));
    	
    	Movement.Filter filter = new Movement.Filter();
    	assertEquals(5, inject(MovementBusiness.class).findByFilter(filter, new DataReadConfiguration()).size());
    	
    	filter(new Object[][]{
    		{null,"MyCode001",2},{null,"MyCode002",1},{null,"MyCode00",3},{null,"MyCode",5},{null,"001",3}
    	});
    	
    	filter(new Object[][]{
    		{Arrays.asList(movementCollection03),"MyCode001",1},{Arrays.asList(movementCollection03),"MyCode002",1},{Arrays.asList(movementCollection03),"MyCode00",2}
    	});
    	
    	filter(new Object[][]{
    		{Arrays.asList(movementCollection01),null,3},{Arrays.asList(movementCollection02),null,0},{Arrays.asList(movementCollection03),null,2}
    	});
    	
    	filter(new Object[][]{
    		{Arrays.asList(movementCollection01,movementCollection02),null,3},{Arrays.asList(movementCollection01,movementCollection03),null,5}
    		,{Arrays.asList(movementCollection02,movementCollection03),null,2}
    	});
    	
    	filter(new Object[][]{
    		{Arrays.asList(movementCollection01,movementCollection02,movementCollection03),null,5}
    	});
    	
    	filter(new Object[][]{
    		{Arrays.asList(movementCollection01.getType().getIncrementAction()),null,3},{Arrays.asList(movementCollection01.getType().getDecrementAction()),null,2}
    		,{Arrays.asList(movementCollection01.getType().getIncrementAction(),movementCollection01.getType().getDecrementAction()),null,5}
    	});
    	
    	filter(new Object[][]{
    		{Arrays.asList(movementCollection02.getType().getIncrementAction()),null,3},{Arrays.asList(movementCollection02.getType().getDecrementAction()),null,2}
    		,{Arrays.asList(movementCollection02.getType().getIncrementAction(),movementCollection02.getType().getDecrementAction()),null,5}
    	});
    	
    	filter(new Object[][]{
    		{Arrays.asList(movementCollection03.getType().getIncrementAction()),null,3},{Arrays.asList(movementCollection03.getType().getDecrementAction()),null,2}
    		,{Arrays.asList(movementCollection03.getType().getIncrementAction(),movementCollection03.getType().getDecrementAction()),null,5}
    	});
    	
    	filter(new Object[][]{
    		{Arrays.asList(movementCollection01,movementCollection01.getType().getIncrementAction()),null,2}
    		,{Arrays.asList(movementCollection02,movementCollection02.getType().getIncrementAction()),null,0}
    		,{Arrays.asList(movementCollection03,movementCollection03.getType().getIncrementAction()),null,1}
    		,{Arrays.asList(movementCollection01.getType().getIncrementAction(),movementCollection02.getType().getIncrementAction(),movementCollection03.getType().getIncrementAction()),null,3}
    		,{Arrays.asList(movementCollection01.getType().getDecrementAction(),movementCollection02.getType().getDecrementAction(),movementCollection03.getType().getDecrementAction()),null,2}
    	});
    }
    
    public void crudOneMovementCollectionIdentifiableGlobalIdentifier(){
    	TestCase testCase = instanciateOneCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	movementCollection.setValue(new BigDecimal("0"));
    	testCase.create(movementCollection);
    	
    	Sex sex = new Sex();
    	String sexCode = testCase.getRandomAlphabetic();
    	sex.setCode(sexCode);
    	testCase.create(sex);
    	
    	MovementCollectionIdentifiableGlobalIdentifier movementCollectionIdentifiableGlobalIdentifier 
    		= inject(MovementCollectionIdentifiableGlobalIdentifierBusiness.class).instanciateOne();
    	
    	movementCollectionIdentifiableGlobalIdentifier.setMovementCollection(movementCollection);
    	movementCollectionIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifier(sex.getGlobalIdentifier());
    	testCase.create(movementCollectionIdentifiableGlobalIdentifier);
    	
    	testCase.clean();
    }
    
    @SuppressWarnings("unchecked")
	private void filter(Object[][] values){
    	for(Object[] index : values){
    		Movement.Filter filter = new Movement.Filter();
    		if(index[0]!=null)
    			filter.addMasters((Collection<Object>)index[0]);
    		filter.use((String)index[1]);
        	assertEquals((Integer)index[2], inject(MovementBusiness.class).findByFilter(filter, new DataReadConfiguration()).size());
    	}
    }
    
	public void computeChanges() {
		TestCase testCase = instanciateOneCase();
		
		String movementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(inject(MovementCollectionBusiness.class).instanciateOne(movementCollectionCode).setValue(new BigDecimal(7)));
    	
		MovementCollection movementCollection = inject(MovementCollectionBusiness.class).find(movementCollectionCode);
		Movement movement = new Movement();
		inject(MovementBusiness.class).computeChanges(movement);
		testCase.assertComputedChanges(movement, null, null);

		movement.setCollection(movementCollection);
		inject(MovementBusiness.class).computeChanges(movement);
		testCase.assertComputedChanges(movement, "7", null);

		movement.setCollection(null);
		inject(MovementBusiness.class).computeChanges(movement);
		testCase.assertComputedChanges(movement, null, null);

		movement.setCollection(movementCollection);
		movement.setAction(movementCollection.getType().getIncrementAction());
		inject(MovementBusiness.class).computeChanges(movement);
		testCase.assertComputedChanges(movement, "7", null);

		movement.setValue(new BigDecimal("5"));
		inject(MovementBusiness.class).computeChanges(movement);
		testCase.assertComputedChanges(movement, "7", "12");

		movement.setValue(null);
		inject(MovementBusiness.class).computeChanges(movement);
		testCase.assertComputedChanges(movement, "7", null);

		movement.setValue(new BigDecimal("5"));
		movement.setAction(null);
		inject(MovementBusiness.class).computeChanges(movement);
		testCase.assertComputedChanges(movement, "7", "12");

		movement.setCollection(null);
		movement.setAction(movementCollection.getType().getIncrementAction());
		inject(MovementBusiness.class).computeChanges(movement);
		testCase.assertComputedChanges(movement, null, null);

		movement.setCollection(movementCollection);
		movement.setValue(new BigDecimal("10"));
		movement.setAction(movementCollection.getType().getIncrementAction());
		inject(MovementBusiness.class).computeChanges(movement);
		testCase.assertComputedChanges(movement, "7", "17");

		movement.setValue(new BigDecimal("-10"));
		movement.setAction(movementCollection.getType().getDecrementAction());
		inject(MovementBusiness.class).computeChanges(movement);
		testCase.assertComputedChanges(movement, "7", "-3");
		
		testCase.clean();
	}
    
    public void crudMovementsWithDestination(){
    	TestCase testCase = instanciateOneCase();
    	String invoiceMovementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(inject(MovementCollectionBusiness.class).instanciateOne(invoiceMovementCollectionCode).setValue(new BigDecimal("100")));
    	String cashRegisterMovementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(inject(MovementCollectionBusiness.class).instanciateOne(cashRegisterMovementCollectionCode).setValue(new BigDecimal("0")));
    	
    	Movement movement = testCase.instanciateOne(Movement.class).__set__(invoiceMovementCollectionCode, Boolean.FALSE, -15, null, null);
    	movement.setDestinationMovementCollection(inject(MovementCollectionDao.class).read(cashRegisterMovementCollectionCode));
    	testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.read(Movement.class, code001);
    	testCase.assertMovement(code001, "-15","85",Boolean.FALSE);
    	testCase.assertMovementCollection(cashRegisterMovementCollectionCode, "15","1");
    	
    	movement = testCase.instanciateOne(Movement.class).__set__(invoiceMovementCollectionCode, Boolean.FALSE, -10, null, null);
    	movement.setDestinationMovementCollection(inject(MovementCollectionDao.class).read(cashRegisterMovementCollectionCode));
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	testCase.assertMovement(code002, "-10","75",Boolean.FALSE);
    	testCase.assertMovementCollection(cashRegisterMovementCollectionCode, "25","2");
    	
    	movement = testCase.instanciateOne(Movement.class).__set__(invoiceMovementCollectionCode, Boolean.FALSE, -7, null, null);
    	movement.setDestinationMovementCollection(inject(MovementCollectionDao.class).read(cashRegisterMovementCollectionCode));
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	testCase.assertMovement(code003, "-7","68",Boolean.FALSE);
    	testCase.assertMovementCollection(cashRegisterMovementCollectionCode, "32","3");
    	
    	testCase.clean();
    	
    }
	
    public void createOneMovementAndOneChildrenWithDateSetByUser(){
		TestCase testCase = instanciateOneCase();
		
    	String identifiablePeriodCode = testCase.getRandomAlphabetic();
		testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode)
				.setBirthDate(getDate(2000, 1, 1, 0, 0)).setDeathDate(getDate(2000, 1, 1, 23, 59)).setCollectionFromCode(RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY));
		
		String saleMovementCollectionCode = testCase.getRandomAlphabetic();
		MovementCollection saleMovementCollection = testCase.create(testCase.instanciateOne(MovementCollection.class,saleMovementCollectionCode).setValueFromObject(1000));
		MovementCollection cashRegisterMovementCollection = testCase.read(MovementCollection.class, RootConstant.Code.MovementCollection.CASH_REGISTER);
		
		String cashRegisterMovementCode = testCase.getRandomAlphabetic();
		Movement cashRegisterMovement = testCase.instanciateOne(Movement.class,cashRegisterMovementCode).setCollectionFromCode(RootConstant.Code.MovementCollection.CASH_REGISTER)
				.setActionFromIncrementation(Boolean.TRUE).setValueFromObject(100).__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDateFromString("1/1/2000 0:5")
				.setParentActionIsOppositeOfChildAction(Boolean.TRUE).set__identifiablePeriod__fromCode(identifiablePeriodCode);
		
		String saleMovementCode = testCase.getRandomAlphabetic();
		Movement movement = testCase.instanciateOne(Movement.class,saleMovementCode).setCollectionFromCode(saleMovementCollectionCode)
				.__setBirthDateComputedByUser__(Boolean.TRUE).setValueSettableFromAbsolute(Boolean.TRUE).setValueAbsoluteFromObject(100)
				.setParent(cashRegisterMovement);
		
		cashRegisterMovement.addIdentifiables(movement);
		testCase.create(cashRegisterMovement);
		
		testCase.assertNotNullByBusinessIdentifier(Movement.class, RootConstant.Code.generate(cashRegisterMovementCollection,cashRegisterMovementCode)
				,RootConstant.Code.generate(saleMovementCollection,saleMovementCode));
		testCase.assertMovement(RootConstant.Code.generate(cashRegisterMovementCollection,cashRegisterMovementCode), "100", "100",Boolean.TRUE);
		testCase.assertMovement(RootConstant.Code.generate(saleMovementCollection,saleMovementCode), "-100", "900",Boolean.FALSE);
		testCase.clean();
    }
	
    public void createOneMovementAndOneChildrenWithDateSetBySystem(){
		TestCase testCase = instanciateOneCase();
		
    	String identifiablePeriodCode = testCase.getRandomAlphabetic();
		testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode)
				.setCollectionFromCode(RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY));
		
		String saleMovementCollectionCode = testCase.getRandomAlphabetic();
		MovementCollection saleMovementCollection = testCase.create(testCase.instanciateOne(MovementCollection.class,saleMovementCollectionCode).setValueFromObject(1000));
		MovementCollection cashRegisterMovementCollection = testCase.read(MovementCollection.class, RootConstant.Code.MovementCollection.CASH_REGISTER);
		
		String cashRegisterMovementCode = testCase.getRandomAlphabetic();
		Movement cashRegisterMovement = testCase.instanciateOne(Movement.class,cashRegisterMovementCode).setCollectionFromCode(RootConstant.Code.MovementCollection.CASH_REGISTER)
				.setActionFromIncrementation(Boolean.TRUE).setValueFromObject(100).__setBirthDateComputedByUser__(Boolean.FALSE)
				.setParentActionIsOppositeOfChildAction(Boolean.TRUE).set__identifiablePeriod__fromCode(identifiablePeriodCode);
		
		String saleMovementCode = testCase.getRandomAlphabetic();
		Movement movement = testCase.instanciateOne(Movement.class,saleMovementCode).setCollectionFromCode(saleMovementCollectionCode)
				.__setBirthDateComputedByUser__(Boolean.TRUE).setValueSettableFromAbsolute(Boolean.TRUE).setValueAbsoluteFromObject(100)
				.setParent(cashRegisterMovement);
		
		cashRegisterMovement.addIdentifiables(movement);
		testCase.create(cashRegisterMovement);
		
		testCase.assertNotNullByBusinessIdentifier(Movement.class, RootConstant.Code.generate(cashRegisterMovementCollection,cashRegisterMovementCode)
				,RootConstant.Code.generate(saleMovementCollection,saleMovementCode));
		testCase.assertMovement(RootConstant.Code.generate(cashRegisterMovementCollection,cashRegisterMovementCode), "100", "100",Boolean.TRUE);
		testCase.assertMovement(RootConstant.Code.generate(saleMovementCollection,saleMovementCode), "-100", "900",Boolean.FALSE);
		testCase.clean();
    }
	
    public void createOneMovementAndOneChildren(){
		TestCase testCase = instanciateOneCase();
		
    	String identifiablePeriodCode = testCase.getRandomAlphabetic();
		testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode)
				.setCollectionFromCode(RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY));
		
		String saleMovementCollectionCode = testCase.getRandomAlphabetic();
		MovementCollection saleMovementCollection = testCase.create(testCase.instanciateOne(MovementCollection.class,saleMovementCollectionCode).setValueFromObject(1000));
		MovementCollection cashRegisterMovementCollection = testCase.read(MovementCollection.class, RootConstant.Code.MovementCollection.CASH_REGISTER);
		
		String cashRegisterMovementCode = testCase.getRandomAlphabetic();
		Movement cashRegisterMovement = testCase.instanciateOne(Movement.class,cashRegisterMovementCode).setCollectionFromCode(RootConstant.Code.MovementCollection.CASH_REGISTER)
				.setActionFromIncrementation(Boolean.TRUE).setValueFromObject(100)
				.setParentActionIsOppositeOfChildAction(Boolean.TRUE).set__identifiablePeriod__fromCode(identifiablePeriodCode);
		
		String saleMovementCode = testCase.getRandomAlphabetic();
		cashRegisterMovement.addIdentifiables(testCase.instanciateOne(Movement.class,saleMovementCode).setCollectionFromCode(saleMovementCollectionCode)
				.__setBirthDateComputedByUser__(Boolean.TRUE).setValueSettableFromAbsolute(Boolean.TRUE).setValueAbsoluteFromObject(100)
				.setParent(cashRegisterMovement));
		testCase.create(cashRegisterMovement);
		
		testCase.assertNotNullByBusinessIdentifier(Movement.class, RootConstant.Code.generate(cashRegisterMovementCollection,cashRegisterMovementCode)
				,RootConstant.Code.generate(saleMovementCollection,saleMovementCode));
		testCase.assertMovement(RootConstant.Code.generate(cashRegisterMovementCollection,cashRegisterMovementCode), "100", "100",Boolean.TRUE);
		testCase.assertMovement(RootConstant.Code.generate(saleMovementCollection,saleMovementCode), "-100", "900",Boolean.FALSE);
		testCase.clean();
    }
	
    public void createOneMovementAndManyChildren(){
		TestCase testCase = instanciateOneCase();
		
		String movementCollectionCode = testCase.getRandomAlphabetic();
		testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode).setValueFromObject(1000));
		
		String identifiablePeriodCode = testCase.getRandomAlphabetic();
		testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode)
				.setCollectionFromCode(RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY));
		
		String parentCode = testCase.getRandomAlphabetic();
		Movement parent = testCase.instanciateOne(Movement.class,parentCode).setCollectionFromCode(RootConstant.Code.MovementCollection.CASH_REGISTER)
				.setActionFromIncrementation(Boolean.TRUE).setValueFromObject(100).set__identifiablePeriod__fromCode(identifiablePeriodCode);
		String child1Code = testCase.getRandomAlphabetic();
		parent.addIdentifiables(testCase.instanciateOne(Movement.class,child1Code).setCollectionFromCode(movementCollectionCode)
				.__setBirthDateComputedByUser__(Boolean.TRUE).setValueSettableFromAbsolute(Boolean.TRUE).setValueAbsoluteFromObject(35).setParent(parent));
		String child2Code = testCase.getRandomAlphabetic();
		parent.addIdentifiables(testCase.instanciateOne(Movement.class,child2Code).setCollectionFromCode(movementCollectionCode)
				.__setBirthDateComputedByUser__(Boolean.TRUE).setValueSettableFromAbsolute(Boolean.TRUE).setValueAbsoluteFromObject(25).setParent(parent));
		String child3Code = testCase.getRandomAlphabetic();
		parent.addIdentifiables(testCase.instanciateOne(Movement.class,child3Code).setCollectionFromCode(movementCollectionCode)
				.__setBirthDateComputedByUser__(Boolean.TRUE).setValueSettableFromAbsolute(Boolean.TRUE).setValueAbsoluteFromObject(40).setParent(parent));
		testCase.create(parent);
		testCase.assertNotNullByBusinessIdentifier(Movement.class, RootConstant.Code.MovementCollection.CASH_REGISTER+Constant.CHARACTER_UNDESCORE+parentCode
				,movementCollectionCode+Constant.CHARACTER_UNDESCORE+child1Code,movementCollectionCode+Constant.CHARACTER_UNDESCORE+child2Code
				,movementCollectionCode+Constant.CHARACTER_UNDESCORE+child3Code);
		testCase.clean();
    }
    
    /* Exceptions */
    
    public void throwCollectionIsNull(){
		TestCase testCase = instanciateOneCase();
		testCase.assertThrowable(new Runnable(testCase) {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class).setValueFromObject(1));}
    	}, FieldHelper.Field.get(Movement.class,Movement.FIELD_COLLECTION).getIdentifier(ConditionHelper.Condition.Builder.Null.class), null);
    	testCase.clean();
	}
	
    public void throwValueIsNull(){
		TestCase testCase = instanciateOneCase();
    	final String collectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode));
		testCase.assertThrowable(new Runnable(testCase) {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class).setCollectionFromCode(collectionCode));}
    	}, null, "La valeur de l'attribut <<valeur>> de l'entité <<mouvement>> doit être non nulle.");
	}
	
    public void throwIdentifiableCollectionIsNull(){
		TestCase testCase = instanciateOneCase();
    	final String collectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode).setTypeFromCode(RootConstant.Code.MovementCollectionType.CASH_REGISTER));
		testCase.assertThrowable(new Runnable(testCase) {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class).setCollectionFromCode(collectionCode).setValueFromObject(1));}
    	}, null, "La valeur de l'attribut <<période identifiable>> de l'entité <<mouvement>> doit être non nulle.");
	}
	
    public void throwDoesNotBelongsToIdentifiablePeriod(){
		TestCase testCase = instanciateOneCase();
		
		final IdentifiablePeriod identifiablePeriod = testCase.create(testCase.instanciateOne(IdentifiablePeriod.class).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("1/1/2000 23:59").setCollectionFromCode(RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY));
		
    	testCase.assertThrowable(new Runnable(testCase) {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class)
					.setCollectionFromCode(RootConstant.Code.MovementCollection.CASH_REGISTER)
					.set__identifiablePeriod__(identifiablePeriod).__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDateFromString("2/2/2000 10:30").setValueFromObject(1));}
    	}, null, "La valeur(02/02/2000 10:30) de l'attribut <<du>> de l'entité <<mouvement>> doit être inférieure ou égale à 01/01/2000 23:59.");
		
		testCase.clean();
	}
	
    public void throwValueIsZero(){
		TestCase testCase = instanciateOneCase();
    	final String collectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode));
		testCase.assertThrowable(new Runnable(testCase) {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class).setCollectionFromCode(collectionCode).setValueFromObject(0));}	
    	}, null, "La valeur(0) de l'attribut <<valeur>> de l'entité <<mouvement>> doit être différente à 0.");
		testCase.clean();
	}
    
	
}
