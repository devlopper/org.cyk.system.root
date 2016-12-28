package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.mathematics.MetricBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;
import org.cyk.system.root.persistence.api.value.ValuePropertiesDao;

public class MetricBusinessImpl extends AbstractCollectionItemBusinessImpl<Metric,MetricDao,MetricCollection> implements MetricBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public MetricBusinessImpl(MetricDao dao) {
		super(dao); 
	} 
	
	@Override
	protected Collection<? extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<?>> getListeners() {
		return Listener.COLLECTION;
	}
	
	@Override
	protected void beforeCreate(Metric metric) {
		super.beforeCreate(metric);
		createIfNotIdentified(metric.getValueProperties());
	}
	
	@Override
	protected void beforeUpdate(Metric metric) {
		super.beforeUpdate(metric);
		createIfNotIdentified(metric.getValueProperties());
	}
	
	@Override
	public Metric instanciateOne(String[] values) {
		Metric metric = super.instanciateOne(values);
		Integer index = 15;
		String value;
		if(values.length > 15){
			if(StringUtils.isNotBlank(value = values[index++]))
				metric.setValueProperties(inject(ValuePropertiesDao.class).read(value));
		}
		return metric;
	}
		
	/**/
	
	public static interface Listener extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<Metric> {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		/**/
		
		public static class Adapter extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.Adapter.Default<Metric> implements Listener,Serializable{
			private static final long serialVersionUID = 1L;
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable{
				private static final long serialVersionUID = 1L;
				
				/**/
			
				
				public static class EnterpriseResourcePlanning extends Listener.Adapter.Default implements Serializable{
					private static final long serialVersionUID = 1L;
					
					/**/
					
					
				}
			}
		}
	}
	
}
