package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.information.IdentifiableCollectionBusiness;
import org.cyk.system.root.business.api.information.IdentifiableCollectionItemBusiness;
import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionTypeBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.Runnable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.information.IdentifiableCollection;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.mathematics.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.MovementCollectionType;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.RandomHelper;
import org.cyk.utility.common.helper.TimeHelper;
import org.junit.Test;

public class MovementBusinessIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    @Test
    public void crudOneMovementCollectionType(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollectionType movementCollectionType = inject(MovementCollectionTypeBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(5));
    	assertNotNull(movementCollectionType.getInterval());
    	assertNotNull(movementCollectionType.getIncrementAction());
    	assertNotNull(movementCollectionType.getDecrementAction());
    	testCase.create(movementCollectionType);
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollection(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(5));
    	assertNotNull(movementCollection.getType());
    	assertNotNull(movementCollection.getType().getInterval());
    	movementCollection.getType().getInterval().getLow().setValue(null);
    	testCase.create(movementCollection);
    	testCase.clean();
    }
    
    @Test
    public void instanciateOneMovement(){
    	TestCase testCase = instanciateTestCase();
    	String collectionCode = RandomHelper.getInstance().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode));
    	testCase.assertNotNull(MovementCollection.class, collectionCode);
    	
    	Movement movement = testCase.instanciateOne(Movement.class,"code").setCollectionFromCode(collectionCode);
    	assertEquals("code", movement.getCode());
    	assertNotNull(movement.getCollection());
    	testCase.clean();
    }
    
    @Test
    public void instanciateOneMovementInIdentifiablePeriod(){
    	final TestCase testCase = instanciateTestCase();
    	final String collectionCode = RandomHelper.getInstance().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode).setTypeFromCode(RootConstant.Code.MovementCollectionType.CASH_REGISTER));
    	testCase.assertNotNull(MovementCollection.class, collectionCode);
    	
    	final String identifiablePeriodCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode)
    			.setBirthDate(date(2000, 1, 1, 0, 0)).setDeathDate(date(2000, 1, 1, 23, 59)));
    	
    	testCase.computeChanges(testCase.instanciateOne(Movement.class).setCollectionFromCode(collectionCode).__setBirthDateComputedByUser__(Boolean.TRUE)
    			.setBirthDateFromString("1/1/2000 1:0").setIdentifiablePeriodFromCode(identifiablePeriodCode));
    	
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
    	String movementCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode));
    	
    	IdentifiablePeriod identifiablePeriod = testCase.instanciateOne(IdentifiablePeriod.class);
		identifiablePeriod.setCode(testCase.getRandomHelper().getAlphabetic(5));
		identifiablePeriod.setBirthDate(date(2000, 1, 1, 0, 0));
		identifiablePeriod.setDeathDate(date(2000, 1, 1, 23, 59));
		testCase.create(identifiablePeriod);
		
    	Movement movement = testCase.instanciateOneMovement(null,movementCollectionCode);
    	movement.setValue(BigDecimal.ONE).setIdentifiablePeriod(identifiablePeriod).setBirthDate(date(2000, 1, 1, 0, 5));
    	testCase.create(movement);
    	testCase.assertMovement(movement.getCode(), "1", "1");
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementWithoutSpecifiedDate(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(MovementCollection.class,movementCollectionCode).setValue(BigDecimal.ZERO));
    	
    	IdentifiablePeriod identifiablePeriod = testCase.instanciateOne(IdentifiablePeriod.class);
		identifiablePeriod.setCode(testCase.getRandomHelper().getAlphabetic(5));
		identifiablePeriod.setBirthDate(date(2000, 1, 1, 0, 0));
		identifiablePeriod.setDeathDate(date(2000, 1, 1, 23, 59));
		testCase.create(identifiablePeriod);
		
    	Movement movement = testCase.instanciateOneMovement(null,movementCollectionCode);
    	movement.setValue(BigDecimal.ONE).setIdentifiablePeriod(identifiablePeriod);
    	testCase.create(movement);
    	movement = testCase.read(Movement.class, movement.getCode());
    	assertNotNull(movement.getBirthDate());
    	testCase.assertMovement(movement.getCode(), "1", "1");
    	testCase.clean();
    }
    
    @Test
    public void crudOneMovementCollectionAndMovements(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(5));
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
    
    @Test
    public void useValueAbsolute(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode = RandomHelper.getInstance().getAlphabetic(5);
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
    
    @Test
    public void useNullAction(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode = RandomHelper.getInstance().getAlphabetic(5);
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
    
    @Test
    public void addSequenceAscending(){
    	TestCase testCase = instanciateTestCase();
    	String movementCollectionCode = RandomHelper.getInstance().getAlphabetic(5);
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
    
    @Test
    public void addSequenceDescending(){
    	TestCase testCase = instanciateTestCase();
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
    
    @Test
    public void findPrevious(){
    	TestCase testCase = instanciateTestCase();
    	String movementUpdatesUnlimitedIdentifier = RandomHelper.getInstance().getAlphabetic(5);
    	MovementCollection movementUpdatesUnlimited = inject(MovementCollectionBusiness.class).instanciateOne(movementUpdatesUnlimitedIdentifier);
    	movementUpdatesUnlimited.setValue(new BigDecimal("0"));
    	testCase.create(movementUpdatesUnlimited);
    	
    	String movementUnlimitedIdentifier = RandomHelper.getInstance().getAlphabetic(5);
    	MovementCollection movementUnlimited = inject(MovementCollectionBusiness.class).instanciateOne(movementUnlimitedIdentifier);
    	movementUnlimited.setValue(new BigDecimal("0"));
    	testCase.create(movementUnlimited);
    	
    	Movement movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "15",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(date(2000, 5, 2));
    	movement = testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.assertMovement(code001, "15","15",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class,code001);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 0);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(date(2000, 5, 3));
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,movement, 1);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(date(2000, 5, 1));
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	testCase.assertMovement(code002, "10","10",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code002 , code001);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 1);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.__setBirthDateComputedByUser__(Boolean.TRUE).setBirthDate(date(2000, 5, 3));
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	testCase.assertMovement(code003, "10","35",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code002 ,code001, code003);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 1);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code003, 2);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUnlimitedIdentifier, "10",Boolean.TRUE);
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
    public void doMovementsAndUpdates(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(5));
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
    
    @Test
    public void filter(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollection movementCollection01 = inject(MovementCollectionBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(5));
    	testCase.create(movementCollection01);
    	
    	MovementCollection movementCollection02 = inject(MovementCollectionBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(5));
    	testCase.create(movementCollection02);
    	
    	MovementCollection movementCollection03 = inject(MovementCollectionBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(5));
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
    
    @Test
    public void crudOneMovementCollectionIdentifiableGlobalIdentifier(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(5));
    	movementCollection.setValue(new BigDecimal("0"));
    	testCase.create(movementCollection);
    	
    	Sex sex = new Sex();
    	String sexCode = RandomHelper.getInstance().getAlphabetic(5);
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
		
		String movementCollectionCode = RandomHelper.getInstance().getAlphabetic(5);
    	testCase.create(inject(MovementCollectionBusiness.class).instanciateOne(movementCollectionCode).setValue(new BigDecimal("7")));
    	
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
    	String invoiceMovementCollectionCode = RandomHelper.getInstance().getAlphabetic(5);
    	testCase.create(inject(MovementCollectionBusiness.class).instanciateOne(invoiceMovementCollectionCode).setValue(new BigDecimal("100")));
    	String cashRegisterMovementCollectionCode = RandomHelper.getInstance().getAlphabetic(5);
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
	
	//@Test
    public void crudMovementsWithDestinationAndIdentifiableCollection(){
		TestCase testCase = instanciateTestCase();
    	String invoiceMovementCollectionCode = RandomHelper.getInstance().getAlphabetic(5);
    	MovementCollection invoiceMovementCollection = testCase.create(inject(MovementCollectionBusiness.class).instanciateOne(invoiceMovementCollectionCode).setValue(new BigDecimal("100")));
    	String cashRegisterMovementCollectionCode = RandomHelper.getInstance().getAlphabetic(5);
    	testCase.create(inject(MovementCollectionBusiness.class).instanciateOne(cashRegisterMovementCollectionCode).setValue(new BigDecimal("0")));
    	
    	IdentifiableCollection identifiableCollection = inject(IdentifiableCollectionBusiness.class).instanciateOne();
    	identifiableCollection.getItems().setSynchonizationEnabled(Boolean.TRUE);
    	
    	String code001 = RandomHelper.getInstance().getAlphabetic(5);
    	Movement movement = inject(MovementBusiness.class).instanciateOne(code001,invoiceMovementCollectionCode, "-15",Boolean.FALSE);
    	movement.setCollection(invoiceMovementCollection);//Trick to make computation work , TODO upgrade computation code
    	movement.setDestinationMovementCollection(inject(MovementCollectionDao.class).read(cashRegisterMovementCollectionCode));
    	inject(IdentifiableCollectionItemBusiness.class).instanciateOne(identifiableCollection).addIdentifiables(movement);
    	
    	String code002 = RandomHelper.getInstance().getAlphabetic(5);
    	movement = inject(MovementBusiness.class).instanciateOne(code002,invoiceMovementCollectionCode, "-10",Boolean.FALSE);
    	movement.setCollection(invoiceMovementCollection);
    	//movement.setDestinationMovementCollection(inject(MovementCollectionDao.class).read(cashRegisterMovementCollectionCode));
    	inject(IdentifiableCollectionItemBusiness.class).instanciateOne(identifiableCollection).addIdentifiables(movement);
    	
    	String code003 = RandomHelper.getInstance().getAlphabetic(5);
    	movement = inject(MovementBusiness.class).instanciateOne(code003,invoiceMovementCollectionCode, "-7",Boolean.FALSE);
    	movement.setCollection(invoiceMovementCollection);
    	//movement.setDestinationMovementCollection(inject(MovementCollectionDao.class).read(cashRegisterMovementCollectionCode));
    	inject(IdentifiableCollectionItemBusiness.class).instanciateOne(identifiableCollection).addIdentifiables(movement);
    	
    	testCase.create(identifiableCollection);
    	
    	code001 = invoiceMovementCollectionCode+"_"+code001;
    	code002 = invoiceMovementCollectionCode+"_"+code002;
    	code003 = invoiceMovementCollectionCode+"_"+code003;
    	
    	testCase.assertMovement(code001, "-15","-15",Boolean.FALSE);
    	testCase.assertMovementCollection(invoiceMovementCollectionCode, "68","3");
    	testCase.assertMovement(code002, "-10","-25",Boolean.FALSE);
    	testCase.assertMovementCollection(invoiceMovementCollectionCode, "68","3");
    	testCase.assertMovement(code003, "-7","-32",Boolean.FALSE);
    	testCase.assertMovementCollection(invoiceMovementCollectionCode, "68","3");
    	
    	testCase.clean();
	}
	
	@Test
    public void createOneMovementAndOneChildren(){
		TestCase testCase = instanciateTestCase();
		IdentifiablePeriod identifiablePeriod = testCase.instanciateOne(IdentifiablePeriod.class);
		identifiablePeriod.setCode("j001");
		identifiablePeriod.setBirthDate(date(2000, 1, 1, 0, 0));
		identifiablePeriod.setDeathDate(date(2000, 1, 1, 23, 59));
		testCase.create(identifiablePeriod);
		
		String saleMovementCollectionCode = RandomHelper.getInstance().getAlphabetic(2);
		MovementCollection saleMovementCollection = testCase.create(testCase.instanciateOne(MovementCollection.class,saleMovementCollectionCode)
				.setValue(new BigDecimal("1000")));
		MovementCollection cashRegisterMovementCollection = testCase.read(MovementCollection.class, RootConstant.Code.MovementCollection.CASH_REGISTER);
		
		String cashRegisterMovementCode = RandomHelper.getInstance().getAlphabetic(5);
		Movement cashRegisterMovement = testCase.instanciateOne(Movement.class,cashRegisterMovementCode).__set__(RootConstant.Code.MovementCollection.CASH_REGISTER
				, Boolean.TRUE, 100, Boolean.TRUE, "1/1/2000 0:5").setParentActionIsOppositeOfChildAction(Boolean.TRUE).setIdentifiablePeriod(identifiablePeriod);
		String saleMovementCode = RandomHelper.getInstance().getAlphabetic(5);
		cashRegisterMovement.addIdentifiables(testCase.instanciateOne(Movement.class,saleMovementCode).__set__(saleMovementCollectionCode, Boolean.FALSE, -100, null, null)
				.setParent(cashRegisterMovement));
		testCase.create(cashRegisterMovement);
		
		testCase.assertNotNull(Movement.class, RootConstant.Code.generate(cashRegisterMovementCollection,cashRegisterMovementCode)
				,RootConstant.Code.generate(saleMovementCollection,saleMovementCode));
		testCase.assertMovement(RootConstant.Code.generate(cashRegisterMovementCollection,cashRegisterMovementCode), "100", "100",Boolean.TRUE);
		testCase.assertMovement(RootConstant.Code.generate(saleMovementCollection,saleMovementCode), "-100", "900",Boolean.FALSE);
		testCase.clean();
    }
	
	@Test
    public void createOneMovementAndManyChildren(){
		TestCase testCase = instanciateTestCase();
		String collectionCode = RandomHelper.getInstance().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode));
    	
		IdentifiablePeriod identifiablePeriod = testCase.instanciateOne(IdentifiablePeriod.class);
		identifiablePeriod.setCode("j001");
		identifiablePeriod.setBirthDate(date(2000, 1, 1, 0, 0));
		identifiablePeriod.setDeathDate(date(2000, 1, 1, 23, 59));
		testCase.create(identifiablePeriod);
		
		String parentCode = RandomHelper.getInstance().getAlphabetic(5);
		Movement parent = testCase.instanciateOne(Movement.class,parentCode)
				.__set__(RootConstant.Code.MovementCollection.CASH_REGISTER, Boolean.TRUE, 100, Boolean.TRUE, "1/1/2000 0:5").setIdentifiablePeriod(identifiablePeriod);
		String child1Code = RandomHelper.getInstance().getAlphabetic(5);
		parent.addIdentifiables(testCase.instanciateOne(Movement.class,child1Code).__set__(collectionCode, Boolean.TRUE, 35).setParent(parent));
		String child2Code = RandomHelper.getInstance().getAlphabetic(5);
		parent.addIdentifiables(testCase.instanciateOne(Movement.class,child2Code).__set__(collectionCode, Boolean.TRUE, 25).setParent(parent));
		String child3Code = RandomHelper.getInstance().getAlphabetic(5);
		parent.addIdentifiables(testCase.instanciateOne(Movement.class,child3Code).__set__(collectionCode, Boolean.TRUE, 40).setParent(parent));
		testCase.create(parent);
		testCase.assertNotNull(Movement.class, RootConstant.Code.MovementCollection.CASH_REGISTER+Constant.CHARACTER_UNDESCORE+parentCode
				,collectionCode+Constant.CHARACTER_UNDESCORE+child1Code,collectionCode+Constant.CHARACTER_UNDESCORE+child2Code
				,collectionCode+Constant.CHARACTER_UNDESCORE+child3Code);
		testCase.clean();
    }
    
    /* Exceptions */
    
	@Test
    public void throwCollectionIsNull(){
		TestCase testCase = instanciateTestCase();
    	testCase.assertThrowable(new Runnable(testCase) {
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class));}
    	}, MovementBusiness.THROWABLE_IDENTIFIER_COLLECTION_NOT_NULL, null);
    	testCase.clean();
	}
	
	@Test
    public void throwValueIsNull(){
		TestCase testCase = instanciateTestCase();
    	final String collectionCode = RandomHelper.getInstance().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode));
		testCase.assertThrowable(new Runnable(testCase) {
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class).setCollectionFromCode(collectionCode));}
    	}, null, "Valeur : ne peut pas être nul");
	}
	
	@Test
    public void throwValueIsZero(){
		TestCase testCase = instanciateTestCase();
    	final String collectionCode = RandomHelper.getInstance().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode));
		testCase.assertThrowable(new Runnable(testCase) {
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class).setCollectionFromCode(collectionCode).setValueFromObject(0));}	
    	}, null, "La ##movvalue##(0) doit être différente à 0.");
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
			return Arrays.asList(Movement.class,IdentifiablePeriod.class,Value.class);
		}
		
    }
}
