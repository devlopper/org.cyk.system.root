package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.globalidentification.AbstractJoinGlobalIdentifierDaoImpl;
import org.cyk.utility.common.Constant;

public class MetricCollectionIdentifiableGlobalIdentifierDaoImpl extends AbstractJoinGlobalIdentifierDaoImpl<MetricCollectionIdentifiableGlobalIdentifier,MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria> implements MetricCollectionIdentifiableGlobalIdentifierDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	@Override
	protected String getReadByCriteriaQueryString() {
		return super.getReadByCriteriaQueryString()+Constant.CHARACTER_SPACE+"AND r.metricCollection.type.identifier IN :"+PARAMETER_METRIC_COLLECTION_TYPE_IDENTIFIERS;
	}
	
	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameter(PARAMETER_METRIC_COLLECTION_TYPE_IDENTIFIERS, ids(((MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria)searchCriteria).getMetricCollectionTypes()));
	}
	
	public static final String PARAMETER_METRIC_COLLECTION_TYPE_IDENTIFIERS = "metricCollectionIdentifiers";
}
 