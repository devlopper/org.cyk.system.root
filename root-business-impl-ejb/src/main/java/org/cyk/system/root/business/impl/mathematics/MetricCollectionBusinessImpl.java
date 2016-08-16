package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValueType;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;
import org.cyk.utility.common.Constant;

@Stateless
public class MetricCollectionBusinessImpl extends AbstractCollectionBusinessImpl<MetricCollection, Metric,MetricCollectionDao,MetricDao,MetricBusiness> implements MetricCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private MetricDao metricDao;
	
	@Inject
	public MetricCollectionBusinessImpl(MetricCollectionDao dao) {
		super(dao); 
	}
	
	@Override
	public MetricCollection create(MetricCollection metricCollection) {
		if(metricCollection.getValueIntervalCollection()!=null)
			if(metricCollection.getValueIntervalCollection().getIdentifier()==null){
				inject(IntervalCollectionBusiness.class).create(metricCollection.getValueIntervalCollection());
			}else
				inject(IntervalCollectionBusiness.class).update(metricCollection.getValueIntervalCollection());
		return super.create(metricCollection);
	}
	
	@Override
	protected MetricBusiness getItemBusiness() {
		return inject(MetricBusiness.class);
	}
	
	@Override
	protected MetricDao getItemDao() {
		return metricDao;
	}
	 
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MetricCollection instanciateOne(String code,String name,MetricValueType metricValueType,String[] items,String intervalCollectionName,String[][] intervals){
		MetricCollection collection = instanciateOne(code,name,items);
		collection.setValueType(metricValueType);
		if(intervalCollectionName==null)
			intervalCollectionName = code+Constant.CHARACTER_UNDESCORE+collection.getClass().getSimpleName()+Constant.CHARACTER_UNDESCORE+Interval.class.getSimpleName();
		collection.setValueIntervalCollection(inject(IntervalCollectionBusiness.class).instanciateOne(code, intervalCollectionName, intervals));
		return collection;
	}
	
	@Override
	public MetricCollection instanciateOne(String code, String name, MetricValueType metricValueType, String[] items,String[][] intervals) {
		return instanciateOne(code, name, metricValueType, items, null, intervals);
	}
	
}
