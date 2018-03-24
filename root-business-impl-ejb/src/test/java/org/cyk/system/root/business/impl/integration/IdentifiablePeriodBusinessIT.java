package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.api.time.IdentifiablePeriodBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.Runnable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionType;
import org.cyk.system.root.model.time.IdentifiablePeriodType;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.persistence.api.time.IdentifiablePeriodDao;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.BooleanHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.junit.Test;

public class IdentifiablePeriodBusinessIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    @Test
    public void crudOneIdentifiablePeriodCollectionType(){
    	TestCase testCase = instanciateTestCase();
    	String identifiablePeriodCollectionTypeCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollectionType.class,identifiablePeriodCollectionTypeCode)
    			.setTimeDivisionTypeFromCode(RootConstant.Code.TimeDivisionType.DAY));
    	testCase.clean();
    }
    
    @Test
    public void crudOneIdentifiablePeriodCollectionWithoutType(){
    	TestCase testCase = instanciateTestCase();    	
    	String identifiablePeriodCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	testCase.clean();
    }
    
    @Test
    public void crudOneIdentifiablePeriodCollectionWithType(){
    	TestCase testCase = instanciateTestCase();    	
    	String identifiablePeriodCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode)
    			.setTypeFromCode(RootConstant.Code.IdentifiablePeriodCollectionType.CASH_REGISTER_WORKING_DAY));
    	IdentifiablePeriodCollection identifiablePeriodCollection = testCase.read(IdentifiablePeriodCollection.class, identifiablePeriodCollectionCode);
    	assertNotNull(identifiablePeriodCollection.getType());
    	assertEquals(RootConstant.Code.IdentifiablePeriodCollectionType.CASH_REGISTER_WORKING_DAY, identifiablePeriodCollection.getType().getCode());
    	testCase.clean();
    }
    
    @Test
    public void crudOneIdentifiablePeriod(){
    	TestCase testCase = instanciateTestCase();    	
    	String identifiablePeriodCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	String identifiablePeriodCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode).setCollectionFromCode(identifiablePeriodCollectionCode));
    	testCase.clean();
    }
    
    @Test
    public void crudTwoIdentifiablePeriodWithDateSetBySystem(){
    	TestCase testCase = instanciateTestCase();
    	String identifiablePeriodCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	
    	String identifiablePeriodCode01 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode01).setCollectionFromCode(identifiablePeriodCollectionCode));
    	
    	testCase.update(testCase.readCollectionItem(IdentifiablePeriod.class,IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode,identifiablePeriodCode01)
    			.setClosed(Boolean.TRUE));
    	
    	String identifiablePeriodCode02 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode02).setCollectionFromCode(identifiablePeriodCollectionCode));
    	
    	testCase.clean();
    }
    
    @Test
    public void crudOneIdentifiablePeriodWithDateSetByUser(){
    	TestCase testCase = instanciateTestCase();
    	
    	String identifiablePeriodCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	
    	String identifiablePeriodCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("1/1/2000 23:59").setCollectionFromCode(identifiablePeriodCollectionCode));
    	
    	testCase.clean();
    }
    
    @Test
    public void crudTwoIdentifiablePeriodWithDateSetByUser(){
    	TestCase testCase = instanciateTestCase();
    	
    	String identifiablePeriodCode01 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode01).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("1/1/2000 23:59:59:999").setCollectionFromCode(RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY));
    	
    	testCase.update(testCase.readCollectionItem(IdentifiablePeriod.class,IdentifiablePeriodCollection.class,RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY,identifiablePeriodCode01)
    			.setClosed(Boolean.TRUE));
    	
    	String identifiablePeriodCode02 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode02).setCollectionFromCode(RootConstant.Code.IdentifiablePeriodCollection.CASH_REGISTER_WORKING_DAY));
    	
    	testCase.clean();
    }
     
    @Test
    public void crudOneIdentifiablePeriodWithType(){
    	TestCase testCase = instanciateTestCase();
    	
    	String identifiablePeriodCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	
    	String identifiablePeriodTypeCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodType.class,identifiablePeriodTypeCode));
    	
    	String identifiablePeriodCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("1/1/2000 23:59").setTypeFromCode(identifiablePeriodTypeCode).setCollectionFromCode(identifiablePeriodCollectionCode));
    	
    	testCase.clean();
    }
    
    @Test
    public void findPreviousIdentifiablePeriod(){
    	TestCase testCase = instanciateTestCase();
    	
    	String identifiablePeriodCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	
    	String identifiablePeriodTypeCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodType.class,identifiablePeriodTypeCode));
    	
    	assertEquals(Boolean.TRUE, CollectionHelper.getInstance().isEmpty(inject(IdentifiablePeriodBusiness.class)
    			.findByFilter(new IdentifiablePeriod.Filter(), new DataReadConfiguration().setMaximumResultCount(1l))));
    	
    	String identifiablePeriodCode001 = "01_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode001).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("1/1/2000 23:59").setTypeFromCode(identifiablePeriodTypeCode).setClosed(Boolean.TRUE)
    			.setCollectionFromCode(identifiablePeriodCollectionCode));   	
    	testCase.assertOrderBasedOnExistencePeriodFromDate(IdentifiablePeriod.class, identifiablePeriodCode001);
    	
    	String identifiablePeriodCode002 = "02_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode002).setBirthDateFromString("2/1/2000 0:0")
    			.setDeathDateFromString("2/1/2000 23:59").setTypeFromCode(identifiablePeriodTypeCode).setClosed(Boolean.TRUE)
    			.setCollectionFromCode(identifiablePeriodCollectionCode));
    	testCase.assertOrderBasedOnExistencePeriodFromDate(IdentifiablePeriod.class, identifiablePeriodCode001,identifiablePeriodCode002);
    	
    	String identifiablePeriodCode003 = "03_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode003).setBirthDateFromString("3/1/2000 0:0")
    			.setDeathDateFromString("3/1/2000 23:59").setTypeFromCode(identifiablePeriodTypeCode).setClosed(Boolean.TRUE)
    			.setCollectionFromCode(identifiablePeriodCollectionCode));
    	testCase.assertOrderBasedOnExistencePeriodFromDate(IdentifiablePeriod.class, identifiablePeriodCode001,identifiablePeriodCode002
    			,identifiablePeriodCode003);
    	
    	String identifiablePeriodCode004 = "04_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode004).setBirthDateFromString("4/1/2000 0:0")
    			.setDeathDateFromString("4/1/2000 23:59").setTypeFromCode(identifiablePeriodTypeCode).setClosed(Boolean.TRUE)
    			.setCollectionFromCode(identifiablePeriodCollectionCode));
    	testCase.assertOrderBasedOnExistencePeriodFromDate(IdentifiablePeriod.class, identifiablePeriodCode001,identifiablePeriodCode002
    			,identifiablePeriodCode003,identifiablePeriodCode004);
    	
    	String identifiablePeriodCode005 = "05_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode005).setBirthDateFromString("5/1/2000 0:0")
    			.setDeathDateFromString("5/1/2000 23:59").setTypeFromCode(identifiablePeriodTypeCode).setClosed(Boolean.TRUE)
    			.setCollectionFromCode(identifiablePeriodCollectionCode));
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
    public void findByFilter(){
    	TestCase testCase = instanciateTestCase();
    	String identifiablePeriodCollectionCode01 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode01));
    	String identifiablePeriodCollectionCode02 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode02));
    	String identifiablePeriodCollectionCode03 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode03));
    	
    	String identifiablePeriodCode01_01 = "01_01_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode01_01).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("1/1/2000 23:59").setClosed(Boolean.TRUE).setCollectionFromCode(identifiablePeriodCollectionCode01));   	
    	
    	String identifiablePeriodCode02_01 = "02_01_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode02_01).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("1/1/2000 23:59").setClosed(Boolean.TRUE).setCollectionFromCode(identifiablePeriodCollectionCode02));  
    	
    	String identifiablePeriodCode03_01 = "03_01_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode03_01).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("1/1/2000 23:59").setClosed(Boolean.TRUE).setCollectionFromCode(identifiablePeriodCollectionCode03)); 
    	
    	String identifiablePeriodCode01_02 = "01_02_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode01_02).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("2/1/2000 23:59").setClosed(Boolean.FALSE).setCollectionFromCode(identifiablePeriodCollectionCode01));   	
    	
    	String identifiablePeriodCode02_02 = "02_02_"+testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode02_02).setBirthDateFromString("1/1/2000 0:0")
    			.setDeathDateFromString("2/1/2000 23:59").setClosed(Boolean.FALSE).setCollectionFromCode(identifiablePeriodCollectionCode02)); 
    	
    	assertEquals(inject(IdentifiablePeriodDao.class).countAll(), inject(IdentifiablePeriodDao.class).countByFilter(new IdentifiablePeriod.Filter()));
    	assertEquals(0l, inject(IdentifiablePeriodDao.class).countByFilter(new IdentifiablePeriod.Filter().setClosed(BooleanHelper.NULL)));    	
    	assertEquals(2l, inject(IdentifiablePeriodDao.class).countByFilter(new IdentifiablePeriod.Filter().setClosed(Boolean.FALSE)));    	
    	assertEquals(3l, inject(IdentifiablePeriodDao.class).countByFilter(new IdentifiablePeriod.Filter().setClosed(Boolean.TRUE)));    	
    	assertEquals(2l, inject(IdentifiablePeriodDao.class).countByFilter(new IdentifiablePeriod.Filter().setClosed(BooleanHelper.NULL,Boolean.FALSE)));    	
    	assertEquals(3l, inject(IdentifiablePeriodDao.class).countByFilter(new IdentifiablePeriod.Filter().setClosed(BooleanHelper.NULL,Boolean.TRUE)));    	
    	assertEquals(5l, inject(IdentifiablePeriodDao.class).countByFilter(new IdentifiablePeriod.Filter().setClosed(Boolean.FALSE,Boolean.TRUE)));    	
    	assertEquals(5l, inject(IdentifiablePeriodDao.class).countByFilter(new IdentifiablePeriod.Filter().setClosed(BooleanHelper.NULL,Boolean.FALSE,Boolean.TRUE)));
    	
    	assertEquals(1l, inject(IdentifiablePeriodDao.class).countByFilter(new IdentifiablePeriod.Filter().setClosed(Boolean.FALSE).addMaster(IdentifiablePeriodCollection.class, identifiablePeriodCollectionCode01)));    	
    	assertEquals(1l, inject(IdentifiablePeriodDao.class).countByFilter(new IdentifiablePeriod.Filter().setClosed(Boolean.FALSE).addMaster(IdentifiablePeriodCollection.class, identifiablePeriodCollectionCode02)));    	
    	assertEquals(0l, inject(IdentifiablePeriodDao.class).countByFilter(new IdentifiablePeriod.Filter().setClosed(Boolean.FALSE).addMaster(IdentifiablePeriodCollection.class, identifiablePeriodCollectionCode03)));
    	
    	testCase.clean();
    }
    
    @Test
    public void crudOneIdentifiablePeriodWithTypeWithDateGeneratedBySystem(){
    	TestCase testCase = instanciateTestCase();
    	String identifiablePeriodTypeCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodType.class,identifiablePeriodTypeCode));
    	
    	String identifiablePeriodCollectionCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	
    	String identifiablePeriodCode = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(IdentifiablePeriod.class,identifiablePeriodCode).setTypeFromCode(identifiablePeriodTypeCode)
    			.setCollectionFromCode(identifiablePeriodCollectionCode));
    	
    	testCase.clean();
    }
    
    /* Exceptions */
    
	@Test
    public void throwCollectionIsNull(){
		TestCase testCase = instanciateTestCase();
		testCase.assertThrowable(new Runnable(testCase) {
			private static final long serialVersionUID = 1L;

			@Override protected void __run__() throws Throwable {create(instanciateOne(IdentifiablePeriod.class));}
    	}, FieldHelper.Field.get(IdentifiablePeriod.class,IdentifiablePeriod.FIELD_COLLECTION).getIdentifier(ConditionHelper.Condition.Builder.Null.class), null);
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
