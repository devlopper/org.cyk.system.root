package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ClassHelper;
import org.junit.Test;

public class MathematicsBusinessIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    @Test 
    public void crudInterval(){
    	TestCase testCase = instanciateTestCase();
    	String intervalCode = testCase.getRandomAlphabetic();
    	Interval interval = testCase.instanciateOne(Interval.class,intervalCode);
    	testCase.assertNull(interval.getLow().getValue());
    	testCase.assertNull(interval.getLow().getValueWithoutExcludedInformation());
    	testCase.assertNull(interval.getLow().getExcluded());
    	
    	testCase.assertNull(interval.getHigh().getValue());
    	testCase.assertNull(interval.getHigh().getValueWithoutExcludedInformation());
    	testCase.assertNull(interval.getHigh().getExcluded());
    	
    	testCase.create(interval);
    	testCase.clean();
    }
    
    @Test 
    public void crudIntervalLowValueIsNullExcludedIsNullHighValueIsNullExcludedIsNull(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(null, null, null, Constant.NUMBER_LOWEST_NEGATIVE, null, null, Constant.NUMBER_HIGHEST_POSITIVE)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalLowValueIsNullExcludedIsTrueHighValueIsNullExcludedIsNull(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(null, null, Boolean.TRUE, Constant.NUMBER_LOWEST_NEGATIVE, null, null, Constant.NUMBER_HIGHEST_POSITIVE)
			.clean();   
    }
    
    @Test 
    public void crudIntervalLowValueIsNullExcludedIsNullHighValueIsNullExcludedIsTrue(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(null, null, null, Constant.NUMBER_LOWEST_NEGATIVE, null, Boolean.TRUE, Constant.NUMBER_HIGHEST_POSITIVE)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalLowValueIsNullExcludedIsTrueHighValueIsNullExcludedIsTrue(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(null, null, Boolean.TRUE, Constant.NUMBER_LOWEST_NEGATIVE, null, Boolean.TRUE, Constant.NUMBER_HIGHEST_POSITIVE)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalIntegerLowInfiniteToHigh0ExcludedIsNull(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(null, null, null, Constant.NUMBER_LOWEST_NEGATIVE, 0, null, 0)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalIntegerLowInfiniteToHigh0ExcludedIsFalse(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(null, null, null, Constant.NUMBER_LOWEST_NEGATIVE, 0, Boolean.FALSE, 0)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalIntegerLowInfiniteToHigh0ExcludedIsTrue(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(null, null, null, Constant.NUMBER_LOWEST_NEGATIVE, 0, Boolean.TRUE, -1)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalIntegerLow0ExcludedIsNullToHighInfinite(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(null, 0, null, 0, null, null, Constant.NUMBER_HIGHEST_POSITIVE)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalIntegerLow0ExcludedIsFalseToHighInfinite(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(null, 0, Boolean.FALSE, 0, null, null, Constant.NUMBER_HIGHEST_POSITIVE)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalIntegerLow0ExcludedIsTrueToHighInfinite(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(null, 0, Boolean.TRUE, 1, null, null, Constant.NUMBER_HIGHEST_POSITIVE)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalFloat1LowInfiniteToHigh0ExcludedIsNull(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(1, null, null, Constant.NUMBER_LOWEST_NEGATIVE, 0, null, 0)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalFloat1LowInfiniteToHigh0ExcludedIsFalse(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(1, null, null, Constant.NUMBER_LOWEST_NEGATIVE, 0, Boolean.FALSE, 0)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalFloat1LowInfiniteToHigh0ExcludedIsTrue(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(1, null, null, Constant.NUMBER_LOWEST_NEGATIVE, 0, Boolean.TRUE, -0.1)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalFloat1Low0ExcludedIsNullToHighInfinite(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(1, 0, null, 0, null, null, Constant.NUMBER_HIGHEST_POSITIVE)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalFloat1Low0ExcludedIsFalseToHighInfinite(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(1, 0, Boolean.FALSE, 0, null, null, Constant.NUMBER_HIGHEST_POSITIVE)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalFloat1Low0ExcludedIsTrueToHighInfinite(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(1, 0, Boolean.TRUE, 0.1, null, null, Constant.NUMBER_HIGHEST_POSITIVE)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalFloat2LowInfiniteToHigh0ExcludedIsTrue(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(2, null, null, Constant.NUMBER_LOWEST_NEGATIVE, 0, Boolean.TRUE, -0.01)
    		.clean();    	
    }
    
    @Test 
    public void crudIntervalFloat2Low0ExcludedIsTrueToHighInfinite(){
    	instanciateTestCase().assertIntervalExtremitiesValueWithoutExcludedInformation(2, 0, Boolean.TRUE, 0.01, null, null, Constant.NUMBER_HIGHEST_POSITIVE)
    		.clean();    	
    }
    
    @Test 
    public void assertIntervalContains(){
    	TestCase testCase = instanciateTestCase();
    	String intervalCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Interval.class,intervalCode).setLowValueFromObject(0).setHighValueFromObject(2));
    	testCase.assertIntervalContains(intervalCode, 1);
    	testCase.assertIntervalDoesNotContain(intervalCode, -1,3);
    	testCase.clean();
    }
    
    @SuppressWarnings("unchecked")
	public static class Data extends DataSet.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@SuppressWarnings({ "rawtypes" })
		@Override
		public Collection getClasses() {
			return Arrays.asList(Interval.class);
		}
		
    }
        
}
