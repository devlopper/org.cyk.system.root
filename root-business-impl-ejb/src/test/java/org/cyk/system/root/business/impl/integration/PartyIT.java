package org.cyk.system.root.business.impl.integration;

import static org.cyk.system.root.model.RootConstant.Code.BusinessRole.COMPANY;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.Store;
import org.cyk.system.root.persistence.api.party.StoreDao;
import org.cyk.utility.common.helper.ClassHelper;
import org.junit.Test;

public class PartyIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;
    
    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    @Test
    public void crudParty(){
    	TestCase testCase = instanciateTestCase();
    	String partyCode = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Party.class,partyCode));
    	testCase.clean();
    }
    
    @Test
    public void assertCountPartyByIdentifiablesByBusinessRoleCode(){
    	TestCase testCase = instanciateTestCase();
    	String storeCode01 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Store.class,storeCode01).setHasPartyAsCompany(Boolean.TRUE));
    	
    	String storeCode02 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Store.class,storeCode02));
    	
    	String storeCode03 = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Store.class,storeCode03).setHasPartyAsCompany(Boolean.TRUE));
    	
    	testCase.assertCountAll(PartyIdentifiableGlobalIdentifier.class, 2);
    	
    	testCase.assertEqualsNumberPartyByIdentifiablesByBusinessRoleCode(1,Arrays.asList(inject(StoreDao.class).read(storeCode01)), COMPANY);
    	testCase.assertEqualsNumberPartyByIdentifiablesByBusinessRoleCode(0,Arrays.asList(inject(StoreDao.class).read(storeCode02)), COMPANY);
    	testCase.assertEqualsNumberPartyByIdentifiablesByBusinessRoleCode(1,Arrays.asList(inject(StoreDao.class).read(storeCode03)), COMPANY);
    	
    	testCase.assertEqualsNumberPartyByIdentifiablesByBusinessRoleCode(1,inject(StoreDao.class).read(Arrays.asList(storeCode01,storeCode02)), COMPANY);
    	testCase.assertEqualsNumberPartyByIdentifiablesByBusinessRoleCode(2,inject(StoreDao.class).read(Arrays.asList(storeCode01,storeCode03)), COMPANY);
    	testCase.assertEqualsNumberPartyByIdentifiablesByBusinessRoleCode(1,inject(StoreDao.class).read(Arrays.asList(storeCode02,storeCode01)), COMPANY);
    	testCase.assertEqualsNumberPartyByIdentifiablesByBusinessRoleCode(1,inject(StoreDao.class).read(Arrays.asList(storeCode02,storeCode03)), COMPANY);
    	testCase.assertEqualsNumberPartyByIdentifiablesByBusinessRoleCode(2,inject(StoreDao.class).read(Arrays.asList(storeCode03,storeCode01)), COMPANY);
    	testCase.assertEqualsNumberPartyByIdentifiablesByBusinessRoleCode(1,inject(StoreDao.class).read(Arrays.asList(storeCode03,storeCode02)), COMPANY);
    	testCase.assertEqualsNumberPartyByIdentifiablesByBusinessRoleCode(2,inject(StoreDao.class).read(Arrays.asList(storeCode01,storeCode02,storeCode03)), COMPANY);
    	
    	testCase.assertEqualsNumberPartyByIdentifiablesByBusinessRoleCode(2,inject(StoreDao.class).readAll(), COMPANY);
    	
    	testCase.deleteAll(PartyIdentifiableGlobalIdentifier.class);
    	testCase.deleteAll(Party.class);
    	
    	testCase.clean();
    }
    
    /**/
    
    @SuppressWarnings("unchecked")
	public static class Data extends DataSet.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@SuppressWarnings({ "rawtypes" })
		@Override
		public Collection getClasses() {
			return Arrays.asList(Party.class);
		}
		
    }

}
