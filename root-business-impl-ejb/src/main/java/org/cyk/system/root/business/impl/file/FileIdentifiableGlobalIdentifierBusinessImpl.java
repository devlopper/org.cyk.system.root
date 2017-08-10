package org.cyk.system.root.business.impl.file;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.FileIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier.SearchCriteria;
import org.cyk.system.root.persistence.api.file.FileIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;

public class FileIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<FileIdentifiableGlobalIdentifier, FileIdentifiableGlobalIdentifierDao,FileIdentifiableGlobalIdentifier.SearchCriteria> implements FileIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public FileIdentifiableGlobalIdentifierBusinessImpl(FileIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
	@Override
	protected void beforeDelete(FileIdentifiableGlobalIdentifier fileIdentifiableGlobalIdentifier) {
		super.beforeDelete(fileIdentifiableGlobalIdentifier);
		
		//TODO is it the right place : report and file are too different package so report logic should be there
		//for(ReportFile reportFile : inject(ReportFileDao.class).readByFile(fileIdentifiableGlobalIdentifier.getFile()))
		//	inject(ReportFileBusiness.class).delete(reportFile);
		
		//inject(FileBusiness.class).delete(fileIdentifiableGlobalIdentifier.getFile());
		//fileIdentifiableGlobalIdentifier.setFile(null);
		//fileIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifier(null);
	}
	
	@Override
	public Collection<FileIdentifiableGlobalIdentifier> findByCriteria(SearchCriteria searchCriteria) {
		if(searchCriteria.getRepresentationTypes().isEmpty())
			searchCriteria.setRepresentationTypes(inject(FileRepresentationTypeDao.class).readAll());
		return super.findByCriteria(searchCriteria);
	}
	
}
