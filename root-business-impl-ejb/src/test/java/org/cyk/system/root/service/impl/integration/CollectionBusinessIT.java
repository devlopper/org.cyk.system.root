package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionItemBusiness;
import org.cyk.system.root.business.impl.RootRandomDataProvider;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class CollectionBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment(); 
    } 
    
    @Inject private MetricCollectionBusiness metricCollectionBusiness;
    @Inject private MetricCollectionItemBusiness metricCollectionItemBusiness;
    
    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
    	installApplication();
    	
    	metricCollectionBusiness.create(new MetricCollection("MC1","MC1"));
    	
    	MetricCollection metricCollection = new MetricCollection("MC2","MC2");
    	metricCollection.addItem("1", "A");
    	metricCollection.addItem("2", "B");
    	metricCollection.addItem("3", "C");
    	metricCollectionBusiness.create(metricCollection);
    	
    	assertThat("Is null", metricCollectionBusiness.find("MC0")==null);
    	assertThat("Is not loaded", metricCollectionBusiness.find("MC2").getCollection().isEmpty());
    	assertThat("Is loaded", metricCollectionBusiness.load("MC2").getCollection().size()==3);
    	assertThat("Is loaded and empty", metricCollectionBusiness.load("MC1").getCollection().size()==0);
    }

    @Override protected void create() {}
    @Override protected void delete() {}
    @Override protected void read() {}
    @Override protected void update() {}

}
