package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MetricBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness.Derive;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionTypeDao;
import org.cyk.system.root.persistence.api.mathematics.MetricValueDao;
import org.cyk.utility.common.LogMessage;

public class MetricValueBusinessImpl extends AbstractTypedBusinessService<MetricValue, MetricValueDao> implements MetricValueBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MetricValueBusinessImpl(MetricValueDao dao) {
		super(dao); 
	}
	
	@Override
	protected void beforeCreate(MetricValue metricValue) {
		super.beforeCreate(metricValue);
		setFieldValuesIfBlank(metricValue, metricValue.getValue());
		createIfNotIdentified(metricValue.getValue());
	}
	
	@Override
	protected void afterUpdate(MetricValue metricValue) {
		super.afterUpdate(metricValue);
		setFieldValuesIfBlank(metricValue, metricValue.getValue());
		inject(ValueBusiness.class).update(metricValue.getValue());
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<MetricValue> findByMetricsByIdentifiables(Collection<Metric> metrics,Collection<? extends AbstractIdentifiable> identifiables) {
		LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Find","metrics identifiables");
		MetricValueIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new MetricValueIdentifiableGlobalIdentifier.SearchCriteria();
		searchCriteria.addIdentifiablesGlobalIdentifiers(identifiables).addMetrics(metrics);
		Collection<MetricValue> metricValues = new ArrayList<>();
		logMessageBuilder.addParameters("criteria",searchCriteria);
		Collection<MetricValueIdentifiableGlobalIdentifier> metricValueIdentifiableGlobalIdentifiers = inject(MetricValueIdentifiableGlobalIdentifierBusiness.class).findByCriteria(searchCriteria);
		logMessageBuilder.addParameters("results",metricValueIdentifiableGlobalIdentifiers);
		for(MetricValueIdentifiableGlobalIdentifier metricValueIdentifiableGlobalIdentifier : metricValueIdentifiableGlobalIdentifiers)
			metricValues.add(metricValueIdentifiableGlobalIdentifier.getMetricValue());
		logTrace(logMessageBuilder);
		return metricValues;
	}
	
	@Override
	public Collection<MetricValue> findByMetricCollectionByMetricByIdentifiable(String metricCollectionCode,String metricRelativeCode, AbstractIdentifiable identifiable) {
		return findByMetricsByIdentifiables(Arrays.asList(inject(MetricBusiness.class).find(metricCollectionCode,metricRelativeCode)), Arrays.asList(identifiable));
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<MetricValue> findByCollectionCodesByCollectionIdentifiablesByMetricIdentifiables(Collection<String> metricCollectionCodes,Collection<? extends AbstractIdentifiable> metricCollectionIdentifiables
			,Collection<? extends AbstractIdentifiable> metricValueIdentifiables){
		Collection<MetricValue> values = new ArrayList<>();
		for(AbstractIdentifiable identifiable : metricValueIdentifiables){
			Collection<MetricCollection> metricCollections = inject(MetricCollectionBusiness.class).findByTypesByIdentifiables(inject(MetricCollectionTypeDao.class)
					.read(metricCollectionCodes), metricCollectionIdentifiables);
			for(MetricCollection metricCollection : metricCollections){
				Collection<Metric> metrics = inject(MetricBusiness.class).findByCollection(metricCollection);
				values.addAll(inject(MetricValueBusiness.class).findByMetricsByIdentifiables(metrics,Arrays.asList(identifiable)));	
			}
		}
		return values;
	}
	
	@Override
	public void setValueRandomly(Collection<MetricValue> metricValues) {
		Collection<Value> values = new ArrayList<>();
		for(MetricValue metricValue : metricValues)
			values.add(metricValue.getValue());
		inject(ValueBusiness.class).setRandomly(values);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<MetricValue> findByMetrics(Collection<Metric> metrics) {
		return dao.readByMetrics(metrics);
	}
	
	@Override
	public void derive(Collection<MetricValue> metricValues, Derive derive) {
		Collection<Value> values = new ArrayList<>();
		for(MetricValue metricValue : metricValues)
			values.add(metricValue.getValue());
		inject(ValueBusiness.class).derive(values, derive);
	}

}
