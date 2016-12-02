package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.MetricValueInputted;
import org.cyk.system.root.model.mathematics.MetricValueType;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionTypeDao;
import org.cyk.system.root.persistence.api.mathematics.MetricValueDao;
import org.cyk.utility.common.generator.RandomDataProvider;

@Stateless
public class MetricValueBusinessImpl extends AbstractTypedBusinessService<MetricValue, MetricValueDao> implements MetricValueBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MetricValueBusinessImpl(MetricValueDao dao) {
		super(dao); 
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
		for(AbstractIdentifiable identifiable : metricValueIdentifiables){
			Collection<MetricCollection> metricCollections = inject(MetricCollectionBusiness.class).findByTypesByIdentifiables(inject(MetricCollectionTypeDao.class)
					.read(metricCollectionCodes), metricCollectionIdentifiables);
			for(MetricCollection metricCollection : metricCollections){
				Collection<Metric> metrics = inject(MetricBusiness.class).findByCollection(metricCollection);
				Collection<MetricValue> metricValues = inject(MetricValueBusiness.class).findByMetricsByIdentifiables(metrics,Arrays.asList(identifiable));
				IntervalCollection intervalCollection = null;
				List<Interval> intervals = null;
				if(metricCollection.getValueIntervalCollection()!=null){
					intervalCollection = metricCollection.getValueIntervalCollection();
					inject(IntervalCollectionBusiness.class).load(intervalCollection);
					intervals = new ArrayList<>(inject(IntervalBusiness.class).findByCollection(intervalCollection));
				}
					
				for(MetricValue metricValue : metricValues){
					if(MetricValueType.BOOLEAN.equals(metricCollection.getValueType())){
						metricValue.getBooleanValue().set(RandomDataProvider.getInstance().randomBoolean());
					}else if(MetricValueType.NUMBER.equals(metricCollection.getValueType())){
						if(metricCollection.getValueIntervalCollection()!=null)
							metricValue.getNumberValue().setUser(new BigDecimal(RandomDataProvider.getInstance().randomInt(intervalCollection.getLowestValue().intValue(), intervalCollection.getHighestValue().intValue())));
					}else if(MetricValueType.STRING.equals(metricCollection.getValueType())){
						if(MetricValueInputted.VALUE_INTERVAL_CODE.equals(metricValue.getMetric().getCollection().getValueInputted()))
							if(metricCollection.getValueIntervalCollection()!=null)
								metricValue.getStringValue().set( ((Interval)RandomDataProvider.getInstance().randomFromList(intervals)).getCode() );
							else
									;
						else
							metricValue.getStringValue().set( RandomStringUtils.randomAlphabetic(1));
					}
				}	
				inject(GenericBusiness.class).update(commonUtils.castCollection(metricValues, AbstractIdentifiable.class));
				//logTrace("Random metrics values : {}", metricValues);
				
			}
		}
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MetricValueType getValueType(MetricValue metricValue) {
		return inject(MetricBusiness.class).getValueType(metricValue.getMetric());
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void setValue(MetricValue metricValue, Object value) {
		switch(getValueType(metricValue)){
		case BOOLEAN:
			metricValue.getBooleanValue().set(value == null ? null :  (Boolean) value);
			break;
		case NUMBER:
			metricValue.getNumberValue().set(value == null ? null : new BigDecimal(value.toString()));
			break;
		case STRING:
			metricValue.getStringValue().set(value == null ? null : value.toString());
			break;
		}
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Object getValue(MetricValue metricValue) {
		switch(getValueType(metricValue)){
		case BOOLEAN:
			return metricValue.getBooleanValue().get();
		case NUMBER:
			return metricValue.getNumberValue().get();
		case STRING:
			return metricValue.getStringValue().get();
		}
		return null;
	}

}
