package org.cyk.system.root.persistence.api.file;

import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface FileIdentifiableGlobalIdentifierDao extends JoinGlobalIdentifierDao<FileIdentifiableGlobalIdentifier,FileIdentifiableGlobalIdentifier.SearchCriteria> {
	
}
