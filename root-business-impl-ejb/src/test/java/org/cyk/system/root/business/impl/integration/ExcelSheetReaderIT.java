package org.cyk.system.root.business.impl.integration;

import java.io.File;
import java.util.Collection;

import org.cyk.system.root.business.api.geography.LocationBusiness;
import org.cyk.system.root.business.impl.IdentifiableExcelSheetReader;
import org.cyk.system.root.business.impl.IdentifiableInstanceFieldSetterAdapter;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.CommonUtils;

public class ExcelSheetReaderIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
	@Override
	protected void businesses() {
		CommonUtils commonUtils = CommonUtils.getInstance();
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\data\\iesa");
		
    	IdentifiableExcelSheetReader<Person> excelSheetReader = new IdentifiableExcelSheetReader<Person>(new File(directory, "2016_2017_Trimester_1.xlsx"),Person.class);
    	excelSheetReader.setIndex(0);
    	excelSheetReader.setFromRowIndex(1);
    	excelSheetReader.setFromColumnIndex(0);
    	
    	IdentifiableInstanceFieldSetterAdapter.OneDimensionObjectArray<Person> setter = new IdentifiableInstanceFieldSetterAdapter.OneDimensionObjectArray<Person>(Person.class);
		setter.addFieldNames(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE) 
				,commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_NAME)
				,Person.FIELD_LASTNAMES
				,commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE)
				,commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_BIRTH_LOCATION,AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER
						,GlobalIdentifier.FIELD_OTHER_DETAILS)
				,Person.FIELD_SEX
				,commonUtils.attributePath(Person.FIELD_EXTENDED_INFORMATIONS, PersonExtendedInformations.FIELD_TITLE)
				);
		excelSheetReader.execute();
		IdentifiableInstanceFieldSetterAdapter.TwoDimensionObjectArray<Person> twoDimensionObjectArray = new IdentifiableInstanceFieldSetterAdapter.TwoDimensionObjectArray<Person>(excelSheetReader.getValues(),setter){
			private static final long serialVersionUID = 1L;

			@Override
			public Person instanciate(Object[] values) {
				Person person = super.instanciate(values);
				person.setExtendedInformations(new PersonExtendedInformations(person));
				person.setBirthLocation((Location) inject(LocationBusiness.class).instanciateOne());
				return person;
			}
			
		};
		
		Collection<Person> persons = twoDimensionObjectArray.execute();
		System.out.println("ExcelSheetReaderIT.businesses() : "+persons);
		for(Person person : persons)
			System.out.println(person.getCode()+" : "+person.getLastnames()+" : "+person.getSex()+" : "+person.getBirthLocation());
	}
   
    @Override
    protected void installApplication() {
    	super.installApplication();
    }
    
}
