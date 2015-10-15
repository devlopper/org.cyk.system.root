package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MetricCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionItemDao;

public class MetricCollectionItemBusinessImpl extends AbstractCollectionItemBusinessImpl<Metric,MetricCollectionItemDao,MetricCollection> implements MetricCollectionItemBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MetricCollectionItemBusinessImpl(MetricCollectionItemDao dao) {
		super(dao); 
	}   
	
}
