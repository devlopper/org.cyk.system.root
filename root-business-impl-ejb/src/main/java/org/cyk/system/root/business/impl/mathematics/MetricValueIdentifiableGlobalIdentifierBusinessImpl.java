package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MetricValueBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier.SearchCriteria;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;
import org.cyk.system.root.persistence.api.mathematics.MetricValueIdentifiableGlobalIdentifierDao;

public class MetricValueIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<MetricValueIdentifiableGlobalIdentifier, MetricValueIdentifiableGlobalIdentifierDao,MetricValueIdentifiableGlobalIdentifier.SearchCriteria> implements MetricValueIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public MetricValueIdentifiableGlobalIdentifierBusinessImpl(MetricValueIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
	@Override
	public void create(Collection<MetricCollection> metricCollections,Collection<? extends AbstractIdentifiable> identifiables) {
		Collection<MetricValue> metricValues = new ArrayList<>();
		Collection<MetricValueIdentifiableGlobalIdentifier> metricValueIdentifiableGlobalIdentifiers = new ArrayList<>();
		for(MetricCollection metricCollection : metricCollections){
			for(Metric metric : inject(MetricDao.class).readByCollection(metricCollection)){
				for(AbstractIdentifiable identifiable : identifiables){
					MetricValue metricValue = new MetricValue(metric,new Value(metric.getValueProperties()));
					metricValues.add(metricValue);
					metricValueIdentifiableGlobalIdentifiers.add(new MetricValueIdentifiableGlobalIdentifier(metricValue, identifiable));
				}
			}
		}
		inject(MetricValueBusiness.class).create(metricValues);
		create(metricValueIdentifiableGlobalIdentifiers);
	}
	
	@Override
	public MetricValueIdentifiableGlobalIdentifier create(MetricValueIdentifiableGlobalIdentifier metricValueIdentifiableGlobalIdentifier) {
		createIfNotIdentified(metricValueIdentifiableGlobalIdentifier.getMetricValue());
		return super.create(metricValueIdentifiableGlobalIdentifier);
	}
	
	@Override
	public MetricValueIdentifiableGlobalIdentifier update(MetricValueIdentifiableGlobalIdentifier metricValueIdentifiableGlobalIdentifier) {
		inject(MetricValueBusiness.class).update(metricValueIdentifiableGlobalIdentifier.getMetricValue());
		return super.update(metricValueIdentifiableGlobalIdentifier);
	}
	
	@Override
	public MetricValueIdentifiableGlobalIdentifier delete(MetricValueIdentifiableGlobalIdentifier metricValueIdentifiableGlobalIdentifier) {
		inject(MetricValueBusiness.class).delete(metricValueIdentifiableGlobalIdentifier.getMetricValue());
		metricValueIdentifiableGlobalIdentifier.setMetricValue(null);
		metricValueIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifier(null);
		return super.delete(metricValueIdentifiableGlobalIdentifier);
	}
	
	@Override
	public void delete(Collection<MetricCollection> metricCollections,Collection<? extends AbstractIdentifiable> identifiables) {
		Collection<Metric> metrics = inject(MetricDao.class).readByCollections(metricCollections);
		delete(inject(MetricValueIdentifiableGlobalIdentifierDao.class)
				.readByCriteria(new MetricValueIdentifiableGlobalIdentifier.SearchCriteria().addIdentifiablesGlobalIdentifiers(identifiables)
						.addMetrics(metrics)));
	}
	
	@Override
	public Collection<MetricValueIdentifiableGlobalIdentifier> findByCriteria(SearchCriteria searchCriteria) {
		if(searchCriteria.getMetrics().isEmpty())
			searchCriteria.setMetrics(inject(MetricDao.class).readAll());
		return super.findByCriteria(searchCriteria);
	}
	
}
