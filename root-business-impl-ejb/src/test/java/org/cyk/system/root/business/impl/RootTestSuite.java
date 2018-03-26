package org.cyk.system.root.business.impl;

import org.cyk.system.root.business.impl.integration.IdentifiablePeriodIT;
import org.cyk.system.root.business.impl.integration.MovementBusinessIT;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value= {
		IdentifiablePeriodIT.class
		,MovementBusinessIT.class
})
public class RootTestSuite {

}
