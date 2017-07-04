package org.cyk.system.root.business.impl.integration;


import javax.inject.Inject;

import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.SexBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.business.impl.RootDataProducerHelper;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.JobInformations;
import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;
import org.cyk.utility.common.file.ExcelSheetReader;
import org.junit.Test;

public class GlobalIdentifierBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 7023768959389316273L;
	
	@Inject private GlobalIdentifierBusiness globalIdentifierBusiness;
	
	@Override
	protected void populate() {
		RootDataProducerHelper.Listener.COLLECTION.add(new RootDataProducerHelper.Listener.Adapter.Default(){
    		private static final long serialVersionUID = 1L;

			@Override
    		public ExcelSheetReader processExcelSheetReader(ExcelSheetReader excelSheetReader) {
    			if(excelSheetReader.getSheetName().equals("Country"))
    				excelSheetReader.setRowCount(2);
    			return super.processExcelSheetReader(excelSheetReader);
    		}
    	});
		super.populate();
	}
	
    //@Test
    public void crud(){
    	TestCase testCase = instanciateTestCase();
    	GlobalIdentifier globalIdentifier = new GlobalIdentifier();
    	Location location = new Location();
    	globalIdentifier.setBirthLocation(location);
    	globalIdentifierBusiness.create(globalIdentifier);
    	globalIdentifier = inject(GlobalIdentifierDao.class).read(globalIdentifier.getIdentifier());
    	testCase.clean();
    }
    
    //@Test
    public void crudSex(){
    	TestCase testCase = instanciateTestCase();
    	Sex sex = inject(SexBusiness.class).instanciateOne("MF", "Male Female");
    	testCase.create(sex);
    	sex = testCase.read(Sex.class, "MF");
    	testCase.clean();
    }

    @Test
    public void crudPerson(){
    	TestCase testCase = instanciateTestCase().addClasses(File.class,ContactCollection.class,PersonExtendedInformations.class,JobInformations.class,MedicalInformations.class).prepare();
    	
    	Person person = inject(PersonBusiness.class).instanciateOneRandomly("p001");
    	testCase.create(person);
    	testCase.assertCountAll(File.class, 2);
    	testCase.deleteByCode(Person.class, "p001");
    	 	
    	testCase.clean();
    	
    }
}
