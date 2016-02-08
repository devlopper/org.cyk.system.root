package org.cyk.system.root.service.impl.unit;

import java.math.BigDecimal;

import org.cyk.system.root.business.api.AbstractBusinessException;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.utility.common.test.TestEnvironmentListener;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;

public class AbstractBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	static {
		TestEnvironmentListener.COLLECTION.add(new TestEnvironmentListener.Adapter.Default(){
			private static final long serialVersionUID = -2347039842308401189L;
			@Override
			protected Throwable getThrowable(Throwable throwable) {
				return commonUtils.getThrowableInstanceOf(throwable, AbstractBusinessException.class);
			}
			@Override
    		public void assertEquals(String message, Object expected, Object actual) {
    			Assert.assertEquals(message, expected, actual);
    		}
    		@Override
    		public String formatBigDecimal(BigDecimal value) {
    			return RootBusinessLayer.getInstance().getNumberBusiness().format(value);
    		}
    	});
	}
	
}
