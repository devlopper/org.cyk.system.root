package org.cyk.system.root.business.impl.__test__;

import org.cyk.system.root.business.impl.AbstractBusinessTestHelper;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.AbstractIdentifiable;

public abstract class Runnable extends org.cyk.utility.common.test.Runnable implements java.lang.Runnable {

	public Runnable(TestCase testCase) {
		this.testCase = testCase;
	}
	
	public Runnable() {
		this(null);
	}
	
	public Runnable create(AbstractIdentifiable identifiable){
		((AbstractBusinessTestHelper.TestCase)testCase).create(identifiable);
		return this;
	}
	
}