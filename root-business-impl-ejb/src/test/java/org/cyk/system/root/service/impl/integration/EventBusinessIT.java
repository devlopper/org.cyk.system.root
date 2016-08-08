package org.cyk.system.root.service.impl.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.RootRandomDataProvider;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.event.EventReminder;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.time.Period;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.joda.time.DateTime;
import org.junit.Assert;

public class EventBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    } 
    
    @Inject private EventBusiness eventBusiness;
    @Inject private PersonBusiness personBusiness;
    
    private Date now = new Date(),oneHourLater,oneHourPast;
    private Event event1,event2,event3,event4;
    
    @Override
    protected void populate() {
        
    }
    
    private Event event(Date fromDate,Date toDate){
        Event event = new Event();
        event.setExistencePeriod(new Period(fromDate, toDate));
        event.setContactCollection(null);
        return event;
    }
    
    private Person createPerson(String code,String firstName,String lastName,Integer day,Integer month,Integer year){
    	Person person = new Person();
    	person.setCode(code);
    	person.setName(firstName);
    	person.setLastnames(lastName);
    	person.setBirthDate(new DateTime(year, month, day, 0, 0).toDate());
    	person. setContactCollection(null);
    	return personBusiness.create(person);
    }

    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
    	installApplication();
    	
    	oneHourLater = DateUtils.addHours(now, 1);
        oneHourPast = DateUtils.addHours(now, -1);
        create(event1 = event(oneHourLater, DateUtils.addMinutes(oneHourLater, 5)));
        create(event2 =event(DateUtils.addMinutes(oneHourLater, 10), DateUtils.addMinutes(oneHourLater, 13)));
        create(event3 =event(now, DateUtils.addMinutes(now, 3)));
    
        create(event4 =event(oneHourPast, DateUtils.addMinutes(oneHourPast, 7)));
        
        Person p1 =  createPerson("1", "P1", "LN1", 10,1,2000);
        Person p2 =  createPerson("2", "komenan", "Yao", 19,8,1983);
        Person p3 =  createPerson("3", "Kouadio", "Odette", 24,3,1960);
        Person p4 =  createPerson("4", "Aya", "Pascale", 28,3,1986);
        createPerson("5", "Gnangnan", "Sandrine", 14,5,1989);
        createPerson("6", "Jramoh", "Pierre", 10,9,2000);
    	
    	Assert.assertEquals(5 * DateUtils.MILLIS_PER_MINUTE, eventBusiness.findDuration(Arrays.asList(event1)).longValue());
        Assert.assertEquals(8 * DateUtils.MILLIS_PER_MINUTE, eventBusiness.findDuration(Arrays.asList(event1,event2)).longValue());
        Assert.assertEquals(11 * DateUtils.MILLIS_PER_MINUTE, eventBusiness.findDuration(Arrays.asList(event1,event2,event3)).longValue());
        Assert.assertEquals(18 * DateUtils.MILLIS_PER_MINUTE, eventBusiness.findDuration(Arrays.asList(event1,event2,event3,event4)).longValue());
        
        //TimeDivisionType year = RootBusinessLayer.getInstance().getTimeDivisionTypeYear();
        
        //System.out.println(eventBusiness.findByTimeDivisionTypeByPeriod(year,new Period(new DateTime(1960, 1, 1, 0, 0).toDate(),new DateTime(1960, 12, 31, 0, 0).toDate())));
        /*
        System.out.println(eventBusiness.findAnniversariesByPeriod(new Period(new DateTime(1983, 1, 1, 0, 0).toDate(),new DateTime(1983, 12, 31, 0, 0).toDate())));
        System.out.println(eventBusiness.findAnniversariesByPeriod(new Period(new DateTime(1986, 1, 1, 0, 0).toDate(),new DateTime(1986, 12, 31, 0, 0).toDate())));
        System.out.println(eventBusiness.findAnniversariesByPeriod(new Period(new DateTime(1989, 1, 1, 0, 0).toDate(),new DateTime(1989, 12, 31, 0, 0).toDate())));
        System.out.println(eventBusiness.findAnniversariesByPeriod(new Period(new DateTime(2000, 1, 1, 0, 0).toDate(),new DateTime(2000, 12, 31, 0, 0).toDate())));
        System.out.println(eventBusiness.findAnniversariesByPeriod(new Period(new DateTime(2010, 1, 1, 0, 0).toDate(),new DateTime(2010, 12, 31, 0, 0).toDate())));
        */
        
        Event event = event(oneHourPast, DateUtils.addMinutes(oneHourPast, 7));
        
        event.setContactCollection(new ContactCollection());
        event.getContactCollection().setPhoneNumbers(new ArrayList<PhoneNumber>());
        event.getContactCollection().getPhoneNumbers().add(RootRandomDataProvider.getInstance()
        		.phoneNumber(event.getContactCollection(),RootBusinessLayer.getInstance().getCountryCoteDivoire(),
        				RootBusinessLayer.getInstance().getLandPhoneNumberType()));
        
        event.getEventParties().add(new EventParty(p1));
        event.getEventParties().add(new EventParty(p2));
        event.getEventParties().add(new EventParty(p3));
        event.getEventParties().add(new EventParty(p4));
        Collection<EventReminder> reminders = new ArrayList<>();
        EventReminder eventReminder = new EventReminder();
        reminders.add(eventReminder);
        eventReminder.getExistencePeriod().setFromDate(new Date());
        eventReminder.getExistencePeriod().setToDate(new Date());
        eventBusiness.create(event,reminders);
        eventBusiness.delete(event);
        
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
