package org.cyk.system.root.business.impl.integration;

import java.util.Collection;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;
import org.cyk.system.root.persistence.api.mathematics.MetricValueDao;
import org.cyk.system.root.persistence.api.value.ValueDao;
import org.cyk.utility.common.CommonUtils;

public class ValueBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void populate() {}
    
    @Override
    protected void businesses() {
    	create(inject(MetricCollectionBusiness.class).instanciateOne("MC01","My metric collection", new String[]{"B1","B2","B3"}));
		for(Metric metric : inject(MetricDao.class).readAll())
			create(new MetricValue(metric, new Value()));
		System.out.println(inject(MetricValueDao.class).readAll());
		System.out.println(inject(ValueDao.class).readAll());
		Collection<Value> values = inject(ValueDao.class).readAll();
		
		System.out.println("B : "+values);
		inject(ValueBusiness.class).setManyRandomly(values);
		inject(GenericBusiness.class).update(CommonUtils.getInstance().castCollection(values, AbstractIdentifiable.class));
		System.out.println("A : "+values);
		
		for(Value value : inject(ValueDao.class).readAll()){
			debug(value);
		}
    }


}
