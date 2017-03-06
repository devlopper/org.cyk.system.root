package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MetricBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionTypeDao;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;

public class MetricCollectionBusinessImpl extends AbstractCollectionBusinessImpl<MetricCollection, Metric,MetricCollectionDao,MetricDao,MetricBusiness> implements MetricCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MetricCollectionBusinessImpl(MetricCollectionDao dao) {
		super(dao); 
	}
	
	@Override
	public MetricCollection create(MetricCollection metricCollection) {
		createIfNotIdentified(metricCollection.getValueProperties());
		return super.create(metricCollection);
	}
	
	@Override
	protected MetricCollection __instanciateOne__(String[] values,org.cyk.system.root.business.api.TypedBusiness.InstanciateOneListener<MetricCollection> listener) {
		MetricCollection metricCollection = super.__instanciateOne__(values, listener);
		set(listener.getSetListener().setIndex(10),MetricCollection.FIELD_TYPE);
		set(listener.getSetListener(),MetricCollection.FIELD_VALUE_PROPERTIES);
		set(listener.getSetListener(),MetricCollection.FIELD_VALUE);
		return metricCollection;
	}
			 
	/*@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MetricCollection instanciateOne(String code,String name,MetricCollectionType type,MetricValueType metricValueType,String[] items,String intervalCollectionName,String[][] intervals){
		MetricCollection collection = instanciateOne(code,name,items);
		collection.setType(type);
		collection.setValueType(metricValueType);
		if(StringUtils.isNotBlank(intervalCollectionName) && intervals!=null){
			if(intervalCollectionName==null)
				intervalCollectionName = code+Constant.CHARACTER_UNDESCORE+collection.getClass().getSimpleName()+Constant.CHARACTER_UNDESCORE+Interval.class.getSimpleName();
			collection.setValueIntervalCollection(inject(IntervalCollectionBusiness.class).instanciateOne(code, intervalCollectionName, intervals));
		}
		return collection;
	}
	
	@Override
	public MetricCollection instanciateOne(String code, String name,MetricCollectionType type, MetricValueType metricValueType, String[] items,String[][] intervals) {
		return instanciateOne(code, name,type, metricValueType, items, null, intervals);
	}*/
	
	@Override
	public MetricCollection instanciateOne(String code, String name, String metricCollectionTypeCode, String[] items) {
		MetricCollection collection = instanciateOne(code,name,items);
		collection.setType(inject(MetricCollectionTypeDao.class).read(metricCollectionTypeCode));
		return collection;
	}
	
	@Override
	public MetricCollection instanciateOne(String code, String name, String metricCollectionTypeCode, String[][] items) {
		MetricCollection collection = instanciateOne(code,name,items);
		collection.setType(inject(MetricCollectionTypeDao.class).read(metricCollectionTypeCode));
		return collection;
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
