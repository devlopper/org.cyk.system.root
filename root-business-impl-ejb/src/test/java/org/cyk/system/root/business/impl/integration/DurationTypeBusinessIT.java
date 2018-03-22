package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.value.Value;
import org.cyk.utility.common.helper.ClassHelper;
import org.junit.Test;

public class DurationTypeBusinessIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    @Test
    public void computeFull(){
    	TestCase testCase = instanciateTestCase();
    	
    	testCase.clean();
    }
    
    @Test
    public void computePartial(){
    	TestCase testCase = instanciateTestCase();
    	
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
