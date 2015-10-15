package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionItemDao;

@Stateless
public class MetricCollectionBusinessImpl extends AbstractCollectionBusinessImpl<MetricCollection, Metric,MetricCollectionDao,MetricCollectionItemDao> implements MetricCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject MetricCollectionItemDao metricCollectionItemDao;
	
	@Inject
	public MetricCollectionBusinessImpl(MetricCollectionDao dao) {
		super(dao); 
	}

	@Override
	protected MetricCollectionItemDao getItemDao() {
		return metricCollectionItemDao;
	}
	

}
