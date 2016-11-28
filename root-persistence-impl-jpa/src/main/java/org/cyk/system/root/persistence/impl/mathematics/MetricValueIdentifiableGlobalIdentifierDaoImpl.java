package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.mathematics.MetricValueIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.globalidentification.AbstractJoinGlobalIdentifierDaoImpl;
import org.cyk.utility.common.Constant;

public class MetricValueIdentifiableGlobalIdentifierDaoImpl extends AbstractJoinGlobalIdentifierDaoImpl<MetricValueIdentifiableGlobalIdentifier,MetricValueIdentifiableGlobalIdentifier.SearchCriteria> implements MetricValueIdentifiableGlobalIdentifierDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	@Override
	protected String getReadByCriteriaQueryString() {
		return super.getReadByCriteriaQueryString()+Constant.CHARACTER_SPACE+"AND r.metricValue.metric.identifier IN :"+PARAMETER_METRIC_IDENTIFIERS;
	}
	
	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameter(PARAMETER_METRIC_IDENTIFIERS, ids(((MetricValueIdentifiableGlobalIdentifier.SearchCriteria)searchCriteria).getMetrics()));
	}
	
	public static final String PARAMETER_METRIC_IDENTIFIERS = "metricIdentifiers";
}
 