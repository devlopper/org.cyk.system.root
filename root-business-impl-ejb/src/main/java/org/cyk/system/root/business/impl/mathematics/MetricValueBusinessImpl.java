package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MetricValueBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.mathematics.MetricValueDao;

public class MetricValueBusinessImpl extends AbstractTypedBusinessService<MetricValue, MetricValueDao> implements MetricValueBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MetricValueBusinessImpl(MetricValueDao dao) {
		super(dao); 
	}

	@Override
	public Collection<MetricValue> findByMetricsByIdentifiables(Collection<Metric> metrics,Collection<? extends AbstractIdentifiable> identifiables) {
		MetricValueIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new MetricValueIdentifiableGlobalIdentifier.SearchCriteria();
		searchCriteria.addIdentifiablesGlobalIdentifiers(identifiables).addMetrics(metrics);
		Collection<MetricValue> metricValues = new ArrayList<>();
		for(MetricValueIdentifiableGlobalIdentifier metricValueIdentifiableGlobalIdentifier : inject(MetricValueIdentifiableGlobalIdentifierBusiness.class).findByCriteria(searchCriteria))
			metricValues.add(metricValueIdentifiableGlobalIdentifier.getMetricValue());
		return metricValues;
	}

}
