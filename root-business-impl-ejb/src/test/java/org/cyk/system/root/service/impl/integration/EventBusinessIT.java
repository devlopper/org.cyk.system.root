package org.cyk.system.root.service.impl.integration;

import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.Period;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class EventBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    } 
    
    @Inject private EventBusiness eventBusiness;
    private Date now = new Date(),oneHourLater,oneHourPast;
    
    @Override
    protected void populate() {
        oneHourLater = DateUtils.addHours(now, 1);
        oneHourPast = DateUtils.addHours(now, -1);
        create(event(oneHourLater, DateUtils.addMinutes(oneHourLater, 5)));
        create(event(DateUtils.addMinutes(oneHourLater, 10), DateUtils.addMinutes(oneHourLater, 13)));
        create(event(now, DateUtils.addMinutes(now, 3)));
    
        create(event(oneHourPast, DateUtils.addMinutes(oneHourPast, 7)));
        
        
    }
    
    private Event event(Date fromDate,Date toDate){
        Event event = new Event();
        event.setPeriod(new Period(fromDate, toDate));
        return event;
    }

    
    @Override
    protected void _execute_() {
        super._execute_();
        System.out.println(eventBusiness.find().all());
    }

    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
        
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
