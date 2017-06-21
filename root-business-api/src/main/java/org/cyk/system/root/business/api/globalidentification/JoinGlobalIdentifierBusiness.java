package org.cyk.system.root.business.api.globalidentification;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;

public interface JoinGlobalIdentifierBusiness<IDENTIFIABLE extends AbstractIdentifiable,SEARCH_CRITERIA extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria> extends TypedBusiness<IDENTIFIABLE> {

	Collection<IDENTIFIABLE> findByIdentifiableGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers);
	Collection<IDENTIFIABLE> findByIdentifiableGlobalIdentifier(GlobalIdentifier globalIdentifier);
	Collection<IDENTIFIABLE> findByIdentifiableGlobalIdentifier(AbstractIdentifiable identifiable);
	
	Collection<IDENTIFIABLE> findByCriteria(SEARCH_CRITERIA searchCriteria);
	Long countByCriteria(SEARCH_CRITERIA searchCriteria);
	
}
