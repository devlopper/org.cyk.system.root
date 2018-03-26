package org.cyk.system.root.business.impl.__test__;

import org.cyk.system.root.business.impl.__test__.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.AbstractIdentifiable;

public abstract class Runnable extends org.cyk.utility.common.test.BasedRunnable<TestCase> implements java.lang.Runnable {
	private static final long serialVersionUID = 1L;

	public Runnable(TestCase testCase) {
		this.testCase = testCase;
	}
	 
	public Runnable() {
		this(null);
	}
	
	public Runnable create(AbstractIdentifiable identifiable){
		testCase.create(identifiable);
		return this;
	}
	
}