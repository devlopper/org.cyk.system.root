package org.cyk.system.root.service.impl.integration;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.event.ScheduleBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.business.impl.event.EventBusinessImpl;
import org.cyk.system.root.business.impl.file.FileBusinessImpl;
import org.cyk.system.root.business.impl.geography.LocalityBusinessImpl;
import org.cyk.system.root.business.impl.message.MailBusinessImpl;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.Period;
import org.cyk.system.root.model.event.Schedule;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactManager;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.persistence.api.event.EventDao;
import org.cyk.system.root.persistence.api.file.FileDao;
import org.cyk.system.root.persistence.api.geography.LocalityDao;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetDao;
import org.cyk.system.root.persistence.impl.event.EventDaoImpl;
import org.cyk.system.root.persistence.impl.file.FileDaoImpl;
import org.cyk.system.root.persistence.impl.geography.LocalityDaoImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class EventIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return deployment(new Class<?>[]{}).getArchive().
                addPackage(File.class.getPackage()).addPackage(FileDao.class.getPackage()).addPackage(FileDaoImpl.class.getPackage()).
                addPackage(FileBusiness.class.getPackage()).addPackage(FileBusinessImpl.class.getPackage()).
                addPackage(MailBusiness.class.getPackage()).addPackage(MailBusinessImpl.class.getPackage()).
                addPackage(Event.class.getPackage()).
                addPackage(EventDao.class.getPackage()).addPackage(EventDaoImpl.class.getPackage()).
                addPackage(EventBusiness.class.getPackage()).addPackage(EventBusinessImpl.class.getPackage()).
                addPackage(ContactManager.class.getPackage()).
                addPackage(LocalityDao.class.getPackage()).addPackage(LocalityDaoImpl.class.getPackage()).addPackage(LocalityBusiness.class.getPackage()).addPackage(LocalityBusinessImpl.class.getPackage()).
                addPackage(NestedSet.class.getPackage()).addPackage(NestedSetDao.class.getPackage());
    }
    
    @Inject private EventBusiness eventBusiness;
    @Inject private ScheduleBusiness scheduleBusiness;
    private Date now = new Date(),oneHourLater,oneHourPast;
    
    @Override
    protected void populate() {
        oneHourLater = DateUtils.addHours(now, 1);
        oneHourPast = DateUtils.addHours(now, -1);
        create(event(oneHourLater, DateUtils.addMinutes(oneHourLater, 5)));
        create(event(DateUtils.addMinutes(oneHourLater, 10), DateUtils.addMinutes(oneHourLater, 13)));
        create(event(now, DateUtils.addMinutes(now, 3)));
    
        create(event(oneHourPast, DateUtils.addMinutes(oneHourPast, 7)));
        
        create(schedule((byte)2, (byte)10, (byte)15, (byte)12, (byte)45, DateUtils.addDays(new Date(), -1), DateUtils.addDays(new Date(), -1)));
    }
    
    private Event event(Date fromDate,Date toDate){
        Event event = new Event();
        event.setPeriod(new Period(fromDate, toDate));
        return event;
    }
    
    private Schedule schedule(Byte day,Byte startHour,Byte startMinute,Byte endHour,Byte endMinute,Date fromDate,Date toDate){
        Schedule schedule = new Schedule(null, null, null, day, startHour, startMinute, endHour, endMinute, new Period(fromDate, toDate));
        return schedule;
    }
    
    @Override
    protected void _execute_() {
        super._execute_();
        System.out.println(eventBusiness.find().all());
        System.out.println(scheduleBusiness.find().all());
    }

    @Override
    protected void finds() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void businesses() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void create() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void delete() {
        // TODO Auto-generated method stub
        
    }

    

    @Override
    protected void read() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void update() {
        // TODO Auto-generated method stub
        
    }

}
