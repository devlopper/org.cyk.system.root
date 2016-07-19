package org.cyk.system.root.persistence.api.globalidentification;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.persistence.api.TypedDao;

public interface JoinGlobalIdentifierDao<IDENTIFIABLE extends AbstractIdentifiable,SEARCH_CRITERIA extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria> extends TypedDao<IDENTIFIABLE> {

	Collection<IDENTIFIABLE> readByCriteria(SEARCH_CRITERIA searchCriteria);
	Long countByCriteria(SEARCH_CRITERIA searchCriteria);
	
}
