package org.cyk.system.root.service.impl.integration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.time.Period;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class EventPeriodBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    } 
    
    @Inject private EventBusiness eventBusiness;
    @Inject private PersonBusiness personBusiness;
    private Date now = new Date();
    private Person person;
    
    @Override
    protected void populate() {
        person = new Person();
        person.setContactCollection(null);
        person.setName("Name");
        person.setCode("code");
        personBusiness.create(person);
        
    	event(DateUtils.addDays(now, -10),DateUtils.addDays(now, -9));
    	event(DateUtils.addDays(now, -8),DateUtils.addDays(now, -7));
    	event(DateUtils.addDays(now, -6),DateUtils.addDays(now, -5));
        
    	event(DateUtils.addHours(now, -3),DateUtils.addHours(now, 1));
    	event(DateUtils.addHours(now, -2),DateUtils.addHours(now, 2));
        
        event(DateUtils.addDays(now, 1),DateUtils.addDays(now, 2));
    	event(DateUtils.addDays(now, 3),DateUtils.addDays(now, 4));
    	event(DateUtils.addDays(now, 5),DateUtils.addDays(now, 6));
    	event(DateUtils.addDays(now, 7),DateUtils.addDays(now, 8));
    }
    
    private Event event(Date  fromDate,Date  toDate){
        Event event = new Event();
        event.setExistencePeriod(new Period(fromDate, toDate));
        event.setContactCollection(null);
        eventBusiness.create(event);
        create(new EventParty(person));
        return event;
    }

    @Override
    protected void finds() {}

    @Override
    protected void businesses() {
    	Collection<Party> parties = new ArrayList<>();
    	parties.add(person);
    	
    	Assert.assertEquals(3l, eventBusiness.countPasts().longValue());
    	Assert.assertEquals(3l, eventBusiness.countPasts(parties).longValue());
    	
        Assert.assertEquals(2l, eventBusiness.countCurrents().longValue());
        Assert.assertEquals(2l, eventBusiness.countCurrents(parties).longValue());
        
        Assert.assertEquals(4l, eventBusiness.countOnComings().longValue());
        Assert.assertEquals(4l, eventBusiness.countOnComings(parties).longValue());
        
    }

    @Override protected void create() {}
    @Override protected void delete() {}
    @Override protected void read() {}
    @Override protected void update() {}

}
