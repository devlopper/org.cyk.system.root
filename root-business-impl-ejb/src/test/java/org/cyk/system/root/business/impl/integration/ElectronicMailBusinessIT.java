package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.junit.Test;

public class ElectronicMailBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
 
    @Test
    public void crud(){
    	TestCase testCase = instanciateTestCase();
    	ElectronicMail electronicMail = new ElectronicMail("maymail@mail.com");
    	testCase.create(electronicMail);
    }
    
    @Test
    public void exceptionFormat(){
    	TestCase testCase = instanciateTestCase();
    	ElectronicMail electronicMail = new ElectronicMail("may...mail@mail.com");
    	testCase.create(electronicMail,"adresse Adresse email mal formée");
    }
}
