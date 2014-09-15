package org.cyk.system.root.persistence.impl.integration;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.Period;
import org.cyk.system.root.persistence.api.event.EventDao;
import org.cyk.utility.common.computation.Function;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class EventPersistenceIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;

	@Deployment
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	} 
	
	@Inject private EventDao eventDao;
	private Event event;
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
	    event.setContactCollection(null);
	    event.setPeriod(new Period(fromDate, toDate));
	    return event;
	}
					
	// CRUD 
	
	@Override
	protected void create() {
	    eventDao.create(event = event(now, DateUtils.addMinutes(now, 3)));
	    Assert.assertNotNull(event.getIdentifier());
	}

	@Override
	protected void read() {
	    Assert.assertNotNull(eventDao.read(event.getIdentifier()));
	}

	@Override
	protected void update() {
	    Date newDate = new Date();
	    event.getPeriod().setFromDate(newDate);
	    eventDao.update(event);
	    Assert.assertEquals(newDate, eventDao.read(event.getIdentifier()).getPeriod().getFromDate());
	}

	@Override
	protected void delete() {
	    eventDao.delete(event);
	    Assert.assertNull(eventDao.read(event.getIdentifier()));
	}
	
	@Override
	protected void queries() {
	    /*for(Event event : eventDao.select().all()){
	        debug(event.getSchedule().getPeriod());
	    }*/
		Assert.assertEquals(4,eventDao.select().all().size());
		Assert.assertEquals(4,eventDao.select(Function.COUNT).oneLong().intValue());
		
		Assert.assertEquals(4,eventDao.readAll().size());
		Assert.assertEquals(4,eventDao.countAll().intValue());
		
		Assert.assertEquals(2,eventDao.readWhereFromDateGreaterThanByDate(now).size());
		Assert.assertEquals(2,eventDao.countWhereFromDateGreaterThanByDate(now).intValue());
		
		Assert.assertEquals(4, eventDao.countWhereFromDateBetweenByStartDateByEndDate(DateUtils.addHours(now, -2), DateUtils.addHours(now, 2)).intValue());
		Assert.assertEquals(3, eventDao.countWhereFromDateBetweenByStartDateByEndDate(DateUtils.addHours(now, 0), DateUtils.addHours(now, 2)).intValue());
		Assert.assertEquals(1, eventDao.countWhereFromDateBetweenByStartDateByEndDate(DateUtils.addMinutes(oneHourLater, 6), DateUtils.addHours(now, 2)).intValue());
	}
	
	
	
	
}
