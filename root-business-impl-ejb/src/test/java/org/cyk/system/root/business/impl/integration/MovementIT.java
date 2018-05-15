package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.mathematics.movement.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionTypeBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementGroupBusiness;
import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.Runnable;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransfer;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferAcknowledgement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollectionItem;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferType;
import org.cyk.system.root.model.mathematics.movement.MovementGroup;
import org.cyk.system.root.model.mathematics.movement.MovementGroupItem;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementDao;
import org.cyk.system.root.persistence.api.party.PartyIdentifiableGlobalIdentifierDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.TimeHelper;
import org.junit.Test;

public class MovementIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    @Test 
    public void crudOneMovementCollectionType(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollectionType movementCollectionType = inject(MovementCollectionTypeBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	assertNotNull(movementCollectionType.getInterval());
    	assertNotNull(movementCollectionType.getIncrementAction());
    	assertNotNull(movementCollectionType.getDecrementAction());
    	testCase.create(movementCollectionType);
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollection(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	assertNotNull(movementCollection.getType());
    	assertNotNull(movementCollection.getType().getInterval());
    	movementCollection.getType().getInterval().getLow().setValue(null);
    	testCase.create(movementCollection);
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionAndItsBuffer(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.assertCountAll(MovementCollection.class, 2);
    	testCase.clean();
    }
    
    @Test
    public void instanciateOneMovement(){
    	TestCase testCase = instanciateTestCase();
    	String collectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode));
    	testCase.assertNotNullByBusinessIdentifier(MovementCollection.class, collectionCode);
    	
    	Movement movement = testCase.instanciateOne(Movement.class,"code").setCollectionFromCode(collectionCode);
    	assertEquals("code", movement.getCode());
    	assertNotNull(movement.getCollection());
    	
    	testCase.clean();
    }
    
    @Test
    public void instanciateOneMovementInIdentifiablePeriod(){
    	final TestCase testCase = instanciateTestCase();
    	final String collectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode).setTypeFromCode(RootConstant.Code.MovementCollectionType.CASH_REGISTER));
    	testCase.assertNotNullByBusinessIdentifier(MovementCollection.class, collectionCode);
    	
    	String identifiablePeriodCollectionCode = testCase.getRandomAlphabetic();
    	IdentifiablePeriodCollection identifiablePeriodCollection = testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	
    	final String identifiablePeriodCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode)
    			.setBirthDate(date(2000, 1, 1, 0, 0)).setDeathDate(date(2000, 1, 1, 23, 59)).setCollectionFromCode(identifiablePeriodCollectionCode));
    	
    	testCase.computeChanges(testCase.instanciateOne(Movement.class).setCollectionFromCode(collectionCode).__setBirthDateComputedByUser__(Boolean.TRUE)
    			.setBirthDateFromString("1/1/2000 1:0").set__identifiablePeriod__fromCode(RootConstant.Code.generate(identifiablePeriodCollection, identifiablePeriodCode)));
    	
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovement(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode));
    	testCase.create(testCase.instanciateOne(Movement.class).setValueFromObject(1).setCollectionFromCode(movementCollectionCode));
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementWhereIdentifiablePeriodIsSetToNotClosed(){
    	TestCase testCase = instanciateTestCase();
    	
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
    
    @Test
    public void crudTwoMovementsWhereIdentifiablePeriodIsSetToNotClosed(){
    	TestCase testCase = instanciateTestCase();
    	
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
    
    @Test
    public void assertComputedMovementBirthDateIsIncrementedWhenSetBySystem(){
    	TestCase testCase = instanciateTestCase();
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
    
    @Test
    public void assertComputedMovementBirthDateIsNotChangedBySystemWhenSetByUser(){
    	TestCase testCase = instanciateTestCase();
    	Movement movement = testCase.instanciateOne(Movement.class);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE);
    	assertNull(movement.getBirthDate());
    	inject(MovementBusiness.class).computeChanges(movement);
    	assertNull(movement.getBirthDate());
    	
    	movement.setBirthDate(date(2000, 1, 1));
    	
    	Date date1 = movement.getBirthDate();
    	assertEquals(date(2000, 1, 1), date1);    
    	
    	inject(MovementBusiness.class).computeChanges(movement);
    	Date date2 = movement.getBirthDate();
    	assertEquals(date(2000, 1, 1), date2);  
    	
    	assertEquals(Boolean.TRUE, date1.compareTo(date2) == 0);
    	
    	inject(MovementBusiness.class).computeChanges(movement);
    	date2 = movement.getBirthDate();
    	assertEquals(date(2000, 1, 1), date2);  
    	
    	assertEquals(Boolean.TRUE, date1.compareTo(date2) == 0);
    	
    	testCase.clean();
    }
    
    @Test
    public void createCreateMovementsWithDateSetBySystem(){
    	TestCase testCase = instanciateTestCase();    	
    	testCase.assertCreateMovements(date(2000, 1, 1, 0, 0), date(2000, 1, 1, 23, 59), new Object[][]{
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
    
    @Test
    public void createCreateMovementsWithDateSetByUserAscending(){
    	TestCase testCase = instanciateTestCase();    	
    	testCase.assertCreateMovements(date(2000, 1, 1, 0, 0), date(2000, 1, 1, 23, 59), new Object[][]{
    		{"true",date(2000, 1, 1, 0, 5),"1","1","1",new String[][] {
    			{"0","1","1","true"}
    		}}
    		,{"true",date(2000, 1, 1, 0, 6),"2","3","2",new String[][] {
    			{"0","1","1","true"}
    			,{"1","2","3","true"}
    		}}
    		,{"true",date(2000, 1, 1, 0, 7),"3","6","3",new String[][] {
    			{"0","1","1","true"}
    			,{"1","2","3","true"}
    			,{"2","3","6","true"}
    		}}
    	});    	  	
    	testCase.clean();
    	testCase.deleteAll(IdentifiablePeriod.class);
    }
    
    @Test
    public void createCreateMovementsWithDateSetByUserDescending(){
    	TestCase testCase = instanciateTestCase();    	
    	testCase.assertCreateMovements(date(2000, 1, 1, 0, 0), date(2000, 1, 1, 23, 59), new Object[][]{
    		{"true",date(2000, 1, 1, 0, 7),"3","3","1",new String[][] {
    			{"0","3","3","true"}
    		}}
    		,{"true",date(2000, 1, 1, 0, 6),"2","5","2",new String[][] {
    			{"0","2","2","true"}
    			,{"1","3","5","true"}
    		}}
    		,{"true",date(2000, 1, 1, 0, 5),"1","6","3",new String[][] {
    			{"0","1","1","true"}
    			,{"1","2","3","true"}
    			,{"2","3","6","true"}
    		}}
    	});    	  	  	
    	testCase.clean();
    	testCase.deleteAll(IdentifiablePeriod.class);
    }
    
    @Test
    public void createCreateMovementsWithDateFirstSpecifiedAncestorSecondNotSpecifiedNewest(){
    	TestCase testCase = instanciateTestCase();    	
    	testCase.assertCreateMovements(date(2000, 1, 1, 0, 0), date(2000, 1, 1, 23, 59), new Object[][]{
    		{"true",date(2000, 1, 1, 0, 5),"1","1","1",new String[][] {
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
    
    @Test
    public void createCreateMovementsWithDateFirstSpecifiedNewestSecondNotSpecifiedAncestor(){
    	TestCase testCase = instanciateTestCase();    	
    	testCase.assertCreateMovements(date(2000, 1, 1, 0, 0), date(2000, 1, 1, 23, 59), new Object[][]{
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
    
    @Test
    public void createCreateMovementsWithDateFirstNotSpecifiedAncestorSecondSpecifiedNewest(){
    	TestCase testCase = instanciateTestCase();    	
    	testCase.assertCreateMovements(date(2000, 1, 1, 0, 0), date(2000, 1, 1, 23, 59), new Object[][]{
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
    
    @Test
    public void createCreateMovementsWithDateFirstNotSpecifiedNewestSecondSpecifiedAncestor(){
    	TestCase testCase = instanciateTestCase();    	
    	testCase.assertCreateMovements(date(2000, 1, 1, 0, 0), date(2000, 1, 1, 23, 59), new Object[][]{
    		{"true",null,"1","1","1",new String[][] {
    			{"0","1","1","true"}
    		}}
    		,{"true",date(2000, 1, 1, 0, 6),"2","3","2",new String[][] {
    			{"0","2","2","true"}
    			,{"1","1","3","true"}
    		}}
    	});    	  	
    	testCase.clean();
    	testCase.deleteAll(IdentifiablePeriod.class);
    }
    
    @Test
    public void crudOneMovementWithSpecifiedDate(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode));
    	
    	String identifiablePeriodCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	
    	IdentifiablePeriod identifiablePeriod = testCase.instanciateOne(IdentifiablePeriod.class).setCollectionFromCode(identifiablePeriodCollectionCode);
		identifiablePeriod.setCode(testCase.getRandomAlphabetic());
		identifiablePeriod.setBirthDate(date(2000, 1, 1, 0, 0));
		identifiablePeriod.setDeathDate(date(2000, 1, 1, 23, 59));
		testCase.create(identifiablePeriod);
		
    	Movement movement = testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode);
    	movement.setValue(BigDecimal.ONE).set__identifiablePeriod__(identifiablePeriod).setBirthDate(date(2000, 1, 1, 0, 5));
    	testCase.create(movement);
    	testCase.assertMovement(movement.getCode(), "1", "1");
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementWithoutSpecifiedDate(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode).setValue(BigDecimal.ZERO));
    	
    	String identifiablePeriodCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	
    	IdentifiablePeriod identifiablePeriod = testCase.instanciateOne(IdentifiablePeriod.class);
		identifiablePeriod.setCode(testCase.getRandomAlphabetic());
		identifiablePeriod.setBirthDate(date(2000, 1, 1, 0, 0));
		identifiablePeriod.setDeathDate(date(2000, 1, 1, 23, 59)).setCollectionFromCode(identifiablePeriodCollectionCode);
		testCase.create(identifiablePeriod);
		
    	Movement movement = testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode);
    	movement.setValue(BigDecimal.ONE).set__identifiablePeriod__(identifiablePeriod);
    	testCase.create(movement);
    	movement = testCase.read(Movement.class, movement.getCode());
    	assertNotNull(movement.getBirthDate());
    	testCase.assertMovement(movement.getCode(), "1", "1");
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionAndMovements(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	Movement movement = inject(MovementBusiness.class).instanciateOne(movementCollection);
    	movement.setCode(testCase.getRandomAlphabetic());
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
    
    @Test
    public void useValueAbsolute(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementCollectionCode);
    	movementCollection.setValue(new BigDecimal("0"));
    	testCase.create(movementCollection);
    	
    	Movement movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), null,Boolean.TRUE);
    	movement.setValueAbsolute(new BigDecimal("15"));
    	movement.setValueSettableFromAbsolute(Boolean.TRUE);
    	testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "15", "1").assertMovement(code001, "15","15",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), null,Boolean.TRUE);
    	movement.setValueAbsolute(new BigDecimal("10"));
    	movement.setValueSettableFromAbsolute(Boolean.TRUE);
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "25", "2").assertMovement(code002, "10","25",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), null,Boolean.FALSE);
    	movement.setValueAbsolute(new BigDecimal("7"));
    	movement.setValueSettableFromAbsolute(Boolean.TRUE);
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "18", "3").assertMovement(code003, "-7","18",Boolean.FALSE);
    	
    	testCase.clean();
    }
    
    @Test
    public void useNullAction(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementCollectionCode);
    	movementCollection.setValue(new BigDecimal("0"));
    	testCase.create(movementCollection);
    	
    	Movement movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), "15",null);
    	testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.read(Movement.class, code001);
    	testCase.assertMovementCollection(movementCollectionCode, "15", "1").assertMovement(code001, "15","15",null);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), "10",null);
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "25", "2").assertMovement(code002, "10","25",null);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), "-7",null);
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "18", "3").assertMovement(code003, "-7","18",null);
    	
    	testCase.clean();
    }
    
    @Test
    public void addSequenceAscending(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementCollectionCode);
    	movementCollection.setValue(new BigDecimal("0"));
    	testCase.create(movementCollection);
    	
    	Movement movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), "15",Boolean.TRUE);
    	testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.read(Movement.class, code001);
    	testCase.assertMovementCollection(movementCollectionCode, "15", "1")
			.assertMovement(code001, "15","15",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), "10",Boolean.TRUE);
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "25", "2")
			.assertMovement(code002, "10","25",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), "7",Boolean.TRUE);
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	testCase.assertMovementCollection(movementCollectionCode, "32", "3")
			.assertMovement(code003, "7","32",Boolean.TRUE);
    	
    	testCase.clean();
    }
    
    @Test
    public void addSequenceDescending(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollection movementCollection = testCase.instanciateOneWithRandomIdentifier(MovementCollection.class);
    	String movementCollectionCode = movementCollection.getCode();
    	testCase.create(movementCollection);
    	
    	String code001 = testCase.getRandomAlphabetic();
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
    	
    	String code004 = testCase.getRandomAlphabetic();
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
    	
    	String code007 = testCase.getRandomAlphabetic();
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
    
    @Test
    public void findPrevious(){
    	TestCase testCase = instanciateTestCase();
    	String movementUpdatesUnlimitedIdentifier = testCase.getRandomAlphabetic();
    	MovementCollection movementUpdatesUnlimited = inject(MovementCollectionBusiness.class).instanciateOne(movementUpdatesUnlimitedIdentifier);
    	movementUpdatesUnlimited.setValue(new BigDecimal("0"));
    	testCase.create(movementUpdatesUnlimited);
    	
    	String movementUnlimitedIdentifier = testCase.getRandomAlphabetic();
    	MovementCollection movementUnlimited = inject(MovementCollectionBusiness.class).instanciateOne(movementUnlimitedIdentifier);
    	movementUnlimited.setValue(new BigDecimal("0"));
    	testCase.create(movementUnlimited);
    	
    	Movement movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementUpdatesUnlimitedIdentifier, "15",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(date(2000, 5, 2));
    	movement = testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.assertMovement(code001, "15","15",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class,code001);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 0);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementUpdatesUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(date(2000, 5, 3));
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,movement, 1);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementUpdatesUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(date(2000, 5, 1));
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	testCase.assertMovement(code002, "10","10",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code002 , code001);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 1);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementUpdatesUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(date(2000, 5, 3));
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	testCase.assertMovement(code003, "10","35",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code002 ,code001, code003);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 1);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code003, 2);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(date(2000, 4, 1));
    	movement = testCase.create(movement);
    	String code001A = movement.getCode();
    	testCase.assertMovement(code001A, "10","10",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code001A);
    	
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code002 ,code001, code003);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 1);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code003, 2);
    	
    	testCase.clean();
    }
    
    @Test
    public void findLatest(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode01));
    	String movementCode0101 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode01).setValueFromObject(3).setActionFromValue()).getCode();
    	String movementCode0102 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode01).setValueFromObject(2).setActionFromValue()).getCode();
    	String movementCode0103 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode01).setValueFromObject(1).setActionFromValue()).getCode();
    	
    	String movementCollectionCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode02));
    	/*String movementCode0201 = */testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode02).setValueFromObject(1).setActionFromValue()).getCode();
    	String movementCode0202 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode02).setValueFromObject(1).setActionFromValue()).getCode();
    	
    	String movementCollectionCode03 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode03));
    	String movementCode0301 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode03).setValueFromObject(1).setActionFromValue()).getCode();
    	
    	String movementCollectionCode04 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode04));
    	
    	testCase.assertMovements(movementCollectionCode01
    		, new String[] {"0","3","3","true"}
    		, new String[] {"1","2","5","true"}
    		, new String[] {"2","1","6","true"}
    	);
    	
    	testCase.assertMovement(movementCode0101, "3", "3",Boolean.TRUE);
    	
    	testCase.assertEquals(movementCode0101, 
    			inject(MovementDao.class).readByCollectionByFromDateAscendingOrderIndex(testCase
    					.getByIdentifierWhereValueUsageTypeIsBusiness(MovementCollection.class, movementCollectionCode01),0l).getCode());
    	
    	testCase.assertEquals(movementCode0102, 
    			inject(MovementDao.class).readByCollectionByFromDateAscendingOrderIndex(testCase
    					.getByIdentifierWhereValueUsageTypeIsBusiness(MovementCollection.class, movementCollectionCode01),1l).getCode());
    	
    	testCase.assertEquals(movementCode0103, 
    			inject(MovementDao.class).readByCollectionByFromDateAscendingOrderIndex(testCase
    					.getByIdentifierWhereValueUsageTypeIsBusiness(MovementCollection.class, movementCollectionCode01),2l).getCode());
    	
    	testCase.assertNotNull(inject(MovementDao.class).readLatestFromDateAscendingOrderIndexByCollection(testCase
				.getByIdentifierWhereValueUsageTypeIsBusiness(MovementCollection.class, movementCollectionCode01)));
    	
    	testCase.assertEquals(movementCode0103, 
    			inject(MovementDao.class).readLatestFromDateAscendingOrderIndexByCollection(testCase
    					.getByIdentifierWhereValueUsageTypeIsBusiness(MovementCollection.class, movementCollectionCode01)).getCode());
    	
    	testCase.assertMovementCollectionLatestFromDateAscendingOrderIndex(movementCollectionCode01, movementCode0103);
    	testCase.assertMovementCollectionLatestFromDateAscendingOrderIndex(movementCollectionCode02, movementCode0202);
    	testCase.assertMovementCollectionLatestFromDateAscendingOrderIndex(movementCollectionCode03, movementCode0301);
    	testCase.assertMovementCollectionLatestFromDateAscendingOrderIndexIsNull(movementCollectionCode04);
    	
    	testCase.clean();
    }
    
    @Test
    public void movementOrderNumberIsAscendingBasedOnBirthDate(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode01));
    	String movementCode0101 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode01).setValueFromObject(3).setActionFromValue()).getCode();
    	String movementCode0102 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode01).setValueFromObject(2).setActionFromValue()).getCode();
    	String movementCode0103 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode01).setValueFromObject(1).setActionFromValue()).getCode();
    	
    	String movementCollectionCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode02));
    	String movementCode0201 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode02).setValueFromObject(1).setActionFromValue()).getCode();
    	String movementCode0202 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode02).setValueFromObject(1).setActionFromValue()).getCode();
    	
    	String movementCollectionCode03 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode03));
    	String movementCode0301 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode03).setValueFromObject(1).setActionFromValue()).getCode();
    	
    	String movementCollectionCode04 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode04));
    	
    	String movementCollectionCode05 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode05));
    	String movementCode0501 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode05).setValueFromObject(3)
    			.setActionFromValue().__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDateFromString("3/1/2000")).getCode();
    	testCase.assertEqualsOrderNumbersByBusinessIdentifier(Movement.class, movementCode0501, 6,0);
    	testCase.assertEqualsCountWhereOrderNumbeIsGreaterThanrByBusinessIdentifier(Movement.class, movementCode0501,0);
    	
    	String movementCode0502 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode05).setValueFromObject(2)
    			.setActionFromValue().__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDateFromString("2/1/2000")).getCode();
    	testCase.assertEqualsOrderNumbersByBusinessIdentifier(Movement.class, movementCode0501, 6,1);
    	testCase.assertEqualsOrderNumbersByBusinessIdentifier(Movement.class, movementCode0502, 7,0);
    	testCase.assertEqualsCountWhereOrderNumbeIsGreaterThanrByBusinessIdentifier(Movement.class, movementCode0501,0);
    	testCase.assertEqualsCountWhereOrderNumbeIsGreaterThanrByBusinessIdentifier(Movement.class, movementCode0502,1);
    	
    	String movementCode0503 = testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode05).setValueFromObject(1)
    			.setActionFromValue().__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDateFromString("1/1/2000")).getCode();
    	
    	testCase.assertEqualsNumber(2, 
    			inject(MovementDao.class).countWhereOrderNumberIsGreaterThan(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Movement.class, movementCode0101)));
    	testCase.assertEqualsNumber(1, 
    			inject(MovementDao.class).countWhereOrderNumberIsGreaterThan(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Movement.class, movementCode0102)));
    	testCase.assertEqualsNumber(0, 
    			inject(MovementDao.class).countWhereOrderNumberIsGreaterThan(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Movement.class, movementCode0103)));
    	
    	testCase.assertEqualsOrderNumbersByBusinessIdentifier(Movement.class, movementCode0101, 0,0);
    	testCase.assertEqualsOrderNumbersByBusinessIdentifier(Movement.class, movementCode0102, 1,1);
    	testCase.assertEqualsOrderNumbersByBusinessIdentifier(Movement.class, movementCode0103, 2,2);
    	
    	testCase.assertEqualsOrderNumbersByBusinessIdentifier(Movement.class, movementCode0201, 3,0);
    	testCase.assertEqualsOrderNumbersByBusinessIdentifier(Movement.class, movementCode0202, 4,1);
    	
    	testCase.assertEqualsOrderNumbersByBusinessIdentifier(Movement.class, movementCode0301, 5,0);
    	
    	testCase.assertEqualsNumber(0, 
    			inject(MovementDao.class).countWhereOrderNumberIsGreaterThan(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Movement.class, movementCode0501)));
    	testCase.assertEqualsNumber(1, 
    			inject(MovementDao.class).countWhereOrderNumberIsGreaterThan(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Movement.class, movementCode0502)));
    	testCase.assertEqualsNumber(2, 
    			inject(MovementDao.class).countWhereOrderNumberIsGreaterThan(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Movement.class, movementCode0503)));
    	
    	testCase.assertEqualsOrderNumbersByBusinessIdentifier(Movement.class, movementCode0501, 6,2);
    	testCase.assertEqualsOrderNumbersByBusinessIdentifier(Movement.class, movementCode0502, 7,1);
    	testCase.assertEqualsOrderNumbersByBusinessIdentifier(Movement.class, movementCode0503, 8,0);
    	
    	testCase.clean();
    }
    
    @Test
    public void doMovementsAndUpdates(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	movementCollection.setValue(new BigDecimal("0"));
    	testCase.create(movementCollection);
    	
    	Movement movement = testCase.create(inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), "15",Boolean.TRUE));
    	String code001 = movement.getCode();
    	testCase.read(Movement.class, code001);
    	testCase.assertMovement(code001, "15","15",Boolean.TRUE);
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), "10",Boolean.TRUE));
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
    	
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), "1",Boolean.TRUE));
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
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), "4",Boolean.TRUE));
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
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection.getCode(), "-6",Boolean.FALSE));
    	String code005 = movement.getCode();
    	testCase.assertMovement(code005, "-6","30",Boolean.FALSE);
    	
    	testCase.clean();
    }
    
    @Test
    public void filter(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollection movementCollection01 = inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	testCase.create(movementCollection01);
    	
    	MovementCollection movementCollection02 = inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	testCase.create(movementCollection02);
    	
    	MovementCollection movementCollection03 = inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic());
    	testCase.create(movementCollection03);
    	
    	testCase.create(inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection01.getCode(), "15",Boolean.TRUE)
    			.setCode("MyCode001"));
    	testCase.create(inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection01.getCode(), "-15",Boolean.FALSE)
    			.setCode("MyCodeABC"));
    	testCase.create(inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection01.getCode(), "15",Boolean.TRUE)
    			.setCode("MyCodeAB001"));
    	
    	testCase.create(inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection03.getCode(), "-15",Boolean.FALSE)
    			.setCode("MyCode001"));
    	testCase.create(inject(MovementBusiness.class).instanciateOne(testCase.getRandomAlphabetic(),movementCollection03.getCode(), "15",Boolean.TRUE)
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
    
    @Test
    public void crudOneMovementCollectionIdentifiableGlobalIdentifier(){
    	TestCase testCase = instanciateTestCase();
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
    		filter.set((String)index[1]);
        	assertEquals((Integer)index[2], inject(MovementBusiness.class).findByFilter(filter, new DataReadConfiguration()).size());
    	}
    }
    
	@Test
	public void computeChanges() {
		TestCase testCase = instanciateTestCase();
		
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
    
	@Test
    public void crudMovementsWithDestination(){
    	TestCase testCase = instanciateTestCase();
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
	
	@Test
    public void createOneMovementAndOneChildrenWithDateSetByUser(){
		TestCase testCase = instanciateTestCase();
		
    	String identifiablePeriodCode = testCase.getRandomAlphabetic();
		testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode)
				.setBirthDate(date(2000, 1, 1, 0, 0)).setDeathDate(date(2000, 1, 1, 23, 59)).setCollectionFromCode(RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY));
		
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
	
	@Test
    public void createOneMovementAndOneChildrenWithDateSetBySystem(){
		TestCase testCase = instanciateTestCase();
		
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
	
	@Test
    public void createOneMovementAndOneChildren(){
		TestCase testCase = instanciateTestCase();
		
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
	
	@Test
    public void createOneMovementAndManyChildren(){
		TestCase testCase = instanciateTestCase();
		
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
    
	@Test
	public void findByFilterWhereChangesComputed(){
		TestCase testCase = instanciateTestCase();
		String movementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode).setValueFromObject(10));
    	
    	/*String movementCode01 = */testCase.create(testCase.instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode).setValueFromObject(1)
    			.setActionFromValue()).getCode();
    	
    	Movement movement = CollectionHelper.getInstance().getFirst(inject(MovementBusiness.class).findByFilter(new Movement.Filter().addMaster(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(MovementCollection.class
    			, movementCollectionCode)), new DataReadConfiguration().setComputeChanges(Boolean.TRUE)));
    	
    	testCase.assertEqualsNumber(10, movement.getPreviousCumul());
    	
    	testCase.clean();
	}
	
	/* Transfer */
	
    @Test
    public void crudOneMovementCollectionValuesTransferType(){
    	TestCase testCase = instanciateTestCase(); 
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionValuesTransferType.class,code));
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionValuesTransfer(){
    	TestCase testCase = instanciateTestCase(); 
    	String senderCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Person.class,senderCode));
    	String receiverCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Person.class,receiverCode));
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionValuesTransfer.class,code).setSenderFromCode(senderCode).setReceiverFromCode(receiverCode));
    	testCase.assertNotNull(inject(PartyIdentifiableGlobalIdentifierDao.class).readByPartyByIdentifiableGlobalIdentifierByRole(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Party.class, senderCode)
    			, testCase.getByIdentifierWhereValueUsageTypeIsBusiness(MovementCollectionValuesTransfer.class,code).getGlobalIdentifier()
    			, testCase.getByIdentifierWhereValueUsageTypeIsBusiness(BusinessRole.class,RootConstant.Code.BusinessRole.SENDER)));
    	testCase.assertNotNull(inject(PartyIdentifiableGlobalIdentifierDao.class).readByPartyByIdentifiableGlobalIdentifierByRole(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Party.class, receiverCode)
    			, testCase.getByIdentifierWhereValueUsageTypeIsBusiness(MovementCollectionValuesTransfer.class,code).getGlobalIdentifier()
    			, testCase.getByIdentifierWhereValueUsageTypeIsBusiness(BusinessRole.class,RootConstant.Code.BusinessRole.RECEIVER)));
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionValuesTransferItemCollection(){
    	TestCase testCase = instanciateTestCase(); 
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionValuesTransferItemCollection.class,code));
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionValuesTransferItemCollectionItem(){
    	TestCase testCase = instanciateTestCase(); 
    	
    	String sourceMovementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, sourceMovementCollectionCode).setValueFromObject(10));
    	
    	String destinationMovementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, destinationMovementCollectionCode).setValueFromObject(3));
    	
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionValuesTransferItemCollectionItem.class,code).setDestinationValueAbsoluteFromObject(1)
    			.setSourceMovementCollectionFromCode(sourceMovementCollectionCode).setDestinationMovementCollectionFromCode(destinationMovementCollectionCode));
    	
    	MovementCollectionValuesTransferItemCollectionItem movementCollectionValuesTransferItemCollectionItem = testCase
    			.getByIdentifierWhereValueUsageTypeIsBusiness(MovementCollectionValuesTransferItemCollectionItem.class, code);
    	
    	testCase.computeChanges(movementCollectionValuesTransferItemCollectionItem);
    	
    	testCase.assertEqualsNumber("source previous cumul",10, movementCollectionValuesTransferItemCollectionItem.getSource().getPreviousCumul());
    	testCase.assertEqualsNumber("source value",-1, movementCollectionValuesTransferItemCollectionItem.getSource().getValue());
    	testCase.assertEqualsNumber("source cumul",9, movementCollectionValuesTransferItemCollectionItem.getSource().getCumul());
    	
    	testCase.assertEqualsNumber("destination previous cumul",3, movementCollectionValuesTransferItemCollectionItem.getDestination().getPreviousCumul());
    	testCase.assertEqualsNumber("destination value",1, movementCollectionValuesTransferItemCollectionItem.getDestination().getValue());
    	testCase.assertEqualsNumber("destination cumul",4, movementCollectionValuesTransferItemCollectionItem.getDestination().getCumul());
    	
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionValuesTransferItemCollectionItemWithMovementCollection(){
    	TestCase testCase = instanciateTestCase(); 
    	
    	testCase.assertCountAll(Movement.class);
    	
    	String movementCollectionSourceCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionSourceCode));
    	
    	String movementCollectionDestinationCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionDestinationCode));
    
    	String code = testCase.getRandomAlphabetic();
    	MovementCollectionValuesTransferItemCollectionItem movementCollectionValuesTransferItemCollectionItem = testCase.create(testCase.instanciateOne(MovementCollectionValuesTransferItemCollectionItem.class,code).setSourceMovementCollectionFromCode(movementCollectionSourceCode)
    			.setDestinationMovementCollectionFromCode(movementCollectionDestinationCode).setSourceValueAbsoluteFromObject(3));
    	
    	movementCollectionValuesTransferItemCollectionItem = testCase.getByIdentifierWhereValueUsageTypeIsBusiness(MovementCollectionValuesTransferItemCollectionItem.class
    			,movementCollectionValuesTransferItemCollectionItem.getCode());
    	
    	testCase.assertNotNull(movementCollectionValuesTransferItemCollectionItem.getSource());
    	testCase.assertNotNull(movementCollectionValuesTransferItemCollectionItem.getDestination());
    	testCase.assertEquals(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(MovementCollection.class, movementCollectionSourceCode)
    			,movementCollectionValuesTransferItemCollectionItem.getSource().getCollection());
    	testCase.assertEquals(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(MovementCollection.class, movementCollectionDestinationCode)
    			,movementCollectionValuesTransferItemCollectionItem.getDestination().getCollection());
    	
    	testCase.assertCountAll(Movement.class,2);
    	
    	testCase.assertMovementCollection(movementCollectionSourceCode, -3, 1);
    	testCase.assertMovementCollection(movementCollectionDestinationCode, 3, 1);
    	
    	testCase.assertMovements(movementCollectionSourceCode
    			, new String[]{"0","-3","-3","false"}
    	);
    	
    	testCase.assertMovements(movementCollectionDestinationCode
    			, new String[]{"0","3","3","true"}
    	);
    	
    	testCase.delete(movementCollectionValuesTransferItemCollectionItem);
    	
    	testCase.assertMovementCollection(movementCollectionSourceCode, 0, 2);
    	testCase.assertMovementCollection(movementCollectionDestinationCode, 0, 2);
    	
    	testCase.clean();
    	testCase.deleteAll(Movement.class);
    }
    
    @Test
    public void crudOneMovementCollectionValuesTransferItemCollectionWithMovementCollectionSourceAndDestination(){
    	TestCase testCase = instanciateTestCase(); 
    	testCase.assertCountAll(Movement.class);
    	
    	String sourceMovementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, sourceMovementCollectionCode));
    	
    	String destinationMovementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, destinationMovementCollectionCode));
    	
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionValuesTransferItemCollection.class,code).setItemsSynchonizationEnabled(Boolean.TRUE)
    			.addBySourceMovementCollectionCodeByDestinationMovementCollectionCodeByValue(sourceMovementCollectionCode, destinationMovementCollectionCode, 3));
    	
    	testCase.assertCountAll(Movement.class,2);
    	
    	testCase.assertMovements(sourceMovementCollectionCode
    			, new String[]{"0","-3","-3","false"}
    	);
    	
    	testCase.assertMovements(destinationMovementCollectionCode
    			, new String[]{"0","3","3","true"}
    	);
    	
    	testCase.clean();
    	testCase.deleteAll(Movement.class);
    }
	
    @Test
    public void crudOneMovementCollectionValuesTransferAndItsAcknowledgement(){
    	TestCase testCase = instanciateTestCase(); 
    	String movementCollectionValuesTransferCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionValuesTransfer.class,movementCollectionValuesTransferCode));
    	String movementCollectionValuesTransferAcknowledgementCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionValuesTransferAcknowledgement.class,movementCollectionValuesTransferAcknowledgementCode)
    			.setTransferFromCode(movementCollectionValuesTransferCode));
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionValuesTransferWithOneItemAndItsAcknowledgementBackNone(){
    	instanciateTestCase().assertOneMovementCollectionValuesTransferWithOneItemAndItsAcknowledgement(3, 3);
    }
    
    @Test
    public void crudOneMovementCollectionValuesTransferWithOneItemAndItsAcknowledgementWithBackSome(){
    	instanciateTestCase().assertOneMovementCollectionValuesTransferWithOneItemAndItsAcknowledgement(3, 2);
    }
    
    @Test
    public void crudOneMovementCollectionValuesTransferItemCollectionWithJoinToParty(){
    	TestCase testCase = instanciateTestCase(); 
    	String partyCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Party.class,partyCode01));
    	
    	String partyCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Party.class,partyCode02));
    	
    	String movementCollectionCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode01).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(movementCollectionCode01).setIdentifiableGlobalIdentifierFromCode(Party.class, partyCode01));
    	
    	String movementCollectionCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode02).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(movementCollectionCode02).setIdentifiableGlobalIdentifierFromCode(Party.class, partyCode01));
    	
    	String movementCollectionCode03 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode03).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(movementCollectionCode03).setIdentifiableGlobalIdentifierFromCode(Party.class, partyCode02));
    	
    	String movementCollectionValuesTransferItemCollectionCode = testCase.getRandomAlphabetic();
    	MovementCollectionValuesTransferItemCollection movementCollectionValuesTransferItemCollection = testCase.instanciateOne(MovementCollectionValuesTransferItemCollection.class
    			,movementCollectionValuesTransferItemCollectionCode).setPartyFromCode(partyCode01);
    	movementCollectionValuesTransferItemCollection.getDestination().setMovementCollectionIsBuffer(Boolean.TRUE);
    	testCase.computeChanges(movementCollectionValuesTransferItemCollection);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementCollectionValuesTransferItemCollection.getItems().getElements()));
    	testCase.computeChanges(movementCollectionValuesTransferItemCollection);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementCollectionValuesTransferItemCollection.getItems().getElements()));
    	
    	movementCollectionValuesTransferItemCollection.setPartyFromCode(partyCode02);
    	testCase.computeChanges(movementCollectionValuesTransferItemCollection);
    	testCase.assertEqualsNumber(1, CollectionHelper.getInstance().getSize(movementCollectionValuesTransferItemCollection.getItems().getElements()));
    	
    	movementCollectionValuesTransferItemCollection.setPartyFromCode(partyCode01);
    	testCase.computeChanges(movementCollectionValuesTransferItemCollection);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementCollectionValuesTransferItemCollection.getItems().getElements()));
    	
    	movementCollectionValuesTransferItemCollection.getItemAt(0).getSource().setValueAbsoluteFromObject(8);
    	movementCollectionValuesTransferItemCollection.getItemAt(1).getSource().setValueAbsoluteFromObject(1);
    	testCase.computeChanges(movementCollectionValuesTransferItemCollection);
    	
    	testCase.assertNotNull(movementCollectionValuesTransferItemCollection.getItemAt(0).getSource().getCollection());
    	testCase.assertEqualsNumber("-8", movementCollectionValuesTransferItemCollection.getItemAt(0).getSource().getValue());
    	
    	testCase.assertNotNull(movementCollectionValuesTransferItemCollection.getItemAt(0).getDestination().getCollection());
    	testCase.assertEqualsNumber("8", movementCollectionValuesTransferItemCollection.getItemAt(0).getDestination().getValue());
    	
    	testCase.assertNotNull(movementCollectionValuesTransferItemCollection.getItemAt(1).getSource().getCollection());    	
    	testCase.assertNotNull(movementCollectionValuesTransferItemCollection.getItemAt(1).getDestination().getCollection());
    	
    	testCase.create(movementCollectionValuesTransferItemCollection);
    	
    	movementCollectionValuesTransferItemCollectionCode = testCase.getRandomAlphabetic();
    	movementCollectionValuesTransferItemCollection = testCase.instanciateOne(MovementCollectionValuesTransferItemCollection.class,movementCollectionValuesTransferItemCollectionCode).setPartyFromCode(partyCode01);
    	testCase.computeChanges(movementCollectionValuesTransferItemCollection);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementCollectionValuesTransferItemCollection.getItems().getElements()));
    	
    	testCase.assertNotNull(movementCollectionValuesTransferItemCollection.getItemAt(0).getSource().getCollection());
    	testCase.assertNotNull(movementCollectionValuesTransferItemCollection.getItemAt(1).getSource().getCollection());
    	   	
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionValuesTransferWithJoinToParty(){
    	TestCase testCase = instanciateTestCase(); 
    	String partyCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Party.class,partyCode01));
    	
    	String partyCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Party.class,partyCode02));
    	
    	String movementCollectionCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode01).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(movementCollectionCode01).setIdentifiableGlobalIdentifierFromCode(Party.class, partyCode01));
    	
    	String movementCollectionCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode02).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(movementCollectionCode02).setIdentifiableGlobalIdentifierFromCode(Party.class, partyCode01));
    	
    	String movementCollectionCode03 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode03).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(movementCollectionCode03).setIdentifiableGlobalIdentifierFromCode(Party.class, partyCode02));
    	
    	String movementCollectionValuesTransferCode = testCase.getRandomAlphabetic();
    	MovementCollectionValuesTransfer movementCollectionValuesTransfer = testCase.instanciateOne(MovementCollectionValuesTransfer.class
    			,movementCollectionValuesTransferCode);
    	movementCollectionValuesTransfer.getItems().setPartyFromCode(partyCode01);
    	testCase.computeChanges(movementCollectionValuesTransfer);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementCollectionValuesTransfer.getItems().getItems().getElements()));
    	testCase.computeChanges(movementCollectionValuesTransfer);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementCollectionValuesTransfer.getItems().getItems().getElements()));
    	
    	movementCollectionValuesTransfer.getItems().setPartyFromCode(partyCode02);
    	testCase.computeChanges(movementCollectionValuesTransfer);
    	testCase.assertEqualsNumber(1, CollectionHelper.getInstance().getSize(movementCollectionValuesTransfer.getItems().getItems().getElements()));
    	
    	movementCollectionValuesTransfer.getItems().setPartyFromCode(partyCode01);
    	testCase.computeChanges(movementCollectionValuesTransfer);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementCollectionValuesTransfer.getItems().getItems().getElements()));
    	
    	movementCollectionValuesTransfer.getItems().getItemAt(0).getSource().setValueAbsoluteFromObject(8);
    	movementCollectionValuesTransfer.getItems().getItemAt(1).getSource().setValueAbsoluteFromObject(1);
    	testCase.computeChanges(movementCollectionValuesTransfer);
    	
    	testCase.assertNotNull(movementCollectionValuesTransfer.getItems().getItemAt(0).getSource().getCollection());
    	testCase.assertEqualsNumber("-8", movementCollectionValuesTransfer.getItems().getItemAt(0).getSource().getValue());
    	
    	testCase.assertNotNull(movementCollectionValuesTransfer.getItems().getItemAt(0).getDestination().getCollection());
    	testCase.assertEqualsNumber("8", movementCollectionValuesTransfer.getItems().getItemAt(0).getDestination().getValue());
    	
    	testCase.assertNotNull(movementCollectionValuesTransfer.getItems().getItemAt(1).getSource().getCollection());    	
    	testCase.assertNotNull(movementCollectionValuesTransfer.getItems().getItemAt(1).getDestination().getCollection());
    	
    	testCase.create(movementCollectionValuesTransfer);
    	
    	movementCollectionValuesTransferCode = testCase.getRandomAlphabetic();
    	movementCollectionValuesTransfer = testCase.instanciateOne(MovementCollectionValuesTransfer.class,movementCollectionValuesTransferCode);
    	movementCollectionValuesTransfer.getItems().setPartyFromCode(partyCode01);
    	testCase.computeChanges(movementCollectionValuesTransfer);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementCollectionValuesTransfer.getItems().getItems().getElements()));
    	
    	testCase.assertNotNull(movementCollectionValuesTransfer.getItems().getItemAt(0).getSource().getCollection());
    	testCase.assertNotNull(movementCollectionValuesTransfer.getItems().getItemAt(1).getSource().getCollection());
    	   	
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionValuesTransferAcknowlegdmentWithJoinToParty(){
    	TestCase testCase = instanciateTestCase(); 
    	String warehouseCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Party.class,warehouseCode01));
    	
    	String warehouseCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Party.class,warehouseCode02));
    	
    	String storeCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Party.class,storeCode01));
    	
    	String warehouse01P01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,warehouse01P01).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(warehouse01P01).setIdentifiableGlobalIdentifierFromCode(Party.class, warehouseCode01));
    	
    	String warehouse01P02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,warehouse01P02).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(warehouse01P02).setIdentifiableGlobalIdentifierFromCode(Party.class, warehouseCode01));
    	
    	String warehouse02P01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,warehouse02P01).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(warehouse02P01).setIdentifiableGlobalIdentifierFromCode(Party.class, warehouseCode02));
    	
    	String store01P01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,store01P01).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(store01P01).setIdentifiableGlobalIdentifierFromCode(Party.class, storeCode01));
    	
    	String store01P02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,store01P02).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(store01P02).setIdentifiableGlobalIdentifierFromCode(Party.class, storeCode01));
    	
    	String transfer01 = testCase.getRandomAlphabetic();
    	MovementCollectionValuesTransfer movementCollectionValuesTransfer = testCase.instanciateOne(MovementCollectionValuesTransfer.class,transfer01);
    	movementCollectionValuesTransfer.getItems().setPartyFromCode(warehouseCode01);
    	testCase.computeChanges(movementCollectionValuesTransfer);
    	movementCollectionValuesTransfer.getItems().getItemAt(0).getSource().setValueAbsoluteFromObject(8);
    	movementCollectionValuesTransfer.getItems().getItemAt(1).getSource().setValueAbsoluteFromObject(1);    	
    	testCase.create(movementCollectionValuesTransfer);
    	
    	String transfer02 = testCase.getRandomAlphabetic();
    	movementCollectionValuesTransfer = testCase.instanciateOne(MovementCollectionValuesTransfer.class,transfer02);
    	movementCollectionValuesTransfer.getItems().setPartyFromCode(warehouseCode02);
    	testCase.computeChanges(movementCollectionValuesTransfer);
    	movementCollectionValuesTransfer.getItems().getItemAt(0).getSource().setValueAbsoluteFromObject(10);
    	testCase.create(movementCollectionValuesTransfer);
    	
    	String acknowledgement01 = testCase.getRandomAlphabetic();
    	MovementCollectionValuesTransferAcknowledgement acknowledgement = testCase.instanciateOne(MovementCollectionValuesTransferAcknowledgement.class
    			,acknowledgement01);
    	acknowledgement.setTransferFromCode(transfer01);
    	//movementCollectionValuesTransferAcknowledgement.getItems().setPartyFromCode(partyCode01);
    	testCase.computeChanges(acknowledgement);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(acknowledgement.getItems().getItems().getElements()));    	
    	testCase.computeChanges(acknowledgement);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(acknowledgement.getItems().getItems().getElements()));
    	
    	//movementCollectionValuesTransferAcknowledgement.getItems().setPartyFromCode(partyCode02);
    	acknowledgement.setTransferFromCode(transfer02);
    	testCase.computeChanges(acknowledgement);
    	testCase.assertEqualsNumber(1, CollectionHelper.getInstance().getSize(acknowledgement.getItems().getItems().getElements()));
    	
    	//movementCollectionValuesTransferAcknowledgement.getItems().setPartyFromCode(partyCode01);
    	acknowledgement.setTransferFromCode(transfer01);
    	testCase.computeChanges(acknowledgement);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(acknowledgement.getItems().getItems().getElements()));
    	
    	acknowledgement.getItems().getItemAt(0).getSource().setValueAbsoluteFromObject(8);
    	acknowledgement.getItems().getItemAt(0).getDestination().setCollectionFromCode(store01P01);
    	acknowledgement.getItems().getItemAt(1).getSource().setValueAbsoluteFromObject(1);
    	acknowledgement.getItems().getItemAt(1).getDestination().setCollectionFromCode(store01P02);
    	testCase.computeChanges(acknowledgement);
    	
    	testCase.assertNotNull(acknowledgement.getItems().getItemAt(0).getSource().getCollection());
    	testCase.assertEqualsNumber("-8", acknowledgement.getItems().getItemAt(0).getSource().getValue());
    	
    	testCase.assertNotNull(acknowledgement.getItems().getItemAt(0).getDestination().getCollection());
    	testCase.assertEqualsNumber("8", acknowledgement.getItems().getItemAt(0).getDestination().getValue());
    	
    	testCase.assertNotNull(acknowledgement.getItems().getItemAt(1).getSource().getCollection());    	
    	testCase.assertNotNull(acknowledgement.getItems().getItemAt(1).getDestination().getCollection());
    	
    	testCase.create(acknowledgement);
    	
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionValuesTransferAcknowlegdmentWithInventoryFirst(){
    	TestCase testCase = instanciateTestCase(); 
    	String warehouse01 = testCase.create(testCase.instanciateOne(Party.class,testCase.getRandomAlphabetic())).getCode();
    	String warehouse02 = testCase.create(testCase.instanciateOne(Party.class,testCase.getRandomAlphabetic())).getCode();
    	
    	String store01 = testCase.create(testCase.instanciateOne(Party.class,testCase.getRandomAlphabetic())).getCode();
    	//String store02 = testCase.create(testCase.instanciateOne(Party.class,testCase.getRandomAlphabetic())).getCode();
    	
    	String warehouse01P01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,warehouse01P01).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(warehouse01P01).setIdentifiableGlobalIdentifierFromCode(Party.class, warehouse01));
    	
    	String warehouse01P02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,warehouse01P02).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(warehouse01P02).setIdentifiableGlobalIdentifierFromCode(Party.class, warehouse01));
    	
    	String warehouse02P01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,warehouse02P01).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(warehouse02P01).setIdentifiableGlobalIdentifierFromCode(Party.class, warehouse02));
    	
    	String store01P01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,store01P01).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(store01P01).setIdentifiableGlobalIdentifierFromCode(Party.class, store01));
    	
    	String store01P02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,store01P02).setIsCreateBufferAutomatically(Boolean.TRUE));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(store01P02).setIdentifiableGlobalIdentifierFromCode(Party.class, store01));
    	
    	String inventory01 = testCase.getRandomAlphabetic();
    	MovementCollectionInventory inventory = testCase.instanciateOne(MovementCollectionInventory.class,inventory01).setPartyFromCode(warehouse01);
    	testCase.computeChanges(inventory);
    	inventory.setValueFromObject(0, 8).setValueFromObject(1, 10);
    	testCase.create(inventory);
    	
    	String transfer01 = testCase.getRandomAlphabetic();
    	MovementCollectionValuesTransfer transfer = testCase.instanciateOne(MovementCollectionValuesTransfer.class,transfer01);
    	transfer.getItems().setPartyFromCode(warehouse01);
    	testCase.computeChanges(transfer);
    	transfer.setSourceValueAbsoluteFromObject(0, 3).setSourceValueAbsoluteFromObject(1, 5);
    	testCase.create(transfer);
    	
    	String acknowledgement01 = testCase.getRandomAlphabetic();
    	MovementCollectionValuesTransferAcknowledgement acknowledgement = testCase.instanciateOne(MovementCollectionValuesTransferAcknowledgement.class
    			,acknowledgement01);
    	acknowledgement.setTransferFromCode(transfer01);
    	testCase.computeChanges(acknowledgement);
    	acknowledgement.setDestinationCollectionFromCode(0, store01P01);
    	testCase.computeChanges(acknowledgement.getItemAt(0));
    	acknowledgement.setSourceValueAbsoluteFromObject(0, 3);
    	testCase.computeChanges(acknowledgement.getItemAt(0));
    	
    	acknowledgement.setDestinationCollectionFromCode(1, store01P02);
    	testCase.computeChanges(acknowledgement.getItemAt(1));
    	acknowledgement.setSourceValueAbsoluteFromObject(1, 5);
    	testCase.computeChanges(acknowledgement.getItemAt(1));
    	
    	testCase.create(acknowledgement);
    	
    	testCase.clean();
    }
    
    /* Group */
    
    @Test
    public void crudOneMovementGroup(){
    	TestCase testCase = instanciateTestCase(); 
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementGroup.class,code));
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementGroupWithOneItem(){
    	TestCase testCase = instanciateTestCase(); 
    	String movementCollectionCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode01));
    	
    	String movementCollectionCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode02));
    	
    	String movementGroupCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementGroup.class,movementGroupCode));
    	String movementCode01 = testCase.create(testCase.instanciateOne(MovementGroupItem.class).setCollectionFromCode(movementGroupCode)
    			.setMovementCollectionFromCode(movementCollectionCode01).setMovementValueFromObject(1)).getMovement().getCode();
    	
    	String movementCode02 = testCase.create(testCase.instanciateOne(MovementGroupItem.class).setCollectionFromCode(movementGroupCode)
    			.setMovementCollectionFromCode(movementCollectionCode02).setMovementValueFromObject(1)).getMovement().getCode();
    	
    	testCase.assertEquals(movementCollectionCode01, testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Movement.class, movementCode01).getCollection().getCode());
    	testCase.assertEquals(movementCollectionCode02, testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Movement.class, movementCode02).getCollection().getCode());
    	
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementGroupWithJoinToParty(){
    	TestCase testCase = instanciateTestCase(); 
    	String partyCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Party.class,partyCode01));
    	
    	String partyCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Party.class,partyCode02));
    	
    	String movementCollectionCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode01));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(movementCollectionCode01).setIdentifiableGlobalIdentifierFromCode(Party.class, partyCode01));
    	
    	String movementCollectionCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode02));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(movementCollectionCode02).setIdentifiableGlobalIdentifierFromCode(Party.class, partyCode01));
    	
    	String movementCollectionCode03 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode03));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(movementCollectionCode03).setIdentifiableGlobalIdentifierFromCode(Party.class, partyCode02));
    	
    	String movementGroupCode = testCase.getRandomAlphabetic();
    	MovementGroup movementGroup = testCase.instanciateOne(MovementGroup.class,movementGroupCode).setPartyFromCode(partyCode01);
    	testCase.computeChanges(movementGroup);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementGroup.getItems().getElements()));
    	testCase.computeChanges(movementGroup);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementGroup.getItems().getElements()));
    	
    	movementGroup.setPartyFromCode(partyCode02);
    	testCase.computeChanges(movementGroup);
    	testCase.assertEqualsNumber(1, CollectionHelper.getInstance().getSize(movementGroup.getItems().getElements()));
    	
    	movementGroup.setPartyFromCode(partyCode01);
    	testCase.computeChanges(movementGroup);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementGroup.getItems().getElements()));
    	
    	CollectionHelper.getInstance().getElementAt(movementGroup.getItems().getElements(), 0).getMovement().setValueFromObject(8);
    	CollectionHelper.getInstance().getElementAt(movementGroup.getItems().getElements(), 1).getMovement().setValueFromObject(1);
    	testCase.create(movementGroup);
    	
    	movementGroupCode = testCase.getRandomAlphabetic();
    	movementGroup = testCase.instanciateOne(MovementGroup.class,movementGroupCode).setPartyFromCode(partyCode01);
    	testCase.computeChanges(movementGroup);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementGroup.getItems().getElements()));
    	
    	testCase.assertNotNull(CollectionHelper.getInstance().getElementAt(movementGroup.getItems().getElements(), 0).getMovement().getCollection());
    	testCase.assertNotNull(CollectionHelper.getInstance().getElementAt(movementGroup.getItems().getElements(), 1).getMovement().getCollection());
    	   	
    	testCase.clean();
    }
    
    /* Inventory */
    
    @Test
    public void crudOneMovementCollectionInventory(){
    	TestCase testCase = instanciateTestCase(); 
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionInventory.class,code));
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionInventoryWithGeneratedCode(){
    	TestCase testCase = instanciateTestCase(); 
    	String movementCollectionInventoryCode = testCase.create(testCase.instanciateOne(MovementCollectionInventory.class)
    			.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDateFromString("12/3/2015 10:50")).getCode();
    	testCase.assertEquals("INV1203201510500001", movementCollectionInventoryCode);
    	
    	movementCollectionInventoryCode = testCase.create(testCase.instanciateOne(MovementCollectionInventory.class)
    			.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDateFromString("12/3/2015 10:50")).getCode();
    	testCase.assertEquals("INV1203201510500002", movementCollectionInventoryCode);
    	
    	movementCollectionInventoryCode = testCase.create(testCase.instanciateOne(MovementCollectionInventory.class)
    			.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDateFromString("12/3/2015 10:50")).getCode();
    	testCase.assertEquals("INV1203201510500003", movementCollectionInventoryCode);
    	
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionInventoryWithJoinToParty(){
    	TestCase testCase = instanciateTestCase(); 
    	String partyCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Party.class,partyCode01));
    	
    	String partyCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Party.class,partyCode02));
    	
    	String movementCollectionCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode01));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(movementCollectionCode01).setIdentifiableGlobalIdentifierFromCode(Party.class, partyCode01));
    	
    	String movementCollectionCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode02));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(movementCollectionCode02).setIdentifiableGlobalIdentifierFromCode(Party.class, partyCode01));
    	
    	String movementCollectionCode03 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode03));
    	testCase.create(testCase.instanciateOne(MovementCollectionIdentifiableGlobalIdentifier.class)
    			.setMovementCollectionFromCode(movementCollectionCode03).setIdentifiableGlobalIdentifierFromCode(Party.class, partyCode02));
    	
    	String movementCollectionInventoryCode = testCase.getRandomAlphabetic();
    	MovementCollectionInventory movementCollectionInventory = testCase.instanciateOne(MovementCollectionInventory.class,movementCollectionInventoryCode).setPartyFromCode(partyCode01);
    	testCase.computeChanges(movementCollectionInventory);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementCollectionInventory.getItems().getElements()));
    	testCase.computeChanges(movementCollectionInventory);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementCollectionInventory.getItems().getElements()));
    	
    	movementCollectionInventory.setPartyFromCode(partyCode02);
    	testCase.computeChanges(movementCollectionInventory);
    	testCase.assertEqualsNumber(1, CollectionHelper.getInstance().getSize(movementCollectionInventory.getItems().getElements()));
    	
    	movementCollectionInventory.setPartyFromCode(partyCode01);
    	testCase.computeChanges(movementCollectionInventory);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementCollectionInventory.getItems().getElements()));
    	
    	CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 0).setValueFromObject(8);
    	CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 1).setValueFromObject(1);
    	testCase.create(movementCollectionInventory);
    	
    	movementCollectionInventoryCode = testCase.getRandomAlphabetic();
    	movementCollectionInventory = testCase.instanciateOne(MovementCollectionInventory.class,movementCollectionInventoryCode).setPartyFromCode(partyCode01);
    	testCase.computeChanges(movementCollectionInventory);
    	testCase.assertEqualsNumber(2, CollectionHelper.getInstance().getSize(movementCollectionInventory.getItems().getElements()));
    	
    	testCase.assertNotNull(CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 0).getMovementCollection());
    	testCase.computeChanges(CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 0));
    	testCase.assertNull(CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 0).getValueGapMovementGroupItem());
    	testCase.assertNull(CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 0).getValueGap());
    	
    	testCase.assertNotNull(CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 1).getMovementCollection());
    	testCase.computeChanges(CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 1));
    	testCase.assertNull(CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 1).getValueGapMovementGroupItem());
    	testCase.assertNull(CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 1).getValueGap());
    	
    	CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 0).setValueFromObject(2);
    	CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 1).setValueFromObject(1);
    	
    	testCase.computeChanges(movementCollectionInventory);
    	testCase.assertNotNull(CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 0).getValueGapMovementGroupItem());
    	testCase.assertNotNull(CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 0).getValueGap());
    	testCase.assertNull(CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 1).getValueGapMovementGroupItem());
    	testCase.assertNotNull(CollectionHelper.getInstance().getElementAt(movementCollectionInventory.getItems().getElements(), 1).getValueGap());
    	
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionInventoryWithGroup(){
    	TestCase testCase = instanciateTestCase(); 
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionInventory.class,code).setMovementGroup(inject(MovementGroupBusiness.class).instanciateOne()));
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionInventoryItem(){
    	TestCase testCase = instanciateTestCase(); 
    	
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode));
    	
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionInventoryItem.class,code).setMovementCollectionFromCode(movementCollectionCode)
    			.setValueFromObject(0));
    	
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionInventoryWithOneItemNoGap(){
    	TestCase testCase = instanciateTestCase(); 
    	
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode).setValueFromObject(10));
    	
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionInventoryItem.class,code).setMovementCollectionFromCode(movementCollectionCode)
    			.setValueFromObject(10));
    	
    	testCase.assertMovementCollection(movementCollectionCode, "10", 0);
    	testCase.assertCountAll(MovementGroup.class);
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionInventoryWithOneItemWithPositiveGap(){
    	TestCase testCase = instanciateTestCase(); 
    	
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode).setValueFromObject(10));
    	
    	String movementCollectionInventoryCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionInventory.class,movementCollectionInventoryCode));
    	
    	testCase.create(testCase.instanciateOne(MovementCollectionInventoryItem.class)
    			.setCollectionFromCode(movementCollectionInventoryCode)
    			.setMovementCollectionFromCode(movementCollectionCode)
    			.setValueFromObject(12)
    			).getCode();
    	
    	testCase.assertMovements(movementCollectionCode
    			, new String[] {"0","2","12","true"});
    	testCase.assertCountAll(MovementGroup.class, 1);
    	testCase.assertCountAll(MovementGroupItem.class, 1);
    	testCase.assertCountAll(Movement.class, 1);
    	
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionInventoryWithOneItemWithNegativeGap(){
    	TestCase testCase = instanciateTestCase(); 
    	
    	String movementCollectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class, movementCollectionCode).setValueFromObject(10));
    	
    	String movementCollectionInventoryCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionInventory.class,movementCollectionInventoryCode));
    	
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollectionInventoryItem.class,code).setCollectionFromCode(movementCollectionInventoryCode)
    			.setMovementCollectionFromCode(movementCollectionCode)
    			.setValueFromObject(7));
    	
    	testCase.assertMovements(movementCollectionCode
    			, new String[] {"0","-3","7","false"});
    	testCase.assertCountAll(MovementGroup.class, 1);
    	
    	testCase.clean();
    }
    
    /* Exceptions */
    
	
    @Test
    public void throwCollectionIsNull(){
		TestCase testCase = instanciateTestCase();
		testCase.assertThrowable(new Runnable(testCase) {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class).setValueFromObject(1));}
    	}, FieldHelper.Field.get(Movement.class,Movement.FIELD_COLLECTION).getIdentifier(ConditionHelper.Condition.Builder.Null.class), null);
    	testCase.clean();
	}
	
	@Test
    public void throwValueIsNull(){
		TestCase testCase = instanciateTestCase();
    	final String collectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode));
		testCase.assertThrowable(new Runnable(testCase) {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class).setCollectionFromCode(collectionCode));}
    	}, null, "La valeur de l'attribut <<valeur>> de l'entité <<mouvement>> doit être non nulle.");
	}
	
	@Test
    public void throwIdentifiableCollectionIsNull(){
		TestCase testCase = instanciateTestCase();
    	final String collectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode).setTypeFromCode(RootConstant.Code.MovementCollectionType.CASH_REGISTER));
		testCase.assertThrowable(new Runnable(testCase) {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class).setCollectionFromCode(collectionCode).setValueFromObject(1));}
    	}, null, "La valeur de l'attribut <<période identifiable>> de l'entité <<mouvement>> doit être non nulle.");
	}
	
	@Test
    public void throwDoesNotBelongsToIdentifiablePeriod(){
		TestCase testCase = instanciateTestCase();
		
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
	
	@Test
    public void throwValueIsZero(){
		TestCase testCase = instanciateTestCase();
    	final String collectionCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode));
		testCase.assertThrowable(new Runnable(testCase) {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class).setCollectionFromCode(collectionCode).setValueFromObject(0));}	
    	}, null, "La valeur(0) de l'attribut <<valeur>> de l'entité <<mouvement>> doit être différente à 0.");
		testCase.clean();
	}
	
	@Test
    public void throwMovementCollectionValueMustBeGreaterThanZero(){
		TestCase testCase = instanciateTestCase();
    	testCase.assertThrowable(new Runnable(testCase) {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {create(inject(MovementCollectionBusiness.class).instanciateOne(testCase.getRandomAlphabetic())
	    			.setTypeFromCode(RootConstant.Code.MovementCollectionType.STOCK_REGISTER).setValueFromObject(-1));}	
    	}, null, "La valeur(-1) de l'attribut <<valeur>> de l'entité <<collection de mouvement>> doit être supérieure ou égale à 0.");
		testCase.clean();
    }
    
    /**/
    
	/**/
	
    @SuppressWarnings("unchecked")
	public static class Data extends DataSet.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@SuppressWarnings({ "rawtypes" })
		@Override
		public Collection getClasses() {
			return Arrays.asList(Movement.class,Interval.class,IdentifiablePeriod.class,Value.class,Party.class);
		}
		
    }
}
