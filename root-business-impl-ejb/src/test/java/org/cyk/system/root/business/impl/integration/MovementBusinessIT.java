package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionTypeBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.business.impl__data__.DataSet;
import org.cyk.system.root.business.impl__data__.RealDataSet;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.mathematics.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.MovementCollectionType;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
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
    
    private String movementUnlimitedIdentifier="MU",movementLimitedIdentifier="ML",movementOnlyUnlimitedIdentifier="MOL"
    		,movementUpdatesUnlimitedIdentifier="MUPL",movementLimitedIdentifier_Low_100_8_High_2_10_Total_0_150="ML10082100150";
    
    @Override
    protected void populate() {
    	super.populate();
    	MovementCollectionType movementCollectionType = inject(MovementCollectionTypeBusiness.class).instanciateOne(movementUnlimitedIdentifier,null, "IN", "OUT");
    	movementCollectionType.getInterval().getLow().setValue(null);
    	create(movementCollectionType);
    	MovementCollection  movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementUnlimitedIdentifier).setType(movementCollectionType);
    	create(movementCollection);
    	
    	movementCollectionType = inject(MovementCollectionTypeBusiness.class).instanciateOne(movementOnlyUnlimitedIdentifier,null, "IN", "OUT");
    	movementCollectionType.getInterval().getLow().setValue(null);
    	create(movementCollectionType);
    	movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementOnlyUnlimitedIdentifier).setType(movementCollectionType);
    	create(movementCollection);
    	
    	movementCollectionType = inject(MovementCollectionTypeBusiness.class).instanciateOne(movementUpdatesUnlimitedIdentifier,null, "IN", "OUT");
    	movementCollectionType.getInterval().getLow().setValue(null);
    	create(movementCollectionType);
    	movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementUpdatesUnlimitedIdentifier).setType(movementCollectionType);
    	create(movementCollection);
    	
    	movementCollectionType = inject(MovementCollectionTypeBusiness.class).instanciateOne(movementUpdatesUnlimitedIdentifier,null, "IN", "OUT");
    	movementCollectionType.getInterval().setHigh(new IntervalExtremity(new BigDecimal("100")));
    	create(movementCollectionType);
    	movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementLimitedIdentifier).setType(movementCollectionType);
    	create(movementCollection);
    	
    	movementCollectionType = inject(MovementCollectionTypeBusiness.class).instanciateOne(movementUpdatesUnlimitedIdentifier,null, "IN", "OUT");
    	movementCollectionType.getInterval().setHigh(new IntervalExtremity(new BigDecimal("150")));
    	movementCollectionType.getDecrementAction().getInterval().getLow().setValue(new BigDecimal("-100"));
    	movementCollectionType.getDecrementAction().getInterval().getHigh().setValue(new BigDecimal("-8"));
    	movementCollectionType.getIncrementAction().getInterval().getLow().setValue(new BigDecimal("2"));
    	movementCollectionType.getIncrementAction().getInterval().getHigh().setValue(new BigDecimal("10"));
    	create(movementCollectionType);
    	movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(movementLimitedIdentifier_Low_100_8_High_2_10_Total_0_150).setType(movementCollectionType);
    	movementCollection.setValue(new BigDecimal("7"));
    	create(movementCollection);
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
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(5));
    	movementCollection.setValue(new BigDecimal("0"));
    	testCase.create(movementCollection);
    	
    	Movement movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), null,Boolean.TRUE);
    	movement.setValueAbsolute(new BigDecimal("15"));
    	movement.setValueSettableFromAbsolute(Boolean.TRUE);
    	testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.read(Movement.class, code001);
    	rootBusinessTestHelper.assertMovement(code001, "15","15", "15",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), null,Boolean.TRUE);
    	movement.setValueAbsolute(new BigDecimal("10"));
    	movement.setValueSettableFromAbsolute(Boolean.TRUE);
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code002, "10","25", "25",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), null,Boolean.FALSE);
    	movement.setValueAbsolute(new BigDecimal("7"));
    	movement.setValueSettableFromAbsolute(Boolean.TRUE);
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code003, "-7","18", "18",Boolean.FALSE);
    	
    	//testCase.clean();
    }
    
    @Test
    public void useNullAction(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(5));
    	movementCollection.setValue(new BigDecimal("0"));
    	testCase.create(movementCollection);
    	
    	Movement movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "15",null);
    	testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.read(Movement.class, code001);
    	rootBusinessTestHelper.assertMovement(code001, "15","15", "15",null);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "10",null);
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code002, "10","25", "25",null);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "-7",null);
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code003, "-7","18", "18",null);
    	
    	//testCase.clean();
    }
    
    @Test
    public void addSequenceAscending(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(5));
    	movementCollection.setValue(new BigDecimal("0"));
    	testCase.create(movementCollection);
    	
    	Movement movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "15",Boolean.TRUE);
    	testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.read(Movement.class, code001);
    	rootBusinessTestHelper.assertMovement(code001, "15","15", "15",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "10",Boolean.TRUE);
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code002, "10","25", "25",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "7",Boolean.TRUE);
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code003, "7","32", "32",Boolean.TRUE);
    	
    	//testCase.clean();
    }
    
    @Test
    public void addSequenceDescending(){
    	TestCase testCase = instanciateTestCase();
    	MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(5));
    	testCase.create(movementCollection);
    	
    	Movement movement;
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "15",Boolean.TRUE);
    	movement.getGlobalIdentifier().getExistencePeriod().setFromDate(TimeHelper.getInstance().getDate(2000, 1, 30));
    	testCase.create(movement);
    	String code001 = movement.getCode();
    	testCase.read(Movement.class, code001);
    	rootBusinessTestHelper.assertMovement(code001, "15","15", "15",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "10",Boolean.TRUE);
    	movement.getGlobalIdentifier().getExistencePeriod().setFromDate(TimeHelper.getInstance().getDate(2000, 1, 29));
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code002, "10","10", "25",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code001, "15","25", "25",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "7",Boolean.TRUE);
    	movement.getGlobalIdentifier().getExistencePeriod().setFromDate(TimeHelper.getInstance().getDate(2000, 1, 28));
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code003, "7","7", "32",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code002, "10","17", "32",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code001, "15","32", "32",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "-7",Boolean.FALSE);
    	movement.getGlobalIdentifier().getExistencePeriod().setFromDate(TimeHelper.getInstance().getDate(2000, 1, 27));
    	movement = testCase.create(movement);
    	String code004 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code004, "-7","-7", "25",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code003, "7","0", "25",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code002, "10","10", "25",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code001, "15","25", "25",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "20",Boolean.TRUE);
    	movement.getGlobalIdentifier().getExistencePeriod().setFromDate(TimeHelper.getInstance().getDate(2000, 1, 26));
    	movement = testCase.create(movement);
    	String code005 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code005, "20","20", "45",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code004, "-7","13", "45",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code003, "7","20", "45",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code002, "10","30", "45",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code001, "15","45", "45",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "8",Boolean.TRUE);
    	movement.getGlobalIdentifier().getExistencePeriod().setFromDate(TimeHelper.getInstance().getDate(2000, 1, 24));
    	movement = testCase.create(movement);
    	String code007 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code007, "8","8", "53",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code005, "20","28", "53",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code004, "-7","21", "53",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code003, "7","28", "53",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code002, "10","38", "53",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code001, "15","53", "53",Boolean.TRUE);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "-3",Boolean.FALSE);
    	movement.getGlobalIdentifier().getExistencePeriod().setFromDate(TimeHelper.getInstance().getDate(2000, 1, 25));
    	movement = testCase.create(movement);
    	String code006 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code007, "8","8", "50",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code006, "-3","5", "50",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code005, "20","25", "50",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code004, "-7","18", "50",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code003, "7","25", "50",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code002, "10","35", "50",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code001, "15","50", "50",Boolean.TRUE);
    	
    	movement = testCase.read(Movement.class, code001);
    	movement.setValue(new BigDecimal("16"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code007, "8","8", "51",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code006, "-3","5", "51",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code005, "20","25", "51",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code004, "-7","18", "51",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code003, "7","25", "51",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code002, "10","35", "51",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code001, "16","51", "51",Boolean.TRUE);
    	
    	movement = testCase.read(Movement.class, code007);
    	movement.setValue(new BigDecimal("17"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code007, "17","17", "60",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code006, "-3","14", "60",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code005, "20","34", "60",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code004, "-7","27", "60",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code003, "7","34", "60",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code002, "10","44", "60",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code001, "16","60", "60",Boolean.TRUE);
    	
    	movement = testCase.read(Movement.class, code004);
    	movement.setValue(new BigDecimal("-13"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code007, "17","17", "54",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code006, "-3","14", "54",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code005, "20","34", "54",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code004, "-13","21", "54",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code003, "7","28", "54",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code002, "10","38", "54",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code001, "16","54", "54",Boolean.TRUE);
    	
    	movement = testCase.read(Movement.class, code007);
    	testCase.delete(movement);
    	rootBusinessTestHelper.assertMovement(code006, "-3","-3", "37",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code005, "20","17", "37",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code004, "-13","4", "37",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code003, "7","11", "37",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code002, "10","21", "37",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code001, "16","37", "37",Boolean.TRUE);
    	
    	movement = testCase.read(Movement.class, code001);
    	testCase.delete(movement);
    	rootBusinessTestHelper.assertMovement(code006, "-3","-3", "21",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code005, "20","17", "21",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code004, "-13","4", "21",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code003, "7","11", "21",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code002, "10","21", "21",Boolean.TRUE);
    	
    	movement = testCase.read(Movement.class, code004);
    	testCase.delete(movement);
    	rootBusinessTestHelper.assertMovement(code006, "-3","-3", "34",Boolean.FALSE);
    	rootBusinessTestHelper.assertMovement(code005, "20","17", "34",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code003, "7","24", "34",Boolean.TRUE);
    	rootBusinessTestHelper.assertMovement(code002, "10","34", "34",Boolean.TRUE);
    	
    	//testCase.clean();
    }
    
    @Test
    public void findPrevious(){
    	TestCase testCase = instanciateTestCase();
    	Movement movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "15",Boolean.TRUE);
    	movement.setBirthDate(date(2000, 5, 2));
    	movement = testCase.create(movement);
    	String code001 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code001, "15","15", "15",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class,code001);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 0);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.setBirthDate(date(2000, 5, 3));
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,movement, 1);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.setBirthDate(date(2000, 5, 1));
    	movement = testCase.create(movement);
    	String code002 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code002, "10","10","25",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code002 , code001);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 1);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUpdatesUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.setBirthDate(date(2000, 5, 3));
    	movement = testCase.create(movement);
    	String code003 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code003, "10","35", "35",Boolean.TRUE);
    	testCase.assertOrderBasedOnExistencePeriodFromDate(Movement.class, code002 ,code001, code003);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code001, 1);
    	testCase.assertWhereExistencePeriodFromDateIsLessThanCount(Movement.class,code003, 2);
    	
    	movement = inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementUnlimitedIdentifier, "10",Boolean.TRUE);
    	movement.setBirthDate(date(2000, 4, 1));
    	movement = testCase.create(movement);
    	String code001A = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code001A, "10","10", "10",Boolean.TRUE);
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
    	rootBusinessTestHelper.assertMovement(code001, "15","15", "15",Boolean.TRUE);
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "10",Boolean.TRUE));
    	String code002 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code002, "10","25", "25",Boolean.TRUE);
    	
    	movement = inject(MovementDao.class).read(code002);
    	movement.setValue(new BigDecimal("6"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code002, "6","21", "21",Boolean.TRUE);
    	
  
    	movement = inject(MovementDao.class).read(code001);
    	movement.setValue(new BigDecimal("10"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code001, "10","10", "16",Boolean.TRUE);
    	
    	movement = inject(MovementDao.class).read(code001);
    	movement.setValue(new BigDecimal("15"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code001, "15","15", "21",Boolean.TRUE);
    	
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "1",Boolean.TRUE));
    	String code003 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code003, "1","22", "22",Boolean.TRUE);
    	
    	movement = inject(MovementDao.class).read(code003);
    	movement.setValue(new BigDecimal("10"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code003, "10","31", "31",Boolean.TRUE);
    	
    	movement = inject(MovementDao.class).read(code003);
    	movement.setValue(new BigDecimal("15"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code003, "15","36", "36",Boolean.TRUE);
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "4",Boolean.TRUE));
    	String code004 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code004, "4","40", "40",Boolean.TRUE);
    	
    	movement = inject(MovementDao.class).read(code004);
    	movement.setAction(movement.getCollection().getType().getDecrementAction());
    	movement.setValue(new BigDecimal("-36"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code004, "-36","0", "0",Boolean.FALSE);
    	
    	movement = inject(MovementDao.class).read(code004);
    	movement.setAction(movement.getCollection().getType().getIncrementAction());
    	movement.setValue(new BigDecimal("1"));
    	testCase.update(movement);
    	rootBusinessTestHelper.assertMovement(code004, "1", "37","37",Boolean.TRUE);
    	
    	movement = inject(MovementDao.class).read(code004);
    	testCase.delete(movement);
    	rootBusinessTestHelper.assertMovementCollection(movementCollection.getCode(), "36");
    	
    	movement = testCase.create(inject(MovementBusiness.class).instanciateOne(RandomHelper.getInstance().getAlphabetic(3),movementCollection.getCode(), "-6",Boolean.FALSE));
    	String code005 = movement.getCode();
    	rootBusinessTestHelper.assertMovement(code005, "-6","30", "30",Boolean.FALSE);
    	
    	//testCase.clean();
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
    		filter.addMasters((Collection<Object>)index[0]);
    		filter.set((String)index[1]);
        	assertEquals((Integer)index[2], inject(MovementBusiness.class).findByFilter(filter, new DataReadConfiguration()).size());
    	}
    }
    
	@Test
	public void computeChanges() {
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
		movement.setAction(movementCollection.getType().getIncrementAction());
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
		rootBusinessTestHelper.assertComputedChanges(movement, "7", "12");

		movement.setCollection(null);
		movement.setAction(movementCollection.getType().getIncrementAction());
		inject(MovementBusiness.class).computeChanges(movement);
		rootBusinessTestHelper.assertComputedChanges(movement, null, null);

		movement.setCollection(movementCollection);
		movement.setValue(new BigDecimal("10"));
		movement.setAction(movementCollection.getType().getIncrementAction());
		inject(MovementBusiness.class).computeChanges(movement);
		rootBusinessTestHelper.assertComputedChanges(movement, "7", "17");

		movement.setValue(new BigDecimal("-10"));
		movement.setAction(movementCollection.getType().getDecrementAction());
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
    
    public static class Data extends DataSet.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Collection getClasses() {
			Collection<Class<?>> classes = new ArrayList<>();
			classes.addAll(RealDataSet.CLASSES_SECURITY);
			classes.addAll(RealDataSet.CLASSES_MATHEMATIQUES);
			return classes;
		}
		
    }
}
