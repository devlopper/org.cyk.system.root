package org.cyk.system.root.business.impl.file;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.FileIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.file.report.ReportFileBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier.SearchCriteria;
import org.cyk.system.root.model.file.report.ReportFile;
import org.cyk.system.root.persistence.api.file.FileIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;
import org.cyk.system.root.persistence.api.file.report.ReportFileDao;

public class FileIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<FileIdentifiableGlobalIdentifier, FileIdentifiableGlobalIdentifierDao,FileIdentifiableGlobalIdentifier.SearchCriteria> implements FileIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public FileIdentifiableGlobalIdentifierBusinessImpl(FileIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
	@Override
	public FileIdentifiableGlobalIdentifier create(FileIdentifiableGlobalIdentifier fileIdentifiableGlobalIdentifier) {
		if(fileIdentifiableGlobalIdentifier.getFile().getIdentifier()==null)
			inject(FileBusiness.class).create(fileIdentifiableGlobalIdentifier.getFile());
		return super.create(fileIdentifiableGlobalIdentifier);
	}
	
	@Override
	public FileIdentifiableGlobalIdentifier update(FileIdentifiableGlobalIdentifier fileIdentifiableGlobalIdentifier) {
		inject(FileBusiness.class).update(fileIdentifiableGlobalIdentifier.getFile());
		return super.update(fileIdentifiableGlobalIdentifier);
	}
	
	@Override
	public FileIdentifiableGlobalIdentifier delete(FileIdentifiableGlobalIdentifier fileIdentifiableGlobalIdentifier) {
		//TODO is it the right place : report and file are too different package so report logic should be there
		for(ReportFile reportFile : inject(ReportFileDao.class).readByFile(fileIdentifiableGlobalIdentifier.getFile()))
			inject(ReportFileBusiness.class).delete(reportFile);
		
		inject(FileBusiness.class).delete(fileIdentifiableGlobalIdentifier.getFile());
		fileIdentifiableGlobalIdentifier.setFile(null);
		fileIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifier(null);
		return super.delete(fileIdentifiableGlobalIdentifier);
	}
	
	@Override
	public Collection<FileIdentifiableGlobalIdentifier> findByCriteria(SearchCriteria searchCriteria) {
		if(searchCriteria.getRepresentationTypes().isEmpty())
			searchCriteria.setRepresentationTypes(inject(FileRepresentationTypeDao.class).readAll());
		return super.findByCriteria(searchCriteria);
	}
	
}
