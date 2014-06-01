package org.cyk.system.root.model.unit;


import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.utility.common.test.AbstractUnitTest;
import org.junit.Assert;

public class ModelUT extends AbstractUnitTest {

	
	@Override
	protected void _execute_() {
		super._execute_();
		Assert.assertEquals(new Locality().get__typeClass__(), 
		        LocalityType.class);
	}
	
	
	
}
