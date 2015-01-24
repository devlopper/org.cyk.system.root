package org.cyk.system.root.service.impl.integration;

import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.time.Period;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class EventBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    } 
    
    @Inject private EventBusiness eventBusiness;
    private Date now = new Date(),oneHourLater,oneHourPast;
    private Event event1,event2,event3,event4;
    
    @Override
    protected void populate() {
        oneHourLater = DateUtils.addHours(now, 1);
        oneHourPast = DateUtils.addHours(now, -1);
        create(event1 = event(oneHourLater, DateUtils.addMinutes(oneHourLater, 5)));
        create(event2 =event(DateUtils.addMinutes(oneHourLater, 10), DateUtils.addMinutes(oneHourLater, 13)));
        create(event3 =event(now, DateUtils.addMinutes(now, 3)));
    
        create(event4 =event(oneHourPast, DateUtils.addMinutes(oneHourPast, 7)));
        
        
    }
    
    private Event event(Date fromDate,Date toDate){
        Event event = new Event();
        event.setPeriod(new Period(fromDate, toDate));
        event.setContactCollection(null);
        return event;
    }

    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
    	Assert.assertEquals(5 * DateUtils.MILLIS_PER_MINUTE, eventBusiness.findDuration(Arrays.asList(event1)).longValue());
        Assert.assertEquals(8 * DateUtils.MILLIS_PER_MINUTE, eventBusiness.findDuration(Arrays.asList(event1,event2)).longValue());
        Assert.assertEquals(11 * DateUtils.MILLIS_PER_MINUTE, eventBusiness.findDuration(Arrays.asList(event1,event2,event3)).longValue());
        Assert.assertEquals(18 * DateUtils.MILLIS_PER_MINUTE, eventBusiness.findDuration(Arrays.asList(event1,event2,event3,event4)).longValue());
    }

    @Override
    protected void create() {
        eventBusiness.create(Arrays.asList(event(now, DateUtils.addMinutes(now, 3))));
    }

    @Override
    protected void delete() {
        
    }

    

    @Override
    protected void read() {
        
    }

    @Override
    protected void update() {
        
    }

}
