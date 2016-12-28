package org.cyk.system.root.persistence.impl.event;

import java.io.Serializable;

import org.cyk.system.root.model.event.EventIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.event.EventIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.globalidentification.AbstractJoinGlobalIdentifierDaoImpl;
import org.cyk.utility.common.Constant;

public class EventIdentifiableGlobalIdentifierDaoImpl extends AbstractJoinGlobalIdentifierDaoImpl<EventIdentifiableGlobalIdentifier,EventIdentifiableGlobalIdentifier.SearchCriteria> implements EventIdentifiableGlobalIdentifierDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	@Override
	protected String getReadByCriteriaQueryString() {
		return super.getReadByCriteriaQueryString()+Constant.CHARACTER_SPACE+"AND r.event.type.identifier IN :"+PARAMETER_VALUE_COLLECTION_IDENTIFIERS;
	}
	
	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameter(PARAMETER_VALUE_COLLECTION_IDENTIFIERS, ids(((EventIdentifiableGlobalIdentifier.SearchCriteria)searchCriteria).getTypes()));
	}
	
	public static final String PARAMETER_VALUE_COLLECTION_IDENTIFIERS = "eventTypeIdentifiers";
}
 