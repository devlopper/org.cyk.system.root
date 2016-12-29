package org.cyk.system.root.business.impl.integration;

import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.FileIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;
import org.cyk.system.root.persistence.api.party.person.SexDao;

public class FileBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

	@Override
	protected void businesses() {
		FileBusiness fileBusiness = inject(FileBusiness.class);
		
		assertEquals("image/jpeg", fileBusiness.findMime("jpg"));
		
		try {
			File file1 = fileBusiness.process(IOUtils.toByteArray(getClass().getResourceAsStream("file/pointofsale.pdf")), "pointofsale.pdf");
			file1.setRepresentationType(inject(FileRepresentationTypeDao.class).read(RootConstant.Code.FileRepresentationType.IDENTITY_IMAGE));
			create(file1);
			file1 = fileBusiness.find(file1.getIdentifier());
			
			File file2 = fileBusiness.process(IOUtils.toByteArray(getClass().getResourceAsStream("file/sequencediagram.pdf")), "sequencediagram.pdf");
			file2.setRepresentationType(inject(FileRepresentationTypeDao.class).read(RootConstant.Code.FileRepresentationType.IDENTITY_DOCUMENT));
			create(file2);
			file2 = fileBusiness.find(file2.getIdentifier());
			
			Sex sexMale = inject(SexDao.class).read(RootConstant.Code.Sex.MALE);
			Sex sexFemale = inject(SexDao.class).read(RootConstant.Code.Sex.FEMALE);
			
			create(new FileIdentifiableGlobalIdentifier(file1,sexMale));
			create(new FileIdentifiableGlobalIdentifier(file2,sexMale));
			create(new FileIdentifiableGlobalIdentifier(file1,sexFemale));
			create(new FileIdentifiableGlobalIdentifier(file2,sexFemale));
			
			FileIdentifiableGlobalIdentifierBusiness fileIdentifiableGlobalIdentifierBusiness = inject(FileIdentifiableGlobalIdentifierBusiness.class);
			
			assertEquals(4l, fileIdentifiableGlobalIdentifierBusiness.countAll());
			
			FileIdentifiableGlobalIdentifier.SearchCriteria searchCriteria;
			searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
			searchCriteria.addGlobalIdentifier(sexMale.getGlobalIdentifier());
			searchCriteria.addGlobalIdentifier(sexFemale.getGlobalIdentifier());
			Collection<FileIdentifiableGlobalIdentifier> fileIdentifiableGlobalIdentifiers = fileIdentifiableGlobalIdentifierBusiness.findByCriteria(searchCriteria);
			assertEquals(4, fileIdentifiableGlobalIdentifiers.size());
			
			searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
			searchCriteria.addRepresentationType(inject(FileRepresentationTypeDao.class).read(RootConstant.Code.FileRepresentationType.IDENTITY_DOCUMENT));
			searchCriteria.addGlobalIdentifier(sexMale.getGlobalIdentifier());
			searchCriteria.addGlobalIdentifier(sexFemale.getGlobalIdentifier());
			fileIdentifiableGlobalIdentifiers = fileIdentifiableGlobalIdentifierBusiness.findByCriteria(searchCriteria);
			assertEquals(2, fileIdentifiableGlobalIdentifiers.size());
			
			searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
			searchCriteria.addGlobalIdentifier(sexMale.getGlobalIdentifier());
			searchCriteria.addRepresentationType(inject(FileRepresentationTypeDao.class).read(RootConstant.Code.FileRepresentationType.IDENTITY_DOCUMENT));
			fileIdentifiableGlobalIdentifiers = fileIdentifiableGlobalIdentifierBusiness.findByCriteria(searchCriteria);
			assertEquals(1, fileIdentifiableGlobalIdentifiers.size());
			
			fileBusiness.writeTo(fileIdentifiableGlobalIdentifiers.iterator().next().getFile(), new java.io.File(System.getProperty("user.dir")+"/target"), "mypointofsale");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
   
    
    
}
