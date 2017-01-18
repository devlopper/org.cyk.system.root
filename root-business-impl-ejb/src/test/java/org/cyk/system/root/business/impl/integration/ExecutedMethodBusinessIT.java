package org.cyk.system.root.business.impl.integration;

import javax.inject.Inject;
import javax.interceptor.Interceptors;

import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.RootDataProducerHelper;
import org.cyk.system.root.model.event.ExecutedMethod;
import org.cyk.system.root.persistence.api.event.ExecutedMethodDao;
import org.cyk.system.root.persistence.api.security.CredentialsDao;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.file.ExcelSheetReader;

public class ExecutedMethodBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Inject private MasterClass master;
    @Inject private SubClass sub;
    
    @Override
    protected void populate() {
    	RootDataProducerHelper.Listener.COLLECTION.add(new RootDataProducerHelper.Listener.Adapter.Default(){
    		private static final long serialVersionUID = 1L;

			@Override
    		public ExcelSheetReader processExcelSheetReader(ExcelSheetReader excelSheetReader) {
    			if(excelSheetReader.getName().equals("Country"))
    				excelSheetReader.setRowCount(2);
    			return super.processExcelSheetReader(excelSheetReader);
    		}
    	});
    	
    	super.populate();
    }
    
	@Override
	protected void businesses() {
		inject(UserAccountBusiness.class).connect(inject(CredentialsDao.class).readByUsernameByPassword("admin", "123"));
		System.out.println("ExecutedMethodBusinessIT.businesses() : "+inject(ExecutedMethodDao.class).countAll());
		for(ExecutedMethod executedMethod : inject(ExecutedMethodDao.class).readAll())
			System.out.println(executedMethod);
	}
   
	//@Test
	public void master(){
		master.master1();
		master.master2();
		master.master3("zadi");
	
		for(ExecutedMethod executedMethod : inject(ExecutedMethodDao.class).readAll())
			System.out.println(executedMethod);
	}
	
	//@Test
	public void connect(){
		sub.sub1();
		try {
			sub.sub2();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sub.sub3(new Object[]{"string",1,2l,12.3,Boolean.TRUE});
		for(ExecutedMethod executedMethod : inject(ExecutedMethodDao.class).readAll())
			System.out.println(executedMethod);
	}
	
	/**/
	
	@Interceptors(value={org.cyk.utility.common.cdi.annotation.Log.Interceptor.class})
	@org.cyk.utility.common.cdi.annotation.Log
	public static class MasterClass {
		
		public void master1(){
			CommonUtils.getInstance().pause(3000);
		}
		
		public void master2(){
			
		}
		
		public Boolean master3(String username){
			return Boolean.FALSE;
		}
		
	}
	
	//@Interceptors(value={org.cyk.utility.common.cdi.annotation.Log.Interceptor.class})
	public static class SubClass {
		
		public void sub1(){
			CommonUtils.getInstance().pause(3000);
		}
		
		public void sub2(){
			throw new RuntimeException("HoLALALALAAL");
		}
		
		public Object[] sub3(Object[] p){
			return new Object[]{"zagadou",45};
		}
		
	}
	
    
}
