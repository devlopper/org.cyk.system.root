package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.store.Store;
import org.cyk.utility.common.helper.ClassHelper;
import org.junit.Test;

public class StoreIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    @Test 
    public void crudOneStore(){
    	TestCase testCase = instanciateTestCase();
    	String storeCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Store.class,storeCode).setTypeFromCode(RootConstant.Code.StoreType.PRODUCT));
    	testCase.clean();
    }
    
    @Test 
    public void crudOneStoreJoinPartyAsCompany(){
    	TestCase testCase = instanciateTestCase();
    	
    	String partyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Person.class, partyCode)); 
    	
    	String storeCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Store.class,storeCode).setTypeFromCode(RootConstant.Code.StoreType.PRODUCT).setPartyCompanyFromCode(partyCode));
    	
    	testCase.assertNotNullPartyIdentifiableGlobalIdentifier(partyCode, RootConstant.Code.BusinessRole.COMPANY, Store.class, storeCode);
    	
    	testCase.clean();
    }
    
    /**/
    
	/**/
	
    @SuppressWarnings("unchecked")
	public static class Data extends DataSet.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@SuppressWarnings({ "rawtypes" })
		@Override
		public Collection getClasses() {
			return Arrays.asList(Store.class,Party.class);
		}
		
    }
}
