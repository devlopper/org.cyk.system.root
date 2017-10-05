package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.geography.ContactCollection;
import org.junit.Test;

public class PrepareBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Test
    public void prepareUpdateContactCollection(){
    	TestCase testCase = instanciateTestCase();
    	ContactCollection contactCollection = inject(ContactCollectionBusiness.class).instanciateOne();
    	inject(ContactCollectionBusiness.class).prepare(contactCollection,Crud.CREATE);
    	contactCollection.setCode("c0001");
    	testCase.create(contactCollection);
    	inject(ContactCollectionBusiness.class).prepare(contactCollection,Crud.UPDATE);
    	testCase.update(contactCollection);
    	
    	testCase.clean();
    }

}
