package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;

@Stateless
public class MetricCollectionBusinessImpl extends AbstractCollectionBusinessImpl<MetricCollection, Metric,MetricCollectionDao,MetricDao> implements MetricCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private IntervalCollectionBusiness intervalCollectionBusiness;
	@Inject MetricDao metricCollectionItemDao;
	
	@Inject
	public MetricCollectionBusinessImpl(MetricCollectionDao dao) {
		super(dao); 
	}
	
	@Override
	public MetricCollection create(MetricCollection metricCollection) {
		if(metricCollection.getValueIntervalCollection()!=null)
			if(metricCollection.getValueIntervalCollection().getIdentifier()==null){
				intervalCollectionBusiness.create(metricCollection.getValueIntervalCollection());
			}else
				intervalCollectionBusiness.update(metricCollection.getValueIntervalCollection());
		return super.create(metricCollection);
	}
	
	@Override
	protected MetricDao getItemDao() {
		return metricCollectionItemDao;
	}
	
	@Override
	protected void __load__(MetricCollection collection) {
		super.__load__(collection);
		collection.setValues(values);
	}

}
