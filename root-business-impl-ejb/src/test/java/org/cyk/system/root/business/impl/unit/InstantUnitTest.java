package org.cyk.system.root.business.impl.unit;

import org.cyk.system.root.model.time.Instant;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class InstantUnitTest extends AbstractUnitTest {

    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
    }
    
    @Test
    public void getUiString() {
        new AssertionHelper.Assertion.Equals.Adapter.Default<Object>(Object.class,Constant.EMPTY_STRING,new Instant().getUiString()).execute();
    }

}
