package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

public class MetricValueBusinessImpl extends AbstractTypedBusinessService<MetricValue, MetricValueDao> implements MetricValueBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MetricValueBusinessImpl(MetricValueDao dao) {
		super(dao); 
	}

	@Override
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
				IntervalCollection intervalCollection = metricCollection.getValueIntervalCollection();
				inject(IntervalCollectionBusiness.class).load(intervalCollection);
				List<Interval> intervals = new ArrayList<>(inject(IntervalBusiness.class).findByCollection(intervalCollection));
				Collection<MetricValue> metricValues = inject(MetricValueBusiness.class).findByMetricsByIdentifiables(metrics,Arrays.asList(identifiable));
				Collection<AbstractIdentifiable> c = new ArrayList<>();
				for(MetricValue metricValue : metricValues){
					if(MetricValueType.NUMBER.equals(metricCollection.getValueType())){
						metricValue.getNumberValue().setUser(new BigDecimal(RandomDataProvider.getInstance().randomInt(intervalCollection.getLowestValue().intValue(), intervalCollection.getHighestValue().intValue())));
					}else
						if(MetricValueInputted.VALUE_INTERVAL_CODE.equals(metricValue.getMetric().getCollection().getValueInputted()))
							metricValue.setStringValue( ((Interval)RandomDataProvider.getInstance().randomFromList(intervals)).getCode() );
						else
							metricValue.setStringValue(RandomStringUtils.randomAlphabetic(1));
					c.add(metricValue);
				}
				inject(GenericBusiness.class).update(c);
			}
		}
	}

}
