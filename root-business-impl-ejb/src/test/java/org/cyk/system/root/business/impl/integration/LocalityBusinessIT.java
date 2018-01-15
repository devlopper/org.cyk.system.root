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

    @Test
    public void crudLocalityTypes(){
    	TestCase testCase = instanciateTestCase();
    	String continentCode = RandomHelper.getInstance().getAlphanumeric(5);
    	LocalityType continent = new LocalityType().setCode(continentCode);
    	testCase.create(continent);
    	
    	String countryCode = RandomHelper.getInstance().getAlphanumeric(5);
    	LocalityType country = new LocalityType().setCode(countryCode).set__parent__(continent);
    	testCase.create(country);
    	
    	String regionCode = RandomHelper.getInstance().getAlphanumeric(5);
    	LocalityType region = new LocalityType().setCode(regionCode).set__parent__(country);
    	testCase.create(region);
    	
    	String cityCode = RandomHelper.getInstance().getAlphanumeric(5);
    	LocalityType city = new LocalityType().setCode(cityCode).set__parent__(region);
    	testCase.create(city);
    	
    	String houseCode = RandomHelper.getInstance().getAlphanumeric(5);
    	LocalityType house = new LocalityType().setCode(houseCode).set__parent__(city);
    	testCase.create(house);
    	
    	testCase.assertNestedSet(continentCode, continentCode, 5l);
    	testCase.assertNestedSetNode(continentCode, continentCode, null, 0, 9, 1l, 4l);
    	testCase.assertNestedSetNode(countryCode, continentCode, continentCode, 1, 8, 1l, 3l);
    	testCase.assertNestedSetNode(regionCode, continentCode, countryCode, 2, 7, 1l, 2l);
    	testCase.assertNestedSetNode(cityCode, continentCode, regionCode, 3, 6, 1l, 1l);
    	testCase.assertNestedSetNode(houseCode, continentCode, cityCode, 4, 5, 0l, 0l);
    	
    	testCase.clean();
    }
}
