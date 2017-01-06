package org.cyk.system.root.business.impl.integration;

import java.util.Collection;

import org.cyk.system.root.business.api.file.FileRepresentationTypeBusiness;
import org.cyk.system.root.business.impl.ManyConverter;
import org.cyk.system.root.business.impl.OneConverter;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;

public class ConvertIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

	@Override
	protected void businesses() {
		Collection<FileRepresentationType> fileRepresentationTypes = inject(FileRepresentationTypeDao.class).readAll();
		
		inject(FileRepresentationTypeBusiness.class).convert(new OneConverter.ConverterToArray<FileRepresentationType, String[]>(FileRepresentationType.class
				,inject(FileRepresentationTypeDao.class).readOneRandomly(), String[].class, null));
		
		inject(FileRepresentationTypeBusiness.class).convert(new ManyConverter.ConverterToArray<>(fileRepresentationTypes, String[][].class, null));
		
	}
   
    
    
}
