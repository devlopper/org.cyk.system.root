package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionTypeDao;

public class MetricCollectionIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<MetricCollectionIdentifiableGlobalIdentifier, MetricCollectionIdentifiableGlobalIdentifierDao,MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria> implements MetricCollectionIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public MetricCollectionIdentifiableGlobalIdentifierBusinessImpl(MetricCollectionIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
	@Override
	public MetricCollectionIdentifiableGlobalIdentifier create(MetricCollectionIdentifiableGlobalIdentifier metricCollectionIdentifiableGlobalIdentifier) {
		createIfNotIdentified(metricCollectionIdentifiableGlobalIdentifier.getMetricCollection());
		createIfNotIdentified(metricCollectionIdentifiableGlobalIdentifier.getValue());
		return super.create(metricCollectionIdentifiableGlobalIdentifier);
	}
	
	@Override
	public MetricCollectionIdentifiableGlobalIdentifier update(MetricCollectionIdentifiableGlobalIdentifier metricCollectionIdentifiableGlobalIdentifier) {
		inject(MetricCollectionBusiness.class).update(metricCollectionIdentifiableGlobalIdentifier.getMetricCollection());
		return super.update(metricCollectionIdentifiableGlobalIdentifier);
	}
	
	@Override
	public MetricCollectionIdentifiableGlobalIdentifier delete(MetricCollectionIdentifiableGlobalIdentifier metricCollectionIdentifiableGlobalIdentifier) {
		inject(MetricCollectionBusiness.class).delete(metricCollectionIdentifiableGlobalIdentifier.getMetricCollection());
		metricCollectionIdentifiableGlobalIdentifier.setMetricCollection(null);
		metricCollectionIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifier(null);
		return super.delete(metricCollectionIdentifiableGlobalIdentifier);
	}
	
	@Override
	public Collection<MetricCollectionIdentifiableGlobalIdentifier> findByCriteria(SearchCriteria searchCriteria) {
		if(searchCriteria.getMetricCollectionTypes().isEmpty())
			searchCriteria.setMetricCollectionTypes(inject(MetricCollectionTypeDao.class).readAll());
		return super.findByCriteria(searchCriteria);
	}

	
	@Override
	public void create(Collection<MetricCollection> metricCollections,Collection<? extends AbstractIdentifiable> identifiables,ValueProperties valueProperties) {
		Collection<MetricCollectionIdentifiableGlobalIdentifier> collection = new ArrayList<>();
		for(MetricCollection metricCollection : metricCollections)
			for(AbstractIdentifiable identifiable : identifiables)
				collection.add(new MetricCollectionIdentifiableGlobalIdentifier(metricCollection, identifiable,valueProperties == null ? null : new Value(valueProperties)));
		create(collection);
	}

	@Override
	public void create(Collection<MetricCollection> metricCollections, AbstractIdentifiable identifiable,ValueProperties valueProperties) {
		create(metricCollections,Arrays.asList(identifiable),valueProperties);
	}

	@Override
	public void create(MetricCollection metricCollection, Collection<? extends AbstractIdentifiable> identifiables,ValueProperties valueProperties) {
		create(Arrays.asList(metricCollection),identifiables,valueProperties);
	}

	@Override
	public void create(MetricCollection metricCollection, AbstractIdentifiable identifiable,ValueProperties valueProperties) {
		create(Arrays.asList(metricCollection),Arrays.asList(identifiable),valueProperties);
	}
	
}
