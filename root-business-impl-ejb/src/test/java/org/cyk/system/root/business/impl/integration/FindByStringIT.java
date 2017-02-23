package org.cyk.system.root.business.impl.integration;

import java.util.Arrays;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.business.impl.RootDataProducerHelper;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.utility.common.file.ExcelSheetReader;

public class FindByStringIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

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
    	Person person = inject(PersonBusiness.class).instanciateOneRandomly();
    	person.setCode("c001");
    	person.setName("konan");
    	person.setLastnames("marius");
    	create(person);
    	
    	person = inject(PersonBusiness.class).instanciateOneRandomly();
    	person.setCode("c002");
    	person.setName("zanga");
    	person.setLastnames("alice");
    	create(person);
    	
    	person = inject(PersonBusiness.class).instanciateOneRandomly();
    	person.setCode("c003a");
    	person.setName("doudou");
    	person.setLastnames("cherif");
    	create(person);
    }
    
	@Override
	protected void businesses() {
		assertEquals(4l, inject(PersonDao.class).countAll());
		assertFindByString(Person.class,"WXWX",0);
		assertFindByString(Person.class,null,4);
		assertFindByString(Person.class,"",4);
		
		assertEquals(1, inject(PersonBusiness.class).findByString("ko", Arrays.asList(inject(PersonDao.class).read("c002"))).size());
		assertEquals(1, inject(PersonBusiness.class).findByString("ko", null).size());
		assertEquals(4, inject(PersonBusiness.class).findByString("a", null).size());
		assertEquals(3, inject(PersonBusiness.class).findByString("a", Arrays.asList(inject(PersonDao.class).read("c002"))).size());
	}
	
	private void assertFindByString(Class<? extends AbstractIdentifiable> aClass,String string,Integer expected){
		assertEquals(expected.intValue(), inject(BusinessInterfaceLocator.class).injectTyped(aClass).findByString(string, null).size());
		assertEquals(expected.longValue(), inject(BusinessInterfaceLocator.class).injectTyped(aClass).countByString(string));
	}
	
	/**/
    
}
