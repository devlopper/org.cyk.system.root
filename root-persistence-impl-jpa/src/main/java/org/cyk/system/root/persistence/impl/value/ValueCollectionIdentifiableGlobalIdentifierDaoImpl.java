package org.cyk.system.root.persistence.impl.value;

import java.io.Serializable;

import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.value.ValueCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.value.ValueCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.globalidentification.AbstractJoinGlobalIdentifierDaoImpl;
import org.cyk.utility.common.Constant;

public class ValueCollectionIdentifiableGlobalIdentifierDaoImpl extends AbstractJoinGlobalIdentifierDaoImpl<ValueCollectionIdentifiableGlobalIdentifier,ValueCollectionIdentifiableGlobalIdentifier.SearchCriteria> implements ValueCollectionIdentifiableGlobalIdentifierDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	@Override
	protected String getReadByCriteriaQueryString() {
		return super.getReadByCriteriaQueryString()+Constant.CHARACTER_SPACE+"AND r.valueCollection.identifier IN :"+PARAMETER_VALUE_COLLECTION_IDENTIFIERS;
	}
	
	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameter(PARAMETER_VALUE_COLLECTION_IDENTIFIERS, ids(((ValueCollectionIdentifiableGlobalIdentifier.SearchCriteria)searchCriteria).getValueCollections()));
	}
	
	public static final String PARAMETER_VALUE_COLLECTION_IDENTIFIERS = "valueCollectionIdentifiers";
}
 