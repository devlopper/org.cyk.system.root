package org.cyk.system.root.business.impl;

import org.cyk.system.root.business.impl.integration.IdentifiablePeriodBusinessIT;
import org.cyk.system.root.business.impl.integration.MovementBusinessIT;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value= {
		IdentifiablePeriodBusinessIT.class
		,MovementBusinessIT.class
})
public class RootTestSuite {

}
