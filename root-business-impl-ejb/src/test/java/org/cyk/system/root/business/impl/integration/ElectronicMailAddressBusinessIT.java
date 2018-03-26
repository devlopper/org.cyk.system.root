package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.impl.__test__.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.junit.Test;

public class ElectronicMailAddressBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
 
    @Test
    public void crud(){
    	TestCase testCase = instanciateTestCase();
    	ElectronicMailAddress electronicMailAddress = new ElectronicMailAddress("maymail@mail.com");
    	testCase.create(electronicMailAddress);
    }
    
    @Test
    public void exceptionFormat(){
    	TestCase testCase = instanciateTestCase();
    	ElectronicMailAddress electronicMailAddress = new ElectronicMailAddress("may...mail@mail.com");
    	testCase.create(electronicMailAddress,"adresse Adresse email mal formée");
    }
}
