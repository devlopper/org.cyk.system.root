package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.model.mathematics.MetricValueType;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;
import org.cyk.utility.common.Constant;

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
	public MetricCollection instanciateOne(String code,String name,MetricCollectionType type,MetricValueType metricValueType,String[] items,String intervalCollectionName,String[][] intervals){
		MetricCollection collection = instanciateOne(code,name,items);
		collection.setType(type);
		collection.setValueType(metricValueType);
		if(intervalCollectionName==null)
			intervalCollectionName = code+Constant.CHARACTER_UNDESCORE+collection.getClass().getSimpleName()+Constant.CHARACTER_UNDESCORE+Interval.class.getSimpleName();
		collection.setValueIntervalCollection(inject(IntervalCollectionBusiness.class).instanciateOne(code, intervalCollectionName, intervals));
		return collection;
	}
	
	@Override
	public MetricCollection instanciateOne(String code, String name,MetricCollectionType type, MetricValueType metricValueType, String[] items,String[][] intervals) {
		return instanciateOne(code, name,type, metricValueType, items, null, intervals);
	}

	@Override
	public Collection<MetricCollection> findByMetricCollectionIdentifiableGlobalIdentifierSearchCriteria(SearchCriteria searchCriteria) {
		Collection<MetricCollectionIdentifiableGlobalIdentifier> metricCollectionIdentifiableGlobalIdentifiers = inject(MetricCollectionIdentifiableGlobalIdentifierDao.class).readByCriteria(searchCriteria);
		Collection<MetricCollection> metricCollections = new LinkedHashSet<>();
		for(MetricCollectionIdentifiableGlobalIdentifier metricCollectionIdentifiableGlobalIdentifier : metricCollectionIdentifiableGlobalIdentifiers)
			metricCollections.add(metricCollectionIdentifiableGlobalIdentifier.getMetricCollection());
		return new ArrayList<>(metricCollections);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<MetricCollection> findByTypesByIdentifiables(Collection<MetricCollectionType> metricCollectionTypes,Collection<? extends AbstractIdentifiable> identifiables) {
		MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria();
    	searchCriteria.addIdentifiablesGlobalIdentifiers((Collection<AbstractIdentifiable>) identifiables);
    	searchCriteria.addMetricCollectionTypes(metricCollectionTypes);
		return findByMetricCollectionIdentifiableGlobalIdentifierSearchCriteria(searchCriteria);
	}

	@Override
	public Collection<MetricCollection> findByTypesByIdentifiable(Collection<MetricCollectionType> metricCollectionTypes, AbstractIdentifiable identifiable) {
		return findByTypesByIdentifiables(metricCollectionTypes,Arrays.asList(identifiable));
	}

	@Override
	public Collection<MetricCollection> findByTypeByIdentifiables(MetricCollectionType metricCollectionType, Collection<? extends AbstractIdentifiable> identifiables) {
		return findByTypesByIdentifiables(Arrays.asList(metricCollectionType),identifiables);
	}

	@Override
	public Collection<MetricCollection> findByTypeByIdentifiable(MetricCollectionType metricCollectionType, AbstractIdentifiable identifiable) {
		return findByTypesByIdentifiables(Arrays.asList(metricCollectionType),Arrays.asList(identifiable));
	}
	
}
