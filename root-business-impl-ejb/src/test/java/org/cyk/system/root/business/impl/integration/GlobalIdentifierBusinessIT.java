package org.cyk.system.root.business.impl.integration;


import javax.inject.Inject;

import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.party.person.SexBusiness;
import org.cyk.system.root.business.impl.__test__.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;

public class GlobalIdentifierBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 7023768959389316273L;
	
	@Inject private GlobalIdentifierBusiness globalIdentifierBusiness;
	
    //@Test
    public void crud(){
    	TestCase testCase = instanciateTestCase();
    	GlobalIdentifier globalIdentifier = new GlobalIdentifier();
    	Location location = new Location();
    	globalIdentifier.setBirthLocation(location);
    	globalIdentifierBusiness.create(globalIdentifier);
    	globalIdentifier = inject(GlobalIdentifierDao.class).read(globalIdentifier.getIdentifier());
    	testCase.clean();
    }
    
    //@Test
    public void crudSex(){
    	TestCase testCase = instanciateTestCase();
    	Sex sex = inject(SexBusiness.class).instanciateOne("MF", "Male Female");
    	testCase.create(sex);
    	sex = testCase.read(Sex.class, "MF");
    	testCase.clean();
    }

    
}
