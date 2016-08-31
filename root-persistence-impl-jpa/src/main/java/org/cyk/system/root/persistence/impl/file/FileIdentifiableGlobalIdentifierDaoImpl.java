package org.cyk.system.root.persistence.impl.file;

import java.io.Serializable;

import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.file.FileIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.globalidentification.AbstractJoinGlobalIdentifierDaoImpl;
import org.cyk.utility.common.Constant;

public class FileIdentifiableGlobalIdentifierDaoImpl extends AbstractJoinGlobalIdentifierDaoImpl<FileIdentifiableGlobalIdentifier,FileIdentifiableGlobalIdentifier.SearchCriteria> implements FileIdentifiableGlobalIdentifierDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	@Override
	protected String getReadByCriteriaQueryString() {
		return super.getReadByCriteriaQueryString()+Constant.CHARACTER_SPACE+"AND r.file.representationType.identifier IN :"+PARAMETER_FILE_IDENTIFIERS;
	}
	
	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameter(PARAMETER_FILE_IDENTIFIERS, ids(((FileIdentifiableGlobalIdentifier.SearchCriteria)searchCriteria).getRepresentationTypes()));
	}
	
	public static final String PARAMETER_FILE_IDENTIFIERS = "fileIdentifiers";
}
 