package org.cyk.system.root.business.impl.integration;

import java.math.BigDecimal;

import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.business.impl.__test__.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.mathematics.Interval;
import org.junit.Test;


public class IntervalBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

	@Override
	protected void businesses() {
		
	}
   
	@Test
	public void format(){
		TestCase testCase = instanciateTestCase();
		Interval interval = new Interval();
		interval.getLow().setExcluded(Boolean.FALSE);
		interval.getLow().setValue(new BigDecimal("0"));
		interval.getHigh().setExcluded(Boolean.FALSE);
		interval.getHigh().setValue(new BigDecimal("2"));
		assertEquals("[0 , 2]", inject(FormatterBusiness.class).format(interval));
		testCase.clean();
	}
    
}
