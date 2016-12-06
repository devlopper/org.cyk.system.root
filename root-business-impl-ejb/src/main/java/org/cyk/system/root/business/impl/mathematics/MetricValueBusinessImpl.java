package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.mathematics.MetricBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionTypeDao;
import org.cyk.system.root.persistence.api.mathematics.MetricValueDao;

@Stateless
public class MetricValueBusinessImpl extends AbstractTypedBusinessService<MetricValue, MetricValueDao> implements MetricValueBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MetricValueBusinessImpl(MetricValueDao dao) {
		super(dao); 
	}
	
	@Override
	protected void beforeCreate(MetricValue metricValue) {
		super.beforeCreate(metricValue);
		//if(metricValue.getValue()==null)
		//	metricValue.setValue(new Value(metricValue.getValueProperties()));
		createIfNotIdentified(metricValue.getValue());
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<MetricValue> findByMetricsByIdentifiables(Collection<Metric> metrics,Collection<? extends AbstractIdentifiable> identifiables) {
		MetricValueIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new MetricValueIdentifiableGlobalIdentifier.SearchCriteria();
		searchCriteria.addIdentifiablesGlobalIdentifiers(identifiables).addMetrics(metrics);
		Collection<MetricValue> metricValues = new ArrayList<>();
		for(MetricValueIdentifiableGlobalIdentifier metricValueIdentifiableGlobalIdentifier : inject(MetricValueIdentifiableGlobalIdentifierBusiness.class).findByCriteria(searchCriteria))
			metricValues.add(metricValueIdentifiableGlobalIdentifier.getMetricValue());
		return metricValues;
	}
	
	@Override
	public void updateManyRandomly(Collection<String> metricCollectionCodes,Collection<? extends AbstractIdentifiable> metricCollectionIdentifiables,Collection<? extends AbstractIdentifiable> metricValueIdentifiables) {
		Collection<Value> values = new ArrayList<>();
		for(AbstractIdentifiable identifiable : metricValueIdentifiables){
			Collection<MetricCollection> metricCollections = inject(MetricCollectionBusiness.class).findByTypesByIdentifiables(inject(MetricCollectionTypeDao.class)
					.read(metricCollectionCodes), metricCollectionIdentifiables);
			for(MetricCollection metricCollection : metricCollections){
				Collection<Metric> metrics = inject(MetricBusiness.class).findByCollection(metricCollection);
				Collection<MetricValue> metricValues = inject(MetricValueBusiness.class).findByMetricsByIdentifiables(metrics,Arrays.asList(identifiable));
				/*IntervalCollection intervalCollection = null;
				List<Interval> intervals = null;
				if(metricCollection.getValueIntervalCollection()!=null){
					intervalCollection = metricCollection.getValueIntervalCollection();
					inject(IntervalCollectionBusiness.class).load(intervalCollection);
					intervals = new ArrayList<>(inject(IntervalBusiness.class).findByCollection(intervalCollection));
				}
				*/	
				for(MetricValue metricValue : metricValues)
					values.add(metricValue.getValue());
					
			}
		}
		inject(ValueBusiness.class).setManyRandomly(values);
		inject(GenericBusiness.class).update(commonUtils.castCollection(values, AbstractIdentifiable.class));
	}

	@Override
	public Collection<MetricValue> findByMetrics(Collection<Metric> metrics) {
		return dao.readByMetrics(metrics);
	}

}
