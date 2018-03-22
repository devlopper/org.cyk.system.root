package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.api.time.DurationTypeBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.value.Value;
import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.TimeHelper;
import org.junit.Test;

public class DurationTypeBusinessIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    @Test
    public void computeFull(){
    	TestCase testCase = instanciateTestCase();
    	
    	AssertionHelper.getInstance().assertEquals(TimeHelper.NUMBER_OF_MILLISECOND_BY_MINUTE * 59, inject(DurationTypeBusiness.class).computeNumberOfMillisecond(RootConstant.Code.DurationType.FULL
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0), TimeHelper.getInstance().getDate(2000, 1, 1, 1, 59)
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0)).intValue());
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.NUMBER_OF_MILLISECOND_BY_MINUTE * 59, inject(DurationTypeBusiness.class).computeNumberOfMillisecond(RootConstant.Code.DurationType.FULL
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0), TimeHelper.getInstance().getDate(2000, 1, 1, 1, 59)
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 30)).intValue());
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.NUMBER_OF_MILLISECOND_BY_MINUTE * 59, inject(DurationTypeBusiness.class).computeNumberOfMillisecond(RootConstant.Code.DurationType.FULL
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0), TimeHelper.getInstance().getDate(2000, 1, 1, 1, 59)
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0)).intValue());
		    	
    	testCase.clean();
    }
    
    @Test
    public void computePartial(){
    	TestCase testCase = instanciateTestCase();
    	
    	AssertionHelper.getInstance().assertEquals(TimeHelper.NUMBER_OF_MILLISECOND_BY_MINUTE * 58, inject(DurationTypeBusiness.class).computeNumberOfMillisecond(RootConstant.Code.DurationType.PARTIAL
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0), TimeHelper.getInstance().getDate(2000, 1, 1, 1, 59)
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 1)).intValue());
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.NUMBER_OF_MILLISECOND_BY_MINUTE * 50, inject(DurationTypeBusiness.class).computeNumberOfMillisecond(RootConstant.Code.DurationType.PARTIAL
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0), TimeHelper.getInstance().getDate(2000, 1, 1, 1, 59)
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 9)).intValue());
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.NUMBER_OF_MILLISECOND_BY_MINUTE * 29, inject(DurationTypeBusiness.class).computeNumberOfMillisecond(RootConstant.Code.DurationType.PARTIAL
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0), TimeHelper.getInstance().getDate(2000, 1, 1, 1, 59)
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 30)).intValue());
    	
    	testCase.clean();
    }
    
    /* Exceptions */
    

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
