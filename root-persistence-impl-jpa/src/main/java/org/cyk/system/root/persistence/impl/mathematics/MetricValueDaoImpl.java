package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.persistence.api.mathematics.MetricValueDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class MetricValueDaoImpl extends AbstractTypedDao<MetricValue> implements MetricValueDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByMetrics;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByMetrics, _select().whereIdentifierIn(MetricValue.FIELD_METRIC));
	}
	
	@Override
	public Collection<MetricValue> readByMetrics(Collection<Metric> metrics) {
		return namedQuery(readByMetrics).parameterIdentifiers(metrics).resultMany();
	}
	

    
   

}
 