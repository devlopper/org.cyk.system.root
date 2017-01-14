package org.cyk.system.root.business.impl.file;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.FileRepresentationTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;

public class FileRepresentationTypeBusinessImpl extends AbstractEnumerationBusinessImpl<FileRepresentationType, FileRepresentationTypeDao> implements FileRepresentationTypeBusiness, Serializable {
	private static final long serialVersionUID = 8072220305781523624L;
	
	@Inject
	public FileRepresentationTypeBusinessImpl(FileRepresentationTypeDao dao) { 
		super(dao);
	}
	

}
