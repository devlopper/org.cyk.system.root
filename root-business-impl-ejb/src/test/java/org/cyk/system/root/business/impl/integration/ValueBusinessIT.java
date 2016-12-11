package org.cyk.system.root.business.impl.integration;

import java.math.BigDecimal;
import java.util.Collection;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.api.value.MeasureBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;
import org.cyk.system.root.persistence.api.mathematics.MetricValueDao;
import org.cyk.system.root.persistence.api.value.MeasureDao;
import org.cyk.system.root.persistence.api.value.ValueDao;
import org.cyk.utility.common.CommonUtils;
import org.joda.time.DateTimeConstants;
import org.junit.Test;

public class ValueBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void businesses() {
    	create(inject(MetricCollectionBusiness.class).instanciateOne("MC01","My metric collection", new String[]{"B1","B2","B3"}));
		for(Metric metric : inject(MetricDao.class).readAll())
			create(new MetricValue(metric, new Value()));
		System.out.println(inject(MetricValueDao.class).readAll());
		System.out.println(inject(ValueDao.class).readAll());
		Collection<Value> values = inject(ValueDao.class).readAll();
		
		System.out.println("B : "+values);
		inject(ValueBusiness.class).setRandomly(values);
		inject(GenericBusiness.class).update(CommonUtils.getInstance().castCollection(values, AbstractIdentifiable.class));
		System.out.println("A : "+values);
		
		/*for(Value value : inject(ValueDao.class).readAll()){
			debug(value);
		}*/
    }

    @Test
    public void measureComputeMultiple(){
    	assertEquals(new BigDecimal(DateTimeConstants.MILLIS_PER_DAY*2),inject(MeasureBusiness.class).computeMultiple(inject(MeasureDao.class).read(RootConstant.Code.Measure.TIME_DAY)
    			, new BigDecimal("2")));
    }
    
    @Test
    public void measureComputeQuotient(){
    	assertEquals(new BigDecimal(2),inject(MeasureBusiness.class).computeQuotient(inject(MeasureDao.class).read(RootConstant.Code.Measure.TIME_DAY)
    			, new BigDecimal(DateTimeConstants.MILLIS_PER_DAY*2)));
    }
}
