package org.cyk.system.root.business.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;

public class CollectionBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
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
    	
    	assertThat("Is null", metricCollectionBusiness.findByGlobalIdentifierCode("MC0")==null);
    	assertThat("Is not loaded", metricCollectionBusiness.findByGlobalIdentifierCode("MC2").getCollection().isEmpty());
    	assertThat("Is loaded", metricCollectionBusiness.load("MC2").getCollection().size()==3);
    	assertThat("Is loaded and empty", metricCollectionBusiness.load("MC1").getCollection().size()==0);
    	
    	assertThat("Has no interval collection", metricBusiness.findByGlobalIdentifierCode("1").getValueIntervalCollection()==null);
    	
    	//metric1.setValueIntervalCollection(new IntervalCollection("IC1"));
    	metric1.getValueIntervalCollection().addItem("1", "I1","1","2"); 
    	metric1.getValueIntervalCollection().addItem("2", "I2","3","4");
    	metric1.getValueIntervalCollection().addItem("3", "I3","5","6");
    	metric1.getValueIntervalCollection().addItem("4", "I4","7","8");
    	metric1 = metricBusiness.update(metric1);
    	
    	//intervalCollectionBusiness.create(metric1.getValueIntervalCollection());
    	
    	assertThat("Is null", metricCollectionBusiness.findByGlobalIdentifierCode("MC0")==null);
    	assertThat("Interval collection IC1 exists", intervalCollectionBusiness.findByGlobalIdentifierCode("IC1")!=null);
    	assertThat("Has interval collection", metricBusiness.findByGlobalIdentifierCode("1").getValueIntervalCollection()!=null);
    	
    	/*metricCollection = metricCollectionBusiness.instanciateOne("MC1", "NAME",null
			, new String[]{"MV1","MV2","MV3"}
			, new String[][]{ {"E", "Excellent", "1", "1"},{"G", "Good", "2", "2"},{"S", "Satisfactory", "3", "3"},{"N", "Needs Improvement", "4", "4"}
	    	,{"H", "Has no regard", "5", "5"} });*/
		//assertEquals(metricCollection, new ObjectFieldValues(MetricCollection.class)
		//	.set(MetricCollection.FIELD_CODE, "MC1"));
		
    }


}
