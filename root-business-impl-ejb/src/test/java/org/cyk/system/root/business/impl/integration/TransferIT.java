package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.transfer.Transfer;
import org.cyk.system.root.model.transfer.TransferItemCollection;
import org.cyk.system.root.model.transfer.TransferItemCollectionItem;
import org.cyk.system.root.model.transfer.TransferType;
import org.cyk.utility.common.helper.ClassHelper;
import org.junit.Test;

public class TransferIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
	
    @Test
    public void crudOneTransferType(){
    	TestCase testCase = instanciateTestCase(); 
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(TransferType.class,code));
    	testCase.clean();
    }
    
    @Test
    public void crudOneTransfer(){
    	TestCase testCase = instanciateTestCase(); 
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(Transfer.class,code));
    	testCase.clean();
    }
    
    @Test
    public void crudOneTransferItemCollection(){
    	TestCase testCase = instanciateTestCase(); 
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(TransferItemCollection.class,code));
    	testCase.clean();
    }
    
    @Test
    public void crudOneTransferItemCollectionItem(){
    	TestCase testCase = instanciateTestCase(); 
    	String code = testCase.getRandomAlphabetic();
    	testCase.create(testCase.instanciateOne(TransferItemCollectionItem.class,code));
    	testCase.clean();
    }
       
    /**/
    
    @SuppressWarnings("unchecked")
	public static class Data extends DataSet.Listener.Adapter implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@SuppressWarnings({ "rawtypes" })
		@Override
		public Collection getClasses() {
			return Arrays.asList(Transfer.class);
		}
		
    }
    
}
