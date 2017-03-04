package org.cyk.system.root.business.impl.integration;

import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.business.impl.RootDataProducerHelper;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
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
    	
    	create(new JobFunction(null, null));
    	create(new JobFunction("mycode", null));
    	create(new JobFunction(null, "mylibeller"));
    	
    }
    
	@Override
	protected void businesses() {
		assertEquals(4l, inject(PersonDao.class).countAll());
		assertFindByString(Person.class,"WXWX",0);
		assertFindByString(Person.class,null,4);
		assertFindByString(Person.class,"",4);
		
		assertFindByString(Person.class,"ko",Arrays.asList("c002"),1);
		assertFindByString(Person.class,"a",4);
		assertFindByString(Person.class,"a",Arrays.asList("c002"),3);
		
		assertFindByString(Person.class,"ius",1);
		
		assertFindByString(JobFunction.class,null,4);
		assertFindByString(JobFunction.class,"",4);
		
		assertFindByString(JobFunction.class,"rec",1);
		assertFindByString(JobFunction.class,"m",2);
		assertFindByString(JobFunction.class,"my",2);
		assertFindByString(JobFunction.class,"myc",1);
		assertFindByString(JobFunction.class,"myl",1);
		assertFindByString(JobFunction.class,"e",3);
		assertFindByString(JobFunction.class,"m",2);
		assertFindByString(JobFunction.class,"my",2);
		assertFindByString(JobFunction.class,"c",2);
		assertFindByString(JobFunction.class,"r",2);
		
	}
	
	private <T extends AbstractIdentifiable> void assertFindByString(Class<T> aClass,String string,Collection<String> excludedCodes,Integer expected){
		TypedBusiness<T> business = inject(BusinessInterfaceLocator.class).injectTyped(aClass);
		Collection<T> excludedIdentifiables = excludedCodes == null ? null : inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(excludedCodes);
		assertEquals(expected.intValue(), business.findByString(string, excludedIdentifiables).size());
		assertEquals(expected.longValue(), business.countByString(string,(Collection<T>) excludedIdentifiables));
	}
	
	private <T extends AbstractIdentifiable> void assertFindByString(Class<T> aClass,String string,Integer expected){
		assertFindByString(aClass, string, null, expected);
	}
	
	/**/
    
}
