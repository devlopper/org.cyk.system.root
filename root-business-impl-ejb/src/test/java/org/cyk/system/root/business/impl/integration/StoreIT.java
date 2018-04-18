package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.Store;
import org.cyk.system.root.model.party.person.Person;
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
    	testCase.assertCountAll(PartyIdentifiableGlobalIdentifier.class);
    	testCase.clean();
    }
    
    @Test 
    public void crudOneStoreCreatePartyAsCompany(){
    	TestCase testCase = instanciateTestCase();
    	String storeCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Store.class,storeCode).setTypeFromCode(RootConstant.Code.StoreType.PRODUCT).setHasPartyAsCompany(Boolean.TRUE));
    	testCase.assertCountAll(PartyIdentifiableGlobalIdentifier.class,1);
    	testCase.deleteAll(Party.class);
    	testCase.clean();
    }
    
    @Test 
    public void crudOneStoreJoinPartyAsCompany(){
    	TestCase testCase = instanciateTestCase();
    	
    	String storeCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Store.class,storeCode).setTypeFromCode(RootConstant.Code.StoreType.PRODUCT).setHasPartyAsCompany(Boolean.TRUE));
    	
    	testCase.assertNotNullPartyIdentifiableGlobalIdentifier(storeCode, RootConstant.Code.BusinessRole.COMPANY, Store.class, storeCode);
    	testCase.assertCountAll(PartyIdentifiableGlobalIdentifier.class,1);
    	testCase.deleteAll(Party.class);
    	testCase.clean();
    }
    
    @Test 
    public void crudOneStoreJoinPartyAsCompanyOnUpdate(){
    	TestCase testCase = instanciateTestCase();
    	
    	String storeCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Store.class,storeCode).setTypeFromCode(RootConstant.Code.StoreType.PRODUCT));
    	testCase.assertCountAll(PartyIdentifiableGlobalIdentifier.class);
    	
    	testCase.update(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Store.class,storeCode).setHasPartyAsCompany(Boolean.TRUE));
    	testCase.assertCountAll(PartyIdentifiableGlobalIdentifier.class,1);
    	testCase.assertNotNullPartyIdentifiableGlobalIdentifier(storeCode, RootConstant.Code.BusinessRole.COMPANY, Store.class, storeCode);
    	
    	testCase.deleteAll(Party.class);
    	
    	testCase.clean();
    }
    
    @Test 
    public void crudOneStoreJoinPartyAsCompanyOnCreateAndChangePartyAsCompanyOnUpdate(){
    	TestCase testCase = instanciateTestCase();
    	
    	String party01Code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Person.class, party01Code)); 
    	
    	String party02Code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Person.class, party02Code)); 
    	
    	String storeCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Store.class,storeCode).setTypeFromCode(RootConstant.Code.StoreType.PRODUCT).setPartyCompanyFromCode(party01Code));
    	testCase.assertCountAll(PartyIdentifiableGlobalIdentifier.class,1);
    	testCase.assertNotNullPartyIdentifiableGlobalIdentifier(party01Code, RootConstant.Code.BusinessRole.COMPANY, Store.class, storeCode);
    	
    	testCase.update(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Store.class,storeCode).setPartyCompanyFromCode(party02Code));
    	testCase.assertCountAll(PartyIdentifiableGlobalIdentifier.class,1);
    	testCase.assertNotNullPartyIdentifiableGlobalIdentifier(party02Code, RootConstant.Code.BusinessRole.COMPANY, Store.class, storeCode);
    	
    	testCase.clean();
    }
    
    @Test 
    public void crudOneStoreJoinPartyAsCompanyOnCreateAndChangePartyAsCompanyOnUpdateSetNull(){
    	TestCase testCase = instanciateTestCase();
    	
    	String party01Code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Person.class, party01Code)); 
    	
    	String party02Code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Person.class, party02Code)); 
    	
    	String storeCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Store.class,storeCode).setTypeFromCode(RootConstant.Code.StoreType.PRODUCT).setPartyCompanyFromCode(party01Code));
    	testCase.assertCountAll(PartyIdentifiableGlobalIdentifier.class,1);
    	testCase.assertNotNullPartyIdentifiableGlobalIdentifier(party01Code, RootConstant.Code.BusinessRole.COMPANY, Store.class, storeCode);
    	
    	testCase.update(testCase.getByIdentifierWhereValueUsageTypeIsBusiness(Store.class,storeCode).setPartyCompany(null));
    	testCase.assertCountAll(PartyIdentifiableGlobalIdentifier.class);
    	testCase.assertNullPartyIdentifiableGlobalIdentifier(party02Code, RootConstant.Code.BusinessRole.COMPANY, Store.class, storeCode);
    	
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
