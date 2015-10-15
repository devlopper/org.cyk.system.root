package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class CollectionBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment(); 
    } 
    
    @Inject private MetricCollectionBusiness metricCollectionBusiness;
    @Inject private MetricBusiness metricBusiness;
    @Inject private IntervalCollectionBusiness intervalCollectionBusiness;
    
    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
    	installApplication();
    	
    	metricCollectionBusiness.create(new MetricCollection("MC1","MC1"));
    	
    	MetricCollection metricCollection = new MetricCollection("MC2","MC2");
    	Metric metric1 = metricCollection.addItem("1", "A");
    	/*Metric metric2 = */metricCollection.addItem("2", "B");
    	/*Metric metric3 = */metricCollection.addItem("3", "C");
    	metricCollectionBusiness.create(metricCollection);
    	
    	assertThat("Is null", metricCollectionBusiness.find("MC0")==null);
    	assertThat("Is not loaded", metricCollectionBusiness.find("MC2").getCollection().isEmpty());
    	assertThat("Is loaded", metricCollectionBusiness.load("MC2").getCollection().size()==3);
    	assertThat("Is loaded and empty", metricCollectionBusiness.load("MC1").getCollection().size()==0);
    	
    	assertThat("Has no interval collection", metricBusiness.find("1").getValueIntervalCollection()==null);
    	
    	metric1.setValueIntervalCollection(new IntervalCollection("IC1"));
    	metric1.getValueIntervalCollection().addItem("1", "I1","1","2");
    	metric1.getValueIntervalCollection().addItem("2", "I2","3","4");
    	metric1.getValueIntervalCollection().addItem("3", "I3","5","6");
    	metric1.getValueIntervalCollection().addItem("4", "I4","7","8");
    	metric1 = metricBusiness.update(metric1);
    	
    	//intervalCollectionBusiness.create(metric1.getValueIntervalCollection());
    	
    	assertThat("Is null", metricCollectionBusiness.find("MC0")==null);
    	assertThat("Interval collection IC1 exists", intervalCollectionBusiness.find("IC1")!=null);
    	assertThat("Has interval collection", metricBusiness.find("1").getValueIntervalCollection()!=null);
    }

    @Override protected void create() {}
    @Override protected void delete() {}
    @Override protected void read() {}
    @Override protected void update() {}

}
