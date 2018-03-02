package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.business.api.time.IdentifiablePeriodBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.Runnable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodType;
import org.cyk.system.root.model.value.Value;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.RandomHelper;
import org.junit.Test;

public class IdentifiablePeriodBusinessIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    @Test
    public void crudOneIdentifiablePeriodType(){
    	TestCase testCase = instanciateTestCase();
    	String identifiablePeriodTypeCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodType.class,identifiablePeriodTypeCode)
    			.setTimeDivisionTypeFromCode(RootConstant.Code.TimeDivisionType.DAY));
    	testCase.clean();
    }
    
    @Test
    public void crudOneIdentifiablePeriod(){
    	TestCase testCase = instanciateTestCase();
    	
    	String identifiablePeriodCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode));
    	
    	testCase.clean();
    }
    
    @Test
    public void crudTwoIdentifiablePeriod(){
    	TestCase testCase = instanciateTestCase();
    	
    	String identifiablePeriodCode01 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode01));
    	
    	String identifiablePeriodCode02 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode02));
    	
    	testCase.clean();
    }
    
    @Test
    public void crudOneIdentifiablePeriodWithDateSetByUser(){
    	TestCase testCase = instanciateTestCase();
    	
    	String identifiablePeriodCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("1/1/2000 23:59"));
    	
    	testCase.clean();
    }
     
    @Test
    public void crudOneIdentifiablePeriodWithType(){
    	TestCase testCase = instanciateTestCase();
    	String identifiablePeriodTypeCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodType.class,identifiablePeriodTypeCode)
    			.setTimeDivisionTypeFromCode(RootConstant.Code.TimeDivisionType.DAY));
    	
    	String identifiablePeriodCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("1/1/2000 23:59").setTypeFromCode(identifiablePeriodTypeCode));
    	
    	testCase.clean();
    }
    
    @Test
    public void findPreviousIdentifiablePeriod(){
    	TestCase testCase = instanciateTestCase();
    	String identifiablePeriodTypeCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodType.class,identifiablePeriodTypeCode)
    			.setTimeDivisionTypeFromCode(RootConstant.Code.TimeDivisionType.DAY));
    	
    	assertEquals(Boolean.TRUE, CollectionHelper.getInstance().isEmpty(inject(IdentifiablePeriodBusiness.class)
    			.findByFilter(new IdentifiablePeriod.Filter(), new DataReadConfiguration().setMaximumResultCount(1l))));
    	
    	String identifiablePeriodCode001 = "01_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode001).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("1/1/2000 23:59").setTypeFromCode(identifiablePeriodTypeCode));   	
    	testCase.assertOrderBasedOnExistencePeriodFromDate(IdentifiablePeriod.class, identifiablePeriodCode001);
    	
    	String identifiablePeriodCode002 = "02_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode002).setBirthDateFromString("2/1/2000 0:0")
    			.setDeathDateFromString("2/1/2000 23:59").setTypeFromCode(identifiablePeriodTypeCode));
    	testCase.assertOrderBasedOnExistencePeriodFromDate(IdentifiablePeriod.class, identifiablePeriodCode001,identifiablePeriodCode002);
    	
    	String identifiablePeriodCode003 = "03_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode003).setBirthDateFromString("3/1/2000 0:0")
    			.setDeathDateFromString("3/1/2000 23:59").setTypeFromCode(identifiablePeriodTypeCode));
    	testCase.assertOrderBasedOnExistencePeriodFromDate(IdentifiablePeriod.class, identifiablePeriodCode001,identifiablePeriodCode002
    			,identifiablePeriodCode003);
    	
    	String identifiablePeriodCode004 = "04_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode004).setBirthDateFromString("4/1/2000 0:0")
    			.setDeathDateFromString("4/1/2000 23:59").setTypeFromCode(identifiablePeriodTypeCode));
    	testCase.assertOrderBasedOnExistencePeriodFromDate(IdentifiablePeriod.class, identifiablePeriodCode001,identifiablePeriodCode002
    			,identifiablePeriodCode003,identifiablePeriodCode004);
    	
    	String identifiablePeriodCode005 = "05_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode005).setBirthDateFromString("5/1/2000 0:0")
    			.setDeathDateFromString("5/1/2000 23:59").setTypeFromCode(identifiablePeriodTypeCode));
    	testCase.assertOrderBasedOnExistencePeriodFromDate(IdentifiablePeriod.class, identifiablePeriodCode001,identifiablePeriodCode002
    			,identifiablePeriodCode003,identifiablePeriodCode004,identifiablePeriodCode005);
    	
    	assertEquals(identifiablePeriodCode001, inject(IdentifiablePeriodBusiness.class)
    			.findByFilter(new IdentifiablePeriod.Filter(), new DataReadConfiguration().setMaximumResultCount(1l)).iterator().next().getCode());
    	
    	assertEquals(identifiablePeriodCode002, inject(IdentifiablePeriodBusiness.class)
    			.findByFilter(new IdentifiablePeriod.Filter(), new DataReadConfiguration().setFirstResultIndex(1l).setMaximumResultCount(1l)).iterator().next().getCode());
    	
    	assertEquals(identifiablePeriodCode003, inject(IdentifiablePeriodBusiness.class)
    			.findByFilter(new IdentifiablePeriod.Filter(), new DataReadConfiguration().setFirstResultIndex(2l).setMaximumResultCount(1l)).iterator().next().getCode());
    	
    	assertEquals(identifiablePeriodCode004, inject(IdentifiablePeriodBusiness.class)
    			.findByFilter(new IdentifiablePeriod.Filter(), new DataReadConfiguration().setFirstResultIndex(3l).setMaximumResultCount(1l)).iterator().next().getCode());
    	
    	assertEquals(identifiablePeriodCode005, inject(IdentifiablePeriodBusiness.class)
    			.findByFilter(new IdentifiablePeriod.Filter(), new DataReadConfiguration().setFirstResultIndex(4l).setMaximumResultCount(1l)).iterator().next().getCode());
    	
    	testCase.clean();
    }
    
    @Test
    public void crudOneIdentifiablePeriodWithTypeWithDateGeneratedBySystem(){
    	TestCase testCase = instanciateTestCase();
    	String identifiablePeriodTypeCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodType.class,identifiablePeriodTypeCode)
    			.setTimeDivisionTypeFromCode(RootConstant.Code.TimeDivisionType.DAY));
    	
    	String identifiablePeriodCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode).setTypeFromCode(identifiablePeriodTypeCode));
    	
    	testCase.clean();
    }
    
    @Test
    public void crudTwoIdentifiablePeriodWithoutTypeCrossing(){
    	TestCase testCase = instanciateTestCase();
    	
    	String identifiablePeriodCode01 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode01).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("1/1/2000 23:59"));
    	
    	String identifiablePeriodCode02 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode02).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("1/1/2000 23:59"));
    	
    	testCase.clean();
    }
    
    @Test
    public void crudTwoIdentifiablePeriodWithTypeCrossing(){
    	TestCase testCase = instanciateTestCase();
    	String identifiablePeriodTypeCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodType.class,identifiablePeriodTypeCode)
    			.setTimeDivisionTypeFromCode(RootConstant.Code.TimeDivisionType.DAY));
    	
    	String identifiablePeriodCode01 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode01).setTypeFromCode(identifiablePeriodTypeCode)
    			.setBirthDateFromString("1/1/2000 0:0").setDeathDateFromString("1/1/2000 23:59"));
    	
    	String identifiablePeriodCode02 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode02).setTypeFromCode(identifiablePeriodTypeCode)
    			.setBirthDateFromString("1/1/2000 0:0").setDeathDateFromString("1/1/2000 23:59"));
    	
    	testCase.clean();
    }
    
    /* Exceptions */
    
	//@Test
    public void throwCollectionIsNull(){
		TestCase testCase = instanciateTestCase();
    	testCase.assertThrowable(new Runnable(testCase) {
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class));}
    	}, MovementBusiness.THROWABLE_IDENTIFIER_COLLECTION_NOT_NULL, null);
    	testCase.clean();
	}
	
	//@Test
    public void throwValueIsNull(){
		TestCase testCase = instanciateTestCase();
    	final String collectionCode = RandomHelper.getInstance().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(MovementCollection.class,collectionCode));
		testCase.assertThrowable(new Runnable(testCase) {
			@Override protected void __run__() throws Throwable {create(instanciateOne(Movement.class).setCollectionFromCode(collectionCode));}
    	}, null, "Valeur : ne peut pas être nul");
	}
	
	//@Test
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
			return Arrays.asList(IdentifiablePeriod.class,Value.class);
		}
		
    }
}
