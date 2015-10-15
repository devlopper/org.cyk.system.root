package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;

@Stateless
public class MetricBusinessImpl extends AbstractCollectionItemBusinessImpl<Metric,MetricDao,MetricCollection> implements MetricBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private IntervalCollectionBusiness intervalCollectionBusiness;
	
	@Inject
	public MetricBusinessImpl(MetricDao dao) {
		super(dao); 
	} 
	
	@Override
	public Metric update(Metric metric) {
		if(metric.getValueIntervalCollection()!=null)
			if(metric.getValueIntervalCollection().getIdentifier()==null){
				intervalCollectionBusiness.create(metric.getValueIntervalCollection());
			}else
				intervalCollectionBusiness.update(metric.getValueIntervalCollection());
		super.update(metric);
		
		return metric;
	}
	
}
