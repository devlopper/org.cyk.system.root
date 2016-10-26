package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;

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
	public Metric update(Metric metric) {
		if(metric.getValueIntervalCollection()!=null)
			if(metric.getValueIntervalCollection().getIdentifier()==null){
				inject(IntervalCollectionBusiness.class).create(metric.getValueIntervalCollection());
			}else
				inject(IntervalCollectionBusiness.class).update(metric.getValueIntervalCollection());
		super.update(metric);
		
		return metric;
	}
	
	@Override
	public Metric delete(Metric identifiable) {
		System.out.println("MetricBusinessImpl.delete()");
		return super.delete(identifiable);
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
