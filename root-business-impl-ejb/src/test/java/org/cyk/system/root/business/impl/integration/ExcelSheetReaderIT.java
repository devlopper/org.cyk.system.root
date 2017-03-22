package org.cyk.system.root.business.impl.integration;

import java.io.File;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.IdentifiableBusinessService.CompleteInstanciationOfOneFromValuesListener;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness.CompletePersonInstanciationOfManyFromValuesArguments;
import org.cyk.system.root.business.impl.IdentifiableExcelSheetReader;
import org.cyk.system.root.model.party.person.Person;

public class ExcelSheetReaderIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    
	@Override
	protected void businesses() {
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\data\\iesa");
		
    	IdentifiableExcelSheetReader<Person> excelSheetReader = new IdentifiableExcelSheetReader<Person>(new File(directory, "2016_2017_Trimester_1.xlsx"),Person.class);
    	
    	excelSheetReader.setIndex(0);
    	excelSheetReader.setFromRowIndex(1);
    	excelSheetReader.setFromColumnIndex(0);
    	//excelSheetReader.setPrimaryKeyColumnIndex(0);
    	
		CompletePersonInstanciationOfManyFromValuesArguments completePersonInstanciationOfManyFromValuesArguments = new CompletePersonInstanciationOfManyFromValuesArguments();
		
		completePersonInstanciationOfManyFromValuesArguments.getInstanciationOfOneFromValuesArguments().getPartyInstanciationOfOneFromValuesArguments().setCodeIndex(0);
		completePersonInstanciationOfManyFromValuesArguments.getInstanciationOfOneFromValuesArguments().getPartyInstanciationOfOneFromValuesArguments().setNameIndex(1);
		completePersonInstanciationOfManyFromValuesArguments.getInstanciationOfOneFromValuesArguments().setLastnameIndex(2);
		completePersonInstanciationOfManyFromValuesArguments.getInstanciationOfOneFromValuesArguments().getPartyInstanciationOfOneFromValuesArguments().setBirthDateIndex(3);
		completePersonInstanciationOfManyFromValuesArguments.getInstanciationOfOneFromValuesArguments().setBirthLocationOtherDetailsIndex(4);
		completePersonInstanciationOfManyFromValuesArguments.getInstanciationOfOneFromValuesArguments().setSexCodeIndex(5);
		completePersonInstanciationOfManyFromValuesArguments.getInstanciationOfOneFromValuesArguments().setListener(new CompleteInstanciationOfOneFromValuesListener<Person>() {
			@Override
			public void beforeProcessing(Person person,String[] values) {
				//if(!ArrayUtils.contains(new String[]{"g9","g10","g11","g12"}, values[7].toLowerCase()));
				//	studentClassroomSessions.add(new PersonClassroomSession(student, getClassroomSession(values[7])));
				
				System.out.println("beforeProcessing() : "+StringUtils.join(values,"|")+" : "+person.getCode());
			}
    		@Override
			public void afterProcessing(Person person,String[] values) {
    			System.out.println("afterProcessing() : "+StringUtils.join(values,"|")+" : "+person.getCode());
    			//if(person.getSex()!=null)
    			//	person.setSex(inject(SexDao.class).read(person.getSex().getCode()));
    			/*
    			if(StringUtils.isNotBlank(values[12]))
    				inject(ElectronicMailBusiness.class).setAddress(person, RootConstant.Code.PersonRelationshipType.FAMILY_FATHER, values[12]);
    			if(StringUtils.isNotBlank(values[17]))
    				inject(ElectronicMailBusiness.class).setAddress(student.getPerson(), RootConstant.Code.PersonRelationshipType.FAMILY_MOTHER, values[17]);
    			File photoFile = new File(imageDirectory,new BigDecimal(values[0]).intValue()+".jpg");
				if(!photoFile.exists())
					photoFile = new File(imageDirectory,new BigDecimal(values[0]).intValue()+".png");
				
    			if(photoFile.exists())
					try {
						student.setImage(inject(FileBusiness.class).process(IOUtils.toByteArray(new FileInputStream(photoFile)), student.getCode()+" photo.jpeg"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				else
					System.out.println("Photo not found for "+student.getCode()+" : "+photoFile.getName());
				
				if(classroomSession!=null && !ArrayUtils.contains(new String[]{"g9","g10","g11","g12"}, values[7].toLowerCase()));
					student.setStudentClassroomSession(new StudentClassroomSession(student, classroomSession));
				*/
			}
    		
		});
		Collection<Person> persons = inject(PersonBusiness.class).instanciateMany(excelSheetReader, completePersonInstanciationOfManyFromValuesArguments);
		
	}
   
    @Override
    protected void installApplication() {
    	//super.installApplication();
    }
    
}
