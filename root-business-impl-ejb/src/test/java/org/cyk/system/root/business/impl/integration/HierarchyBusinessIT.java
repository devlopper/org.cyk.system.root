package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.geography.LocalityType;
import org.junit.Test;

public class HierarchyBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 8691254326402622637L;
    
	@Override
	protected void installApplication() {}
	
    @Test
    public void crudLocalityType(){
    	TestCase testCase = instanciateTestCase();
    	LocalityType continent = new LocalityType(null, RootConstant.Code.LocalityType.CONTINENT, "continent");
    	testCase.create(continent);
    	
    	testCase.clean();
    }

    

}
