package org.cyk.system.root.business.impl.file;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.file.FileIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.file.FileIdentifiableGlobalIdentifierDao;

@Stateless
public class FileIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<FileIdentifiableGlobalIdentifier, FileIdentifiableGlobalIdentifierDao,FileIdentifiableGlobalIdentifier.SearchCriteria> implements FileIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public FileIdentifiableGlobalIdentifierBusinessImpl(FileIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
	@Override
	public FileIdentifiableGlobalIdentifier create(FileIdentifiableGlobalIdentifier fileIdentifiableGlobalIdentifier) {
		if(fileIdentifiableGlobalIdentifier.getFile().getIdentifier()==null)
			RootBusinessLayer.getInstance().getFileBusiness().create(fileIdentifiableGlobalIdentifier.getFile());
		return super.create(fileIdentifiableGlobalIdentifier);
	}
	
}
