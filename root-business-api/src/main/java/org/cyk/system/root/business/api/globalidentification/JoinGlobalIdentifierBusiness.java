package org.cyk.system.root.business.api.globalidentification;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;

public interface JoinGlobalIdentifierBusiness<IDENTIFIABLE extends AbstractIdentifiable,SEARCH_CRITERIA extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria> extends TypedBusiness<IDENTIFIABLE> {

	Collection<IDENTIFIABLE> findByCriteria(SEARCH_CRITERIA searchCriteria);
	Long countByCriteria(SEARCH_CRITERIA searchCriteria);
	
}
