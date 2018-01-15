package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.utility.common.helper.RandomHelper;
import org.junit.Test;

public class LocalityBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 8691254326402622637L;
    
	@Override
	protected void installApplication() {}
	
    @Test
    public void crudLocalityType(){
    	TestCase testCase = instanciateTestCase();
    	String localityTypeCode = RandomHelper.getInstance().getAlphanumeric(5);
    	LocalityType continent = new LocalityType().setCode(localityTypeCode);
    	testCase.create(continent);
    	testCase.delete(continent);
    	
    	localityTypeCode = RandomHelper.getInstance().getAlphanumeric(5);
    	continent = new LocalityType().setCode(localityTypeCode);
    	testCase.create(continent);
    	
    	testCase.clean();
    }

}
